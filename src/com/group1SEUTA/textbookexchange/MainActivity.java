package com.group1SEUTA.textbookexchange;


import com.group1SEUTA.textbookexchange.R;
import com.parse.Parse;
import com.parse.ParseInstallation;

//import com.parse.ParseACL;
//import com.parse.ParseException;
//import com.parse.ParseObject;
import com.parse.ParseUser;
//import com.parse.SaveCallback;
//import com.parse.SignUpCallback;
//import com.parse.ParseClassName;



//import android.annotation.SuppressLint;
import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.Toast;
//import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;


public class MainActivity extends Activity {
	
	private Button goToPostButton;
	private Button logOutButton;
	private Button goToSearch;
	private Button goToViewPosts;
	private Button goToAddWTB;
	private Button goToViewWTB;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Parse.initialize(this, "aMoKFCkcN3PmUYryTT3fBwDZYeFW72OQSyYOgAs0", "EYKNmPaZLTOVmkDPoOlNxkza7qbA1Xr542vLJYz5");
		
		// Associate the device with a user
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put("user",ParseUser.getCurrentUser());
		installation.saveInBackground();
		
		goToPostButton = (Button) findViewById(R.id.buttonGoToPost);
		logOutButton = (Button) findViewById(R.id.buttonLogOut);
		goToSearch = (Button) findViewById(R.id.buttonGoToSearch);
		goToViewPosts = (Button) findViewById(R.id.buttonGoToViewPosts);
		goToAddWTB = (Button) findViewById(R.id.buttonWTB);
		goToViewWTB = (Button) findViewById(R.id.buttonViewWTB);
		
		
		
		goToPostButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  goToPost();
		      }
		    });	
		
		goToSearch.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  goToSearch();
		      }
		    });	
		
		goToViewPosts.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  goToViewPosts();
		      }
		    });	
		goToAddWTB.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  goToAddWTB();
		      }
		    });	
		goToViewWTB.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  goToViewWTB();
		      }
		    });	
		
		logOutButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	 logOut();
		      }
		    });	
		
	}
	
	private void logOut() {
		ParseUser.logOut();
  	  	ParseUser currentUser = ParseUser.getCurrentUser();
  	  	if(currentUser == null){
  	  		Intent logInActivity = new Intent(this, LoginActivity.class);
  	  		startActivity(logInActivity);
  	  	}		
	}
	
	private void goToPost() {
		Intent postActivity = new Intent(this, PostBookActivity.class);
		startActivity(postActivity);
		
	}
	
	private void goToSearch() {
		Intent searchActivity = new Intent(this, SearchActivity.class);
		startActivity(searchActivity);
		
	}
	
	private void goToViewPosts() {
		Intent viewPostsActivity = new Intent(this, ViewPostsActivity.class);
		startActivity(viewPostsActivity);
		
	}
	
	private void goToAddWTB() {
		Intent WTB = new Intent(this, AddWTB.class);
		startActivity(WTB);
	}
	
	private void goToViewWTB() {
		Intent WTB = new Intent(this, ViewWTBActivity.class);
		startActivity(WTB);
	}

}
