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

    public static final String TAG = EventDao.class.getName();
    private final StorageHelper mStorageHelper;

    public EventDao(Context context) {
        mStorageHelper = new StorageHelper(context);
    }

    public List<Event> getEvents() {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT title, description, author, date_begin, date_end FROM events", null);

        List<Event> events = new ArrayList<Event>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            Event event;
            do {
                event = new Event();
                event.setTitle(c.getString(c.getColumnIndex("title")));
                event.setDesc(c.getString(c.getColumnIndex("description")));
                event.setAuthor(c.getString(c.getColumnIndex("author")));
                event.setDateBegin(c.getString(c.getColumnIndex("date_begin")));
                event.setDateEnd(c.getString(c.getColumnIndex("date_end")));
                ;
                events.add(event);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        Log.i(TAG, "returning " + events.size() + " events");

        return events;
    }

    public void insertEvent(Event event) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();
        ContentValues valuesDb = new ContentValues();

        valuesDb.put("title", event.getTitle());
        valuesDb.put("description", event.getDesc());
        valuesDb.put("author", event.getAuthor());
        valuesDb.put("date_begin", event.getDateBegin());
        valuesDb.put("date_end", event.getDateEnd());

        db.insert("Events", null, valuesDb);

        Log.i(TAG, event.toString() + " inserted");
        db.close();
    }
}
