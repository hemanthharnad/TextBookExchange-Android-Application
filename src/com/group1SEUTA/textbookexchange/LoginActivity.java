package com.group1SEUTA.textbookexchange;

import com.group1SEUTA.textbookexchange.R;
import com.parse.Parse;

import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
//import com.parse.ParseObject;
import com.parse.ParseUser;
//import com.parse.SaveCallback;
//import android.annotation.SuppressLint;
import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
//import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private EditText username;
	private EditText password;
	private Button submitButton;
	private Button createAccountButton;
	private Button forgotPasswordButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvity_login);
		
		Parse.initialize(this, "aMoKFCkcN3PmUYryTT3fBwDZYeFW72OQSyYOgAs0", "EYKNmPaZLTOVmkDPoOlNxkza7qbA1Xr542vLJYz5");
		
		username = (EditText) findViewById(R.id.editTextUsername);
		password = (EditText) findViewById(R.id.editTextPassword);
		submitButton = (Button) findViewById(R.id.buttonLogin);
		createAccountButton = (Button) findViewById(R.id.buttonCreateAccount);
		forgotPasswordButton = (Button) findViewById(R.id.buttonForgotPassword);
		
		submitButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		        submitLogin();
		      }
		    });		
		
		createAccountButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		        goToCreateAccount();
		      }
		    });	
		
		forgotPasswordButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  goToForgotPassword();
		      }
		    });	
	}
	
	private void submitLogin(){
		
		String userName = username.getText().toString().trim();
		
		String passWord = password.getText().toString().trim();
		
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		
		ParseUser.logInInBackground(userName, passWord, new LogInCallback() {

			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      // The user is logged in, go to main
			    	goToMain();
			    } 
			    else {
			    	// invalid username or password
			    	if(e.getCode() == ParseException.OBJECT_NOT_FOUND){
			    		Toast.makeText(LoginActivity.this, "Invalid username or password.", Toast.LENGTH_LONG).show();
			    	}
			    	// cannot connect to server
			    	if(e.getCode() == ParseException.CONNECTION_FAILED){
			    		Toast.makeText(LoginActivity.this, "Cannot connect to server. Please try again later.", Toast.LENGTH_LONG).show();
			    	}
			    	
			    }
			  }
			});
		}
	private void goToMain() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	private void goToCreateAccount() {
		Intent intent = new Intent(this, CreateAccountActivity.class);
		startActivity(intent);
	}
	
	private void goToForgotPassword() {
		Intent intent = new Intent(this, ForgotPasswordActivity.class);
		startActivity(intent);
	}

}
