package epsi.fx.com.simplecalendarproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.db.EventDao;

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
        TextView date = (TextView) findViewById(R.id.event_form_date);

        event.setTitle(title.getText().toString());
        event.setDesc(desc.getText().toString());
        event.setDate(date.getText().toString());

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
