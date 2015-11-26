package epsi.fx.com.simplecalendarproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.adapters.ParticipantItemAdapter;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static epsi.fx.com.simplecalendarproject.adapters.EventItemAdapter.findUserName;

public class EventItemActivity extends AppCompatActivity {

    private final String TAG = EventItemActivity.class.getName();
    private Event mEvent;
    private ApiClient mApiClient;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDateInfo;
    private ListView mParticipantsList;
    private TextView mDescription;

    private DateTimeFormatter mDateFormatter = DateTimeFormat.shortDateTime().withLocale(Locale.FRANCE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_item);

        Intent intent = getIntent();
        final String eventId = intent.getStringExtra(EventListActivity.EXTRA_EVENT_ID);

        if (checkEventId(eventId)) {
            Log.e(TAG, "eventId is null whereas it should not be");
            finish();
        }

        mApiClient = new ApiClient(this);

        mTitle = (TextView) findViewById(R.id.activity_event_item_title);
        mAuthor = (TextView) findViewById(R.id.activity_event_item_author);
        mDateInfo = (TextView) findViewById(R.id.activity_event_item_date_info);
        mDescription = (TextView) findViewById(R.id.activity_event_item_description);
        mParticipantsList = (ListView) findViewById(R.id.activity_event_item_partipants_list);

        fetchEvent(eventId);
    }

    /**
     * Get even from the API, given the id
     *
     * @param eventId Id to look for
     */
    private void fetchEvent(final String eventId) {
        mApiClient.getEvent(eventId).enqueue(new Callback<Event>() {

            @Override
            public void onResponse(Response<Event> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    mEvent = response.body();
                    fillTemplate(mEvent);
                } else {
                    Log.e(TAG, String.format("error finding %s event", eventId));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, String.format("Failure when getting event: %s", t.getLocalizedMessage()));
            }
        });
    }

    /**
     * Fill template with event data
     * @param event Event to fill data from
     */
    private void fillTemplate(Event event) {
        Log.i(TAG, String.format("Filling for %s", event));
        mTitle.setText(event.getTitle());
        mAuthor.setText(findUserName(event.getAuthor()));
        mDateInfo.setText(String.format("%s %s %s %s", getString(R.string.event_item_details_from), event.getBegin().toString(mDateFormatter), getString(R.string.event_item_details_to), event.getEnd().toString(mDateFormatter)));
        mDescription.setText(event.getDescription());

        ParticipantItemAdapter participantItemAdapter = new ParticipantItemAdapter(this, event.getParticipants());
        mParticipantsList.setAdapter(participantItemAdapter);
    }

    /**
     * Check if id is null or not
     * @param eventId
     * @return true if not valid, else false
     */
    private boolean checkEventId(String eventId) {
        return (eventId == null || eventId.equals(""));
    }
}
