package epsi.fx.com.simplecalendarproject.ws.interceptors;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import epsi.fx.com.simplecalendarproject.AppConfig;

/**
 * Created by fx on 29/10/2015.
 * Register cookie from response
 */
public class SetCookiesInterceptor implements Interceptor {

    public static final String TAG = SetCookiesInterceptor.class.getName();
    public static final String SET_COOKIE_HEADER_NAME = "Set-Cookie";

    private Context mContext;

    public SetCookiesInterceptor(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers(SET_COOKIE_HEADER_NAME).isEmpty()) {
            Set<String> cookies = new HashSet<>();
            for (String cookie : originalResponse.headers(SET_COOKIE_HEADER_NAME)) {
                cookies.add(cookie);
                Log.v(TAG, String.format("Setting cookie: %s", cookie));
            }
            SharedPreferences.Editor simpleCalendar = mContext.getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE).edit();
            simpleCalendar.putStringSet(AppConfig.COOKIES_KEY, cookies).apply();
        }

        return originalResponse;
    }
}
