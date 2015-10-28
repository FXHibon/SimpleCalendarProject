package epsi.fx.com.simplecalendarproject.beans.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.db.StorageHelper;

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

        Cursor c = db.rawQuery("SELECT title, description, author, date_begin, date_end FROM " + StorageHelper.EVENT_TABLE_NAME, null);

        List<Event> events = new ArrayList<Event>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            Event event;
            do {
                event = parseEvent(c);
                events.add(event);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        Log.i(TAG, "returning " + events.size() + " events");

        return events;
    }

    /**
     * Get Event from Cursor
     *
     * @param cursor
     * @return
     */
    private Event parseEvent(Cursor cursor) {
        Event event;
        event = new Event();
        event.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        event.setDesc(cursor.getString(cursor.getColumnIndex("description")));
        event.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
        event.setDateBegin(cursor.getString(cursor.getColumnIndex("date_begin")));
        event.setDateEnd(cursor.getString(cursor.getColumnIndex("date_end")));
        return event;
    }

    public Event getEventById(String id) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT title, description, author, date_begin, date_end FROM " + StorageHelper.EVENT_TABLE_NAME + " WHERE id = '" + id + "'", null);

        Event event = null;

        if (c.getCount() == 1) {
            event = parseEvent(c);
        }

        return event;
    }

    private void insertEvent(Event event, boolean update) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();
        ContentValues valuesDb = new ContentValues();

        if (update) {
            valuesDb.put("id", event.getId());
        } else {
            valuesDb.put("id", UUID.randomUUID().toString());
        }
        valuesDb.put("title", event.getTitle());
        valuesDb.put("description", event.getDesc());
        valuesDb.put("author", event.getAuthor());
        valuesDb.put("date_begin", event.getDateBegin());
        valuesDb.put("date_end", event.getDateEnd());

        db.insert(StorageHelper.EVENT_TABLE_NAME, null, valuesDb);

        Log.i(TAG, event.toString() + " inserted");
        db.close();
    }

    public void updateEvent(Event event) {
        insertEvent(event, true);
    }

    public void insertEvent(Event event) {
        insertEvent(event, false);
    }
}
