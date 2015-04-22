package com.group1SEUTA.textbookexchange;

//import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResultsFragment extends Fragment {
	
	private TextView searchResults;
	private ListView listView;
	private ParseQueryAdapter<PostTextInfo> adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_search_results, parent, false);
		
		
		adapter = ((SearchActivity) getActivity()).getParseAdapter();
		searchResults = (TextView) v.findViewById(R.id.textView1);
		searchResults.setText("Search Results");
		
		adapter.setTextKey("title");
		
		listView = (ListView) v.findViewById(R.id.search_results_list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PostTextInfo post = adapter.getItem(position);
				goToPost(post);
				
			}
		});
		
		
		
		
		return v;
	}
	
	private void goToPost(PostTextInfo post) {
		// set selectedPost from SearchActivity so it can be shared
		((SearchActivity) getActivity()).setSelectedPost(post);
		
		// switch to selected post fragment
		Fragment fragment = new SelectedResultFragment();
		 FragmentTransaction transaction = getFragmentManager().beginTransaction();
		 transaction.replace(R.id.fragmentContainerSearch, fragment);
		 transaction.addToBackStack(null);
		 transaction.commit();
		
		
	}
	
	

}
