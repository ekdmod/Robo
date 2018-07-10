package org.webmenu.level11.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import org.webmenu.webmenuapp.R;

public class ImageAdapter extends BaseAdapter {
	
	private Context context;
	private String[] mTitles;
	private TypedArray mImageResource;
//	private int[] mImageResource;
	private TypedArray mIcons;
//	private int[] mIcons;
	private int layoutResourceId;
	ViewGroup listView;
	int changed = 0;
	int densityWidth;
	int densityHeight;
	
    public ImageAdapter(Context context, int layoutResourceId) {
    	this.context = context;
    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
    	DisplayMetrics metrics = new DisplayMetrics();
    	int densityDpi = (int)(metrics.density * 160f);
    	wm.getDefaultDisplay().getMetrics(metrics);
    	densityWidth = metrics.heightPixels * 25 / 100;
    	densityHeight = metrics.widthPixels * 25 / 100;
    	switch(layoutResourceId) {
    	case 1:
    		mTitles = context.getResources().getStringArray(R.array.grid_menu_item_names);
        	mImageResource = context.getResources().obtainTypedArray(R.array.grid_menu_item_background);
        	mIcons = context.getResources().obtainTypedArray(R.array.grid_menu_item_icons);
    	default:
	    	mTitles = context.getResources().getStringArray(R.array.grid_menu_item_names);
	    	mImageResource = context.getResources().obtainTypedArray(R.array.grid_menu_item_background);
	    	mIcons = context.getResources().obtainTypedArray(R.array.grid_menu_item_icons);
    	}
    }
    
    public int getCount() {
    	return mTitles.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    
	public View getView(int position, View view, ViewGroup viewgroup)
    {
    	View gridView = view;
        LayoutInflater layoutinflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (gridView == null)
        {
           gridView = new View(context);
           gridView = layoutinflater.inflate(R.layout.frame_image_relative, null);
           GridImageHolder gridimageholder = new GridImageHolder();
           ImageView background_view = (ImageView)gridView.findViewById(R.id.image_menu);
           ImageView icon = (ImageView)gridView.findViewById(R.id.icon);
           icon.setImageResource(mIcons.getResourceId(position, -1));
           background_view.setBackgroundResource(mImageResource.getResourceId(position, -1));
           TextView textview = (TextView)gridView.findViewById(R.id.text_menu);
           textview.setTextColor(context.getResources().getColor(R.color.White));
           textview.setText(mTitles[position]);
           gridView.setLayoutParams(new android.widget.AbsListView.LayoutParams(densityWidth, densityWidth));
           gridView.setTag(gridimageholder);
        } else {
	    		ImageView image =  new ImageView(context);
	    		image.setScaleType(ScaleType.FIT_CENTER);
	            image.setBackgroundResource(mImageResource.getResourceId(position, -1));
	            ((ViewGroup)gridView).addView(image,position);
        }
		ImageView image =  new ImageView(context);
        image.setBackgroundResource(mImageResource.getResourceId(position, -1));
        ((ViewGroup)gridView).addView(image);
        mImageResource.recycle();
        mIcons.recycle();
        return gridView;
    }

    public void onAnimationEnd(Animation animation)
    {
    }

    public void onAnimationRepeat(Animation animation)
    {
    }

    public void onAnimationStart(Animation animation)
    {
    }    
    
	static class GridImageHolder {
		ImageView icon;
		TextView text;
		ImageView image;
	}
	
	public void getDensityType() {
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
		switch(metrics.densityDpi){
		     case DisplayMetrics.DENSITY_LOW:
		                break;
		     case DisplayMetrics.DENSITY_MEDIUM:
		                 break;
		     case DisplayMetrics.DENSITY_HIGH:
		                 break;
		}
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {      
		// First decode with inJustDecodeBounds=true to check dimensions     
		final BitmapFactory.Options options = new BitmapFactory.Options();     
		options.inJustDecodeBounds = true;     
		BitmapFactory.decodeResource(res, resId, options);      
		// Calculate inSampleSize     
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);      
		// Decode bitmap with inSampleSize set     
		options.inJustDecodeBounds = false;     
		return BitmapFactory.decodeResource(res, resId, options); 
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {     
		// Raw height and width of image     
		final int height = options.outHeight;     
		final int width = options.outWidth;     
		int inSampleSize = 1;      
		if (height > reqHeight || width > reqWidth) {          
			final int halfHeight = height / 2;         
			final int halfWidth = width / 2;          
			// Calculate the largest inSampleSize value that is a power of 2 and keeps both         
			// height and width larger than the requested height and width.         
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {             
				inSampleSize *= 2;         }     
			}      
		
			return inSampleSize; 
	}
	
}
