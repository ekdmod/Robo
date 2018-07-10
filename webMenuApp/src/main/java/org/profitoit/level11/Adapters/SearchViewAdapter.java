package org.webmenu.level11.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import org.webmenu.webmenuapp.R;

/**
 * Created by EK on 15.1.21.
 */
public class SearchViewAdapter extends AdapterView {

    public String queryString;
    public Cursor adapterViewQuery;
    public Context mContext;

    public SearchViewAdapter(Context context, Cursor results) {
        super(context);
        mContext = context;
        adapterViewQuery = results;
    }

    @Override
    public Adapter getAdapter() {
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter) {

    }

    @Override
    public View getSelectedView() {
        View view = View.inflate(getContext(), R.layout.search_menu_item, null);
        TextView title = (TextView) view.findViewById(R.id.searchMenuTitle);
        TextView summary = (TextView) view.findViewById(R.id.searchMenuTitleSummary);
        ImageView icon = (ImageView) view.findViewById(R.id.searchMenuIconView);
        return view;
    }

    @Override
    public void setSelection(int i) {

    }

}
