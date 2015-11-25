package epsi.fx.com.simplecalendarproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by fx on 25/11/2015.
 */
public abstract class GenericAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;

    public GenericAdapter(Context context, List<T> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.isEmpty() ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
