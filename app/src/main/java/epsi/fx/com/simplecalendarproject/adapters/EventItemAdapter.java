package epsi.fx.com.simplecalendarproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Event;

/**
 * Created by fx on 16/10/2015.
 */
public class EventItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<Event> mEvents;

    public EventItemAdapter(Context context, List<Event> events) {
        mContext = context;
        mEvents = events;
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
        tvDate.setText(event.getBegin().toString());
        tvAuthor.setText(event.getAuthor().toString());

        return convertView;
    }
}
