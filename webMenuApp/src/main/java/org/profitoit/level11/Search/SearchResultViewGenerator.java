package org.webmenu.level11.Search;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import org.webmenu.webmenuapp.R;

/**
 * Created by EK on 15.1.19.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SearchResultViewGenerator extends ListFragment
        implements SearchView.OnQueryTextListener, SearchView.OnCloseListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    // This is the Adapter being used to display the list's data.
    SimpleCursorAdapter mAdapter;
    String query;

    // The SearchView for doing filtering.
    SearchView mSearchView;

    // If non-null, this is the current filter the user has provided.
    String mCurFilter;

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        setEmptyText("Nor results is found for query :" + query);

        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.search_menu_item, null,
                new String[] {SearchDatabase.SearchDb.COLUMN_NAME_TITLE, SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION},
                new int[] { R.id.searchMenuTitle, R.id.searchMenuTitleSummary}, 0);
        setListAdapter(mAdapter);

        // Start out with a progress indicator.
        setListShown(false);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    public static class MySearchView extends SearchView {
        public MySearchView(Context context) {
            super(context);
        }

        // The normal SearchView doesn't clear its search text when
        // collapsed, so we will do this for it.
        @Override
        public void onActionViewCollapsed() {
            setQuery("", false);
            super.onActionViewCollapsed();
        }
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Place an action bar item for searching.
        MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        mSearchView = new MySearchView(getActivity());
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
        mSearchView.setIconifiedByDefault(true);
        item.setActionView(mSearchView);
    }

    public boolean onQueryTextChange(String newText) {
        // Called when the action bar search text has changed.  Update
        // the search filter, and restart the loader to do a new query
        // with this filter.
        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
        // Don't do anything if the filter hasn't actually changed.
        // Prevents restarting the loader when restoring state.
        if (mCurFilter == null && newFilter == null) {
            return true;
        }
        if (mCurFilter != null && mCurFilter.equals(newFilter)) {
            return true;
        }
        mCurFilter = newFilter;
        getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    @Override public boolean onQueryTextSubmit(String query) {
        // Don't care about this.
        return true;
    }

    @Override
    public boolean onClose() {
        if (!TextUtils.isEmpty(mSearchView.getQuery())) {
            mSearchView.setQuery(null, true);
        }
        return true;
    }

    @Override public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        Log.i("FragmentComplexList", "Item clicked: " + id);
    }

    /*// These are the Contacts rows that we will retrieve.
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            Contacts.CONTACT_STATUS,
            Contacts.CONTACT_PRESENCE,
            Contacts.PHOTO_ID,
            Contacts.LOOKUP_KEY,
    };*/

    static final String[] SEARCH_QUERY_PROJECTION = new String[] {
            SearchDatabase.SearchDb._ID,
            SearchDatabase.SearchDb.COLUMN_ID,
            SearchDatabase.SearchDb.COLUMN_NAME_ID,
            SearchDatabase.SearchDb.COLUMN_NAME_TITLE,
            SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION,
            SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_URI,
            SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_URI_FILTER,
            SearchDatabase.SearchDb.COLUMN_NAME_UPDATED,
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        Uri baseUri;
        // Uri uri = Uri.parse("android.resource://com.example.myapp/" + R.raw.my_resource");
        // Uri uri = Uri.parse("android.resource://com.example.myapp/raw/my_resource");
        if (mCurFilter != null) {
            baseUri = Uri.withAppendedPath(Uri.parse(SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_URI_FILTER),
                    Uri.encode(mCurFilter));
        } else {
            baseUri = Uri.parse(SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_URI);
        }

        query = "a";
        String[] selectionArgs = new String[0];
        String selectionQuery;
        int type = 0;
        if (type == 0) {
            selectionArgs = new String[]{query, query};
            selectionQuery = SearchDatabase.SearchDb.COLUMN_NAME_TITLE + " LIKE ? OR " + SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + " LIKE ?";
        } else if(type == 1) {
            selectionArgs[0] = query;
            selectionQuery = SearchDatabase.SearchDb.COLUMN_NAME_TITLE + " LIKE ?";
        } else if (type == 2) {
            selectionArgs[0] = query;
            selectionQuery = SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + " LIKE ?";
        }

        selectionArgs = null;
        selectionQuery = null;

        return new CursorLoader(getActivity(), baseUri,
                SEARCH_QUERY_PROJECTION, selectionQuery, selectionArgs,

                SearchDatabase.SearchDb.COLUMN_NAME_CONTENT_URI + " COLLATE LOCALIZED ASC");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);

        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }
}
