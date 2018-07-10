package org.webmenu.level11.Menu;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewAnimator;

import org.webmenu.level11.View.ImageViewExample;
import org.webmenu.level11.ViewGroup.CustomMenuView;
import org.webmenu.webmenuapp.R;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class FourImagesMenu extends Fragment {

    final private int SWIPE_MIN_DISTANCE = 200;
	private TypedArray mThumbIds;
	private ImageViewExample image_1;
    private ImageViewExample image_2;
    private ImageViewExample image_3;
    private CustomMenuView customMenuViewGroup;
    private FrameLayout viewContainer_1;
    private FrameLayout viewContainer_2;
    private FrameLayout viewContainer_3;
    private FrameLayout viewContainer_4;
    private int heightPixels;
    private int widthPixels;
    public ArrayList<View> touchableView;

    public void getDisplayMetrics() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        wm.getDefaultDisplay().getMetrics(metrics);
        heightPixels = metrics.heightPixels;
        widthPixels = metrics.widthPixels;
        int scale = metrics.heightPixels * 50 / 100;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
        getDisplayMetrics();
        // customMenuViewGroup = (CustomMenuView) inflater.inflate(R.layout.view_menu, null);
        View menu_view = inflater.inflate(R.layout.main_view_menu, null);
        ViewGroup swipe_right = (ViewGroup) inflater.inflate(R.layout.swipe_right, null);
        RelativeLayout list_view = (RelativeLayout) menu_view.findViewById(R.id.relative_layout);
       // RelativeLayout containerView = new RelativeLayout(getActivity());
        // CustomMenuView.LayoutParams params = new CustomMenuView.LayoutParams(CustomMenuView.LayoutParams.WRAP_CONTENT, CustomMenuView.LayoutParams.WRAP_CONTENT);
       // RelativeLayout main = (RelativeLayout) inflater.inflate(R.layout.main_image_menu, null);
        //SurfaceView surfaceView = (SurfaceView) menu_view.findViewById(R.id.surfaceView);
        final ScaleAnimation scale_in = new ScaleAnimation(0, 1, 0, 1);
        final ScaleAnimation scale_out = new ScaleAnimation(1 , 0, 1, 0);
        scale_in.setDuration(800);
        scale_out.setDuration(800);
        final TranslateAnimation translateIn = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1, Animation.RELATIVE_TO_PARENT,0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
        translateIn.setDuration(800);
        final TranslateAnimation translateOut = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,1, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
        translateOut.setDuration(800);
        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(translateIn);
        animSet.addAnimation(scale_out);
        animSet.setDuration(800);
        final TextSwitcher txt = new TextSwitcher(getActivity());
        final ViewAnimator animator = new ViewAnimator(getActivity());
        txt.setInAnimation(translateIn);
        txt.setOutAnimation(translateOut);
        translateIn.setDuration(800);
        animator.setInAnimation(animSet);
        animator.setOutAnimation(translateOut);
        // transit.setAnimator(LayoutTransition.APPEARING, animator);
        // customMenuViewGroup.setLayoutTransition(transit);
       // params.position = CustomMenuView.LayoutParams.POSITION_LEFT;
        // View menu_view = inflater.inflate(R.layout.view, null);
       // RelativeLayout rel = (RelativeLayout) menu_view.findViewById(R.id.relative_layout);
/*        getDisplayMetrics();
		View base = inflater.inflate(R.layout.front_side_menu_api11, null);
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
    	int densityWidth = heightPixels * 50 / 100;
    	int densityHeight = widthPixels * 50 / 100;
    //	base.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,densityHeight));
    	Animation scale_image_left = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_forward);
    	ScaleAnimation scale_image = new ScaleAnimation(0, 50, 0, 0);
    //	scale_image.setDuration(Animation.INFINITE);
    	image_1 = (ImageViewExample) base.findViewById(R.id.image_menu);
        image_1.setScaleType(ScaleType.FIT_XY);
        image_1.setOnTouchListener(new OnImageTouch());
	//	translateCameraOnImageView(1,image_1);
		// image_1.setAnimation(scale_image_left);
    	image_2 = (ImageViewExample) base.findViewById(R.id.image_menu_2);
        image_2.setScaleType(ScaleType.FIT_XY);
    	image_3 = (ImageViewExample) base.findViewById(R.id.image_menu_3);
        image_3.setScaleType(ScaleType.FIT_XY);
    	ImageViewExample image_4 = (ImageViewExample) base.findViewById(R.id.image_menu_4);*/
		mThumbIds = getActivity().getResources().obtainTypedArray(R.array.nav_front_menu);
/*		image_1.setImageResource(mThumbIds.getResourceId(0, -1));
		image_1.setLayoutParams(new RelativeLayout.LayoutParams(densityHeight,densityHeight));
		image_2.setImageResource(mThumbIds.getResourceId(1, -1));*/
       // image_2.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT));
       // image_3.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT));
        Drawable shape = getResources().getDrawable(R.drawable.front_menu_shape_view);
        Canvas round = new Canvas();
        Paint pt_1 = new Paint();
        pt_1.setStrokeWidth(25);
        pt_1.setColor(getResources().getColor(R.color.Green));
        round.drawPaint(pt_1);
       // shape.setBounds(100,100,widthPixels + 100, heightPixels + 100);
        BitmapDrawable bitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.company_3);
        RoundedBitmapDrawable roundedBitmap = (RoundedBitmapDrawable) RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.news_8));
       // roundedBitmap.setAlpha(80);
        roundedBitmap.setCornerRadius(18);
        final LevelListDrawable levelList = (LevelListDrawable) getResources().getDrawable(R.drawable.front_side_menu_drawable_list);
        Drawable insetShapeView = (InsetDrawable) getResources().getDrawable(R.drawable.inset_draw_example);
        final TextView txt_1 = new TextView(getActivity());
        final TextView txt_2 = new TextView(getActivity());
        ImageView image_11 = new ImageView(getActivity());
        ImageView image_21 = new ImageView(getActivity());
        Drawable frameLayer = getResources().getDrawable(R.drawable.front_menu_shape_view);
        // roundedBitmap.draw(round);
        // containerView.setBackground(shape);
        // lp.setLayoutDirection(CustomMenuView.LayoutParams.POSITION_RIGHT);
       // CustomMenuView.LayoutParams params = (CustomMenuView.LayoutParams) menuViewGroup.getLayoutParams();
       // params.position = CustomMenuView.LayoutParams.POSITION_LEFT;
       // image_11.setLayoutParams(params);
        View image_menu_1 = new org.webmenu.level11.View.View(getActivity());
       // image_menu_1.setBackgroundResource(mThumbIds.getResourceId(0, -1));
        final View image_menu_2 = new org.webmenu.level11.View.View(getActivity());
        // image_menu_2.setBackgroundResource(mThumbIds.getResourceId(1, -1));
       // image_menu_2.setBackground(roundedBitmap);
        // image_11.setImageResource(mThumbIds.getResourceId(0, -1));
        // image_21.setImageResource(mThumbIds.getResourceId(1, -1));
        image_21.setId(R.id.image_view);
       // txt.addView(image_menu_1);
        String text = String.format(getResources().getString(R.string.letter_to_all), "", -1);
        CharSequence styledText = Html.fromHtml(text);
        txt_1.setText(styledText);
        txt_1.setTextColor(getResources().getColor(R.color.primary_material_light));
        txt_1.setTextSize(18);
        txt_2.setText(styledText);
        RelativeLayout firstLayout = new RelativeLayout(getActivity());
        firstLayout.setId(R.id.image_menu);
        firstLayout.setPadding(50, 50, 50, 50);
        txt_1.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // image_menu_2.setBackground(levelList);
        txt_1.setPadding(75, 75, 75, 75);
        // txt_1.setBackground(roundedBitmap);
        txt_1.setBackground(bitmap);
        txt_1.setVerticalScrollBarEnabled(true);
        // firstLayout.setBackground(shape);
        firstLayout.addView(txt_1);
        // rl.addView(image_menu_1);
        //txt.setBackground(levelList);
        txt.setPadding(120, 120, 120, 120);
       // rl.addView(image_menu_1);
        //txtV.setBackground(shape);
       // txt.setBackground(levelList);
       // txt.setForeground(shape);
       // txt.addView(txt_1);
       // txt.addView(txt_2);
       // txt.setBackground(getResources().getDrawable(R.drawable.front_menu_shape_view));
       // txt.setText(styledText);
        //rel.addView(image_11);
        //rel.addView(image_13);
        //txt.addView(image_menu_1);
       // txt.setText(getResources().getString(R.string.menu_1));
       // Canvas first = new Canvas(Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.solutions), 10, 10, 100, 100));
       // txt.draw(first);
       // image_menu_1.draw(first);
       // animator.addView(txt);
       // animator.setBackground(levelList);
       // animator.addView(image_menu_2,0);
       // animator.addView(txt,0);
       // animator.addView(image_menu_1);
        list_view.setOnTouchListener(new View.OnTouchListener() {
            int action;
            int level;
            float aX;
            float aY;
            float cX;
            float cY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        aX = motionEvent.getX();
                        aY = motionEvent.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        cX = motionEvent.getX();
                        cY = motionEvent.getY();
                        if (cX > aX) {
                            // if (motionEvent.getX() - menuX > SWIPE_MIN_DISTANCE) animator.showNext();
                            animator.showNext();
                            level = levelList.getLevel();
                            if (level < 4) {
                                levelList.setLevel(level + 1);
                            } else {
                                levelList.setLevel(0);
                            }
                        }
                        if (aX > cX) {
                            animator.showPrevious();
                        }
                    }
                }
                return true;
            }
        });
       // animator.addView(image_menu_1);
       // animator.addView(rl);
       // animator.addView(image_menu_2);
       // list_view.addView(animator);
       // image_menu_1.setId(R.id.image_menu);
        image_menu_2.setId(R.id.image_menu_2);
        image_menu_2.setVisibility(View.GONE);
       // swipe_right.addView(image_menu_1, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.RIGHT));
        swipe_right.addView(image_menu_2, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.RIGHT));
        swipe_right.addView(firstLayout, 1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,Gravity.LEFT));
        swipe_right.setOnTouchListener(new View.OnTouchListener() {

            float curX;
            float x;
            @Override
            public boolean onTouch(View view, MotionEvent ev) {

                switch(ev.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        x = ev.getX();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        curX = ev.getX();
                        if (curX > x) {
                           // View image_1 = view.findViewById(R.id.image_menu);
                            View firstLayout = view.findViewById(R.id.image_menu);
                            View image_2 = view.findViewById(R.id.image_menu_2);
                           // image_1.setVisibility(View.VISIBLE);
                            firstLayout.setVisibility(View.VISIBLE);
                            image_2.setVisibility(View.VISIBLE);
                            image_2.startAnimation(scale_in);
                           // image_1.startAnimation(translateOut);
                           // image_1.setVisibility(View.GONE);
                            firstLayout.startAnimation(translateOut);
                            firstLayout.setVisibility(View.GONE);
                        }
                        if (curX < x) {
                           // View image_1 = view.findViewById(R.id.image_menu);
                            View firstLayout = view.findViewById(R.id.image_menu);
                            View image_2 = view.findViewById(R.id.image_menu_2);
                           // image_1.setVisibility(View.VISIBLE);
                            firstLayout.setVisibility(View.VISIBLE);
                            image_2.setVisibility(View.VISIBLE);
                            image_2.startAnimation(translateOut);
                          //  image_1.startAnimation(scale_in);
                            firstLayout.startAnimation(scale_in);
                            image_2.setVisibility(View.GONE);
                        }
                    }
                }
                return true;

            }
        });
        //ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams ( ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        // list_view.addView(image_menu_1);
        // list_view.addView(image_21);
        //customMenuViewGroup.addView(animator);
       // image_13.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT));
       // image_13.setLayoutParams(params);
       // image_11.setLayoutParams(lp);
       // frameLayout.addView(image_11);
       // touchableView.add(image_11);
       // touchableView.add(image_13);
        //viewContainer_1.addTouchables(touchableView);
        //viewContainer_1.addView(image_11);
        // menuViewGroup.addView(image_11,0,new CustomMenuView.LayoutParams(CustomMenuView.LayoutParams.WRAP_CONTENT, CustomMenuView.LayoutParams.WRAP_CONTENT));
        // menuViewGroup.addView(image_13,1,new CustomMenuView.LayoutParams(CustomMenuView.LayoutParams.MATCH_PARENT, CustomMenuView.LayoutParams.MATCH_PARENT));
       // rel.addView(image_11);
       // rel.addView(image_13);
       // touchableView.add(image_11);
       // touchableView.add(image_13);
       // customMenuViewGroup.addTouchables(touchableView);
       // customMenuViewGroup.setOnTouchListener(new OnViewTouchListen());
        // viewContainer_1.addView(menuViewGroup);
       // frameLayout.addTouchables(touchableView);
        //viewContainer_1.addView(image_3);
        //viewContainer_1.addView(image_4);
