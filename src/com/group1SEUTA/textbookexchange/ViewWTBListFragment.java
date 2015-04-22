package com.group1SEUTA.textbookexchange;

//import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import android.util.Log;

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

public class ViewWTBListFragment extends Fragment {
	
	private TextView searchResults;
	private ListView listView;
	private WTBAdapter adapter;
	private final String TAG = "ViewWTBFragment";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "Done with on create");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_search_results, parent, false);
		
		
		adapter = ((ViewWTBActivity) getActivity()).getAdapter();
		searchResults = (TextView) v.findViewById(R.id.textView1);
		searchResults.setText("My WTB List");
		
		
		listView = (ListView) v.findViewById(R.id.search_results_list_view);
		listView.setAdapter(adapter);
		Log.i(TAG, "after set adapter");
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG, "clicked");
				WantToBuy post = adapter.getItem(position);
				System.out.println("right before post");
				goToPost(post);
				
			}
		});
		
		
		
		
		return v;
	}
	
	private void goToPost(WantToBuy post) {
		// set selectedPost from ViewPostActivity so it can be shared
		((ViewWTBActivity) getActivity()).setSelectedPost(post);
		
		// switch to selected post fragment
		 Fragment fragment = new ModifyWTBFragment();
		 FragmentTransaction transaction = getFragmentManager().beginTransaction();
		 transaction.replace(R.id.fragmentContainerViewWTB, fragment);
		 transaction.addToBackStack(null);
		 transaction.commit();
		
		
	}
	
	

}
