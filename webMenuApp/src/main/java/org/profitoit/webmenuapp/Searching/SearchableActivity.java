package org.webmenu.webmenuapp.Searching;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;

public class SearchableActivity extends Activity {

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
		super.onNewIntent(intent);
	}

	@Override
	public boolean onSearchRequested() {
		// TODO Auto-generated method stub
		return super.onSearchRequested();
	}
	
	private void handleIntent(Intent intent) {
		
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

	
}
