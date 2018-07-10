package org.webmenu.level11.Menu;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
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
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.webmenu.level7.WebViewDataItem;
import org.webmenu.webmenuapp.Animation.Rotate3dAnimation;
import org.webmenu.webmenuapp.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

@SuppressLint("NewApi")
public class ScrollMenu extends Fragment {
    public Cursor data;
    private String[] types;
    private String path;

	private static final String DEBUG_TAG = "Gestures";
	private GestureDetector mDetector; 
	
	public static String MENU_ID = "MENU_ID";
	private String[] mTitles;
	private TypedArray mImageResource;
	private static final Handler menuAnimationHandle = new Handler();
	private static final int SWIPE_MIN_DISTANCE = 5;
	private static final int SWIPE_MAX_OFF_PATH = 5000;
	private static final int SWIPE_THRESHOLD_VELOCITY = 530;

	private static final float AXIS_X_MAX = 5000;

	private static final float AXIS_Y_MIN = 0;

	private static final float AXIS_X_MIN = 0;

	private static final float AXIS_Y_MAX = 5000;
    private int parentId;
	private ArrayList<String> mItems = null;
	private int mActiveFeature = 0;
    private int densityDpi;
    private int displayParams;
	private TypedArray mIcons;
	private TableLayout tableView;
	private View layoutGroupView;
    private LayoutInflater layoutInflater;
	private HorizontalScrollView horizontalScroll;
	private ScrollView verticalScroll;
    public List<String[]> list;
    private Iterator<String[]> readList;

    public ScrollMenu() {
        setRetainInstance(true);
    }

