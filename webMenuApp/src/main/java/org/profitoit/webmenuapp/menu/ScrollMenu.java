package org.webmenu.webmenuapp.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.webmenu.level7.WebViewDataItem;
import org.webmenu.webmenuapp.Animation.Rotate3dAnimation;
import org.webmenu.webmenuapp.R;

import java.util.ArrayList;

public class ScrollMenu extends Fragment {

	private static final String DEBUG_TAG = "Gestures";
	private GestureDetectorCompat mDetector; 
	
	public final static String MENU_ID = "sec_nr";
	
	private Context context;
	private String[] mTitles;
	private TypedArray mImageResource;
	private static final Handler menuAnimationHandle = new Handler();
	private static final int SWIPE_MIN_DISTANCE = 5;
	private static final int SWIPE_MAX_OFF_PATH = 5000;
	private static final int SWIPE_THRESHOLD_VELOCITY = 1000;

	private static final float AXIS_X_MAX = 5000;

	private static final float AXIS_Y_MIN = 0;

	private static final float AXIS_X_MIN = 0;

	private static final float AXIS_Y_MAX = 5000;
	
	private ArrayList<String> mItems = null;
	private Scroller mScroller;
	private int mActiveFeature = 0;
//	private int[] mImageResource;
	private TypedArray mIcons;
//	private int[] mIcons;
	// private ViewGroup layoutGroupView;
	private TableLayout tableView;
	private View layoutGroupView;
	private HorizontalScrollView horizontalScroll;
	private ScrollView verticalScroll;
	// The current viewport. This rectangle represents the currently visible 
	// chart domain and range. The viewport is the part of the app that the
	// user manipulates via touch gestures.
	private RectF mCurrentViewport = 
	        new RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX);

	// The current destination rectangle (in pixel coordinates) into which the 
	// chart data should be drawn.
	private Rect mContentRect;
	private RectF mScrollerStartViewport;

	private int layoutResourceId;
	
	public static ScrollMenu newInstance(int number){
		ScrollMenu table_view = new ScrollMenu();
		Bundle args = new Bundle();
		args.putInt(MENU_ID, number);
		table_view.setArguments(args);
		return table_view;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		int displaySz = 0;
		View view = inflater.inflate(R.layout.android_scrollable_menu_table, container, false);
		mScroller = new Scroller(getActivity().getApplicationContext());
		//Set the gesture detector for detecting a swipe
		//  mDetector = new GestureDetectorCompat(getActivity(), new MyGestureListener());
	//	View group_view = (ViewGroup) inflater.inflate(R.layout.frame_image_relative, container, false);
		LinearLayout linear_layout = (LinearLayout) view.findViewById(R.id.layout_view);
	//	linear_layout.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
		horizontalScroll = (HorizontalScrollView) view.findViewById(R.id.scrollHorizontal);
		verticalScroll = (ScrollView) view.findViewById(R.id.scrollVertical);
		ScrollView scrollView = new VerticalScrollView(getActivity(), null);
	//	ImageView icon = (ImageView) group_view.findViewById(R.id.icon);
	//	ImageView image = (ImageView) group_view.findViewById(R.id.image_menu);
	//	TextView text = (TextView) group_view.findViewById(R.id.text_view);
		tableView = (TableLayout) view.findViewById(R.id.table_layout);
		mTitles = getActivity().getResources().getStringArray(R.array.grid_menu_item_names);
    	mImageResource = getActivity().getResources().obtainTypedArray(R.array.grid_menu_item_background);
    	mIcons = getActivity().getResources().obtainTypedArray(R.array.grid_menu_item_icons);
    	WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
    	DisplayMetrics metrics = new DisplayMetrics();
    	int densityDpi = (int)(metrics.density * 160f);
    	wm.getDefaultDisplay().getMetrics(metrics);
    	if (metrics.heightPixels > metrics.widthPixels) {
    		displaySz = metrics.widthPixels * 50 / 100;
    	} else {
    		displaySz = metrics.heightPixels * 25 / 100;
    	}
    	int densityWidth = metrics.heightPixels * 25 / 100;
    	int densityHeight = metrics.widthPixels * 25 / 100;
    	Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotate);
    //	animation.setRepeatCount(100);
    //	Animation fadeout = new AlphaAnimation(1.f, 0.f);
    //	fadeout.setDuration(500);
