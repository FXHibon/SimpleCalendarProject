package epsi.fx.com.simplecalendarproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.adapters.EventItemAdapter;
import epsi.fx.com.simplecalendarproject.beans.dao.EventDao;
import epsi.fx.com.simplecalendarproject.beans.dao.UserDao;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventListActivity extends AppCompatActivity {

    public static final int EVENT_FORM_ACTIVITY_REQUEST_CODE = 1;
    public static final String TAG = EventListActivity.class.getName();
    public static final String SIMPLE_CALENDAR_EPSI = "SimpleCalendarEpsi";
    private ListView mList;
    private EventDao mEventDao;
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init view
        setContentView(R.layout.event_list);

        // Init fields
        this.mList = (ListView) findViewById(R.id.event_list_view);
        mEventDao = new EventDao(this);
        mUserDao = new UserDao(this);

        // Internet Access
        ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = mgr.getActiveNetworkInfo();
        if (activeNetworkInfo.isConnected()) {
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        }

        ApiClient apiClient = new ApiClient(this);
        apiClient.register("asdg", "asfdg@df.com", "afdg").enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                Toast.makeText(EventListActivity.this, "Registered", Toast.LENGTH_LONG).show();
                Log.d(TAG, "" + response.code());
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EventListActivity.this, "Not registered", Toast.LENGTH_LONG).show();
                Log.wtf(TAG, t);
            }
        });

        // Init data
        refreshData();
    }

    /**
     * Refresh data
     */
    private void refreshData() {
        EventItemAdapter eventItemAdapter = new EventItemAdapter(this, mEventDao.getEvents());
        mList.setAdapter(eventItemAdapter);
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
        SharedPreferences prefs = getSharedPreferences(EventListActivity.SIMPLE_CALENDAR_EPSI, Context.MODE_PRIVATE);

        if (prefs.getString("currentUserId", "").equals("")) {
            Intent intent = new Intent(EventListActivity.this, UserFormActivity.class);
            startActivity(intent);
        }
    }
}
