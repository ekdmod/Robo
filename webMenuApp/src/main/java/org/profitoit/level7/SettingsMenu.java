package org.webmenu.level7;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.webmenu.level7.Adapters.SettingsMenuAdapter;
import org.webmenu.webmenuapp.R;

public class SettingsMenu extends ActionBarActivity {
	
	private Context stContext;
	private static FragmentManager fm;
	
	public static SettingsMenu newInstance(FragmentManager fragmentManager){
		SettingsMenu menu = new SettingsMenu();
		fm = fragmentManager;
		return menu;
	}

	@Override
	public View onCreateView(View parent, String name, Context context,
			AttributeSet attrs) {
		stContext = context;
		return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options_menu_setting);
		fm = getSupportFragmentManager();
		int color = getResources().getColor(R.color.Aqua);
		TextView name = (TextView) findViewById(R.id.settings_title_name);
		name.setTextColor(color);
		name.setText(R.string.settings);
		name.setTextSize(32);
		int size = (int) (getBaseContext().getResources().getDisplayMetrics().density * 48);		
		ImageView icon = (ImageView) findViewById(R.id.image_view);
		// icon.setLayoutParams(new RelativeLayout.LayoutParams(size, size));
		icon.setImageResource(R.drawable.settings);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 1:
				return true;
			case 2:
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.v7_settings_menu, menu);
	}
	
	public static class MenuViewFragment extends Fragment {

		public MenuViewFragment() {
			super();
		}
		
		public MenuViewFragment(Context countext, int res) {
			super();
		}

		@Override
		public View onCreateView(LayoutInflater inflater,
				ViewGroup container,
				Bundle savedInstanceState) {
			View gridView = inflater.inflate(R.layout.settings_menu_grid, container);
			GridView grid = (GridView) gridView.findViewById(R.id.grid_view);
			grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					fm.beginTransaction().add(R.id.popUp_Fragment, new MenuViewFragment()).commit();
				}
				
			});
			grid.setAdapter(new SettingsMenuAdapter(getActivity()));
			return grid;
		}
		
	}
	
	public static class PopUp extends Fragment {

		public PopUp() {
			super();
		}
		
		public PopUp(Context countext, int res) {
			super();
		}

		@Override
		public View onCreateView(LayoutInflater inflater,
				ViewGroup container,
				Bundle savedInstanceState) {
			View gridView = inflater.inflate(R.layout.settings_menu_grid, container);
			GridView grid = (GridView) gridView.findViewById(R.id.grid_view);
			return grid;
		}
		
	}
	
}