/*    	table_view.startAnimation(animation);
    	table_view.postDelayed(new Runnable() {
    	    @Override
    	    public void run() {
    	    //	table_view.setVisibility(View.GONE);
    	    }
    	}, 500);*/
    //	animation.setFillAfter(true);
    //	scrollView.setAnimation(animation);
    	RelativeLayout.LayoutParams reLAY = new RelativeLayout.LayoutParams(1000, 1000);
	    for (int len = 1 ; len < mTitles.length; len++) {
    		TableRow row = new TableRow(getActivity());
    	//	row.setLayoutParams(new LinearLayout.LayoutParams(displaySz, displaySz));
	    	for (int pos = 1;pos<5;pos++) {
				final View view_2 = inflater.inflate(R.layout.frame_image_relative, null);
				// Since we are caching large views, we want to keep their cache
		        // between each animation
			//	view_2.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth, densityWidth));
				ImageView icon = (ImageView) view_2.findViewById(R.id.icon);
				ImageView image = (ImageView) view_2.findViewById(R.id.image_menu);
				image.setLayoutParams(new RelativeLayout.LayoutParams(displaySz, displaySz));
			//	view_2.startAnimation(animation);
				TextView text_view = (TextView) view_2.findViewById(R.id.text_menu);
				applyRotation(0, 10, 35, 10, image);
			//	Drawable icon = getResources().getDrawable(mImageResource.getResourceId(pos, -1));
				// image_view.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
				image.setBackgroundResource(mImageResource.getResourceId(pos, -1));
			//	image.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
				image.postDelayed(new Runnable() {

					@Override
					public void run() {
					//	image_view.startAnimation(animation);
					}
					
				}, 500);
				icon.setImageResource(mIcons.getResourceId(pos, -1));
			//	icon.setLayoutParams(new RelativeLayout.LayoutParams(20,20));
				text_view.setTextColor(getActivity().getResources().getColor(R.color.White));
			//	text_view.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
			//	image_view.startAnimation(animation);
			//	text_view.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
		        text_view.setText(mTitles[pos]);
			//	image_2.setLayoutParams(new FrameLayout.LayoutParams(densityWidth,densityWidth));
			//	Bitmap bitmap_image = BitmapFactory.decodeResource(getResources(), mIcons.getResourceId(pos, -1));
	    	//	image_2.setBackgroundResource(mIcons.getResourceId(pos, -1));
			//	image.setBackgroundResource(mIcons.getResourceId(pos, -1));
			//	image_2.setImageBitmap(bitmap_image);
	    	//	View menu_view = new View(group_view.getContext());
	    	//	menu_view.inflate(getActivity(), R.layout.image_menu, container);
	    	//	ImageView icon_image = (ImageView) menu_view.findViewById(R.id.icon);
	    	//	ImageView menu_image = (ImageView) menu_view.findViewById(R.id.image_menu);
	    	//	TextView text_icon = (TextView) menu_view.findViewById(R.id.text_view);
	    	//	menu_image.setImageResource(mIcons.getResourceId(pos, -1));
			//	group_view_.addView(group_view_);
				row.addView(view_2);
				image.setOnClickListener(new OnClickListener() {
				
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent web_view = new Intent(getActivity(), WebViewDataItem.class);
						web_view.putExtra("ARGUMENT_URL", "file:///android_asset/DIAMETER_PROTOCOL_RESULT_CODES.htm");
						//	WebViewDataItem webviewdataitem = new WebViewDataItem();
						//  webviewdataitem.setURLContent("file:///android_asset/DIAMETER_PROTOCOL_RESULT_CODES.htm");
						startActivity(web_view);
						//  getFragmentManager().beginTransaction().replace(R.id.container, webviewdataitem).commit();
					}
				});
				//linear_layout.addView(image);
			}
	    	tableView.addView(row);
	    }
	    
    	mIcons.recycle();
    	mImageResource.recycle();
    	// Instantiate the gesture detector with the
        // application context and an implementation of
    	verticalScroll.setOnTouchListener(new View.OnTouchListener() {

    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
    			/*// TODO Auto-generated method stub
    			if (mDetector.onTouchEvent(event)) {
    				return true;
    			}
    			else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
    				int scrollX = horizontalScroll.getScrollX();
    				int featureWidth = v.getMeasuredWidth();
    				mActiveFeature = ((scrollX + (featureWidth/2))/featureWidth);
    				int scrollTo = mActiveFeature*featureWidth;
    			//	scrollView.smoothScrollTo(scrollTo, 0);
    				return true;
    			}
    			else{
    				return false;
    			}*/
    			  return false;
    		} 
    		
		});
    	horizontalScroll.setOnTouchListener(new View.OnTouchListener() { //outer scroll listener  
        	int featureHeight = verticalScroll.getMeasuredHeight();
        	int featureWidth = horizontalScroll.getMeasuredWidth();
        	
	        private float mx, my, curX, curY;
	        private boolean started = false;

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
/*	        	mScroller.fling((int)event.getX(), (int)event.getY(), 1000 / SWIPE_THRESHOLD_VELOCITY, 1000 / SWIPE_THRESHOLD_VELOCITY, (int)AXIS_X_MIN, (int)AXIS_Y_MIN, (int)AXIS_X_MAX, (int)AXIS_Y_MAX);
	        	mScroller.startScroll((int)event.getX(), (int)event.getY(), 500, 500);
	        	float maxFlingVelocity    = ViewConfiguration.get(getActivity()).getScaledMaximumFlingVelocity();
	        	float velocityPercentX    = 50 / maxFlingVelocity;          // the percent is a value in the range of (0, 1]
	        	float normalizedVelocityX = velocityPercentX * PIXELS_PER_SECOND;  // where PIXELS_PER_SECOND is a device-independent measuremen
    			if (mScroller.computeScrollOffset()) {
    			     // Get current x and y positions
    			     currX = mScroller.getCurrX();
    			     currY = mScroller.getCurrY();
    			 }
	        	Log.d(DEBUG_TAG, "onFling: " + "e1: " + event.getRawX() + "Raw Y: " + event.getRawY()); */

	            switch (event.getAction()) {
	            	
	                case MotionEvent.ACTION_DOWN:
	                    mx = event.getX();
	                    my = event.getY();
	                    break;
	                case MotionEvent.ACTION_MOVE:
	                    curX = event.getX();
	                    curY = event.getY();
	                    if (started) {
	                    //	verticalScroll.smoothScrollBy((int) (mx - curX), (int) (my - curY));
	                    	if (mx > curX) {
	                    	//	horizontalScroll.scrollBy((int) (mx - curX), 0);
	                    		horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	} else {
	                    		horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	//	horizontalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
	                    	}
	                    	if (my > curY) {
		                    	//	verticalScroll.scrollBy(0, (int) (my - curY));
		                    //	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
		                    	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	} else {
		                    	//	verticalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
		                    	//	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
	                    		verticalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
	                    	}
		                    //	horizontalScroll.smoothScrollBy((int) (mx - curX), (int) (my - curY));
	                    } else {
	                    	started = true;
	                    }
	                    mx = curX;
	                    my = curY;
	                    break;
	                case MotionEvent.ACTION_UP:
	                	curX = event.getX();
	                    curY = event.getY();
			              //  verticalScroll.smoothScrollBy((int) (mx - currX + 500), (int) (my - currY + 500));
			              //  horizontalScroll.smoothScrollBy((int) (mx - currX + 500), (int) (my - currY + 500));
	                    if (mx > curX) {
	                    		horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	//	horizontalScroll.scrollBy((int) (mx - curX), 0);
                    	} else {
	                    	//	horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	//	horizontalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                    	}
                    	if (my > curY) {
	                    	//	horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    		verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	//	verticalScroll.scrollBy(0, (int) (my - curY));
                    	} else {
	                    	//	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	//	verticalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                    	}
		                started = false;
	                    break;
	            }

	            return true;
	            /*
	        	if (mDetector.onTouchEvent(event)) {
    				return true;
    			}
    			else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
    				int scrollX = horizontalScroll.getScrollX();
    				int featureWidth = v.getMeasuredWidth();
    				mActiveFeature = ((scrollX + (featureWidth/2))/featureWidth);
    				int scrollTo = mActiveFeature*featureWidth;
    			//	scrollView.smoothScrollTo(scrollTo, 0);
    				return true;
    			} else {
    				return false;
    			}
    			*/
	        }
	    });
    	
        mDetector = new GestureDetectorCompat(getActivity().getApplicationContext(),new MyGestureListener());
    	return view;
	}
	
	
	
	class MyGestureListener extends GestureDetector.SimpleOnGestureListener { 
		protected MotionEvent mLastOnDownEvent;
        // Detect a single-click and call my own handler.
        @Override 
        public boolean onSingleTapUp(MotionEvent e) {
            int pos = ((AbsListView) layoutGroupView).pointToPosition((int)e.getX(), (int)e.getY());
            return false;
        }/*

        @Override 
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { 
        	try {
        	float maxFlingVelocity    = ViewConfiguration.get(getActivity()).getScaledMaximumFlingVelocity();
	        float velocityPercentX    = 50 / maxFlingVelocity;          // the percent is a value in the range of (0, 1]
	        float normalizedVelocityX = velocityPercentX * PIXELS_PER_SECOND;  // where PIXELS_PER_SECOND is a device-independent measuremen
    			Log.d(DEBUG_TAG, "onFling: " + e1.toString() + e2.toString());
    			mScroller.fling((int)e2.getX(), (int)e2.getY(), (int)velocityX / SWIPE_THRESHOLD_VELOCITY, (int)velocityY / SWIPE_THRESHOLD_VELOCITY, (int)AXIS_X_MIN, (int)AXIS_Y_MIN, (int)AXIS_X_MAX, (int)AXIS_Y_MAX);
    			if (mScroller.computeScrollOffset()) {
    			     // Get current x and y positions
    			     int currX = mScroller.getCurrX();
    			     int currY = mScroller.getCurrY();
    			 }
    			horizontalScroll.postInvalidate();
    			verticalScroll.postInvalidate();
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) 
                    return false; 
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && 
                    Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) { 
                  //  onRTLFling(getListView().pointToPosition((int)e1.getX(),(int)e1.getY()));
        			Log.d(DEBUG_TAG, "onFling: " + e1.toString() + e2.toString());
                	int featureWidth = horizontalScroll.getMeasuredWidth();
    				mActiveFeature = (mActiveFeature < (mItems.size() - 1))? mActiveFeature + 1:mItems.size() -1;
    			//	scrollView.smoothScrollTo(mActiveFeature*featureWidth, 0);
    				return true;
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && 
                    Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) { 
                  //  onLTRFling(lstView1.pointToPosition((int)e1.getX(),(int)e1.getY()));
                	int featureWidth = horizontalScroll.getMeasuredWidth();
    				mActiveFeature = (mActiveFeature > 0)? mActiveFeature - 1:0;
    			//	scrollView.smoothScrollTo(mActiveFeature*featureWidth, 0);
    				return true;
                } 
                return false; 
        	} 
            	 catch (Exception e) {
     				Log.e("Fling", "There was an error processing the Fling event:" + e.getMessage());
    			}
    			return false;
        }*/
        @Override
        public boolean onDown(MotionEvent e) {
        	mLastOnDownEvent = e;
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;/*
            try {
            	if (e1 == null)
            		e1 = mLastOnDownEvent;
            	if (e1 == null || e2 == null)
            		return false;
                float diffY = e1.getY() - e2.getY();
                float diffX = e1.getX() - e2.getX();
                int currX = 0;
                int currY = 0;
                if (diffX > 0) {
                	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                } else {
                	verticalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                	horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                }
                Log.d(DEBUG_TAG, "onFling: " + e1.toString() + e2.toString());
            	if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false; 
            	}
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if (diffX > 0) {
                            // Swipe right
                        	Log.d(DEBUG_TAG, "onFling: " + e1.toString() + e2.toString());
                			mScroller.fling((int)e2.getX(), (int)e2.getY(), (int)velocityX / SWIPE_THRESHOLD_VELOCITY, (int)velocityY / SWIPE_THRESHOLD_VELOCITY, (int)AXIS_X_MIN, (int)AXIS_Y_MIN, (int)AXIS_X_MAX, (int)AXIS_Y_MAX);
                			if (mScroller.computeScrollOffset()) {
                			     // Get current x and y positions
                			     currX = mScroller.getCurrX();
                			     currY = mScroller.getCurrY();
                			 }
	                    	horizontalScroll.smoothScrollBy(currX, currY);
                        	horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                        } else {
                            // Swipe left
//                        	Log.d(DEBUG_TAG, "onFling: " + e1.toString() + e2.toString());
//                			mScroller.fling((int)e2.getX(), (int)e2.getY(), (int)velocityX / SWIPE_THRESHOLD_VELOCITY, (int)velocityY / SWIPE_THRESHOLD_VELOCITY, (int)AXIS_X_MIN, (int)AXIS_Y_MIN, (int)AXIS_X_MAX, (int)AXIS_Y_MAX);
//                			if (mScroller.computeScrollOffset()) {
//                			     // Get current x and y positions
//                			     currX = mScroller.getCurrX();
//                			     currY = mScroller.getCurrY();
//                			 }
//	                    	horizontalScroll.smoothScrollBy(currX, currY);
//                			horizontalScroll.postInvalidate();
//                			verticalScroll.postInvalidate();
                        	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                        }
                    }
                    cursor = true;
                } 
                else if (Math.abs(diffY) > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        if (diffY > 0) {
                            // Swipe bottom
                        //	verticalScroll.smoothScrollBy(currX, currY);
                        	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                        } else {
                            // Swipe top
                        //	verticalScroll.smoothScrollBy(currX, currY);
                        	verticalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                        }
                    }
                    cursor = true;

            } catch (Exception e) {
            	Log.e("Fling", "There was an error processing the Fling event:" + e.getMessage());
            }
 */           return result;
        }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
	}
