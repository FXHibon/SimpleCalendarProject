package epsi.fx.com.simplecalendarproject.ws;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.joda.time.DateTime;

import java.util.List;

import epsi.fx.com.simplecalendarproject.Common;
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
                    .baseUrl(Common.API_END_POINT)
                    .addConverterFactory(
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
        return mWs.register(u);
    }

    /**
     * Log the user in
     *
     * @param u User to be logged in
     * @return Call
     */
    public retrofit.Call<Void> login(User u) {
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
        return mWs.listEvents();
    }

    /**
     * Create an event
     *
     * @return Call
     */
    public Call<Void> insertEvent(Event event) {
        return mWs.insertEvent(event);
    }

    public Call<Void> logout() {
        Log.i(TAG, "logging out");
        return mWs.logout();
    }

}
