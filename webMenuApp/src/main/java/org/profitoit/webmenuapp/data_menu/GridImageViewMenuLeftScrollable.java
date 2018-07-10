package org.webmenu.webmenuapp.data_menu;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.webmenu.level7.Adapters.ImageAdapter;

public class GridImageViewMenuLeftScrollable extends ListFragment implements OnTouchListener {
	static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private int REL_SWIPE_MIN_DISTANCE; 
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;
    public final static int RIGHT_TO_LEFT=1;
    public final static int LEFT_TO_RIGHT=2;
    public final static int TOP_TO_BOTTOM=3;
    public final static int BOTTOM_TO_TOP=4;
    
    BaseAdapter adapter;
    GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 DisplayMetrics dm = getResources().getDisplayMetrics();
	        REL_SWIPE_MIN_DISTANCE = (int)(120.0f * dm.densityDpi / 160.0f + 0.5); 
	        REL_SWIPE_MAX_OFF_PATH = (int)(250.0f * dm.densityDpi / 160.0f + 0.5);
	        REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);
	        adapter = new ImageAdapter(getActivity(),1);
		 setListAdapter(adapter);
		 gestureDetector = new GestureDetector(getActivity(),new MyGestureDetector());
		 gestureListener = new View.OnTouchListener() {
	            @Override
	            public boolean onTouch(View v, MotionEvent event) {
	                return gestureDetector.onTouchEvent(event);
	            }
	     };
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		 DisplayMetrics dm = getResources().getDisplayMetrics();
	        REL_SWIPE_MIN_DISTANCE = (int)(120.0f * dm.densityDpi / 160.0f + 0.5); 
	        REL_SWIPE_MAX_OFF_PATH = (int)(250.0f * dm.densityDpi / 160.0f + 0.5);
	        REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);
		 adapter = new ImageAdapter(getActivity(),1);
		 setListAdapter(adapter);
		 gestureDetector = new GestureDetector(getActivity(),new MyGestureDetector());
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void onRightToLeftSwipe(){   
        Log.i("Swipe left","true");
    }

    public void onLeftToRightSwipe(){   
        adapter.notifyDataSetChanged();
        Log.i("Swipe right","true");
    }

    public void onTopToBottomSwipe(){   
    	Log.i("Swipe bottom","true");
    }

    public void onBottomToTopSwipe(){
    	Log.i("Swipe top","true");
    }
	
    private void onLTRFling() {
        Toast.makeText(getActivity(), "Left-to-right fling", Toast.LENGTH_SHORT).show();
    }
    
    private void onRTLFling() {
        Toast.makeText(getActivity(), "Right-to-left fling", Toast.LENGTH_SHORT).show();
    }

    class MyGestureDetector extends SimpleOnGestureListener { 

        // Detect a single-click and call my own handler.
        @Override 
        public boolean onSingleTapUp(MotionEvent e) {
            ListView lv = getListView();
            int pos = lv.pointToPosition((int)e.getX(), (int)e.getY());
            return false;
        }

        @Override 
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { 
            if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH) 
                return false; 
            if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE && 
                Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) { 
            }  else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE && 
                Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) { 
            	adapter.notifyDataSetChanged();
            } 
            return false; 
        } 

    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getListView().setOnTouchListener(gestureListener);
		getListView().setDivider(null);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (gestureDetector.onTouchEvent(event)) {
			return true;
		}
		return false;
	} 
	
	private static String getDensityName(Context context) {
		double density = context.getResources().getDisplayMetrics().density;
		if (density >= 4.0) {
		   //"xxxhdpi";
		}
		if (density >= 3.0 && density < 4.0) {
		   //xxhdpi
		}
		if (density >= 2.0) {
		   //xhdpi
		}
		if (density >= 1.5 && density < 2.0) {
		   //hdpi
		}
		if (density >= 1.0 && density < 1.5) {
		   //mdpi
		}
		return null;
	}
	
	void printSecreenInfo(){
		String TAG = "screen_disp";
	    Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    DisplayMetrics metrics = new DisplayMetrics();
	    display.getMetrics(metrics);

	    Log.i(TAG, "density :" +  metrics.density);

	    // density interms of dpi
	    Log.i(TAG, "D density :" +  metrics.densityDpi);

	    // horizontal pixel resolution
	    Log.i(TAG, "width pix :" +  metrics.widthPixels);

	     // actual horizontal dpi
	    Log.i(TAG, "xdpi :" +  metrics.xdpi);

	    // actual vertical dpi
	    Log.i(TAG, "ydpi :" +  metrics.ydpi);

	}
}
