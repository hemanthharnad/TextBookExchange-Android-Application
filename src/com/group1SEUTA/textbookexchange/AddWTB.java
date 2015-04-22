package com.group1SEUTA.textbookexchange;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddWTB extends Activity {
	
	private Button submitButton;
	private Button deleteButton;
	private EditText postTitle;
	private EditText postAuthor;
	private EditText postEdition;
	private EditText postProfessor;
	private EditText postSubject;
	private EditText postISBN;
	private int maxTitleLength = 255;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wtb);
		ParseObject.registerSubclass(WantToBuy.class);
		
		postTitle = (EditText) findViewById(R.id.editTextWTBTitle);
		postAuthor = (EditText) findViewById(R.id.editTextWTBAuthor);
		postEdition = (EditText) findViewById(R.id.editTextWTBEdition);
		postProfessor = (EditText) findViewById(R.id.editTextWTBProfessor);
		postSubject = (EditText) findViewById(R.id.editTextWTBSubject);
		postISBN = (EditText) findViewById(R.id.editTextWTBISBN);
		deleteButton = (Button) findViewById(R.id.deleteWTB);
		deleteButton.setVisibility(View.GONE);
		
		submitButton = (Button) findViewById(R.id.submitWTB);
		submitButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  // if modify get from viewposts else get from postactivity
		        post();
		      }
		    });	
	}
	
	private void post() {
		//get parseobject PostTextInfo from parent activity
		WantToBuy post = new WantToBuy();
		// get string from text box
		String title = postTitle.getText().toString().trim();
		
		//if no title, return from method, don't post
		if(title.length() == 0) {
			pleaseEnterInfo("All Required Fields");
			return;
		}
		//if title too long, return don't post, display toast
		if(title.length() > maxTitleLength){
			tooManyCharacters("Title");
		    return;	
		}
		//calls setTitle method to save title
		post.setTitle(title);
		post.setTitleLower(title);
		
		String author = postAuthor.getText().toString().trim();
		if(author.isEmpty()) {
			pleaseEnterInfo("All Required Fields");
			return;
		}
		
		if(author.length() > maxTitleLength){
			tooManyCharacters("Author");
		    return;	
		}
		
		post.setAuthor(author);
		post.setAuthorLower(author);
		
		String edition = postEdition.getText().toString().trim();
		if(edition.isEmpty()) {
			pleaseEnterInfo("All Required Fields");
			return;
		}
		
		if(edition.length() > maxTitleLength){
			tooManyCharacters("Edition");
		    return;	
		}
		
		post.setEdition(edition);
		
		String professor = postProfessor.getText().toString().trim();
		
		if(professor.length() > maxTitleLength){
			tooManyCharacters("Professor");
		    return;	
		}
		post.setProfessor(professor);
		post.setProfessorLower(professor);
		
		String subject = postSubject.getText().toString().trim();
		
		if(subject.length() > maxTitleLength){
			tooManyCharacters("Course");
		    return;	
		}
		
		post.setSubject(subject);
		post.setSubjectLower(subject);
		
		String isbn = postISBN.getText().toString().trim();
		
		if(isbn.length() > maxTitleLength){
			tooManyCharacters("ISBN");
		    return;	
		}
		
		post.setISBN(isbn);
		

		
	    //associate post with the user
		post.setUser(ParseUser.getCurrentUser());
		String userSchool = ParseUser.getCurrentUser().getString("school");
		post.setSchool(userSchool);
	    
		//gives read access
		ParseACL acl = new ParseACL();
		acl.setPublicReadAccess(true);
		acl.setWriteAccess(ParseUser.getCurrentUser(), true);
		post.setACL(acl);
		
		//save the data to parse database. If e is null, the post was successful
		post.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if(e == null) {
					successAlert();
					
				}
				else {
					if(e.getCode() == ParseException.CONNECTION_FAILED ) {
			    		Context context = getApplicationContext();
						CharSequence text = "Cannot connect to server. Please try again later.";
						int duration = Toast.LENGTH_SHORT;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
			    	}
					else {
						Context context = getApplicationContext();
						Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}
		});
			
	}
	
	@SuppressLint("NewApi")
	private void pleaseEnterInfo(String textIn) {
		Context context = getApplicationContext();
		CharSequence text = "Please Enter " + textIn;
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	//method displays toast if too many characters
	private void tooManyCharacters(String textIn){
		Context context = getApplicationContext();
		CharSequence text = textIn + " is too long! (max 255)";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	//Display an alert if the post is successful. Goes back to main screen after this.
	private void successAlert() {
	    Context context = this;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Book Successfully Added to WTB List!");
		builder
			.setMessage("Press OK")
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   setResult(Activity.RESULT_OK);
		        	   finish();
		           }
		       });
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		
	}


}