	public static ScrollMenu newInstance(int number){
		ScrollMenu table_view = new ScrollMenu();
		Bundle args = new Bundle();
		args.putInt("index", number);
		table_view.setArguments(args);
		return table_view;
	}

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// View view = inflater.inflate(R.layout.android_scrollable_menu_table, container, false);
        layoutInflater = inflater;
        // ViewGroup frameScrollView = (ViewGroup) inflater.inflate(R.layout.frame_scroll_view,null);
         View frameScrollView = inflater.inflate(R.layout.scrollable_view, null);
        ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.scrollview, null);
        // final RelativeLayout rel = (RelativeLayout) inflater.inflate(R.layout.scrollview, null);
        final RelativeLayout rel = (RelativeLayout) scrollView.findViewById(R.id.scrollView);
        //  rel.offsetLeftAndRight(5000);
        final ViewGroup menuContainer = (ViewGroup) frameScrollView.findViewById(R.id.menuView);
        final ScrollView verticalScroller = (ScrollView) frameScrollView.findViewById(R.id.scrollView);
        final HorizontalScrollView horizontalScrollView = (HorizontalScrollView) frameScrollView.findViewById(R.id.horizontalScrollView);
        // final ScrollView vps = (ScrollView) frameScrollView.findViewById(R.id.scrollVertical);
        // final HorizontalScrollView hsp = (HorizontalScrollView) frameScrollView.findViewById(R.id.scrollHorizontal);
        // RelativeLayout rl = (RelativeLayout) frameScrollView.findViewById(R.id.container);
		//Set the gesture detector for detecting a swipe
		//  mDetector = new GestureDetectorCompat(getActivity(), new MyGestureListener());
	//	View group_view = (ViewGroup) inflater.inflate(R.layout.frame_image_relative, container, false);
	//	LinearLayout linear_layout = (LinearLayout) view.findViewById(R.id.layout_view);
	//	linear_layout.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
		// horizontalScroll = (HorizontalScrollView) view.findViewById(R.id.scrollHorizontal);
		// verticalScroll = (ScrollView) view.findViewById(R.id.scrollVertical);
	//	ImageView icon = (ImageView) group_view.findViewById(R.id.icon);
	//	ImageView image = (ImageView) group_view.findViewById(R.id.image_menu);
	//	TextView text = (TextView) group_view.findViewById(R.id.text_view);
	//	tableView = (TableLayout) view.findViewById(R.id.table_layout);
		mTitles = getActivity().getResources().getStringArray(R.array.grid_menu_item_names);
    	types = getResources().getStringArray(R.array.knowledge_menu_list);
        mImageResource = getActivity().getResources().obtainTypedArray(R.array.grid_menu_item_background);
    	mIcons = getActivity().getResources().obtainTypedArray(R.array.grid_menu_item_icons);
    	WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
        int rotation = display.getRotation();
    	DisplayMetrics metrics = new DisplayMetrics();
    	wm.getDefaultDisplay().getMetrics(metrics);
      //  densityDpi = (int)(metrics.density * 160f);
        densityDpi = metrics.densityDpi;
    	if (metrics.heightPixels > metrics.widthPixels) {
    		displayParams = metrics.widthPixels * 50 / 100;
    	} else {
    		displayParams = metrics.heightPixels * 50 / 100;
    	}
        Point screenSize = new Point();
        display.getSize(screenSize);
        int borderEnd = screenSize.x / densityDpi;
        int hCount = (screenSize.x / densityDpi) + 2;
        int vCount = (screenSize.y / densityDpi) + 2;
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
        // View menuView = menuViewAdd(-1, 0);
        View menuItemView = menuViewAdd(-1, 0);
        // View toLeft = menuViewAdd(2, 1);
       // menuContainer.addView(menuView, menuView.getLayoutParams());
        menuContainer.addView(menuItemView, menuItemView.getLayoutParams());
        data.moveToNext();
        //rl.addView(toLeft, toLeft.getLayoutParams());
	   // for (int id = 1; id < data.length; id++) {
        int pos = 0;
        int cnt = 0;
        do {
            // row.setLayoutParams(new LinearLayout.LayoutParams(displaySz, displaySz));
            if (cnt < hCount) {
                View layRight = menuViewAdd(1, pos);
               // View toRight = menuViewAdd(1, pos);
               // menuContainer.addView(toRight, toRight.getLayoutParams());
                menuContainer.addView(layRight, layRight.getLayoutParams());
                cnt++;
            } else {
               // View menuBelow = menuViewAdd(3, pos);
               // menuContainer.addView(menuBelow, menuBelow.getLayoutParams());
                View layBelow = menuViewAdd(3, pos);
                menuContainer.addView(layBelow, layBelow.getLayoutParams());
                cnt = 0;
            }
            pos++;
        } while(data.moveToNext());
        data.moveToFirst();
	    	// for (int pos = 1;pos<5;pos++) {
                // View toRight = menuViewAdd(id, 2);
				// final View view_2 = inflater.inflate(R.layout.frame_image_relative, null);
               // RelativeLayout.LayoutParams viewLayoutParams = (RelativeLayout.LayoutParams) view_2.getLayoutParams();
                // RelativeLayout.LayoutParams viewLayoutParams = new RelativeLayout.LayoutParams(displayParams, displayParams);
                // view_2.setId(menuTag[id]);
                // viewLayoutParams.addRule(RelativeLayout.LEFT_OF, menuTag[0]);
                // id++;
                // view_2.setPadding(len * 100,0,0,0);
				// Since we are caching large views, we want to keep their cache
		        // between each animation
				// view_2.setLayoutParams(new RelativeLayout.LayoutParams(displaySz, displaySz));
				// ImageView icon = (ImageView) view_2.findViewById(R.id.icon);
				// ImageView image = (ImageView) view_2.findViewById(R.id.image_menu);
				// image.setLayoutParams(new RelativeLayout.LayoutParams(displayParams, displayParams));
			//	view_2.startAnimation(animation);
				// TextView text_view = (TextView) view_2.findViewById(R.id.text_menu);
				// applyRotation(0, 10, 35, 10, image);
			//	Drawable icon = getResources().getDrawable(mImageResource.getResourceId(pos, -1));
				// image_view.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
				// image.setBackgroundResource(mImageResource.getResourceId(pos, -1));
			//	image.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
            //				image.postDelayed(new Runnable() {
            //
            //					@Override
            //					public void run() {
            //					//	image_view.startAnimation(animation);
            //					}
            //
            //				}, 500);
            //				icon.setImageResource(mIcons.getResourceId(pos, -1));
			//	icon.setLayoutParams(new RelativeLayout.LayoutParams(20,20));
			//  text_view.setTextColor(getActivity().getResources().getColor(R.color.White));
			//	text_view.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
			//	image_view.startAnimation(animation);
			//	text_view.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
		    //  text_view.setText(mTitles[pos]);
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
			//	row.addView(view_2);
            //  rl.addView(view_2, viewLayoutParams);
            //  rl.addView(menuView, menuView.getLayoutParams());
            //  rl.addView(toLeft, toLeft.getLayoutParams());
            // rl.addView(toRight, toRight.getLayoutParams());
            //				image.setOnClickListener(new OnClickListener() {
            //
            //					@Override
            //					public void onClick(View v) {
            //						// TODO Auto-generated method stub
            //						Intent web_view = new Intent(getActivity(), WebViewDataItem.class);
            //						web_view.putExtra("ARGUMENT_URL", "file:///android_asset/DIAMETER_PROTOCOL_RESULT_CODES.htm");
            //						//	WebViewDataItem webviewdataitem = new WebViewDataItem();
            //						//  webviewdataitem.setURLContent("file:///android_asset/DIAMETER_PROTOCOL_RESULT_CODES.htm");
            //						startActivity(web_view);
            //						//  getFragmentManager().beginTransaction().replace(R.id.container, webviewdataitem).commit();
            //					}
            //				});
				//linear_layout.addView(image);
			// }
          // menuView = menuViewAdd(id, 4);
          // rl.addView(menuView, menuView.getLayoutParams());
	            //	tableView.addView(row);
	   // }

        // rl.addView(menuView, menuView.getLayoutParams());

    	mIcons.recycle();
    	mImageResource.recycle();
    	// Instantiate the gesture detector with the
        // application context and an implementation of

