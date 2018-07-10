package org.webmenu.level11;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import org.webmenu.webmenuapp.R;
@TargetApi(11)
public class Preferences extends Activity {

    public int styleId;
    private SettingsFragment settingsFragment = new SettingsFragment();

	@Override     
	protected void onCreate(Bundle savedInstanceState) {         
		super.onCreate(savedInstanceState);          
		// Display the fragment as the main content.
		getFragmentManager().beginTransaction().replace(android.R.id.content, settingsFragment).commit();
	}

    public static class SettingsFragment extends PreferenceFragment {

        SharedPreferences.OnSharedPreferenceChangeListener sharedPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            if(key.equals("theme_preferences")) {
                // int themeId = Integer.parseInt(prefs.getString("theme_preferences", "1"));
                LeftMenu.theme_changed = true;
                LeftMenu.recreate = true;
               // LeftMenu.themeChange = true;
               // LeftMenu.themeId = getTheme(themeId);
            }
            if (key.equals("text_preferences")) {
                LeftMenu.textSize_changed = true;
                LeftMenu.recreate = true;
            }
            if (key.equals("icons_preferences")) {
                LeftMenu.icons_changed = true;
                LeftMenu.recreate = true;
            }
            }
        };

		@Override
        public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Load the preferences from an XML resource
            /*String settings = getArguments().getString("settings");*/
          /*  if ("main".equals(settings)) {*/
            addPreferencesFromResource(R.xml.settings);
//            } else if ("style".equals(settings)) {
                // addPreferencesFromResource(R.xml.settings);
//            } else if ("other".equals(settings)) {
//            }
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPrefListener);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPrefListener);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(sharedPrefListener);
        }
    }
}
