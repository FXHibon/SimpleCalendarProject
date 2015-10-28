package epsi.fx.com.simplecalendarproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.beans.dao.EventDao;

/**
 * Created by fx on 15/10/2015.
 */
public class StorageHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "calendar";
    public static final int DB_VERSION = 8;
    public static final String TAG = EventDao.class.getName();

    public StorageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Create the db schemas
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, DB_NAME + " db creation");

        db.execSQL(User.USER_TABLE_CREATION);
        db.execSQL(Event.EVENT_TABLE_CREATION);
        db.execSQL(Event.PARTICIPATION_TABLE_CREATION);
    }


    /**
     * Reset DB when new version (usefull for tests)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade(oldVersion: " + oldVersion + ", newVersion: " + newVersion + ")");
        Log.i(TAG, "resetting db");

        db.execSQL("DROP TABLE IF EXISTS " + Event.PARTICIPATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Event.EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + User.USER_TABLE_NAME);

        onCreate(db);
    }
}
