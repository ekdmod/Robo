package org.webmenu.webmenuapp.Searching;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.webmenu.level7.Adapters.SearchableDialogAdapter;
import org.webmenu.webmenuapp.R;

import java.util.ArrayList;

public class SearchablesDialogFragment extends Dialog {
	private Context mContext;
	private Editable results;

	public SearchablesDialogFragment(Context context, int theme) {
		super(context, theme);
		
	}

	public SearchablesDialogFragment(Context context, int theme,
			Editable editable) {
		super(context, theme);
		results = editable;
		mContext = context;
	}

	@Override
	public View onCreatePanelView(int featureId) {
		
		return super.onCreatePanelView(featureId);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.search_query_completion_form);
	//	String[] m = new String[8];
		ArrayList<Integer> matchesF = new ArrayList<Integer>();
		ListView resultLayout = (ListView) findViewById(R.id.listView);
		TextView resultsView = (TextView) findViewById(R.id.resultsView);
		String[] search_query_result = mContext.getResources().getStringArray(R.array.search_query_dict);
/*		String[] about_us_query = mContext.getResources().getStringArray(R.array.search_query_about_us);
		boolean a_0 = Arrays.asList(about_us_query).contains(results.toString());
		m[0] = a_0 == true ? "1" : "0";
		String[] products_query = mContext.getResources().getStringArray(R.array.search_query_products);
		boolean a_1 = Arrays.asList(products_query).contains(results.toString());
		m[1] = a_1 == true ? "1" : "0";
		String[] services_query = mContext.getResources().getStringArray(R.array.search_query_services);
		boolean a_2 = Arrays.asList(services_query).contains(results.toString());
		m[2] = a_2 == true ? "1" : "0";
		String[] support_query = mContext.getResources().getStringArray(R.array.search_query_support);
		boolean a_3 = Arrays.asList(support_query).contains(results.toString());
		m[3] = a_3 == true ? "1" : "0";
		String[] knowledge_base_query = mContext.getResources().getStringArray(R.array.search_query_knowledge);
		boolean a_4 = Arrays.asList(knowledge_base_query).contains(results.toString());
		m[4] = a_4 == true ? "1" : "0";
		String[] knowledge_base_2 = mContext.getResources().getStringArray(R.array.search_query_knowledge);
		boolean a_5 = Arrays.asList(knowledge_base_2).contains(results.toString());
		m[5] = a_5 == true ? "1" : "0";
		String[] technology_desc_query = mContext.getResources().getStringArray(R.array.search_query_technology);
		boolean a_6 = Arrays.asList(technology_desc_query).contains(results.toString());
		m[6] = a_6 == true ? "1" : "0";*/
		
		for(int i = 0, n = 0; i < search_query_result.length ;i++) {
			if(search_query_result[i].matches(".*" + results.toString() + ".*")) {
			//	nm[n++] = i;
				matchesF.add(i);
			}
		}
		
		if (matchesF.isEmpty()) {
			resultsView.setText("No matches found \n For result : " + results);
		} else {
			resultsView.setText("Results for: " + results);
			resultLayout.setAdapter(new SearchableDialogAdapter(getContext(), matchesF, results.toString()));
		}
		
		super.onCreate(savedInstanceState);
	}

}
