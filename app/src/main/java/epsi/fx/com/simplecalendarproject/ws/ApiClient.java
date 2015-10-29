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

    private Context mContext;
    private final WebService ws;

    public ApiClient(Context ctx) {
        mContext = ctx;
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors()
                .add(new AddCookiesInterceptor(mContext));
        okHttpClient.interceptors()
                .add(new SetCookiesInterceptor(mContext));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://epsi5-android.cleverapps.io")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        ws = retrofit.create(WebService.class);
    }

    public retrofit.Call<Void> register(User u) {
        return ws.register(u);
    }

    public retrofit.Call<Void> login(User u) {
        Login login = new Login();
        login.setEmail(u.getEmail());
        login.setPassword(u.getPassword());
        return ws.login(login);
    }

    public Call<List<Event>> listEvents() {
        return ws.listEvents();
    }
}
