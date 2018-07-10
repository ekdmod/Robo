package org.webmenu.webmenuapp.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.webmenu.webmenuapp.R;

public class BottomContextualMenu extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			View view = inflater.inflate(R.layout.bottom_menu, null);
			GridView grid_view = (GridView) view.findViewById(R.id.grid_menu);
			grid_view.setAdapter(new BottomGridAdapter());
			grid_view.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
				}
				
			});
			return view;
	}
	
	public class BottomGridAdapter extends BaseAdapter {
		
		private int densityWidth;
		private String[] mTitles;
		private int[] mImages;
		
		public BottomGridAdapter() {
			WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
	    	Display display = wm.getDefaultDisplay();
	    	DisplayMetrics metrics = new DisplayMetrics();
	    	int densityDpi = (int)(metrics.density * 160f);
	    	wm.getDefaultDisplay().getMetrics(metrics);
	    	densityWidth = metrics.heightPixels * 33 / 100;
	    	int densityHeight = metrics.widthPixels * 50 / 100;
	    	mTitles = getResources().getStringArray(R.array.bottom_menu_titles);
	    	mImages = getResources().getIntArray(R.array.bottom_menu_icons);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup viewGroup) {
			View convertView = view;
			TextView textView;
			ImageView imageView;
			MenuItemHolder holder = new MenuItemHolder();
	        if (convertView == null) {
	        	 textView = new TextView(getActivity());
	        	 textView.setGravity(Gravity.CENTER);
	 		   	 imageView = new ImageView(getActivity());
	        	 imageView.setId(R.id.image_menu);
	 		   	 imageView.setLayoutParams(new GridView.LayoutParams(densityWidth, densityWidth));
	 		   	 imageView.setAdjustViewBounds(false);
	 		   	 imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	 		   	 imageView.setPadding(8, 8, 8, 8);
	             
	             holder.tv.setText(mTitles[position]);
	             holder.img.setImageResource(mImages[position]);
	        }
	        
	        return convertView;
			
		}
		
		public class MenuItemHolder {
			ImageView img;
			TextView tv;
		}
		
	}
	
}
