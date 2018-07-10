package org.webmenu.level7;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.webmenu.webmenuapp.DialogFragment.DialogExitApp;
import org.webmenu.webmenuapp.R;
import org.webmenu.webmenuapp.Searching.SearchablesDialogFragment;
import org.webmenu.webmenuapp.menu.FourImagesMenu;
import org.webmenu.webmenuapp.menu.Rotating3DListMenu;
import org.webmenu.webmenuapp.menu.Rotating3DListMenuForNews;
import org.webmenu.webmenuapp.menu.ScrollMenu;
import org.webmenu.webmenuapp.menu.ScrollRelativeLayout;


public class ActiveMenu extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private String[] mItems;
	private String editTextView;
	public static boolean themeChange = false;
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		changeTheme();
		//String themePref = sharedPref.getString("theme_preferences", "");
		//setTheme(Integer.parseInt(String.valueOf(R.style.AppTheme)));
	//	setTheme(Integer.parseInt( sharedPref.getString("theme_preferences", String.valueOf(R.style.AppBaseTheme))));		
		setContentView(R.layout.compatible_left_top);
		setToFront();
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
	//	mNavigationDrawerFragment.setToFront();
		mTitle = getTitle();
		mItems = getResources().getStringArray(R.array.nav_drawer_items);
		
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		Bundle args = new Bundle();
		Log.d("WHERE_TO", "Pos:" + position);
		switch(position) {
			case 0:
				Intent about_us = new Intent();
				break;
			case 1:
				String title = "Products";
				Intent products = new Intent();
				break;
			case 2:
				// News
				Intent newsFragment = new Intent(this, Rotating3DListMenuForNews.class);
				newsFragment.putExtra("MENU_ID", position);
				startActivity(newsFragment);
				break;
			case 3:
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						new ScrollMenu()).commit();
				break;
			case 4:
				String title_4 = "Services";
				break;
			case 5:
				Intent support = new Intent(this, Rotating3DListMenu.class);
				support.putExtra("MENU_ID", position);
				startActivity(support);
				break;
			case 6:
				Intent tech_desc = new Intent(this, ScrollRelativeLayout.class);
				startActivity(tech_desc);
				break;
			case 7:
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						new ScrollMenu()).commit();
				break;
			case 8:
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						new ScrollMenu()).commit();
				break;
			case 9:
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						new ScrollMenu()).commit();
				break;
			case 10:
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						new ScrollMenu()).commit();
				break;
			default:
				fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new ScrollMenu()).commit();
		}
	}

	public void onSectionAttached(int position) {
		mTitle = mItems[position -1];
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		View customNav = LayoutInflater.from(this).inflate(R.layout.actionbar_search_view, null); // layout which contains your button.
		actionBar.setCustomView(customNav, lp);
		actionBar.setDisplayShowCustomEnabled(true);
		final EditText editText = (EditText) findViewById(R.id.ab_search);
		editText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
				//	Dialog query_results = new SearchablesDialogFragment(getApplicationContext(), R.style.Dialog, editText.getText());
				//	query_results.show();
					editText.setVisibility(View.INVISIBLE);
					editTextView = editText.getText().toString();
					showSearchQuery(editText);
					return true;
				}
				return false;
			}
		});
		if(themeChange) {
			changeTheme();
		}
	}
	
	public void setToFront() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new FourImagesMenu()).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.active_menu_v7, menu);
			getMenuInflater().inflate(R.menu.v7_settings_menu, menu);
			restoreActionBar();
		}
	    
		return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(themeChange) {
			changeTheme();
		}
		if (id == R.id.editTextSearch) {
			EditText editText = (EditText) findViewById(R.id.ab_search);
			if(!editText.isShown()) {
				showSoftKeyboard(editText);
				editText.setVisibility(View.VISIBLE);
			} else {
				editText.setVisibility(View.INVISIBLE);
			}
			return true;
		}
		if (id == R.id.feedback) {
			Intent Email = new Intent(Intent.ACTION_SEND);
	        Email.setType("text/email");
	        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "admin@webmenu.com" });
	        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
	        Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
	        startActivity(Intent.createChooser(Email, "Send Feedback:"));
	        return true;
		}
		if (id == R.id.theme_settings) {
		//	Intent setting_menu = new Intent(this,SettingsActivity.class);
			Intent settings_activity = new Intent(this,SettingsActivity.class);
			// set an exit transition
		//	getWindow().setExitTransition(new Explode());
			startActivity(settings_activity);
			//	startActivity(setting_menu);
			return true;
		}
		if (id == R.id.closeApplication) {
			new DialogExitApp();
			DialogFragment exitApp = DialogExitApp.newInstance(R.string.exitApp);
			exitApp.setCancelable(true);
			exitApp.show(getSupportFragmentManager(), "dialog");
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void queryYou(View view){
/*		EditText query = (EditText) findViewById(R.id.ab_search);
		Log.w("Auto-Complete-text", "Text: " + query.getText());
		Dialog query_results = new SearchablesDialogFragment(this, R.style.Dialog, query.getText());
		query_results.show();*/
		EditText editText = (EditText) findViewById(R.id.ab_search);
		String txtQry = editText.getText().toString();
		if(!editText.isShown()) {
			editText.setVisibility(View.VISIBLE);
			showSoftKeyboard(editText);
			editTextView = txtQry;
		} else {
			if(editText.getText().length() != 0 && !txtQry.equals(editTextView)) {
				Dialog dialog_1 = new SearchablesDialogFragment(this, R.style.Dialog, editText.getText());
				dialog_1.show();
			}
			hideSoftKeyboard(editText);
			editText.setVisibility(View.INVISIBLE);
		}
	//	if(editText.isFocused()) {
	//		editText.setVisibility(View.INVISIBLE);
	//	} else {
	//		showSoftKeyboard(editText);
	//	}
/*		Bundle args = new Bundle();
		Intent queryYou = new Intent(this, SearchableActivity.class);
		queryYou.putExtra("Main-search", 0);
		startActivity(queryYou);*/
	}
	
	private void showSearchQuery(EditText editTxt) {
		Dialog dialog_1 = new SearchablesDialogFragment(this, R.style.Dialog, editTxt.getText());
		dialog_1.setCanceledOnTouchOutside(true);
		dialog_1.show();
	}
	
	public void changeTheme(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		int id = Integer.parseInt(sharedPref.getString("theme_preferences", "1"));
		int themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
		switch(id){
			case 0:
				themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
			break;
			case 1:
				themeId = R.style.AppTheme;
				break;
			case 2:
				themeId = R.style.Dialog;
				break;
			case 3:
				themeId = R.style.Theme_AppCompat_Light;
				break;
			case 4:
				themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
				break;
			case 5:
				themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
				break;
			case 6:
				themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
				break;
			case 7:
				themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
				break;
			case 8:
				themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
				break;
			case 9:
				themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
				break;
			case 10:
				themeId = R.style.Theme_AppCompat_Light_DarkActionBar;
				break;
		}
		boolean result = false;
		themeChange = false;
	    try {
	        int test = getResources().getIdentifier(String.valueOf(themeId),"style",getPackageName());
	        if (test != 0) result = true;
	    }
	    //this should be "catch" and not "finally"
	    catch(Exception e) {
	        result = false;
	        Log.e("Style does not exists :", e.getMessage());
	    }
		setTheme(themeId);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_active_menu,
					container, false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((ActiveMenu) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}
	
/*	private void closeApplication() {
		new AlertDialog.Builder(this)
	    .setMessage("Exit apllication?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            System.exit(0);
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.cancel();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}*/
	
	public void hideSoftKeyboard(View view) {
	    if (view.requestFocus()) {
	        InputMethodManager imm = (InputMethodManager)
	                getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
	    }
	}
	
	public void showSoftKeyboard(View view) {
	    if (view.requestFocus()) {
	        InputMethodManager imm = (InputMethodManager)
	                getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	    }
	}

}
