package epsi.fx.com.simplecalendarproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EventFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_form);

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
                Intent intent = new Intent();
                intent.putExtra("event", "HEEEEEEEEEEEEY");
                setResult(1, intent);
                EventFormActivity.this.finish();
            }
        });
    }
}
