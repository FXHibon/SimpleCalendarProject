package epsi.fx.com.simplecalendarproject.ws;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fx on 29/10/2015.
 */
public class AddCookiesInterceptor implements Interceptor {

    public static final String TAG = AddCookiesInterceptor.class.getName();

    private Context mContext;

    public AddCookiesInterceptor(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        SharedPreferences prefs = mContext.getSharedPreferences("SimpleCalendar", Context.MODE_PRIVATE);
        Set<String> preferences = (Set) prefs.getStringSet("cookies", new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v(TAG, "Adding header " + cookie);
        }
        return chain.proceed(builder.build());
    }
}
