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
    public static final int DB_VERSION = 4;

    public StorageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public StorageHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE EVENTS " +
                "(id INTEGER PRIMARY KEY, " +
                "title TEXT, " +
                "description TEXT)";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("sql", "onUpgrade(oldVersion: " + oldVersion + ", newVersion: " + newVersion + ")");
//        db.execSQL("DELETE FROM EVENTS WHERE 1 = 1");
    }
}