/*	
	*//**
     * Setup a new 3D rotation on the container view.
     *
     * @param position
     *            the item that was clicked to show a picture, or -1 to show the
     *            list
     * @param start
     *            the start angle at which the rotation must begin
     * @param end
     *            the end angle of the rotation
     */
    private void applyRotation(int position, float start, float end, final int cnt, final View menuView) {
    	
        // Find the center of the container
        final float centerX = menuView.getWidth() / 2.0f;
        final float centerY = menuView.getHeight() / 2.0f;
        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation accelerate = new Rotate3dAnimation(start, end,
                centerX, centerY, 310.0f, true);
        accelerate.setDuration(1000);
        accelerate.setInterpolator(new AnticipateOvershootInterpolator());
        final Rotate3dAnimation decelerate = new Rotate3dAnimation(start, end,
                50, 100, 310.0f, true);
      //  menuView.startAnimation(accelerate);
        /*
        final Runnable animationRunnable = new Runnable() {
        	int count = 0;
        	boolean first = true;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(menuView.getAnimation().hasEnded() == false) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (count <= cnt) {
					if(first){
						first = false;
						menuView.startAnimation(decelerate);
					} else {
						menuView.startAnimation(accelerate);
					}
				}
				count++;
			}
        	
        };*/
        accelerate.setAnimationListener(new AnimationListener() {
        	boolean first;
			int count = 0;
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
					if (first) {
						menuView.startAnimation(decelerate);
						first = false;
					} else {
						menuView.startAnimation(accelerate);
						first = true;
					}
					menuView.startAnimation(accelerate);
				count++;
			}
		});
        decelerate.setDuration(1000);
        decelerate.setInterpolator(new AnticipateInterpolator());
        
       // rotation.setAnimationListener(new DisplayNextView(position));
 
        menuView.startAnimation(accelerate);
      //  menuAnimationHandle.post(animationRunnable);
    }	
    
    public class LockableScrollView extends ScrollView {

    	public LockableScrollView(Context context, AttributeSet attrs, int defStyle) {
    	    super(context, attrs, defStyle);
    	    // TODO Auto-generated constructor stub
    	}
    	public LockableScrollView(Context context, AttributeSet attrs) 
    	{
    	    super(context, attrs);
    	}

    	public LockableScrollView(Context context) 
    	{
    	    super(context);
    	}

    	// true if we can scroll (not locked)
    	// false if we cannot scroll (locked)
    	private boolean mScrollable = true;

    	public void setScrollingEnabled(boolean enabled) {
    	    mScrollable = enabled;
    	}

    	public boolean isScrollable() {
    	    return mScrollable;
    	}

    	@Override
    	public boolean onTouchEvent(MotionEvent ev) {
    	    switch (ev.getAction()) {
    	        case MotionEvent.ACTION_DOWN:
    	            // if we can scroll pass the event to the superclass
    	            if (mScrollable) return super.onTouchEvent(ev);
    	            // only continue to handle the touch event if scrolling enabled
    	            return mScrollable; // mScrollable is always false at this point
    	        default:
    	            return super.onTouchEvent(ev);
    	    }
    	}



    	@Override  
    	public boolean onInterceptTouchEvent(MotionEvent ev) {
    		switch (ev.getAction()) {     
    			case MotionEvent.ACTION_DOWN:         
    					// if we can scroll pass the event to the superclass      
    			    if (mScrollable) return super.onInterceptTouchEvent(ev);      
    				    // only continue to handle the touch event if scrolling enabled    
    				    return mScrollable; // mScrollable is always false at this point     
    			    default:          
    			        return super.onInterceptTouchEvent(ev);      
    			}
    		}
    	}
    
    public class VerticalScrollView extends ScrollView {
    	private float xDistance, yDistance, lastX, lastY;

    	public VerticalScrollView(Context context, AttributeSet attrs) {
    	    super(context, attrs);
    	}

    	@Override
    	public boolean onTouchEvent(MotionEvent ev) {
    		// TODO Auto-generated method stub
    		return super.onTouchEvent(ev);
    	}

    	@Override
    	public boolean onInterceptTouchEvent(MotionEvent ev) {
    	    switch (ev.getAction()) {
    	        case MotionEvent.ACTION_DOWN:
    	            xDistance = yDistance = 0f;
    	            lastX = ev.getX();
    	            lastY = ev.getY();
    	            break;
    	        case MotionEvent.ACTION_MOVE:
    	            final float curX = ev.getX();
    	            final float curY = ev.getY();
    	            xDistance += Math.abs(curX - lastX);
    	            yDistance += Math.abs(curY - lastY);
    	            lastX = curX;
    	            lastY = curY;
    	            if(xDistance > yDistance)
    	                return false;
    	    }
    	
    	    return super.onInterceptTouchEvent(ev);
    	}
    }
    
}



