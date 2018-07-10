package org.webmenu.level11.Search;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.webmenu.level11.Adapters.SearchableDialogAdapter;
import org.webmenu.webmenuapp.R;

/**
 * Created by EK on 15.1.15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SearchResults extends ListFragment {

    public String search;
    // public SearchViewAdapter adapterView;
    public SearchableDialogAdapter searchableDialogAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("Nor results for :" + search);
        int count = 0;
        SearchDatabase searchResDB = new SearchDatabase(getActivity());
        // dPAdapter = new SearchableDialogAdapter(getActivity());
        // dPAdapter.results = queryDb.readFromDb(search, selections);
        // Cursor mCur = queryDb.readFromDb(search, selections);
        if (search != null) {
            searchResDB.execSelectQuery(search, 0);
            Cursor c = searchResDB.cursor;
            if (c != null) {
                count = c.getCount();
                // adapterView = new SearchViewAdapter(getActivity(), c);
                searchableDialogAdapter = new SearchableDialogAdapter(getActivity(), count, c);
                // searchableDialogAdapter.cursorResults = c;
                // searchableDialogAdapter.itemCnt = count;
                String[] columnResolver = new String[]{SearchDatabase.SearchDb.COLUMN_NAME_TITLE, SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION};
                int[] valRes = new int[]{R.id.searchMenuTitle, R.id.searchMenuTitleSummary};
                // mAdapter = new MenuSearchCursorAdapterForDB(getActivity(), R.layout.search_menu_item, c, columnResolver, valRes, CursorAdapter.IGNORE_ITEM_VIEW_TYPE);
                // mAdapter = new SearchableDialogAdapter(getActivity());
                setListAdapter(searchableDialogAdapter);
            }
        }
        setListShown(true);
        Toast result = Toast.makeText(getActivity(), count + " results found.", Toast.LENGTH_LONG);
        result.setGravity(Gravity.CENTER,0,0);
        result.show();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(),"Item id:" + id, Toast.LENGTH_SHORT).show();
    }
}
