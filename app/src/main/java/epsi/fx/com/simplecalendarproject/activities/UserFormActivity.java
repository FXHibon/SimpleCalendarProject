package epsi.fx.com.simplecalendarproject.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.beans.dao.UserDao;

public class UserFormActivity extends AppCompatActivity {

    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        mUserDao = new UserDao(this);
    }

    public void onClickOk(View view) {
        saveUser();
        finish();
    }

    private void saveUser() {
        EditText name = (EditText) findViewById(R.id.user_form_name);
        EditText email = (EditText) findViewById(R.id.user_form_email);

        User user = new User();
        user.setEmail(email.getText().toString());
        user.setName(name.getText().toString());

        String id = mUserDao.insertUser(user);

        SharedPreferences prefs = getSharedPreferences(EventListActivity.SIMPLE_CALENDAR_EPSI, Context.MODE_PRIVATE);
        if (prefs.getString("currentUserId", "").equals("")) {
            SharedPreferences.Editor ed = prefs.edit();
            ed.putString("currentUserId", id);
            ed.apply();
        }
    }

    public void onClickCancel(View view) {
        finish();
    }
}
