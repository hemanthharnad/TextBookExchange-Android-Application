package com.group1SEUTA.textbookexchange;

import com.group1SEUTA.textbookexchange.R;
import com.parse.Parse;

//import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
//import com.parse.SaveCallback;
import com.parse.SignUpCallback;
//import com.parse.ParseClassName;


//import android.support.v7.app.ActionBarActivity;
//import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
//import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class CreateAccountActivity extends Activity  {
	
	private EditText username;
	private EditText email;
	private EditText password;
	private EditText password2;
	private EditText phoneNumber;
	private Spinner school;
	private Button submitButton;
	final Context context = this;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createaccount);
		
		//register parse object subclass
		ParseObject.registerSubclass(PostTextInfo.class);
		Parse.initialize(this, "aMoKFCkcN3PmUYryTT3fBwDZYeFW72OQSyYOgAs0", "EYKNmPaZLTOVmkDPoOlNxkza7qbA1Xr542vLJYz5");
		
		username = (EditText) findViewById(R.id.editTextCreateUsername);
		password = (EditText) findViewById(R.id.editTextCreatePassword);
		password2 = (EditText) findViewById(R.id.editTextCreatePassword2);
		email = (EditText) findViewById(R.id.editTextEmail);
		phoneNumber = (EditText) findViewById(R.id.editTextPhone);
		submitButton = (Button) findViewById(R.id.buttonCreateAccount2);
		
		
		//drop down menu stuff
		school = (Spinner) findViewById(R.id.spinnerSchool);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.schools_array, android.R.layout.simple_spinner_item);
		school.setAdapter(adapter);
		school.setOnItemSelectedListener(new CustomOnItemSelectedListener() );
		
		
		submitButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		        submitUserAccount();
		      }
		    });		
	}
	
	private void submitUserAccount() {
		ParseUser user = new ParseUser();
		
		String userName = username.getText().toString().trim();
		// check to make sure username is entered
		if(userName.length() == 0){
			pleaseEnterInfo("All Required Fields");
			return;
		}
		user.setUsername(userName);
		
		String passWord = password.getText().toString().trim();
		// check to make sure password is entered
		if(passWord.length() == 0){
			pleaseEnterInfo("All Required Fields");
			return;
		}
		
		
		String passWord2 = password2.getText().toString().trim();
		// check to make sure password2 is entered
		if(passWord2.length() == 0){
			pleaseEnterInfo("All Required Fields");
			return;
		}
		
		if(passWord.equals(passWord2)){
			user.setPassword(passWord);
		}
		else {
			passwordsDifferToast();
			return;
		}
		
		
		String eMail = email.getText().toString().trim();
		if(eMail.length() == 0){
			pleaseEnterInfo("All Required Fields");
			return;
		}
		if(!android.util.Patterns.EMAIL_ADDRESS.matcher(eMail).matches())
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid email address.", Toast.LENGTH_SHORT);
			toast.show();
		}
		
		user.setEmail(eMail);
		
		String userSchool = String.valueOf(school.getSelectedItem());
		user.put("school", userSchool);
		
		String phone = phoneNumber.getText().toString().trim();
		user.put("phone", phone);
		
		user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
			    if (e == null) {
			      // Account created successfully
			    	successAlert();
			    } else {
			    	// Account could not be created
			    	// This will give an exception code
			    	// Check to see if username is already taken
			    	if(e.getCode() == ParseException.USERNAME_TAKEN){
			    		Context context = getApplicationContext();
						CharSequence text = "Username already in use. Please select another one.";
						int duration = Toast.LENGTH_SHORT;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
			    	}
			    	// Check to see if email is already taken
			    	if(e.getCode() == ParseException.EMAIL_TAKEN) {
			    		Context context = getApplicationContext();
						CharSequence text = "Email already in use. Please select another one.";
						int duration = Toast.LENGTH_SHORT;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
			    	}
			    	// Check to see if can't connect to Parse server
			    	if(e.getCode() == ParseException.CONNECTION_FAILED ) {
			    		Context context = getApplicationContext();
						CharSequence text = "Cannot connect to server. Please try again later.";
						int duration = Toast.LENGTH_SHORT;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
			    	}
			    	
			      
			    }
			  }
			});	
		
	}
	
	private void successAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Account Successfully Created!");
		builder
			//.setMessage("Press OK")
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               CreateAccountActivity.this.finish();
		           }
		       });
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		
	}
	
	private void pleaseEnterInfo(String textIn) {
		Context context = getApplicationContext();
		CharSequence text = "Please Enter " + textIn;
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	private void passwordsDifferToast() {
		Context context = getApplicationContext();
		CharSequence text = "Passwords are not the same. Please enter the same password";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
