package epsi.fx.com.simplecalendarproject.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import epsi.fx.com.simplecalendarproject.Common;
import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.beans.dao.UserDao;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserFormActivity extends AppCompatActivity {

    private static final String TAG = UserFormActivity.class.getName();

    private UserDao mUserDao;
    private ApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        mUserDao = new UserDao(this);
        client = new ApiClient(this);
    }

    public void onClickOk(View view) {
        Switch register = (Switch) findViewById(R.id.user_form_register);
        if (register.isChecked()) {
            saveUser();
        } else {
            loginUser();
        }
        finish();
    }

    private void loginUser() {
        EditText name = (EditText) findViewById(R.id.user_form_name);
        EditText email = (EditText) findViewById(R.id.user_form_email);
        EditText password = (EditText) findViewById(R.id.user_form_password);

        final User user = new User();
        user.setEmail(email.getText().toString());
        user.setName(name.getText().toString());
        user.setPassword(password.getText().toString());

        client.login(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                Log.v(TAG, Integer.toString(response.code()));
                if (response.isSuccess()) {
                    SharedPreferences.Editor simpleCalendar = UserFormActivity.this.getSharedPreferences(Common.SIMPLE_CALENDAR_EPSI, Context.MODE_PRIVATE).edit();
                    simpleCalendar.putString(Common.SIMPLE_CALENDAR_EMAIL, user.getEmail());
                    simpleCalendar.apply();
                    Log.v(TAG, "Authenticated: " + user);
                    finish();
                } else {
                    Toast.makeText(UserFormActivity.this, "Not authenticated", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(UserFormActivity.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void saveUser() {
        EditText name = (EditText) findViewById(R.id.user_form_name);
        EditText email = (EditText) findViewById(R.id.user_form_email);
        EditText password = (EditText) findViewById(R.id.user_form_password);

        final User user = new User();
        user.setEmail(email.getText().toString());
        user.setName(name.getText().toString());
        user.setPassword(password.getText().toString());

        client.register(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (!response.isSuccess()) {
                    Toast.makeText(UserFormActivity.this, "Nop", Toast.LENGTH_LONG).show();
                    Log.v(TAG, Integer.toString(response.code()));
                } else {
                    Toast.makeText(UserFormActivity.this, "Registered", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(UserFormActivity.this, "Unexpected error", Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });


    }

    public void onClickCancel(View view) {
        finish();
    }
}
