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

    /**
     * Get User from cursor
     *
     * @param cursor
     * @return
     */
    public static User parseUser(Cursor cursor) {
        User user;
        user = new User();
        user.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(User.ID))));
        user.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
        user.setDescription(cursor.getString(cursor.getColumnIndex(User.DESCRIPTION)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(User.EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(User.PASSWORD)));
        return user;
    }

    public List<User> getUsers() {
        SQLiteDatabase db = mStorageHelper.getReadableDatabase();

        Cursor c = db.rawQuery(String.format("SELECT * FROM %s", StorageHelper.USER_TABLE_NAME), null);

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

        Log.i(TAG, String.format("returning %d users", users.size()));

        return users;
    }

    public User getUserById(String id) {
        SQLiteDatabase db = mStorageHelper.getReadableDatabase();

        Cursor c = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = '%s'", StorageHelper.USER_TABLE_NAME, User.ID, id), null);
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
            valuesDb.put(User.ID, user.getId().toString());
        } else {
            valuesDb.put(User.ID, UUID.randomUUID().toString());
        }
        String id = valuesDb.get(User.ID).toString();
        valuesDb.put(User.NAME, user.getName());
        valuesDb.put(User.DESCRIPTION, user.getDescription());
        valuesDb.put(User.EMAIL, user.getEmail());
        valuesDb.put(User.PASSWORD, user.getPassword());

        db.insert(StorageHelper.USER_TABLE_NAME, null, valuesDb);

        Log.i(TAG, String.format("%s inserted", user.toString()));
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
