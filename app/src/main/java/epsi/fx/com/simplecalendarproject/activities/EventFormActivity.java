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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import epsi.fx.com.simplecalendarproject.Common;
import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.Participant;
import epsi.fx.com.simplecalendarproject.beans.dao.EventDao;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventFormActivity extends AppCompatActivity {

    public static final String TAG = EventFormActivity.class.getName();

    public static final int REQUEST_CODE = 1;
    private EventDao mEventDao;
    private ApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init view
        setContentView(R.layout.event_form);

        // Init field
        mEventDao = new EventDao(this);

        mApiClient = new ApiClient(this);
    }

    public void onClickOk(View view) {
        Event event = new Event();

        TextView title = (TextView) findViewById(R.id.event_form_title);
        TextView desc = (TextView) findViewById(R.id.event_form_desc);
        TextView dateBegin = (TextView) findViewById(R.id.event_form_date_begin);
        TextView dateEnd = (TextView) findViewById(R.id.event_form_date_end);

        event.setTitle(title.getText().toString());
        event.setDescription(desc.getText().toString());
        event.setBegin(DateTime.now());
        event.setEnd(DateTime.now());
//        event.setBegin(dateBegin.getText().toString());
//        event.setEnd(dateEnd.getText().toString());

        SharedPreferences prefs = getSharedPreferences(Common.PREFS_SCOPE, Context.MODE_PRIVATE);

        String uuid = prefs.getString(Common.USER_ID_KEY, "0049beeb-6412-4831-b08e-bb86609fbefe");
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

    public void onClickCancel(View view) {
        Intent intent = new Intent();
        setResult(EventFormActivity.RESULT_CANCELED, intent);
        EventFormActivity.this.finish();
    }

    public ApiClient getApiClient() {
        return mApiClient;
    }
}