//    	verticalScroll.setOnTouchListener(new View.OnTouchListener() {
//
//    		@Override
//    		public boolean onTouch(View v, MotionEvent event) {
//    			/*// TODO Auto-generated method stub
//    			if (mDetector.onTouchEvent(event)) {
//    				return true;
//    			}
//    			else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
//    				int scrollX = horizontalScroll.getScrollX();
//    				int featureWidth = v.getMeasuredWidth();
//    				mActiveFeature = ((scrollX + (featureWidth/2))/featureWidth);
//    				int scrollTo = mActiveFeature*featureWidth;
//    			//	scrollView.smoothScrollTo(scrollTo, 0);
//    				return true;
//    			}
//    			else{
//    				return false;
//    			}*/
//    			  return false;
//    		}
//
//		});
//    	horizontalScroll.setOnTouchListener(new View.OnTouchListener() { //outer scroll listener
//        	int featureHeight = verticalScroll.getMeasuredHeight();
//        	int featureWidth = horizontalScroll.getMeasuredWidth();
//
//	        private float mx, my, curX, curY;
//	        private boolean started = false;
//
//	        @Override
//	        public boolean onTouch(View v, MotionEvent event) {
///*	        	mScroller.fling((int)event.getX(), (int)event.getY(), 1000 / SWIPE_THRESHOLD_VELOCITY, 1000 / SWIPE_THRESHOLD_VELOCITY, (int)AXIS_X_MIN, (int)AXIS_Y_MIN, (int)AXIS_X_MAX, (int)AXIS_Y_MAX);
//	        	mScroller.startScroll((int)event.getX(), (int)event.getY(), 500, 500);
//	        	float maxFlingVelocity    = ViewConfiguration.get(getActivity()).getScaledMaximumFlingVelocity();
//	        	float velocityPercentX    = 50 / maxFlingVelocity;          // the percent is a value in the range of (0, 1]
//	        	float normalizedVelocityX = velocityPercentX * PIXELS_PER_SECOND;  // where PIXELS_PER_SECOND is a device-independent measuremen
//    			if (mScroller.computeScrollOffset()) {
//    			     // Get current x and y positions
//    			     currX = mScroller.getCurrX();
//    			     currY = mScroller.getCurrY();
//    			 }
//	        	Log.d(DEBUG_TAG, "onFling: " + "e1: " + event.getRawX() + "Raw Y: " + event.getRawY()); */
//
//	            switch (event.getAction()) {
//
//	                case MotionEvent.ACTION_DOWN:
//	                    mx = event.getX();
//	                    my = event.getY();
//	                    break;
//	                case MotionEvent.ACTION_MOVE:
//	                    curX = event.getX();
//	                    curY = event.getY();
//	                    if (started) {
//	                    //	verticalScroll.smoothScrollBy((int) (mx - curX), (int) (my - curY));
//	                    	if (mx > curX) {
//	                    	//	horizontalScroll.scrollBy((int) (mx - curX), 0);
//	                    		horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	} else {
//	                    		horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
//	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	//	horizontalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
//	                    	}
//	                    	if (my > curY) {
//		                    	//	verticalScroll.scrollBy(0, (int) (my - curY));
//		                    //	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
//		                    	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	} else {
//		                    	//	verticalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
//		                    	//	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
//	                    		verticalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
//	                    	}
//		                    //	horizontalScroll.smoothScrollBy((int) (mx - curX), (int) (my - curY));
//	                    } else {
//	                    	started = true;
//	                    }
//	                    mx = curX;
//	                    my = curY;
//	                    break;
//	                case MotionEvent.ACTION_UP:
//	                	curX = event.getX();
//	                    curY = event.getY();
//			              //  verticalScroll.smoothScrollBy((int) (mx - currX + 500), (int) (my - currY + 500));
//			              //  horizontalScroll.smoothScrollBy((int) (mx - currX + 500), (int) (my - currY + 500));
//	                    if (mx > curX) {
//	                    		horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	//	horizontalScroll.scrollBy((int) (mx - curX), 0);
//                    	} else {
//	                    	//	horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	//	horizontalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
//                    	}
//                    	if (my > curY) {
//	                    	//	horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    		verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	//	verticalScroll.scrollBy(0, (int) (my - curY));
//                    	} else {
//	                    	//	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
//	                    	//	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
//	                    	//	verticalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
//                    	}
//		                started = false;
//	                    break;
//	            }
//
//	            return true;
//	            /*
//	        	if (mDetector.onTouchEvent(event)) {
//    				return true;
//    			}
//    			else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
//    				int scrollX = horizontalScroll.getScrollX();
//    				int featureWidth = v.getMeasuredWidth();
//    				mActiveFeature = ((scrollX + (featureWidth/2))/featureWidth);
//    				int scrollTo = mActiveFeature*featureWidth;
//    			//	scrollView.smoothScrollTo(scrollTo, 0);
//    				return true;
//    			} else {
//    				return false;
//    			}
//    			*/
//	        }
//	    });
        frameScrollView.setOnTouchListener(new View.OnTouchListener() {

            private float mx, my, curX, curY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    Log.v("EDGE_BOTTOM_FLAG:","1");
                }
                // Toast.makeText(getActivity(), view.getId() + "1", Toast.LENGTH_SHORT);
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mx = motionEvent.getX();
                        my = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = motionEvent.getX();
                        curY = motionEvent.getY();
                            if (mx > curX) {
                              // horizontalScrollView.scrollBy((int) (mx - curX), 0);
                                horizontalScrollView.fling(SWIPE_THRESHOLD_VELOCITY);
                            } else {
                               // horizontalScrollView.scrollBy((int) (mx - curX), (int) (my - curY));
                                horizontalScrollView.fling(-SWIPE_THRESHOLD_VELOCITY);
                            }
                            if (my > curY) {
                                verticalScroller.fling(SWIPE_THRESHOLD_VELOCITY);
                             //  verticalScroller.scrollBy(0, (int) (my - curY));
                            } else {
                                verticalScroller.fling(-SWIPE_THRESHOLD_VELOCITY);
                             //  verticalScroller.scrollBy((int) (mx - curX), (int) (my - curY));
                            }
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        curX = motionEvent.getX();
                        curY = motionEvent.getY();
                        if (mx > curX) {
                            //	horizontalScroll.scrollBy((int) (mx - curX), 0);
                        } else {
                            //	horizontalScroll.scrollBy((int) (mx - curX), (int) (my - curY));

                        }
                        if (my > curY) {
                            //	verticalScroll.scrollBy(0, (int) (my - curY));
                        } else {
                            //	verticalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                        }
                        break;
                }
                return true;
            }
        });
        /*

        view.setOnTouchListener(new View.OnTouchListener() {

            private float mx, my, curX, curY;
	        private boolean started = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

	                case MotionEvent.ACTION_DOWN:
	                    mx = motionEvent.getX();
	                    my = motionEvent.getY();
	                    break;
	                case MotionEvent.ACTION_MOVE:
	                    curX = motionEvent.getX();
	                    curY = motionEvent.getY();
	                    if (started) {
	                    //	verticalScroll.smoothScrollBy((int) (mx - curX), (int) (my - curY));
	                    	if (mx > curX) {
                                //	tableView.scrollBy((int) (mx - curX), 0);
                                //  tableView.scrollBy((int) (mx - curX), 0);
                                    horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                                //	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	} else {
                                    horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                                //	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                                //	horizontalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                                // tableView.scrollBy((int) (mx - curX), (int) (my - curY));
	                    	}
	                    	if (my > curY) {
                                    //	verticalScroll.scrollBy(0, (int) (my - curY));
                                //  tableView.scrollBy(0, (int) (my - curY));
                                //	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                                    verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
	                    	} else {
		                    	//	verticalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                                //  tableView.scrollBy((int) (mx - curX), (int) (my - curY));
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
	                	curX = motionEvent.getX();
	                    curY = motionEvent.getY();
			              //  verticalScroll.smoothScrollBy((int) (mx - currX + 500), (int) (my - currY + 500));
			              //  horizontalScroll.smoothScrollBy((int) (mx - currX + 500), (int) (my - currY + 500));
                        if (mx > curX) {
                            //	horizontalScroll.scrollBy((int) (mx - curX), 0);
                            //  tableView.scrollBy((int) (mx - curX), 0);
                               // horizontalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                            //	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                        } else {
                             //   horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                            //	verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                            //	horizontalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                            //  tableView.scrollBy((int) (mx - curX), (int) (my - curY));
                        }
                        if (my > curY) {
                            //	verticalScroll.scrollBy(0, (int) (my - curY));
                             // tableView.scrollBy(0, (int) (my - curY));
                            //	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                              //  verticalScroll.fling(SWIPE_THRESHOLD_VELOCITY);
                        } else {
                            //	verticalScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                            //  tableView.scrollBy((int) (mx - curX), (int) (my - curY));
                            //	horizontalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                               // verticalScroll.fling(-SWIPE_THRESHOLD_VELOCITY);
                        }
		                started = false;
	                    break;
	            }
                return true;
            }
        });
        mDetector = new GestureDetector(getActivity().getApplicationContext(),new MyGestureListener());
        */
    	//  return view;
        setRetainInstance(true);
        path = getActivity().getFilesDir().getPath();
    	return frameScrollView;
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

    public View menuViewAdd(int param, int pos) {
        final String uri = data.getString(5);
        final View menuView = layoutInflater.inflate(R.layout.frame_image_relative, null);
        // RelativeLayout.LayoutParams viewLayoutParams = (RelativeLayout.LayoutParams) view_2.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(densityDpi, densityDpi);
    //  int id = getResources().getIdentifier(menuTagIds[menuId], "id", getActivity().getPackageName());
        int id = getResources().getIdentifier(data.getString(2), "id", getActivity().getPackageName());
        menuView.setId(id);
        switch(param) {
            case 0:
                layoutParams.addRule(RelativeLayout.LEFT_OF, parentId);
                break;
            case 1:
                layoutParams.addRule(RelativeLayout.RIGHT_OF, parentId);
                layoutParams.addRule(RelativeLayout.ALIGN_TOP, parentId);
                layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, parentId);
                break;
            case 2:
                layoutParams.addRule(RelativeLayout.ABOVE, parentId);
                break;
            case 3:
                layoutParams.addRule(RelativeLayout.BELOW, parentId);
                break;
        }
        parentId = id;
        // view_2.setPadding(len * 100,0,0,0);
        // Since we are caching large views, we want to keep their cache
        // between each animation
        // view_2.setLayoutParams(new RelativeLayout.LayoutParams(displaySz, displaySz));
        ImageView icon = (ImageView) menuView.findViewById(R.id.icon);
        ImageView image = (ImageView) menuView.findViewById(R.id.image_menu);
       // image.setLayoutParams(new RelativeLayout.LayoutParams(densityDpi, densityDpi));
        //	view_2.startAnimation(animation);
        TextView text_view = (TextView) menuView.findViewById(R.id.text_menu);
        applyRotation(0, 10, 35, 10, image);
        //	Drawable icon = getResources().getDrawable(mImageResource.getResourceId(pos, -1));
        // image_view.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
      //  image.setBackgroundResource(mImageResource.getResourceId(menuId, -1));
        //	image.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
