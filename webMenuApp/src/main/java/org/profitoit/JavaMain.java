package org.webmenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import org.webmenu.level11.LeftMenu;
import org.webmenu.level7.ActiveMenu;
import org.webmenu.webmenuapp.R;

@SuppressLint("NewApi")
public class JavaMain extends Activity {

	private CloseApp exitWin;
	private boolean startDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		exitWin = new CloseApp();
		exitWin = exitWin.newInstance(R.string.exitApp);
		exitWin.setCancelable(true);
		start();
	}

	public void start() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			Intent _new_ = new Intent(this, LeftMenu.class);
			startActivity(_new_);
		} else {
			Intent honey = new Intent(this, ActiveMenu.class);
			startActivity(honey);
		}
	}

	@Override
	protected void onPause() {	
		super.onPause();
		finish();
	}
	
}
