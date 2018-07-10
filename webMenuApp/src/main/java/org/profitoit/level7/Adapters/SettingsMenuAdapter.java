package org.webmenu.level7.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.webmenu.webmenuapp.R;

public class SettingsMenuAdapter extends BaseAdapter {

	Context context;
	String[] menuTitles;
	TypedArray menuIcons;
	final int iconScale;
	
	public SettingsMenuAdapter(Context context) {
		this.context = context;
		menuTitles = context.getResources().getStringArray(R.array.nav_drawer_items);
		menuIcons = context.getResources().obtainTypedArray(R.array.nav_drawer_icon);
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
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.settings_list_menu_item, null);
		}
		if (position % 2 != 0) {
			FrameLayout rightContainer = (FrameLayout) (convertView).findViewById(R.id.settingCont);
			ImageView rightView = new ImageView(context);
			rightView.setImageResource(R.drawable.ic_action);
			rightView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			//rightContainer.setBackgroundResource(R.drawable.ic_action);
			rightContainer.addView(rightView);
		} {
			ImageView icon = (ImageView) convertView.findViewById(R.id.settings_icon);
			icon.setLayoutParams(new RelativeLayout.LayoutParams(iconScale, iconScale));
			TextView textView = (TextView) convertView.findViewById(R.id.settings_name);
			icon.setBackgroundResource(menuIcons.getResourceId(position,-1));
			
			textView.setText(menuTitles[position]);
			menuIcons.recycle();
		}
		return convertView;
	}
	
}
