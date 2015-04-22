package com.group1SEUTA.textbookexchange;
// Some of the code used below is from freely available tutorials at parse.com/tutorials/mealspotting
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.GetCallback;


public class ModifyWTBFragment extends Fragment {
	
	private Button submitButton;
	private Button deleteButton;
	private EditText postTitle;
	private EditText postAuthor;
	private EditText postEdition;
	private EditText postProfessor;
	private EditText postSubject;
	private EditText postISBN;
	private int maxTitleLength = 255;
	private WantToBuy post;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.activity_wtb, parent, false);
		
		// get input from text fields
		post = ((ViewWTBActivity) getActivity()).getSelectedPost();
		post.fetchInBackground(new GetCallback<WantToBuy>() {
			  public void done(WantToBuy object, ParseException e) {
			    if (e == null) {
			      // Success!
			    } else {
			      // Failure!
			    }
			  }
			});
		
		postTitle = (EditText) v.findViewById(R.id.editTextWTBTitle);
		postTitle.setText(post.getTitle());
		postAuthor = (EditText) v.findViewById(R.id.editTextWTBAuthor);
		postAuthor.setText(post.getAuthor());
		postEdition = (EditText) v.findViewById(R.id.editTextWTBEdition);
		postEdition.setText(post.getEdition());
		postProfessor = (EditText) v.findViewById(R.id.editTextWTBProfessor);
		postProfessor.setText(post.getProfessor());
		postSubject = (EditText) v.findViewById(R.id.editTextWTBSubject);
		postSubject.setText(post.getSubject());
		postISBN = (EditText) v.findViewById(R.id.editTextWTBISBN);
		postISBN.setText(post.getISBN());
		
		
		
		
		
		submitButton = (Button) v.findViewById(R.id.submitWTB);
		submitButton.setText("Submit Modified Post");
		submitButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  // if modify get from viewposts else get from postactivity
		        post();
		      }
		    });
		
		deleteButton = (Button) v.findViewById(R.id.deleteWTB);
		deleteButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  // if modify get from viewposts else get from postactivity
		        deletePost();
		      }
		    });
		
		
		return v;
	}
	
	
	
	
	private void post() {
		//get parseobject WantToBuy from parent activity
		
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
			    		Context context = getActivity().getApplicationContext();
						CharSequence text = "Cannot connect to server. Please try again later.";
						int duration = Toast.LENGTH_SHORT;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
			    	}
					else {
						Context context = getActivity().getApplicationContext();
						Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}
		});
			
	}
	
	@SuppressLint("NewApi")
	private void pleaseEnterInfo(String textIn) {
		Context context = getActivity().getApplicationContext();
		CharSequence text = "Please Enter " + textIn;
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	//method displays toast if too many characters
	private void tooManyCharacters(String textIn){
		Context context = getActivity().getApplicationContext();
		CharSequence text = textIn + " is too long! (max 255)";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	//Display an alert if the post is successful. Goes back to main screen after this.
	private void successAlert() {
	    Context context = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("WTB Book Successfully Modified!");
		builder
			.setMessage("Press OK")
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   FragmentManager fm = getActivity().getFragmentManager();
		       		fm.popBackStack();
		           }
		       });
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		
	}
	
	
	
	private void deletePost() {
		
		 Context context = getActivity();
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Are you sure you want to delete this post?");
			builder
				
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   post.deleteInBackground();
			        	   FragmentManager fm = getActivity().getFragmentManager();
			       		fm.popBackStack();
			           }
			       })
			    .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   return;
			           }
			       });
			    
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		
	}
	

}

