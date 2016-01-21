package epsi.fx.com.simplecalendarproject.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import epsi.fx.com.simplecalendarproject.AppConfig;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.Participant;
import epsi.fx.com.simplecalendarproject.beans.User;

/**
 * Created by fx on 15/10/2015.
 */
public class StorageHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "events";

    public static final int DB_VERSION = 12;

    public static final String TAG = StorageHelper.class.getName();

    public static final String EVENT_TABLE_NAME = "events";
    public static final String USER_TABLE_NAME = "users";
    public static final String PARTICIPATION_TABLE_NAME = "participants";

    public static final String USER_TABLE_CREATION = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", USER_TABLE_NAME, User.ID, User.NAME, User.DESCRIPTION, User.EMAIL, User.PASSWORD);

    public static final String EVENT_TABLE_CREATION = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, FOREIGN KEY(%s) REFERENCES %s(%s))", EVENT_TABLE_NAME, Event.ID, Event.TITLE, Event.DESCRIPTION, Event.AUTHOR, Event.BEGIN, Event.END, Event.AUTHOR, USER_TABLE_NAME, User.ID);

    public static final String PARTICIPATION_TABLE_CREATION = String.format("CREATE TABLE %s (id_event TEXT, id_user TEXT, %s TEXT, FOREIGN KEY(id_event) REFERENCES %s(%s), FOREIGN KEY(id_user) REFERENCES %s(%s))", PARTICIPATION_TABLE_NAME, Participant.STATUS, EVENT_TABLE_NAME, Event.ID, USER_TABLE_NAME, User.ID);

    private Context mContext;

    public StorageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.v(TAG, "new StorageHelper");
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, String.format("%s db creation", DB_NAME));
        db.execSQL(USER_TABLE_CREATION);
        db.execSQL(EVENT_TABLE_CREATION);
        db.execSQL(PARTICIPATION_TABLE_CREATION);
    }

    /**
     * Reset dbs
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.v(TAG, String.format("onUpgrade(oldVersion: %d, newVersion: %d)", oldVersion, newVersion));
        Log.v(TAG, "resetting db and shared prefs");

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(AppConfig.USER_ID_KEY);
        edit.remove(AppConfig.USER_EMAIL_KEY);
        edit.apply();

        db.execSQL(String.format("DROP TABLE IF EXISTS %s", PARTICIPATION_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", EVENT_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", USER_TABLE_NAME));
        onCreate(db);
    }
}
