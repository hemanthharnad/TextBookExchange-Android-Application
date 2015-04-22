package com.group1SEUTA.textbookexchange;

import java.util.Locale;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.LayoutInflater;
import android.widget.TextView;

public class ViewPostsActivity extends Activity {
	
	private ParseUser currentUser;
	private ViewPostsAdapter adapter;
	private PostTextInfo selectedPost;
	
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  
		  ParseObject.registerSubclass(PostTextInfo.class);
		  Parse.initialize(this, "aMoKFCkcN3PmUYryTT3fBwDZYeFW72OQSyYOgAs0", "EYKNmPaZLTOVmkDPoOlNxkza7qbA1Xr542vLJYz5");
		  
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
		  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 // super.onCreate(savedInstanceState);		  
		  setContentView(R.layout.activity_view_posts);
		  
		  currentUser = ParseUser.getCurrentUser();
		  // if no user is logged in go to login screen
		  if(currentUser == null) {
			  Intent logInActivity = new Intent(this, LoginActivity.class);
	  	  		startActivity(logInActivity);
		  }
		  
		  adapter = new ViewPostsAdapter(this, new ParseQueryAdapter.QueryFactory<PostTextInfo>() {
			    public ParseQuery<PostTextInfo> create() {
				      // This query will and title, author, and edition if they are entered
				      ParseQuery<PostTextInfo> query = new ParseQuery<PostTextInfo>("Post");
				      query.whereEqualTo("user", currentUser);
				      return query;
			    }
		  });
		  
		// This code starts the search fragment, which allows user to input search info and submit search
		  FragmentManager manager = getFragmentManager();
		  // fragment container search is the id of the actvity_search_layout file. This file is in res/layout.
		  Fragment fragment = manager.findFragmentById(R.id.fragmentContainerViewPosts);
		  if(fragment == null) {
				// switch to SearchFragment
				fragment = new ViewPostsListFragment();
				manager.beginTransaction().add(R.id.fragmentContainerViewPosts,  fragment).commit();
			}
		  
	}
	
	public void setAdapter(ViewPostsAdapter adapterIn) {
		adapter = adapterIn;
	}
	
	public ViewPostsAdapter getAdapter() {
		return adapter;
	}
		
	public void setSelectedPost(PostTextInfo post) {
		selectedPost = post;
	}
	
	public PostTextInfo getSelectedPost() {
		return selectedPost;
	}

}
