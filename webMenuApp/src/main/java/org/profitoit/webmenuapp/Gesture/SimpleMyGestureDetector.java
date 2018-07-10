package org.webmenu.webmenuapp.Gesture;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

public class SimpleMyGestureDetector extends SimpleOnGestureListener { 
	static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private int REL_SWIPE_MIN_DISTANCE; 
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;
    public final static int RIGHT_TO_LEFT=1;
    public final static int LEFT_TO_RIGHT=2;
    public final static int TOP_TO_BOTTOM=3;
    public final static int BOTTOM_TO_TOP=4;
    static AbsListView absListView;
    
    
    
    public SimpleMyGestureDetector(View view) {
    	absListView = (AbsListView) view;
	}

	// Detect a single-click and call my own handler.
    @Override 
    public boolean onSingleTapUp(MotionEvent e) {
        int pos = absListView.pointToPosition((int)e.getX(), (int)e.getY());
        return false;
    }

    @Override 
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { 
        if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH) 
            return false; 
        if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE && 
            Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) { 
          //  onRTLFling(getListView().pointToPosition((int)e1.getX(),(int)e1.getY()));
        	Toast.makeText(absListView.getContext(), "Left-to-right fling", Toast.LENGTH_SHORT).show();
        }  else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE && 
            Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) { 
          //  onLTRFling(lstView1.pointToPosition((int)e1.getX(),(int)e1.getY()));
        	Toast.makeText(absListView.getContext(), "Left-to-right fling", Toast.LENGTH_SHORT).show();
        } 
        return false; 
    }
}
