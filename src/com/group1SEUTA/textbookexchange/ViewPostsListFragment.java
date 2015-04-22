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

public class ViewPostsListFragment extends Fragment {
	
	private TextView searchResults;
	private ListView listView;
	private ViewPostsAdapter adapter;
	private final String TAG = "ViewPostsFragment";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "Done with on create");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_search_results, parent, false);
		
		
		adapter = ((ViewPostsActivity) getActivity()).getAdapter();
		searchResults = (TextView) v.findViewById(R.id.textView1);
		searchResults.setText("My Posts");
		
		
		listView = (ListView) v.findViewById(R.id.search_results_list_view);
		listView.setAdapter(adapter);
		Log.i(TAG, "after set adapter");
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG, "clicked");
				PostTextInfo post = adapter.getItem(position);
				System.out.println("right before post");
				goToPost(post);
				
			}
		});
		
		
		
		
		return v;
	}
	
	private void goToPost(PostTextInfo post) {
		// set selectedPost from ViewPostActivity so it can be shared
		((ViewPostsActivity) getActivity()).setSelectedPost(post);
		
		// switch to selected post fragment
		 Fragment fragment = new ModifyPostFragment();
		 FragmentTransaction transaction = getFragmentManager().beginTransaction();
		 transaction.replace(R.id.fragmentContainerViewPosts, fragment);
		 transaction.addToBackStack(null);
		 transaction.commit();
		
		
	}
	
	

}
