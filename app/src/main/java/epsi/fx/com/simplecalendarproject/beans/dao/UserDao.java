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

        Cursor c = db.rawQuery("SELECT id, name, email FROM users", null);

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
    private User parseUser(Cursor cursor) {
        User user;
        user = new User();
        user.setId(cursor.getString(cursor.getColumnIndex("id")));
        user.setName(cursor.getString(cursor.getColumnIndex("name")));
        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        return user;
    }

    public User getUserById(String id) {
        SQLiteDatabase db = mStorageHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT id, name, email FROM " + StorageHelper.USER_TABLE_NAME + " WHERE id = '" + id + "'", null);
        User user = null;
        if (c.getCount() == 1) {
            c.moveToFirst();
            user = parseUser(c);
        }
        return user;
    }

    private void insertUser(User user, boolean update) {
        SQLiteDatabase db = mStorageHelper.getWritableDatabase();
        ContentValues valuesDb = new ContentValues();

        if (update) {
            valuesDb.put("id", user.getId());
        } else {
            valuesDb.put("id", UUID.randomUUID().toString());
        }
        valuesDb.put("name", user.getName());
        valuesDb.put("email", user.getEmail());

        db.insert(StorageHelper.USER_TABLE_NAME, null, valuesDb);

        Log.i(TAG, user.toString() + " inserted");
        db.close();
    }

    public void insertUser(User user) {
        insertUser(user, false);
    }

    public void updateUser(User user) {
        insertUser(user, true);
    }
}
