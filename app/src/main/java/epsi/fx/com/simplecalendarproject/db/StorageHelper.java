package epsi.fx.com.simplecalendarproject.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import epsi.fx.com.simplecalendarproject.Common;

/**
 * Created by fx on 15/10/2015.
 */
public class StorageHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "events";
    public static final int DB_VERSION = 10;
    public static final String TAG = StorageHelper.class.getName();

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
    private Context mContext;

    public StorageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.v(TAG, "new StorageHelper");
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, DB_NAME + " db creation");
        db.execSQL(USER_TABLE_CREATION);
        db.execSQL(EVENT_TABLE_CREATION);
        db.execSQL(PARTICIPATION_TABLE_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Common.SIMPLE_CALENDAR_EPSI, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(Common.CURRENT_USER_ID);
        edit.remove(Common.SIMPLE_CALENDAR_EMAIL);
        edit.apply();
        Log.v(TAG, "onUpgrade(oldVersion: " + oldVersion + ", newVersion: " + newVersion + ")");
        Log.v(TAG, "resetting db");
        db.execSQL("DROP TABLE IF EXISTS " + PARTICIPATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}
