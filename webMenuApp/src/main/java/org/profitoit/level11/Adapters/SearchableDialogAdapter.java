package org.webmenu.level11.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.webmenu.webmenuapp.R;

import java.util.ArrayList;

public class SearchableDialogAdapter extends BaseAdapter {
    private Context mContext;
    public Cursor cursorResults;
    public int pos;
    private String[][] stringArrayRes;
    public boolean end;
    public int count;

    public SearchableDialogAdapter(Context context) {
        mContext = context;
        count = cursorResults.getCount();
    }

    public SearchableDialogAdapter(Context context, int itemCount, Cursor c) {
        mContext = context;
        cursorResults = c;
        pos = 0;
        count = itemCount;
        stringArrayRes = new String[itemCount][5];
        do {
            stringArrayRes[pos][0] = cursorResults.getString(1);
            stringArrayRes[pos][1] = cursorResults.getString(3);
            stringArrayRes[pos][2] = cursorResults.getString(4);
            pos++;
        } while (cursorResults.moveToNext());
    }

	public int getCount() {
        return cursorResults.getCount();
	}

    public Object getItem(int position) {
	//	return matches.get(position);
        return null;
	}

	public long getItemId(int position) {
		return position;
	}

    public View getView(int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid unnecessary calls
		// to findViewById() on each row.
		// int type = getItemViewType(position);
		ViewHolder holder;
			// When convertView is not null, we can reuse it directly, there is no need
			// to reinflate it. We only inflate a new View when the convertView supplied
			// by ListView is null.
		if (!end) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext,R.layout.search_menu_item, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.searchMenuIconView);
            int iconDrawableId = mContext.getResources().getIdentifier(stringArrayRes[position][0] + "_icon","drawable", mContext.getPackageName());
            holder.icon.setBackgroundResource(iconDrawableId);
            holder.textTitle = (TextView) convertView.findViewById(R.id.searchMenuTitle);
            holder.textTitle.setTextSize(16);
            holder.textTitle.setText(stringArrayRes[position][1]);
            holder.textSummary = (TextView) convertView.findViewById(R.id.searchMenuTitleSummary);
            holder.textSummary.setTextSize(13);
            holder.textSummary.setText(stringArrayRes[position][2]);
            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            // Bind the data efficiently with the holder.
            convertView.setTag(holder);
            if(position == count)
                end = true;
            // pos = cursorResults.getPosition();
		} else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView textTitle;
        TextView textSummary;
	}
	 	
}
