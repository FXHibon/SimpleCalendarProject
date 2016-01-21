package epsi.fx.com.simplecalendarproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import epsi.fx.com.simplecalendarproject.AppConfig;
import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.adapters.EventItemAdapter;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Event list related actions/views
 */
public class EventListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final int EVENT_FORM_ACTIVITY_REQUEST_CODE = 1;
    public static final String TAG = EventListActivity.class.getName();
    public static final String EXTRA_EVENT_ID = "EXTRA_EVENT_ID";
    private ListView mList;
    private ApiClient mApiClient;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init view
        setContentView(R.layout.activity_event_list);

        // Init fields
        this.mList = (ListView) findViewById(R.id.event_list_view);
        this.mSharedPrefs = getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE);

        mApiClient = new ApiClient(this);
    }

    /**
     * Refresh data
     */
    private void refreshData() {

        mApiClient.listEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Response<List<Event>> response, Retrofit retrofit) {
                Log.v(TAG, String.format("listEvents.response: %d", response.code()));
                if (response.isSuccess()) {
                    EventItemAdapter eventItemAdapter = new EventItemAdapter(EventListActivity.this, response.body());
                    mList.setOnItemClickListener(EventListActivity.this);
                    mList.setAdapter(eventItemAdapter);
                } else {
                    Log.e(TAG, "can't get events from api ");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, String.format("Error: %s", t.getMessage()));
            }
        });
    }

    /**
     * Callback when another activity lead us to this one
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EventFormActivity.REQUEST_CODE) {
            if (resultCode == EventFormActivity.RESULT_OK) {
                Log.d(TAG, "new event, refreshing list");
                refreshData();
            } else if (resultCode == EventFormActivity.RESULT_CANCELED) {
                Log.d(TAG, "operation cancelled");
            }
        }
    }

    /**
     * Listener to click event
     * @param view
     */
    public void onClickAddEvent(View view) {
        Intent intent = new Intent(EventListActivity.this, EventFormActivity.class);
        this.startActivityForResult(intent, EVENT_FORM_ACTIVITY_REQUEST_CODE);
    }

    /**
     * Check if user is authorize to be there
     */
    @Override
    protected void onResume() {
        super.onResume();

        Log.v(TAG, String.format("prefs.USER_EMAIL_KEY = %s", mSharedPrefs.getString(AppConfig.USER_EMAIL_KEY, "")));
        if (mSharedPrefs.getString(AppConfig.USER_EMAIL_KEY, "").equals("")) {
            Intent intent = new Intent(this, UserFormActivity.class);
            startActivity(intent);
        } else {
            refreshData();
        }
    }

    /**
     * on click listener
     * @param view
     */
    public void onClickDisconnect(View view) {
        mApiClient.logout().enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                localDisconnect();
                onResume();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EventListActivity.this, "Can not logout", Toast.LENGTH_SHORT).show();
                Log.e(TAG, String.format("Error while logging out: %s", t.getLocalizedMessage()));
            }
        });

    }

    /**
     * Disconnect locally
     */
    private void localDisconnect() {
        Log.v(TAG, String.format("Clearing %s prefs", AppConfig.PREFS_SCOPE));
        SharedPreferences.Editor edit = this.mSharedPrefs.edit();
        edit.clear();
        edit.apply();
    }

    /**
     * Listener for list item click
     * Launch activity for view all event info
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event clickedItem = (Event) parent.getItemAtPosition(position);
        Log.v(TAG, clickedItem.toString());
        Intent intent = new Intent(this, EventItemActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, clickedItem.getId().toString());
        startActivity(intent);
    }
}
