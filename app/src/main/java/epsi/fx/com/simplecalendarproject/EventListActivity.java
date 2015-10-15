package epsi.fx.com.simplecalendarproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private Button add;
    private ListView list;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        add = (Button) findViewById(R.id.add_event_btn);
        list = (ListView) findViewById(R.id.event_list_view);

        final List<String> dataList = new ArrayList<>();
        dataList.add("hello");
        dataList.add("world");
        dataList.add("!");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);

        list.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventListActivity.this, EventFormActivity.class);
                EventListActivity.this.startActivity(intent);
            }
        });
    }
}
