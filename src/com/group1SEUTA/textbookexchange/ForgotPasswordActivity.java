package com.group1SEUTA.textbookexchange;

import com.group1SEUTA.textbookexchange.R;
import com.parse.Parse;

//import com.parse.LogInCallback;
//import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
//import com.parse.SaveCallback;
import com.parse.RequestPasswordResetCallback;




//import com.parse.ParseClassName;



//import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
//import android.content.Intent;
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

public class ForgotPasswordActivity extends Activity {
	
	private EditText email;
	private Button  submitButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpassword);
		
		//register parse object subclass
		ParseObject.registerSubclass(PostTextInfo.class);
		Parse.initialize(this, "aMoKFCkcN3PmUYryTT3fBwDZYeFW72OQSyYOgAs0", "EYKNmPaZLTOVmkDPoOlNxkza7qbA1Xr542vLJYz5");
		
		email = (EditText) findViewById(R.id.editTextEmailForgotPassword);
		submitButton = (Button) findViewById(R.id.buttonSubmitForgotPasssword);
		
		submitButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		        submitForgotPassword();
		      }
		    });		
	}
	
	private void submitForgotPassword() {
		String eMail = email.getText().toString().trim();
		ParseUser.requestPasswordResetInBackground(eMail, new RequestPasswordResetCallback(){
			public void done(ParseException e) {
				if (e == null) {
					//email was sent
					successAlert();
				}
				else {
					//check error
					Context context = getApplicationContext();
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
			
		});
		
	}
	
	private void successAlert() {
		//Context context = getApplicationContext();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Email Sent!");
		builder
			.setMessage("Please follow the instructions in the email to change your password.")
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               ForgotPasswordActivity.this.finish();
		           }
		       });
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		
	}

}
