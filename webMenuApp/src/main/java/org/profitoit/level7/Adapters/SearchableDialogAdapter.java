package org.webmenu.level7.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.webmenu.webmenuapp.R;

import java.util.ArrayList;

public class SearchableDialogAdapter extends BaseAdapter {
	private String[] originalData = null;
	private static final int LIST_ITEM = 1;
	private static final int HEADER_ITEM = 0;
	public int iconScale;
    public Cursor results;
	private TypedArray imageQueryResults = null;
	private String[] matches_0 = null;
	private String[] found;
	private int[] viewTypeArray;
	private ArrayList<Integer> matches;
	private Context context;
	private String[] filteredData;
	private LayoutInflater mInflater;
	private ArrayList<String> mData = new ArrayList<String>();
	private int[] headerItems = null;
	private String[] headerNames;
	private int headerPosition = 0;
	 
	public SearchableDialogAdapter(Context context, String results) {
		this.originalData = context.getResources().getStringArray(R.array.search_query_results);
		headerItems = context.getResources().getIntArray(R.array.search_query_results_headers);
		this.headerNames = context.getResources().getStringArray(R.array.search_query_results_header_title);
		this.filteredData = new String[originalData.length + headerItems.length];		
		this.viewTypeArray = new int[originalData.length + headerItems.length];
		//translateToList();
		this.imageQueryResults =  context.getResources().obtainTypedArray(R.array.search_query_icon_set);
		// this.filteredData = originalData;
		//  mInflater = LayoutInflater.from(context);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		iconScale = (int) (context.getResources().getDisplayMetrics().density * 48);
		context = context;
	}

	public SearchableDialogAdapter(Context context2, ArrayList<Integer> matchesF, String string) {
		originalData = context2.getResources().getStringArray(R.array.search_query_results);
		headerItems = context2.getResources().getIntArray(R.array.search_query_results_headers);
		headerNames = context2.getResources().getStringArray(R.array.search_query_results_header_title);
		filteredData = new String[originalData.length + headerItems.length];
		found = new String[originalData.length + headerItems.length];
		matches = matchesF;		
		viewTypeArray = new int[originalData.length + headerItems.length];
		// translateToList();
		imageQueryResults =  context2.getResources().obtainTypedArray(R.array.search_query_icon_set);
		// this.filteredData = originalData;
		//  mInflater = LayoutInflater.from(context);
		mInflater = (LayoutInflater) context2
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		iconScale = (int) (context2.getResources().getDisplayMetrics().density * 48);
		context = context2;
	//	matchArrView = nm;
	}

	public int getCount() {
		return matches.size();
	}
	 
	public Object getItem(int position) {
		return matches.get(position);
	}
	 
	public long getItemId(int position) {
		return position;
	}
	 
/*	@Override
	public int getItemViewType(int position) {
		super.getItemViewType(position);
		return viewTypeArray[position];
	}*/
	
	public void translateToList() {
		if (headerItems.length != -1) {
			for(int c = 0 , b = 0, g = 0; getCount() > c; c++) {
				if (headerItems[b] == c) {
						viewTypeArray[c] = 0;
						filteredData[c] = headerNames[b];
						found[c] = "1";
					if (headerItems.length > b+1) {
						b++;
					}
				} else {
					viewTypeArray[c] = 1;
					filteredData[c] = originalData[g];
				//	found[c] = matches_n[g];
					g++;
				}
			}
		}
	}

//	@Override
//	public int getViewTypeCount() {
//		return 2;
//	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid unnecessary calls
		// to findViewById() on each row.
		int type = getItemViewType(position);
		ViewHolder holder;
		HeaderViewHold headerView;
			// When convertView is not null, we can reuse it directly, there is no need
			// to reinflate it. We only inflate a new View when the convertView supplied
			// by ListView is null.
			if (convertView == null) {
				
/*                switch(type) {
                case LIST_ITEM:*/
	    				holder = new ViewHolder();
	                	convertView = mInflater.inflate(R.layout.searchables_list_item, parent, false);
	                //	if (matches[position] == "1") {
		    				holder.image = (ImageView) convertView.findViewById(R.id.image_view);
		    				holder.text = (TextView) convertView.findViewById(R.id.text_view);
		    				holder.text.setTextSize(18);
		    				holder.text.setText(filteredData[position]);
		    				holder.image.setLayoutParams(new LinearLayout.LayoutParams(iconScale, iconScale));
		    				holder.image.setImageResource(imageQueryResults.getResourceId(position, -1));
	                //	} else {
//	                		holder.text = (TextView) convertView.findViewById(R.id.text_view);
//		    				holder.text.setTextSize(18);
//		    				holder.text.setText(R.string.no_matches_found);
	                //	}
	    				convertView.setTag(holder);
//                    break;
//                case HEADER_ITEM:
//                	headerView = new HeaderViewHold();
//                	convertView = mInflater.inflate(R.layout.searchable_header_items, parent, false);
//                	headerView.headerTxt = (TextView) convertView.findViewById(R.id.headerText);
//                	headerView.headerTxt.setText(filteredData[position]);
// /*               	headerView.image = (ImageView) convertView.findViewById(R.id.image_view);
//                	headerView.image.setLayoutParams(new RelativeLayout.LayoutParams(iconScale, iconScale));
//                	headerView.image.setImageResource(imageQueryResults.getResourceId(position, -1));
//                	headerView.text = (TextView) convertView.findViewById(R.id.text_view);
//                	headerView.text.setTextSize(18);
//                	headerView.text.setText(filteredData[position]);*/
//                	convertView.setTag(headerView);
//                    break;
//                }
				
				// Creates a ViewHolder and store references to the two children views
				// we want to bind data to.
				 
				// Bind the data efficiently with the holder.
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
		//		if (type == 1) {
					holder = (ViewHolder) convertView.getTag();
		//		} else {
				//	headerView = (HeaderViewHold) convertView.getTag();
		//		}
			}
			
		return convertView;
	}	
	
	static class ViewHolder {
		ImageView image;
		TextView text;
	}
	
	static class HeaderViewHold {
		TextView headerTxt;
		ImageView image;
		TextView text;
	}
	 	
}
