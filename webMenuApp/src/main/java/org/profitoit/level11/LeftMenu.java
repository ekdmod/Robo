package org.webmenu.level11;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.webmenu.CloseApp;
import org.webmenu.level11.Adapters.NavigationDrawerMenuAdapter;
import org.webmenu.level11.Data.Database;
import org.webmenu.level11.Keyboard.SoftKeyboard;
import org.webmenu.level11.Menu.FourImagesMenu;
import org.webmenu.level11.Menu.Rotating3DListMenu;
import org.webmenu.level11.Menu.Rotating3DListMenuForNews;
import org.webmenu.level11.Menu.ScrollMenu;
import org.webmenu.level11.Search.SearchActivity;
import org.webmenu.level11.Search.VoiceSearchDialog;
import org.webmenu.webmenuapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LeftMenu extends ActionBarActivity {
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
    private Intent keyboardService;
	private View mFragmentContainerView;
	private int mCurrentSelectedPosition = 0;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;
    private static final int SPEECH_REQUEST_CODE = 0;
    private int searchViewType = 0;
    private InputMethodManager inputMethodManager;
    private FragmentManager fragmentManager;
    private String editTextView;
	public static boolean recreate;
    public static boolean icons_changed;
    public static boolean textSize_changed;
    public static boolean theme_changed;
    public static int themeId = 0;
	private ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences sharedPreferences;
    private FourImagesMenu mainView;
    private ScrollMenu scrollMenu;
	private CharSequence mDrawerTitle;     
	private CharSequence mTitle;
	private String[] mItems;
    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";

    @Override
	public void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        fragmentManager = getFragmentManager();
        setThemeId();
        changeTextSize();
        super.onCreate(savedInstanceState);
		setContentView(R.layout.nawigation_drawer);
		mItems = getResources().getStringArray(R.array.nav_drawer_items);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mTitle = mDrawerTitle = getTitle();
		mDrawerListView = (ListView) findViewById(R.id.left_drawer);
		// Set the adapter for the list view         
	//	mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mItems)); 
		mDrawerListView.setAdapter(new NavigationDrawerMenuAdapter(this));
	//	mDrawerList.setItemChecked(mCurrentSelectedPosition, true);
		// Set the list's click listener
		mDrawerListView.setOnItemClickListener(new NavigationListViewClickListener());
        //scrollMenu.list = data;
	//	actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
		//	actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
		SetupDrawerView();
//        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mainView = (FourImagesMenu) fragmentManager.findFragmentByTag("about");
        scrollMenu = (ScrollMenu) fragmentManager.findFragmentByTag("knowledge");
        if (savedInstanceState == null) {
            mainView = new FourImagesMenu();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Database database = new Database(this);
            scrollMenu = new ScrollMenu();
            scrollMenu.data = database.readFromDb(null, null);
            fragmentTransaction.add(R.id.content_frame, scrollMenu, "knowledge");
            fragmentTransaction.add(R.id.content_frame, mainView, "about");
            fragmentTransaction.hide(scrollMenu);
            fragmentTransaction.commit();
        }
        String[] files;
        int u = 0;
        try {
            FileOutputStream output = new FileOutputStream("/data/data/org.webmenu.webmenuapp/Bitwise_Operators_IN_PHP.html");
            InputStream fileInput = getClass().getResourceAsStream("/data/php/Bitwise_Operators_IN_PHP.html");
            byte[] bytes = new byte[fileInput.available()];
            output.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("/");
        Log.v("write", file.canWrite() + "");
        files = file.list();
        while (u < files.length) {
            Log.d("path:", files[u]);
            u++;
        }
	}

	private class NavigationListViewClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			   onNavigationDrawerItemSelected(position);
		} 
	}

    @Override
	public void setTitle(CharSequence title) {     
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	//	setTitle(mTitle); 
	}

	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hide();
        switch(position) {
			case 0:
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
               // transaction.replace(R.id.content_frame, mainView);
                transaction.show(mainView);
                //transaction.addToBackStack("about");
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
                // transaction.replace(R.id.content_frame,	scrollMenu);
               // transaction.addToBackStack("knowledge");
                transaction.show(scrollMenu);
                //transaction.addToBackStack("knowledge");
				break;
			case 4:
				String title_4 = "Services";
				break;
			case 5:
				Intent support = new Intent(this, Rotating3DListMenu.class);
				support.putExtra("MENU_ID", position);
				startActivity(support);
				break;
			case 7:
                transaction.replace(R.id.content_frame,
                        scrollMenu);
				break;
			case 8:
                transaction.replace(R.id.content_frame,
                        scrollMenu);
				break;
			case 9:
                transaction.replace(R.id.content_frame,
                        scrollMenu);
				break;
			case 10:
                transaction.replace(R.id.content_frame,
                        scrollMenu);
				break;
			default:
                transaction.replace(R.id.content_frame,
                        scrollMenu);
		}

		// Highlight the selected item, update the title, and close the drawer   
		mCurrentSelectedPosition = position;
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);     
			setTitle(mItems[position]);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mDrawerListView);
		}
        // Commit the transaction
        transaction.commit();
	}

    public void onSectionAttached(int position) {
		mTitle = mItems[position -1];
	}

    public void hide() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (mCurrentSelectedPosition) {
            case 0:
                transaction.hide(mainView);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                transaction.hide(scrollMenu);
                break;
        }
        transaction.commit();
    }
	
	public void hideSoftKeyboard(View view) {
	    if (view.requestFocus()) {
/*	        InputMethodManager imm = (InputMethodManager)
	                getSystemService(Context.INPUT_METHOD_SERVICE);*/
	        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
	    }
	}
	
	public void showSoftKeyboard(View view) {
	    if (view.requestFocus()) {
/*	        InputMethodManager imm = (InputMethodManager)
	                getSystemService(Context.INPUT_METHOD_SERVICE);*/
	        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	    }
	}

    public void changeInputMethod(View view, int inputMethodId) {
      //  if(view.requestFocus()) {
            switch (inputMethodId) {
                case 0:
                    inputMethodManager.setInputMethod(view.getWindowToken(), "samsung_keyboard");
                    break;
                case 1:
                    inputMethodManager.setInputMethod(view.getWindowToken(), "search_keyboard");
                    break;
            }
      //  }
    }

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 *
     */
	public void SetupDrawerView() {
    /*	try {
			Field mDragger = mDrawerLayout.getClass().getDeclaredField(
			        "mLeftDragger");//mRightDragger for right obviously
			mDragger.setAccessible(true);
			ViewDragHelper draggerObj = (ViewDragHelper) mDragger
			        .get(mDrawerLayout);

			Field mEdgeSize = draggerObj.getClass().getDeclaredField(
			        "mEdgeSize");
			mEdgeSize.setAccessible(true);
			int edge = mEdgeSize.getInt(draggerObj);

			mEdgeSize.setInt(draggerObj, edge * 5); //optimal value as for me, you may set any constant in dp
			// set a custom shadow that overlays the main content when the drawer
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// opens
	//	mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
	//			Gravity.START);
	//	toolbar.setNavigationIcon(R.drawable.metro_black_e);
	//	toolbar.setLogo(R.drawable.metro_black_e);
	//	toolbar.inflateMenu(R.menu.v7_settings_menu);
	//	toolbar.setLogo(R.drawable.navigation_list_selector);
		// set up the drawer's list view with items and click listener
		
	//	ActionBar actionBar = getActionBar();
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	//	actionBar.setHomeAsUpIndicator(homeUpButton);
	//	actionBar.setDisplayUseLogoEnabled(true);
	//	actionBar.setDisplayShowTitleEnabled(true);
		View view = LayoutInflater.from(this).inflate(R.layout.search, null); // layout which contains your button.
      //view.findViewById(R.id.icon).setVisibility(View.INVISIBLE);
	//	actionBar.setLogo(R.drawable.metro_black_e);
		actionBar.setCustomView(view);
        actionBar.setDisplayShowCustomEnabled(true);
        final TextView search = (TextView) findViewById(R.id.searchTextView);
/*		final EditText editText = (EditText) findViewById(R.id.ab_search);
        editText.setTextColor(getResources().getColor(R.color.White));
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
		});*/
	//	actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
	//	actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.string.navigation_drawer_open, /*
										 * "open drawer" description for
										 * accessibility
										 */
		R.string.navigation_drawer_close /*
										 * "close drawer" description for
										 * accessibility
										 */
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				setTitle(mTitle);
                // actionBar.setDisplayShowCustomEnabled(true);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				setTitle(mTitle);
                // actionBar.setDisplayShowCustomEnabled(false);
				invalidateOptionsMenu();
			}
		};
		// Defer code dependent on restoration of previous instance state.
    /*	mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});*/

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}



    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mDrawerLayout.isDrawerOpen(mDrawerListView)) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
          //  getActionBar().setDisplayShowCustomEnabled(true);
		//	getMenuInflater().inflate(R.menu.v11_menu, menu);
		    getMenuInflater().inflate(R.menu.v11_settings, menu);
            if (searchViewType == 1) {
                MenuItem searchMenu = menu.findItem(R.id.editTextSearch);
                SearchView searchView = (SearchView) menu.findItem(R.id.editTextSearch).getActionView();
                //	restoreActionBar();
            }
		}
	    
		return super.onCreateOptionsMenu(menu);

	}

    public void checkPermissions() {
        PackageManager pm = getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName());
        if (hasPerm != PackageManager.PERMISSION_GRANTED) {

        }
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {         
		// Pass the event to ActionBarDrawerToggle, if it returns         
		// true, then it has handled the app icon touch event         
		if (mDrawerToggle.onOptionsItemSelected(item)) {           
		    return true;
		}
		// Handle your other action bar items...
		int id = item.getItemId();
        if(id == R.id.login) {
            Intent login = new Intent(this, LoginSceen.class);
            startActivity(login);
        }
        if (id == R.id.editTextSearch) {
            DialogFragment voiceSearchDialogFragment = new VoiceSearchDialog();
            voiceSearchDialogFragment.show(getFragmentManager(), "searchDialog");
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
			Intent pref_menu = new Intent(this,Preferences.class);
		//	Intent settings_activity = new Intent(this,SettingsActivity.class);
			startActivity(pref_menu);
			return true;
		}
		if (id == R.id.closeApplication) {
			DialogFragment exitApp = CloseApp.newInstance(R.string.exitApp);
			exitApp.setCancelable(true);
			exitApp.show(getFragmentManager(), "dialog");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    public void search(MenuItem menu) {
        Intent searchActivity = new Intent(this, SearchActivity.class);
        startActivity(searchActivity);
    }

	public void queryYou(MenuItem menu) {
    /*	EditText query = (EditText) findViewById(R.id.ab_search);
		Log.w("Auto-Complete-text", "Text: " + query.getText());
		Dialog query_results = new SearchablesDialogFragment(this, R.style.Dialog, query.getText());
		query_results.show();*//*
		EditText editText = (EditText) findViewById(R.id.ab_search);
		String txtQry = editText.getText().toString();
		if(!editText.isShown()) {
			// editText.setVisibility(View.VISIBLE);
            // DialogFragment showInputMethods = new InputMethodChooserDialog();
            DialogFragment voiceSearchDialogFragment = new VoiceSearchDialog();
            voiceSearchDialogFragment.show(getFragmentManager(), "searchDialog");
			// showSoftKeyboard(editText);
			editTextView = txtQry;
		} else {
			if(editText.getText().length() != 0 && !txtQry.equals(editTextView)) {
				Dialog dialog_1 = new SearchResults(this, R.style.Dialog, editText.getText());
				dialog_1.show();
			}
			hideSoftKeyboard(editText);
			editText.setVisibility(View.INVISIBLE);
		}*/
          //  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          //  imm.setInputMethod(getCurrentFocus().getWindowToken(), "search_keyboard");
          //  View searchLayout = getLayoutInflater().inflate(R.layout.search_layout, null);
          //  EditText searchText = (EditText) searchLayout.findViewById(R.id.editText);
           // DialogFragment voiceSearchDialogFragment = new VoiceSearchDialog();
         //  ViewGroup content = (ViewGroup) findViewById(R.id.content_frame);
         //  content.addView(searchLayout);
         //  showSoftKeyboard(searchText);
          //  voiceSearchDialogFragment.show(getFragmentManager(), "searchDialog");
      //  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      //  imm.setInputMethod(getCurrentFocus().getWindowToken(),"search_keyboard");
	//	if(editText.isFocused()) {
	//		editText.setVisibility(View.INVISIBLE);
	//	} else {
	//		showSoftKeyboard(editText);
	//	}
    /*	Bundle args = new Bundle();
		Intent queryYou = new Intent(this, SearchableActivity.class);
		queryYou.putExtra("Main-search", 0);
		startActivity(queryYou);*/
	}

    public void changeIcons() {
        int id = Integer.parseInt(sharedPreferences.getString("icons_preferences", "1"));
    }

    public void changeTextSize() {
        int id = Integer.parseInt(sharedPreferences.getString("text_preferences", "1"));
        int textStyleId = R.style.Normal;
        switch (id) {
            case 0:
                textStyleId = R.style.Normal;
            break;
            case 1:
                textStyleId = R.style.Small;
            break;
            case 2:
                textStyleId = R.style.Medium;
            break;
            case 3:
                textStyleId = R.style.Big;
            break;
        }
        getTheme().applyStyle(textStyleId,false);
    }
	
	public void setThemeId(){
		int id = Integer.parseInt(sharedPreferences.getString("theme_preferences", "1"));
		int themeId = R.style.BaseTheme;
		switch(id){
			case 0:
				themeId = R.style.BaseTheme;
			break;
			case 1:
				themeId = R.style.ThemeDark;
				break;
			case 2:
				themeId = R.style.ThemeBlue;
				break;
			case 3:
				themeId = R.style.ThemeRed;
				break;
			case 4:
				themeId = R.style.ThemeYellow;
				break;
			case 5:
				themeId = R.style.ThemeGreen;
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
				themeId = R.style.ThemeNight;
				break;
			case 10:
				themeId = R.style.ThemeLast;
				break;
		}
		boolean result = false;
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

    public void loadDatabase() {
        Database db = new Database(this);
        db.execSelectQuery("",0);
        scrollMenu.data = db.data;
        String[][] data = new String[db.data.getCount()][8];
        int pos = 0;
        do {
            data[pos][0] = db.data.getString(0);
            data[pos][1] = db.data.getString(1);
            data[pos][2] = db.data.getString(2);
            data[pos][3] = db.data.getString(3);
            data[pos][4] = db.data.getString(4);
            pos++;
        } while (db.data.moveToNext());
    }

    /* Called whenever we call invalidateOptionsMenu() */
	@Override     
	public boolean onPrepareOptionsMenu(Menu menu) {         
		// If the nav drawer is open, hide action items related to the content view         
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerListView);
    //    getActionBar().setDisplayShowCustomEnabled(false);
	//	menu.findItem(R.id.search).setVisible(!drawerOpen);         
		return super.onPrepareOptionsMenu(menu);     
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {         
    	super.onPostCreate(savedInstanceState);         
    	// Sync the toggle state after onRestoreInstanceState has occurred.         
    	mDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recreate) {
            recreate = false;
            recreate();
        }
    }

    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

    public static class InputMethodChooserDialog extends DialogFragment {

        public RecognizerIntent speechActivityIntent;

        private static final int SPEECH_REQUEST_CODE = 0;
        private static final int RESULT_OK = -1;
        public EditText editText;

        public InputMethodChooserDialog() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            RelativeLayout searchDialog = (RelativeLayout) View.inflate(getActivity().getBaseContext(),R.layout.input_method_chooser, null);
            editText = (EditText) searchDialog.findViewById(R.id.editText);
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (SpeechRecognizer.isRecognitionAvailable(getActivity())) {
                Drawable background_2 = imm.getEnabledInputMethodList().get(1).loadIcon(getActivity().getPackageManager());
                ImageButton speechInput = (ImageButton) searchDialog.findViewById(R.id.speech);
                speechInput.setImageDrawable(background_2);
                speechInput.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        hideSoftKeyboard(view);
                        displaySpeechRecognizer();
                        // InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        // String id = imm.getEnabledInputMethodList().get(1).getId();
                    }
                });
            }
            return searchDialog;
        }

        // Create an intent that can start the Speech Recognizer activity
        private void displaySpeechRecognizer() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // Start the activity, the intent will be populated with the speech text
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }

        // This callback is invoked when the Speech Recognizer returns.
        // This is where you process the intent and extract the speech text from the intent.
        @Override
        public void onActivityResult(int requestCode, int resultCode,
                                     Intent data) {
            if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
              //  List<String> results = data.getStringArrayListExtra(
              //          RecognizerIntent.EXTRA_RESULTS);
              //  editText.append(results.get(0));
                // Do something with spokenText
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

        public void hideSoftKeyboard(View view) {
            if (view.requestFocus()) {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
            }
        }

        public void showSoftKeyboard(View view, int inputMethodId) {
            if (view.requestFocus()) {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                switch(inputMethodId) {
                    case 0:
                        imm.setInputMethod(view.getWindowToken(), "samsung_keyboard");
                    case 1:
                        imm.setInputMethod(view.getWindowToken(), "search_keyboard");
                }
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }

    }
}