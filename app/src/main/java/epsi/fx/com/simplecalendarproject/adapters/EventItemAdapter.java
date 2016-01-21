package epsi.fx.com.simplecalendarproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.beans.dao.UserDao;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fx on 16/10/2015.
 */
public class EventItemAdapter extends GenericAdapter<Event> {

    private static final String TAG = EventItemAdapter.class.getName();


    private static DateTimeFormatter mDateFormatter = DateTimeFormat.mediumDateTime().withLocale(Locale.FRANCE);
    private static UserDao mUserDao;

    public EventItemAdapter(Context context, List<Event> events) {
        super(context, events);
        Log.d(TAG, "EventItemAdapter() called with: " + "context = [" + context + "], events = [" + events + "]");
        if (mUserDao == null) {
            mUserDao = new UserDao(context);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_event_list_item, null);
        }

        Event event = getItem(position);

        return buildView(convertView, event);

    }

    public static View buildView(View convertView, Event event) {
        Log.d(TAG, "buildView() called with: " + "convertView = [" + convertView + "], event = [" + event + "]");
        TextView tvTitle = (TextView) convertView.findViewById(R.id.event_item_title);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.event_item_desc);
        TextView tvDate = (TextView) convertView.findViewById(R.id.event_item_date);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.event_item_author);

        if (event == null) {
            return null;
        }

        tvTitle.setText(event.getTitle());
        tvDesc.setText(event.getDescription());
        tvDate.setText(event.getBegin().toString(mDateFormatter));
        tvAuthor.setText(findUserName(event.getAuthor()));
        return convertView;
    }

    public static String findUserName(UUID authorId) {
        User userById = mUserDao.getUserById(authorId.toString());
        return (userById == null) ? authorId.toString().substring(0, 4) : userById.getName();
    }
}
