package epsi.fx.com.simplecalendarproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fx on 16/10/2015.
 */
public class EventItemAdapter extends BaseAdapter {

    private final String TAG = EventItemAdapter.class.getName();

    private final ApiClient mApiClient;
    private Context mContext;
    private List<Event> mEvents;

    private DateTimeFormatter mDateFormatter = DateTimeFormat.mediumDateTime().withLocale(Locale.FRANCE);
    private List<User> mUsers;

    public EventItemAdapter(Context context, List<Event> events) {
        mContext = context;
        mEvents = events;
        mApiClient = new ApiClient(mContext);
        mUsers = new ArrayList<>();
        mApiClient.listUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                Log.i(TAG, "onResponse list users");
                if (response.isSuccess()) {
                    mUsers = response.body();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, String.format("Error listing users: %s", t.getLocalizedMessage()));
            }
        });
    }


    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return (getCount() <= position - 1) ? null : mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.event_item, null);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.event_item_title);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.event_item_desc);
        TextView tvDate = (TextView) convertView.findViewById(R.id.event_item_date);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.event_item_author);

        Event event = (Event) getItem(position);

        if (event == null) {
            return null;
        }

        tvTitle.setText(event.getTitle());
        tvDesc.setText(event.getDescription());
        tvDate.setText(event.getBegin().toString(mDateFormatter));
        String name = event.getAuthor().toString().substring(0, 4);
        for (User u : mUsers) {
            if (u.getId().equals(event.getAuthor())) {
                name = u.getName();
                break;
            }
        }
        tvAuthor.setText(name);

        return convertView;
    }
}
