package epsi.fx.com.simplecalendarproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.UUID;

import epsi.fx.com.simplecalendarproject.AppConfig;
import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.Participant;
import epsi.fx.com.simplecalendarproject.beans.dao.EventDao;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Handle event-form related action
 */
public class EventFormActivity extends AppCompatActivity {

    public static final String TAG = EventFormActivity.class.getName();

    public static final int REQUEST_CODE = 1;
    private EventDao mEventDao;
    private ApiClient mApiClient;
    private TextView mTitle;
    private TextView mDesc;
    private TextView mDateBegin;
    private TextView mDateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init view
        setContentView(R.layout.activity_event_form);

        // Init field
        mEventDao = new EventDao(this);

        mApiClient = new ApiClient(this);

        setUpViews();
    }

    /**
     * Init view fields
     */
    private void setUpViews() {
        mTitle = (TextView) findViewById(R.id.activity_event_form_title);
        mDesc = (TextView) findViewById(R.id.activity_event_form_desc);
        mDateBegin = (TextView) findViewById(R.id.activity_event_form_date_begin);
        mDateEnd = (TextView) findViewById(R.id.activity_event_form_date_end);
    }

    /**
     * Listener to OK button
     *
     * @param view
     */
    public void onClickOk(View view) {

        Event event = new Event();

        event.setTitle(mTitle.getText().toString());
        event.setDescription(mDesc.getText().toString());

        event.setBegin(DateTime.now());
        event.setEnd(DateTime.now().plusHours(2));

        SharedPreferences prefs = getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE);

        // It should have been given by the API, I guess
        String uuid = prefs.getString(AppConfig.USER_ID_KEY, "74fcf467-dc27-4942-8b56-8609ba3c603a");
        event.setAuthor(UUID.fromString(uuid));
        event.setParticipants(
                Collections.singletonList(new Participant()
                        .withId(UUID.fromString(uuid))
                        .withStatus(Participant.Status.PRESENT))
        );

        mApiClient.insertEvent(event).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                Log.d(TAG, String.format("responseCode is %d", response.code()));
                if (response.isSuccess()) {
                    Log.d(TAG, "Creation success");
//                    mEventDao.insertEvent(event);
                    Intent intent = new Intent();
                    setResult(EventFormActivity.RESULT_OK, intent);
                    EventFormActivity.this.finish();
                } else {
                    Log.d(TAG, "Creation failed");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, String.format("FAILURE: %s", t.getMessage()));
            }
        });

    }

    /**
     * Go back to last activity
     *
     * @param view
     */
    public void onClickCancel(View view) {
        Intent intent = new Intent();
        setResult(EventFormActivity.RESULT_CANCELED, intent);
        EventFormActivity.this.finish();
    }
}
