package org.webmenu.webmenuapp.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import org.webmenu.webmenuapp.R;
import org.webmenu.webmenuapp.View.ImageViewExample;
import org.webmenu.webmenuapp.View.TextView3DRotate;

public class FourImagesMenu extends Fragment {

	private TypedArray mThumbIds;
    private ImageViewExample image_1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View base = inflater.inflate(R.layout.front_slide_menu, null);
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
    	DisplayMetrics metrics = new DisplayMetrics();
    	int densityDpi = (int)(metrics.density * 160f);
    	wm.getDefaultDisplay().getMetrics(metrics);
    	int densityWidth = metrics.heightPixels * 50 / 100;
    	int densityHeight = metrics.widthPixels * 50 / 100;
    	int scale = metrics.heightPixels * 50 / 100;
    //	base.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,densityHeight));
    	Animation scale_image_left = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_forward);
    	ScaleAnimation scale_image = new ScaleAnimation(0, 50, 0, 0);
    //	scale_image.setDuration(Animation.INFINITE);  	
    	image_1 = (ImageViewExample) base.findViewById(R.id.image_menu);
        image_1.setOnTouchListener(new ImageViewTouchListener());
	//	translateCameraOnImageView(1,image_1);
		// image_1.setAnimation(scale_image_left);
    	ImageViewExample image_2 = (ImageViewExample) base.findViewById(R.id.image_menu_2);
    	ImageViewExample image_3 = (ImageViewExample) base.findViewById(R.id.image_menu_3);
    	ImageViewExample image_4 = (ImageViewExample) base.findViewById(R.id.image_menu_4);
		mThumbIds = getActivity().getResources().obtainTypedArray(R.array.nav_front_menu);
		image_1.setImageResource(mThumbIds.getResourceId(0, -1));		
		image_1.setLayoutParams(new RelativeLayout.LayoutParams(densityHeight,densityHeight));
		image_2.setImageResource(mThumbIds.getResourceId(1, -1));
		String[] mTitles = getActivity().getResources().getStringArray(R.array.grid_menu_item_names);
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
		}
	//	translateCameraOnImageView(1,image_1);
	//	translateCameraOnImageView(3,image_4);
		mThumbIds.recycle();
		return base;
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

    private class ImageViewTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            image_1.setImageResource(mThumbIds.getResourceId(2, -1));
            image_1.refreshDrawableState();
            return true;
        }
    }
}