/*        image.postDelayed(new Runnable() {

            @Override
            public void run() {
                //	image_view.startAnimation(animation);
            }

        }, 500);*/
        // icon.setImageResource(mIcons.getResourceId(menuId, -1));
        int iconDrawableId = getResources().getIdentifier(types[data.getInt(1)] + "_icon","drawable", getActivity().getPackageName());
        icon.setImageResource(iconDrawableId);
        //	icon.setLayoutParams(new RelativeLayout.LayoutParams(20,20));
        text_view.setTextColor(getActivity().getResources().getColor(R.color.White));
        //	text_view.setLayoutParams(new RelativeLayout.LayoutParams(densityWidth,densityWidth));
        //	image_view.startAnimation(animation);
        //	text_view.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
      //  text_view.setText(mTitles[menuId]);
        text_view.setText(data.getString(3));
        menuView.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View view) {
                ImageView background = (ImageView) view.findViewById(R.id.image_menu);
                background.setBackgroundColor(getResources().getColor(R.color.Blue));
                Intent web_view = new Intent(getActivity(), WebViewDataItem.class);
                web_view.putExtra("ARGUMENT_URL", "file:///data/data/org.webmenu.webmenuapp/files/Bitwise_Operators_IN_PHP.html");
                //						//	WebViewDataItem webviewdataitem = new WebViewDataItem();
                //						//  webviewdataitem.setURLContent("file:///android_asset/DIAMETER_PROTOCOL_RESULT_CODES.htm")
                 startActivity(web_view);
            }
        });
        //menuView.setLayoutParams(viewLayoutParams);
        menuView.setLayoutParams(layoutParams);
        return  menuView;
    }
}



