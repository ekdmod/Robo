package org.webmenu.level11.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.webmenu.webmenuapp.R;

/**
 * Created by EK on 15.1.17.
 */
public class MenuSearchSimpleCursorAdapterForDB extends SimpleCursorAdapter {


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MenuSearchSimpleCursorAdapterForDB(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View searchMenuItem = View.inflate(context, R.layout.search_menu_item, null);
        ImageView icon = (ImageView) searchMenuItem.findViewById(R.id.searchMenuIconView);
        Log.v("COLUMN_TITLE",cursor.getString(2));
        TextView searchMenuTitle = (TextView) searchMenuItem.findViewById(R.id.searchMenuTitle);
        // searchMenuTitle.setText(menuTitle);
        TextView summaryTxt = (TextView) searchMenuItem.findViewById(R.id.searchMenuTitleSummary);
        return searchMenuItem;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        View searchMenuItem = View.inflate(context, R.layout.search_menu_item, null);
        ImageView icon = (ImageView) searchMenuItem.findViewById(R.id.searchMenuIconView);
        TextView searchMenuTitle = (TextView) searchMenuItem.findViewById(R.id.searchMenuTitle);
        // searchMenuTitle.setText(menuTitle);
        TextView summaryTxt = (TextView) searchMenuItem.findViewById(R.id.searchMenuTitleSummary);
    }
}
