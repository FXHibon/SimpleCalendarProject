package epsi.fx.com.simplecalendarproject.beans.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.db.StorageHelper;

/**
 * Created by fx on 28/10/2015.
 */
public class UserDao {

    public static final String TAG = UserDao.class.getName();
    private final StorageHelper mStorageHelper;

    public UserDao(Context context) {
        mStorageHelper = new StorageHelper(context);
    }

    public List<User> getUsers() {
        SQLiteDatabase db = mStorageHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM users", null);

        List<User> users = new ArrayList<User>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            User user;
            do {
                user = parseUser(c);
                users.add(user);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        Log.i(TAG, "returning " + users.size() + " users");

        return users;
    }

    /**
     * Get User from cursor
     *
     * @param cursor
     * @return
     */
    public static User parseUser(Cursor cursor) {
        User user;
        user = new User();
        user.setId(cursor.getString(cursor.getColumnIndex(User.ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(User.EMAIL)));
        return user;
    }

    public User getUserById(String id) {
        SQLiteDatabase db = mStorageHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + User.USER_TABLE_NAME + " WHERE id = '" + id + "'", null);
        User user = null;
        if (c.getCount() == 1) {
            c.moveToFirst();
            user = parseUser(c);
            c.close();
        }
        db.close();
        return user;
    }

    private String insertUser(User user, boolean update) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();
        ContentValues valuesDb = new ContentValues();

        if (update) {
            valuesDb.put(User.ID, user.getId());
        } else {
            valuesDb.put(User.ID, UUID.randomUUID().toString());
        }
        String id = valuesDb.get(User.ID).toString();
        valuesDb.put(User.NAME, user.getName());
        valuesDb.put(User.EMAIL, user.getEmail());

        db.insert(User.USER_TABLE_NAME, null, valuesDb);

        Log.i(TAG, user.toString() + " inserted");
        db.close();
        return id;
    }

    public String insertUser(User user) {
        return insertUser(user, false);
    }

    public String updateUser(User user) {
        return insertUser(user, true);
    }
}
