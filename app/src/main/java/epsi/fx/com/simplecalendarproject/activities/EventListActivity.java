package epsi.fx.com.simplecalendarproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import epsi.fx.com.simplecalendarproject.Common;
import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.adapters.EventItemAdapter;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventListActivity extends AppCompatActivity {

    public static final int EVENT_FORM_ACTIVITY_REQUEST_CODE = 1;
    public static final String TAG = EventListActivity.class.getName();
    private ListView mList;
    private ApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init view
        setContentView(R.layout.event_list);

        // Init fields
        this.mList = (ListView) findViewById(R.id.event_list_view);

        mApiClient = new ApiClient(this);

        // Init data
        refreshData();
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
                    mList.setAdapter(eventItemAdapter);
                } else {
                    Log.e(TAG, "listEvents. error");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, String.format("Error: %s", t.getMessage()));
            }
        });
    }

    /**
     * listener for "refresh" button
     *
     * @param v Useless here
     */
    public void refreshData(View v) {
        refreshData();
    }

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

    public void onClickAddEvent(View view) {
        Intent intent = new Intent(EventListActivity.this, EventFormActivity.class);
        this.startActivityForResult(intent, EVENT_FORM_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(Common.PREFS_SCOPE, Context.MODE_PRIVATE);

        Log.v(TAG, String.format("prefs.USER_EMAIL_KEY = %s", prefs.getString(Common.USER_EMAIL_KEY, "")));
        if (prefs.getString(Common.USER_EMAIL_KEY, "").equals("")) {
            Intent intent = new Intent(EventListActivity.this, UserFormActivity.class);
            startActivity(intent);
        } else {
            refreshData();
        }
    }

    public void onClickDisconnect(View view) {
        mApiClient.logout().enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                disconnect();
                onResume();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EventListActivity.this, "Cannot logged out", Toast.LENGTH_SHORT).show();
                Log.e(TAG, String.format("Error while logging out: %s", t.getLocalizedMessage()));
            }
        });
        
    }

    private void disconnect() {
        Log.v(TAG, String.format("Clearing %s prefs", Common.PREFS_SCOPE));
        SharedPreferences.Editor edit = this.getSharedPreferences(Common.PREFS_SCOPE, Context.MODE_PRIVATE).edit();
        edit.clear();
        edit.apply();
    }
}
