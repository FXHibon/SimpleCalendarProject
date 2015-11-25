package epsi.fx.com.simplecalendarproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Participant;

/**
 * Created by fx on 25/11/15.
 */
public class ParticipantItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<Participant> mList;

    public ParticipantItemAdapter(Context context, List<Participant> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return (getCount() <= position - 1) ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_participant_list_item, null);
        }

        Participant item = (Participant) getItem(position);
        if (item == null) {
            return convertView;
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.participant_item_name);
        TextView statusView = (TextView) convertView.findViewById(R.id.participant_item_status);


        nameView.setText(item.getId().toString().substring(0, 4));
        statusView.setText(item.getStatus().toString());

        return convertView;
    }
}
