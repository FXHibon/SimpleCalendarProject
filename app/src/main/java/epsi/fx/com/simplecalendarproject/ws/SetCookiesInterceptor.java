package epsi.fx.com.simplecalendarproject.ws;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fx on 29/10/2015.
 */
public class SetCookiesInterceptor implements Interceptor {

    public static final String TAG = SetCookiesInterceptor.class.getName();


    private Context mContext;

    public SetCookiesInterceptor(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            Set<String> cookies = new HashSet<>();
            for (String cookie : originalResponse.headers("Set-Cookie")) {
                cookies.add(cookie);
                Log.v(TAG, "Setting cookie: " + cookie);
            }
            SharedPreferences.Editor simpleCalendar = mContext.getSharedPreferences("SimpleCalendar", Context.MODE_PRIVATE).edit();
            simpleCalendar.putStringSet("cookies", cookies).apply();
        }

        return originalResponse;
    }
}
