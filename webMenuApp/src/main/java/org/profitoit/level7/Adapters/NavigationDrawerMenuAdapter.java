package org.webmenu.level7.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.webmenu.webmenuapp.R;

public class NavigationDrawerMenuAdapter extends BaseAdapter {

	Context context;
	String[] menuTitles;
	TypedArray menuIcons;
	final int iconScale;
	
	public NavigationDrawerMenuAdapter(Context context) {
		this.context = context;
		menuTitles = context.getResources().getStringArray(R.array.nav_drawer_items);
		menuIcons = context.getResources().obtainTypedArray(R.array.nav_drawer_icon);
		// The gesture threshold expressed in dp
		final float GESTURE_THRESHOLD_DP = 16.0f;

		// Get the screen's density scale
		iconScale = (int) (context.getResources().getDisplayMetrics().density * 48);

	}
	
	@Override
	public int getCount() {
		return menuTitles.length;
	}

	@Override
	public Object getItem(int position) {
		return menuTitles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.menu_style, null);
		}
		
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		imgIcon.setLayoutParams(new RelativeLayout.LayoutParams(iconScale, iconScale));
		TextView textView = (TextView) convertView.findViewById(R.id.title);
		// TextView textCounter = (TextView) convertView.findViewById(R.id.counter);
		
		int id = menuIcons.getResourceId(position,-1);
		
		// Bitmap bm = BitmapFactory.decodeResource(context.getResources(), id);
		
		// imgIcon.setImageBitmap(bm);
		
		imgIcon.setBackgroundResource(id);
		
		textView.setText(menuTitles[position]);
		menuIcons.recycle();
		return convertView;
	}
	
}
