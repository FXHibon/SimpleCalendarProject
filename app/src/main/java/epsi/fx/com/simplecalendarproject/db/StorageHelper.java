package epsi.fx.com.simplecalendarproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by fx on 15/10/2015.
 */
public class StorageHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "events";
    public static final String TABLE_NAME = "events";
    public static final int DB_VERSION = 1;
    public static final String TAG = EventDao.class.getName();

    public StorageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, DB_NAME + " db creation");
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY, " +
                "title TEXT, " +
                "description TEXT, " +
                "date TEXT)";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade(oldVersion: " + oldVersion + ", newVersion: " + newVersion + ")");
        Log.i(TAG, "resetting db");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
