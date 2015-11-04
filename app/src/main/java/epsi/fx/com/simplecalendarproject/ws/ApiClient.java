package epsi.fx.com.simplecalendarproject.ws;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

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

    private static final String API_END_POINT = "http://epsi5-android.cleverapps.io";
    private static WebService ws;

    public ApiClient(Context ctx) {

        if (ws == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.interceptors()
                    .add(new AddCookiesInterceptor(ctx));
            okHttpClient.interceptors()
                    .add(new SetCookiesInterceptor(ctx));

            // Build API Client
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            ws = retrofit.create(WebService.class);
        }

    }

    /**
     * Register a new user
     *
     * @param u User to be registered
     * @return Call
     */
    public retrofit.Call<Void> register(User u) {
        return ws.register(u);
    }

    /**
     * Log the user in
     * @param u User to be logged in
     * @return Call
     */
    public retrofit.Call<Void> login(User u) {
        Login login = new Login();
        login.setEmail(u.getEmail());
        login.setPassword(u.getPassword());
        return ws.login(login);
    }

    /**
     * List all events
     * @return Call
     */
    public Call<List<Event>> listEvents() {
        return ws.listEvents();
    }

    /**
     * Create an event
     *
     * @return Call
     */
    public Call<Void> insertEvent(Event event) {
        return ws.insertEvent(event);
    }
}
