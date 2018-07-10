package org.webmenu.level7;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.webmenu.webmenuapp.R;

import java.util.ArrayList;
import java.util.List;

public class WebViewDataItem extends Activity
{
	public final static String ARGUMENT_URL = "ARGUMENT_URL";
	String mURL;
	WebView mWebView;
    List<SatelliteMenuItem> satMenuItems = new ArrayList<SatelliteMenuItem>();
    String[] satMenuIds;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view);
        final ViewGroup rl = (ViewGroup) findViewById(R.id.satMenu);
        RelativeLayout.LayoutParams params_0 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        satMenuIds = getResources().getStringArray(R.array.cplus_knowledge_menu_id);
/*        View hideButton = findViewById(R.id.hideMenu);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rl.getVisibility() == View.VISIBLE) {
                    rl.setVisibility(View.INVISIBLE);
                } else {
                    rl.setVisibility(View.VISIBLE);
                }
            }
        });*/
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
		mWebView = (WebView) findViewById(R.id.web_view);
        SatelliteMenu sm = new SatelliteMenu(this);
        int search = R.drawable.search;
        int search_res = R.id.search;
        SatelliteMenuItem search_item = new SatelliteMenuItem(search_res, search);
        satMenuItems.add(search_item);
        for (int i = 0; i < 1; i++) {
            int drawable = R.drawable.abc_btn_radio_to_on_mtrl_015;
            int id = getResources().getIdentifier(satMenuIds[i],"id",getPackageName());
            SatelliteMenuItem item = new SatelliteMenuItem(id, drawable);
            satMenuItems.add(item);
        }
        sm.addItems(satMenuItems);
        sm.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {
            @Override
            public void eventOccured(int id) {
                Toast hAnim = Toast.makeText(getBaseContext(), "Enter for: " + id, Toast.LENGTH_LONG);
                hAnim.show();
            }
        });
        sm.setLayoutParams(params);
		mURL = getIntent().getStringExtra(ARGUMENT_URL);
		if (savedInstanceState != null) {
		    //  mURL = savedInstanceState.getString("url", "");
		}
        rl.addView(sm);
		if(!mURL.trim().equalsIgnoreCase("")){
			mWebView = (WebView) findViewById(R.id.web_view);
			mWebView.getSettings().setJavaScriptEnabled(false);
			mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setUseWideViewPort(true);
			mWebView.setWebViewClient(new MyWebViewClient());
			mWebView.loadUrl(mURL.trim());
		 }
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("url", mURL);
	}
	
	 public void setURLContent(String url) {
		 mURL = url;
	}

	public void updateURLContent(String url) {
		  mURL = url;
		  mWebView = (WebView) findViewById(R.id.web_view);
//		  myWebView.getSettings().setJavaScriptEnabled(true);
		  mWebView.setWebViewClient(new MyWebViewClient());
		  mWebView.loadUrl(url.trim());
	}
		 
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        if (Uri.parse(url).getHost().equals("www.example.com")) {
	            // This is my web site, so do not override; let my WebView load the page
	            return false;
	        }
	        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
	        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	        startActivity(intent);
	        return true;
	    }
	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
	        mWebView.goBack();
	        return;
	    }
	    super.onBackPressed();
	}

	@Override
	protected void onNewIntent(Intent intent) {		
		super.onNewIntent(intent);
		mURL = intent.getStringExtra(ARGUMENT_URL);
	}	
	
}
