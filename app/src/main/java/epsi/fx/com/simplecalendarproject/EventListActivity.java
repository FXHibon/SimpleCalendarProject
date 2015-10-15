package epsi.fx.com.simplecalendarproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                EventListActivity.this.startActivityForResult(intent, 1);
            }
        });

        StorageHelper storageHelper = new StorageHelper(this);
        SQLiteDatabase db = storageHelper.getWritableDatabase();

//        ContentValues valuesDb = new ContentValues();
//        valuesDb.put("id", 1);
//        valuesDb.put("title", "My title");
//        valuesDb.put("description", "My desc");
//        valuesDb.put("date_begin", "000");
//        valuesDb.put("date_end", "111");
//        long row = db.insert("Events", "null", valuesDb);
//        Log.d("sql", "inserted at line " + row);
//
//        String[] projection = {"title", "description"};
//        String[] values = {"1"};
//        Cursor c = db.query(
//                "events",
//                projection,
//                "id=?",
//                values,
//                null, null, null);
//        c.moveToFirst();
//        String title = c.getString(c.getColumnIndex("title"));
//        Log.d("sql", "title");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 1) {
                Bundle res = data.getExtras();
                String result = res.getString("event");
                Log.d("result", "got event: " + result);
            } else if (resultCode == 0) {
                Log.d("result", "Error form");
            }
        }
    }
}
