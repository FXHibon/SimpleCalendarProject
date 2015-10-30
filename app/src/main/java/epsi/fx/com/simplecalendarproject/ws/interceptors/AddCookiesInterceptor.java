package epsi.fx.com.simplecalendarproject.ws.interceptors;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import epsi.fx.com.simplecalendarproject.Common;

/**
 * Created by fx on 29/10/2015.
 */
public class AddCookiesInterceptor implements Interceptor {

    public static final String TAG = AddCookiesInterceptor.class.getName();
    public static final String COOKIE_HEADER_NAME = "Cookie";

    private Context mContext;

    public AddCookiesInterceptor(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        SharedPreferences prefs = mContext.getSharedPreferences(Common.PREFS_SCOPE, Context.MODE_PRIVATE);
        Set<String> preferences = prefs.getStringSet("cookies", new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader(COOKIE_HEADER_NAME, cookie);
            Log.v(TAG, String.format("Adding header %s", cookie));
        }
        return chain.proceed(builder.build());
    }
}
