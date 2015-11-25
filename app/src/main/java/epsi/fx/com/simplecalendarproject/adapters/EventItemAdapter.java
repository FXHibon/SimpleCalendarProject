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

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.User;
import epsi.fx.com.simplecalendarproject.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fx on 16/10/2015.
 */
public class EventItemAdapter extends GenericAdapter<Event> {

    private final String TAG = EventItemAdapter.class.getName();


    private DateTimeFormatter mDateFormatter = DateTimeFormat.mediumDateTime().withLocale(Locale.FRANCE);

    public EventItemAdapter(Context context, List<Event> events) {
        super(context, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_event_list_item, null);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.event_item_title);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.event_item_desc);
        TextView tvDate = (TextView) convertView.findViewById(R.id.event_item_date);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.event_item_author);

        Event event = getItem(position);

        if (event == null) {
            return null;
        }

        tvTitle.setText(event.getTitle());
        tvDesc.setText(event.getDescription());
        tvDate.setText(event.getBegin().toString(mDateFormatter));
        String name = event.getAuthor().toString().substring(0, 4);
        tvAuthor.setText(name);

        return convertView;
    }
}
