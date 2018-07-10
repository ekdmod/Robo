package org.webmenu.webmenuapp.menu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.webmenu.level7.ActiveMenu;
import org.webmenu.level7.Adapters.ImageAdapter;
import org.webmenu.webmenuapp.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GridImageViewMenu extends Fragment {
	public static final String ARG_SECTION_ID = "menu_item_id";

    public static GridImageViewMenu newInstance(int i)
    {
    	GridImageViewMenu grid_view = new GridImageViewMenu();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_ID, i);
        grid_view.setArguments(bundle);
        return grid_view;
    }

	public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        super.onCreateView(layoutinflater, viewgroup, bundle);
        int menu_id = getArguments().getInt("menu_item_id");
        final OnScrollChangedListener gridViewOnScrollChange;
        final Handler mHandler = new Handler();

        View view = layoutinflater.inflate(R.layout.grid_view, viewgroup, false);
        final GridView gridview = (GridView)view.findViewById(R.id.grid_view);
        gridview.setAdapter(new ImageAdapter(getActivity(), menu_id));
        gridview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
			
			
			
		});
        gridview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, final View grid_item, int j, long l)
            {
            	 Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.accelerate_image);
                ((ViewGroup) grid_item).removeView(((TextView)grid_item.findViewById(R.id.text_menu)));
                ((ImageView)grid_item.findViewById(R.id.image_menu)).startAnimation(animation);
                
            }
        });
        gridview.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView adapterview, View view1, int j, long l)
            {
/*                (new Bundle()).putString("link", "file:///android_asset/DIAMETER_PROTOCOL_RESULT_CODES.htm");
                WebViewDataItem webviewdataitem = new WebViewDataItem();
                webviewdataitem.setURLContent("file:///android_asset/DIAMETER_PROTOCOL_RESULT_CODES.htm");
                getFragmentManager().beginTransaction().replace(R.id.container, webviewdataitem).commit();*/
                return true;
            }
        });
        return view;
    }

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((ActiveMenu) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_ID));
	}
    
}
