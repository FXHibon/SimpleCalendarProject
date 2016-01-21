package epsi.fx.com.simplecalendarproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import epsi.fx.com.simplecalendarproject.R;
import epsi.fx.com.simplecalendarproject.beans.Participant;

import static epsi.fx.com.simplecalendarproject.adapters.EventItemAdapter.findUserName;

/**
 * Created by fx on 25/11/15.
 * Participant item adapter
 */
public class ParticipantItemAdapter extends GenericAdapter<Participant> {

    public ParticipantItemAdapter(Context context, List<Participant> mList) {
        super(context, mList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_participant_list_item, null);
        }

        Participant item = getItem(position);
        if (item == null) {
            return convertView;
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.participant_item_name);
        TextView statusView = (TextView) convertView.findViewById(R.id.participant_item_status);


        nameView.setText(findUserName(item.getId()));
        statusView.setText(item.getStatus().toString());

        return convertView;
    }
}
