package epsi.fx.com.simplecalendarproject.beans.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.User;
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

        Cursor c = db.rawQuery("SELECT * FROM " + Event.EVENT_TABLE_NAME, null);

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
        Event event = new Event();
        event.setId(cursor.getString(cursor.getColumnIndex(Event.ID)));
        event.setTitle(cursor.getString(cursor.getColumnIndex(Event.TITLE)));
        event.setDesc(cursor.getString(cursor.getColumnIndex(Event.DESCRIPTION)));
        event.setAuthor(cursor.getString(cursor.getColumnIndex(Event.AUTHOR)));
        event.setDateBegin(DateTime.parse(cursor.getString(cursor.getColumnIndex(Event.DATE_BEGIN))));
        event.setDateEnd(DateTime.parse(cursor.getString(cursor.getColumnIndex(Event.DATE_END))));
        return event;
    }

    public Event getEventById(String id) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + Event.EVENT_TABLE_NAME + " WHERE id = '" + id + "'", null);

        Event event = null;

        if (c.getCount() == 1) {
            event = parseEvent(c);
            c.close();
        }
        db.close();

        return event;
    }

    private void insertEvent(Event event, boolean update) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();
        ContentValues valuesDb = new ContentValues();

        if (update) {
            valuesDb.put(Event.ID, event.getId());
        } else {
            valuesDb.put(Event.ID, UUID.randomUUID().toString());
        }
        valuesDb.put(Event.TITLE, event.getTitle());
        valuesDb.put(Event.DESCRIPTION, event.getDesc());
        valuesDb.put(Event.AUTHOR, event.getAuthor());
        valuesDb.put(Event.DATE_BEGIN, event.getDateBegin().toString());
        valuesDb.put(Event.DATE_END, event.getDateEnd().toString());

        db.insert(Event.EVENT_TABLE_NAME, null, valuesDb);

        Log.i(TAG, event.toString() + " inserted");
        db.close();
    }

    public void updateEvent(Event event) {
        insertEvent(event, true);
    }

    public void insertEvent(Event event) {
        insertEvent(event, false);
    }

    public List<Event> getEventByAuthor(String id) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + Event.EVENT_TABLE_NAME + " WHERE author = '" + id + "'", null);

        List<Event> events = new ArrayList<>();
        if (c.getCount() > 0) {
            c.moveToFirst();
            Event event;
            do {
                event = parseEvent(c);
                events.add(event);
            } while (c.moveToNext());
            c.close();
        }
        db.close();

        return events;
    }

    public List<User> getParticipants(Event event) {
        SQLiteDatabase db = mStorageHelper.getReadableDatabase();

        String bigRequest = "SELECT * FROM " + Event.PARTICIPATION_TABLE_NAME + " pr INNER JOIN " + Event.EVENT_TABLE_NAME + " ev ON pr.id_event = ev.id WHERE id = " + event.getId();
        Cursor cursor = db.rawQuery(bigRequest, null);


        List<User> users = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                users.add(UserDao.parseUser(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return users;
    }
}
