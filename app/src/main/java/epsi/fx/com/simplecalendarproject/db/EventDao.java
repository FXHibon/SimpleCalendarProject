package epsi.fx.com.simplecalendarproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import epsi.fx.com.simplecalendarproject.beans.Event;

/**
 * Created by fx on 16/10/2015.
 */
public class EventDao {

    private final StorageHelper mStorageHelper;

    public EventDao(Context context) {
        mStorageHelper = new StorageHelper(context);
    }

    public List<Event> getEvents() {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT title, description, date FROM events", null);

        List<Event> events = new ArrayList<Event>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            Event event;
            do {
                event = new Event();
                event.setTitle(c.getString(c.getColumnIndex("title")));
                event.setDesc(c.getString(c.getColumnIndex("description")));
                event.setDate(c.getString(c.getColumnIndex("date")));
                events.add(event);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        Log.i("DAO", "returning " + events.size() + " events");

        return events;
    }

    public void insertEvent(Event event) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();
        ContentValues valuesDb = new ContentValues();
        valuesDb.put("title", event.getTitle());
        valuesDb.put("description", event.getDesc());
        valuesDb.put("date", event.getDate());
        db.insert("Events", null, valuesDb);
        Log.i("DAO", event.toString() + " inserted");
        db.close();
    }
}
