package epsi.fx.com.simplecalendarproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.adapters.EventItemAdapter;
import epsi.fx.com.simplecalendarproject.db.EventDao;

public class EventListActivity extends AppCompatActivity {

    private ListView list;
    private EventDao mEventDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        this.list = (ListView) findViewById(R.id.event_list_view);
        mEventDao = new EventDao(this);
        refreshData();
    }

    /**
     * Refresh data
     */
    private void refreshData() {
        EventItemAdapter eventItemAdapter = new EventItemAdapter(this, mEventDao.getEvents());
        list.setAdapter(eventItemAdapter);
    }

    public void refreshData(View v) {
        refreshData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 1) {
                Log.d("result", "new event, refreshing list");
                refreshData();
            } else if (resultCode == 0) {
                Log.d("result", "operation cancelled");
            }
        }
    }

    public void onClickAddEvent(View view) {
        Intent intent = new Intent(EventListActivity.this, EventFormActivity.class);
        this.startActivityForResult(intent, 1);
    }
}
