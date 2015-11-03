package epsi.fx.com.simplecalendarproject.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import epsi.fx.com.simplecalendarproject.Common;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.User;

/**
 * Created by fx on 15/10/2015.
 */
public class StorageHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "events";
    public static final int DB_VERSION = 11;
    public static final String TAG = StorageHelper.class.getName();

    private Context mContext;

    public StorageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.v(TAG, "new StorageHelper");
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, DB_NAME + " db creation");
        db.execSQL(User.USER_TABLE_CREATION);
        db.execSQL(Event.EVENT_TABLE_CREATION);
        db.execSQL(Event.PARTICIPATION_TABLE_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Common.PREFS_SCOPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(Common.PREFS_USER_ID);
        edit.remove(Common.PREFS_USER_EMAIL);
        edit.apply();
        Log.v(TAG, "onUpgrade(oldVersion: " + oldVersion + ", newVersion: " + newVersion + ")");
        Log.v(TAG, "resetting db and shared prefs");
        db.execSQL("DROP TABLE IF EXISTS " + Event.PARTICIPATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Event.EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + User.USER_TABLE_NAME);
        onCreate(db);
    }
}
