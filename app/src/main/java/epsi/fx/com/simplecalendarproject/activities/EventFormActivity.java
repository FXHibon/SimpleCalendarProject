package epsi.fx.com.simplecalendarproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.UUID;

import epsi.fx.com.simplecalendarproject.Common;
import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.Participant;
import epsi.fx.com.simplecalendarproject.beans.dao.EventDao;

public class EventFormActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    private EventDao mEventDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init view
        setContentView(R.layout.event_form);

        // Init field
        mEventDao = new EventDao(this);
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

        String uuid = prefs.getString(Common.PREFS_USER_ID, "");
        event.setAuthor(UUID.fromString(uuid));
        event.setParticipants(new ArrayList<Participant>());

        mEventDao.insertEvent(event);

        Intent intent = new Intent();
        setResult(EventFormActivity.RESULT_OK, intent);
        EventFormActivity.this.finish();
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent();
        setResult(EventFormActivity.RESULT_CANCELED, intent);
        EventFormActivity.this.finish();
    }
}
