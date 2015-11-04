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
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserFormActivity extends AppCompatActivity {

    private static final String TAG = UserFormActivity.class.getName();

    private ApiClient mClient;
    private Switch mRegisterSwitch;
    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init view
        setContentView(R.layout.activity_user_form);

        // Init fields
        mClient = new ApiClient(this);
        mRegisterSwitch = (Switch) findViewById(R.id.user_form_register);
        mNameField = (EditText) findViewById(R.id.user_form_name);
        mEmailField = (EditText) findViewById(R.id.user_form_email);
        mPasswordField = (EditText) findViewById(R.id.user_form_password);
    }

    public void onClickOk(View view) {
        if (mRegisterSwitch.isChecked()) {
            saveUser();
        } else {
            loginUser();
        }
    }

    /**
     * Try to login using fields
     */
    private void loginUser() {

        final User user = new User();
        user.setEmail(mEmailField.getText().toString());
        user.setName(mNameField.getText().toString());
        user.setPassword(mPasswordField.getText().toString());

        mClient.login(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                Log.v(TAG, Integer.toString(response.code()));
                if (response.isSuccess()) {
                    SharedPreferences.Editor simpleCalendar = UserFormActivity.this.getSharedPreferences(Common.PREFS_SCOPE, Context.MODE_PRIVATE).edit();
                    simpleCalendar.putString(Common.PREFS_USER_EMAIL, user.getEmail());
                    if (simpleCalendar.commit()) {
                        Log.v(TAG, String.format("Authentication succeed: %s", user));
                        finish();
                    } else {
                        Log.v(TAG, "Can't write into ShardPreferences");
                    }
                } else {
                    Toast.makeText(UserFormActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
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

    /**
     * Try to register user using fields
     */
    private void saveUser() {

        final User user = new User();
        user.setEmail(mEmailField.getText().toString());
        user.setName(mNameField.getText().toString());
        user.setPassword(mPasswordField.getText().toString());
        user.setDescription("ma description");

        mClient.register(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                Log.v(TAG, String.format("register.onResponse => %s", Integer.toString(response.code())));
                if (!response.isSuccess()) {
                    Toast.makeText(UserFormActivity.this, "Can't register. Try another email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserFormActivity.this, user.getEmail() + " registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(UserFormActivity.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });


    }

    public void onClickCancel(View view) {
        finish();
    }
}
