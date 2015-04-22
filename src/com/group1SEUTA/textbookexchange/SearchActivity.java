package com.group1SEUTA.textbookexchange;
import com.parse.Parse;
import com.parse.ParseObject;
//import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

//import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
//import android.view.View;
import android.view.Window;
import android.view.WindowManager;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;

/*The search activity has been implemented as fragments. The fragments implement the UI. This is the main search activity which starts the first fragment.
 * This activity class contains two object variables, an adapter and a PostTextInfo object.
 * By using the set and get methods for these two objects, they can accessed from the fragments.
 * This allows the variables to be easily shared between the fragments.
 * We can then share the adapter from the search fragment with the display results fragment,
 * and share the PostTextInfo object selected from the display results fragment with the display selected post fragment */

public class SearchActivity extends Activity {
	
	// This adapter is shared between fragments. It gets info from database and sends to listview for display.
	private ParseQueryAdapter<PostTextInfo> adapter;
	// This is post selected from the list view
	private PostTextInfo selectedPost;
	//private Button searchTitle;
	//private EditText editTextTitle;
	
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  
		  ParseObject.registerSubclass(PostTextInfo.class);
		  Parse.initialize(this, "aMoKFCkcN3PmUYryTT3fBwDZYeFW72OQSyYOgAs0", "EYKNmPaZLTOVmkDPoOlNxkza7qbA1Xr542vLJYz5");
		  
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
		  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 // super.onCreate(savedInstanceState);		  
		  setContentView(R.layout.activity_search_book);
		  
		// This code starts the search fragment, which allows user to input search info and submit search
		  FragmentManager manager = getFragmentManager();
		  // fragment container search is the id of the actvity_search_layout file. This file is in res/layout.
		  Fragment fragment = manager.findFragmentById(R.id.fragmentContainerSearch);
			
			if(fragment == null) {
				// switch to SearchFragment
				fragment = new SearchFragment();
				manager.beginTransaction().add(R.id.fragmentContainerSearch,  fragment).commit();
			}
		 
		  
	}
	
	// These are the set and get methods for the two attributes defined at top
	public ParseQueryAdapter<PostTextInfo> getParseAdapter() {
		return adapter;
	}
	
	public void setParseAdapter(ParseQueryAdapter<PostTextInfo> inputAdapter) {
		adapter = inputAdapter;
	}
	
	public PostTextInfo getSelectedPost() {
		return selectedPost;
	}
	
	public void setSelectedPost(PostTextInfo post) {
		selectedPost = post;
	}

}
