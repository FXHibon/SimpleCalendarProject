package epsi.fx.com.simplecalendarproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.beans.dao.UserDao;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LauncherActivity extends AppCompatActivity {

    private static final String TAG = LauncherActivity.class.getName();
    private ProgressBar mProgressBar;
    private ApiClient mApiClient;
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mProgressBar = (ProgressBar) findViewById(R.id.activity_launcher_progress);
        mProgressBar.setIndeterminate(true);

        mApiClient = new ApiClient(this);
        mUserDao = new UserDao(this);

        mApiClient.listUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    initData(response.body());
                } else {
                    handleError();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                handleError();
            }
        });
    }

    private void handleError() {
        mProgressBar.setIndeterminate(false);
        Toast.makeText(LauncherActivity.this, R.string.activity_launcher_api_error, Toast.LENGTH_SHORT).show();
    }

    private void initData(List<User> users) {
        mProgressBar.setIndeterminate(false);
        int localSize = mUserDao.getUsers().size();
        Log.d(TAG, String.format("receiving %d comparing to %d", users.size(), localSize));
        if (localSize != users.size()) {
            mUserDao.remove();
            for (User u : users) {
                Log.d(TAG, String.format("inserting %s in db", u));
                mUserDao.insertUser(u);
            }
        }

        Intent intent = new Intent(LauncherActivity.this, EventListActivity.class);
        startActivity(intent);
    }
}
