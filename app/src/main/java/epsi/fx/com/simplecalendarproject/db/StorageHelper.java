package epsi.fx.com.simplecalendarproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import epsi.fx.com.simplecalendarproject.beans.dao.EventDao;

/**
 * Created by fx on 15/10/2015.
 */
public class StorageHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "events";
    public static final int DB_VERSION = 3;
    public static final String TAG = EventDao.class.getName();

    public static final String USER_TABLE_NAME = "users";
    public static final String USER_TABLE_CREATION = "CREATE TABLE " + USER_TABLE_NAME + " (" +
            "id TEXT, " +
            "name TEXT, " +
            "email TEXT)";

    public static final String EVENT_TABLE_NAME = "events";
    public static final String EVENT_TABLE_CREATION = "CREATE TABLE " + EVENT_TABLE_NAME + " (" +
            "id TEXT, " +
            "title TEXT, " +
            "author TEXT, " +
            "description TEXT, " +
            "date_begin TEXT, " +
            "date_end TEXT, " +
            "FOREIGN KEY(author) REFERENCES " + USER_TABLE_NAME + "(id))";

    public static final String PARTICIPATION_TABLE_NAME = "participation";
    public static final String PARTICIPATION_TABLE_CREATION = "CREATE TABLE " + PARTICIPATION_TABLE_NAME + " (" +
            "id_event TEXT, " +
            "id_user TEXT, " +
            "status TEXT, " +
            "FOREIGN KEY(id_event) REFERENCES " + EVENT_TABLE_NAME + "(id), " +
            "FOREIGN KEY(id_user) REFERENCES " + USER_TABLE_NAME + "(id))";

    public StorageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, DB_NAME + " db creation");
        db.execSQL(USER_TABLE_CREATION);
        db.execSQL(EVENT_TABLE_CREATION);
        db.execSQL(PARTICIPATION_TABLE_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade(oldVersion: " + oldVersion + ", newVersion: " + newVersion + ")");
        Log.i(TAG, "resetting db");
        db.execSQL("DROP TABLE IF EXISTS " + PARTICIPATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}