/*		String[] mTitles = getActivity().getResources().getStringArray(R.array.grid_menu_item_names);
		TextView3DRotate text_menu_0 = (TextView3DRotate) base.findViewById(R.id.text_menu);
		text_menu_0.setHeight(densityHeight * 50 / 100);
		TextView3DRotate text_menu_2 = (TextView3DRotate) base.findViewById(R.id.text_menu_2);
		text_menu_2.setHeight(densityHeight * 50 / 100);
		TextView3DRotate text_menu_4 = (TextView3DRotate) base.findViewById(R.id.text_menu_4);
		text_menu_4.setHeight(densityHeight * 75 / 100);
		TextView3DRotate text_menu_5 = (TextView3DRotate) base.findViewById(R.id.text_menu_5);
		text_menu_5.setHeight(densityHeight * 75 / 100);
		text_menu_0.setText(mTitles[0]);
		text_menu_2.setText(mTitles[1]);
		if (densityWidth > densityHeight) {
				image_3.setImageResource(mThumbIds.getResourceId(2, -1));
				text_menu_4.setText(mTitles[2]);
		}*/
	//	translateCameraOnImageView(1,image_1);
	//	translateCameraOnImageView(3,image_4);
		mThumbIds.recycle();
	//	return customMenuViewGroup;
        return swipe_right;
	}
	
	public void translateCameraOnImageView(int position, ImageView imageBm) {
		Matrix mati = new Matrix();
		// mati.postSkew(0.2f, 0.2f);
		Camera cam = new Camera();
		cam.rotateY(35);
		cam.getMatrix(mati);
	//	Paint paint = new Paint();
		Canvas canvas = new Canvas();
		// Bitmap bm = BitmapFactory.decodeResource(getResources(), mThumbIds.getResourceId(position, -1));
		// imageBm.setImageBitmap(bm);
		mati.preScale(0.3f, 0.3f);
		imageBm.setScaleType(ScaleType.MATRIX);
		imageBm.setImageMatrix(mati);
	}

    public class OnImageTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            image_1.setImageResource(R.drawable.aaa3);
          //  viewTouchImage.add(image_1);
          //  viewTouchImage.add(image_2);
            int width = widthPixels * 50 / 100;
         //   image_1.setLayoutParams(new RelativeLayout.LayoutParams(width,width));
            image_1.refreshDrawableState();
          //  view.addTouchables(viewTouchImage);
            return true;
        }
    }

    public class OnViewTouchListen implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            view.findViewById(R.id.image).scrollBy(-50,0);
            return true;
        }
    }

    public void getImageResources() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.id.image_menu, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
    }

    public Bitmap createRoundedCornersImage(int orig, int res, boolean frame, Paint frmPaint) {
        Bitmap output = BitmapFactory.decodeResource(getResources(), orig);
        Canvas canvas = new Canvas(output);
        RoundedBitmapDrawable round = (RoundedBitmapDrawable) getResources().getDrawable(res);
        round.draw(canvas);
        return  output;
    }

}
