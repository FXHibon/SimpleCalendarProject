package epsi.fx.com.simplecalendarproject.ws;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.joda.time.DateTime;

import java.util.List;

import epsi.fx.com.simplecalendarproject.AppConfig;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.Login;
import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.ws.interceptors.AddCookiesInterceptor;
import epsi.fx.com.simplecalendarproject.ws.interceptors.SetCookiesInterceptor;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by fx on 29/10/2015.
 * Build our client api
 */
public class ApiClient {

    public static final String TAG = ApiClient.class.getName();

    private static WebService mWs;

    public ApiClient(Context ctx) {

        if (mWs == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.interceptors()
                    .add(new AddCookiesInterceptor(ctx));
            okHttpClient.interceptors()
                    .add(new SetCookiesInterceptor(ctx));

            // Build API Client
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.API_END_POINT)
                    .addConverterFactory(
                            // Handle Date (un)serialize
                            GsonConverterFactory
                                    .create(
                                            new GsonBuilder()
                                                    .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                                                    .create()
                                    )
                    )
                    .client(okHttpClient)
                    .build();
            mWs = retrofit.create(WebService.class);
        }

    }

    /**
     * Register a new user
     *
     * @param u User to be registered
     * @return Call
     */
    public retrofit.Call<Void> register(User u) {
        Log.i(TAG, "register");
        return mWs.register(u);
    }

    /**
     * Log the user in
     *
     * @param u User to be logged in
     * @return Call
     */
    public retrofit.Call<Void> login(User u) {
        Log.i(TAG, "login");
        Login login = new Login();
        login.setEmail(u.getEmail());
        login.setPassword(u.getPassword());
        return mWs.login(login);
    }

    /**
     * List all events
     *
     * @return Call
     */
    public Call<List<Event>> listEvents() {
        Log.i(TAG, "listEvents");
        return mWs.listEvents();
    }

    /**
     * Create an event
     *
     * @return Call
     */
    public Call<Void> insertEvent(Event event) {
        Log.i(TAG, "insertEvent");
        return mWs.insertEvent(event);
    }

    public Call<Event> getEvent(String id) {
        Log.i(TAG, "getEvent");
        return mWs.getEvent(id);
    }

    public Call<Void> logout() {
        Log.i(TAG, "logout");
        return mWs.logout();
    }

    public Call<List<User>> listUsers() {
        Log.i(TAG, "listUsers");
        return mWs.listUsers();
    }
}
