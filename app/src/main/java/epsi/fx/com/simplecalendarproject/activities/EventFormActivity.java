package epsi.fx.com.simplecalendarproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.db.EventDao;

public class EventFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_form);

        getIntent();

        final EventDao eventDao = new EventDao(this);

        Button btnCancel = (Button) findViewById(R.id.event_form_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(0, intent);
                EventFormActivity.this.finish();
            }
        });

        Button btnValidate = (Button) findViewById(R.id.event_form_validate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Event event = new Event();

                TextView title = (TextView) findViewById(R.id.event_form_title);
                TextView desc = (TextView) findViewById(R.id.event_form_desc);
                TextView date = (TextView) findViewById(R.id.event_form_date);

                event.setTitle(title.getText().toString());
                event.setDesc(desc.getText().toString());
                event.setDate(date.getText().toString());

                eventDao.insertEvent(event);

                Intent intent = new Intent();
                setResult(1, intent);
                EventFormActivity.this.finish();
            }
        });
    }
}
