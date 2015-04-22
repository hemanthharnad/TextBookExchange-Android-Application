package com.group1SEUTA.textbookexchange;
// Some of the code used below is from freely available tutorials at parse.com/tutorials/mealspotting
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
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

import com.parse.FunctionCallback;
import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class PostBookFragment extends Fragment {
	
	private Button submitButton;
	private Button addPhotoButton;
	private Button deleteButton;
	private EditText postTitle;
	private EditText postAuthor;
	private EditText postEdition;
	private EditText postPrice;
	private EditText postProfessor;
	private EditText postSubject;
	private EditText postISBN;
	private EditText postDescription;
	private int maxTitleLength = 255;
	private ParseImageView postPreview;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_post, parent, false);
		
		// get input from text fields
		postTitle = (EditText) v.findViewById(R.id.editTextTitle);
		postAuthor = (EditText) v.findViewById(R.id.editTextAuthor);
		postEdition = (EditText) v.findViewById(R.id.editTextEdition);
		postPrice = (EditText) v.findViewById(R.id.editTextPrice);
		postProfessor = (EditText) v.findViewById(R.id.editTextProfessor);
		postSubject = (EditText) v.findViewById(R.id.editTextSubject);
		postISBN = (EditText) v.findViewById(R.id.editTextISBN);
		postDescription = (EditText) v.findViewById(R.id.editTextDescription);
		
		
		addPhotoButton = (Button) v.findViewById(R.id.buttonPostAddPhoto);
		
		
		addPhotoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(postTitle.getWindowToken(), 0);
				startCamera();
			}
		});
		
		submitButton = (Button) v.findViewById(R.id.submitPost);
		submitButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  // if modify get from viewposts else get from postactivity
		        post();
		      }
		    });
		
		deleteButton = (Button) v.findViewById(R.id.deletePost);
		deleteButton.setVisibility(View.GONE);
		
		postPreview = (ParseImageView) v.findViewById(R.id.post_preview_image);
		postPreview.setVisibility(View.INVISIBLE);
		
		return v;
	}
	
	
	public void startCamera() {
		Fragment cameraFragment = new CameraFragment();
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragmentContainer, cameraFragment);
		transaction.addToBackStack("PostBookFragment");
		transaction.commit();
	}
	
	private void post() {
		//get parseobject PostTextInfo from parent activity
		PostTextInfo post = ((PostBookActivity) getActivity()).getCurrentPost();
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
		
		String price = postPrice.getText().toString().trim();
		if(price.isEmpty()) {
			pleaseEnterInfo("All Required Fields");
			return;
		}
		
		if(price.length() > maxTitleLength){
			tooManyCharacters("Price");
		    return;	
		}
		
		post.setPrice(price);
		
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
		
		String description = postDescription.getText().toString().trim();
		
		if(description.length() > maxTitleLength){
			tooManyCharacters("Description");
		    return;	
		}
		
		post.setDescription(description);
		
	    //associate post with the user
		post.setUser(ParseUser.getCurrentUser());
		String userSchool = ParseUser.getCurrentUser().getString("school");
		post.setSchool(userSchool);
	    
		//gives read access
		ParseACL acl = new ParseACL();
		acl.setPublicReadAccess(true);
		acl.setWriteAccess(ParseUser.getCurrentUser(), true);
		post.setACL(acl);
		
		final String titleLower = title.toLowerCase();
		final String authorLower = author.toLowerCase();
		final String professorLower = professor.toLowerCase();
		final String subjectLower = subject.toLowerCase();
		final String editionLower = edition;
		final String isbnLower = isbn;
		final String schoolLower = userSchool;
		//save the data to parse database. If e is null, the post was successful
		post.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if(e == null) {
					callCloud(titleLower, authorLower, editionLower, professorLower, subjectLower, isbnLower, schoolLower);
					successAlert();
					//callCloud(titleLower);
					
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
	
	private void callCloud(String titleLower, String authorLower, String editionLower, String professorLower, String subjectLower, String isbn, String school) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("title", titleLower);
		params.put("author", authorLower);
		params.put("edition", editionLower);
		params.put("professor", professorLower);
		params.put("course", subjectLower);
		params.put("isbn", isbn);
		params.put("school", school);
		ParseCloud.callFunctionInBackground("pushOnMatch", params, new FunctionCallback<Integer>() {
		   public void done(Integer num, ParseException e) {
		       if (e == null) {
		    	   Context context = getActivity().getApplicationContext();
					CharSequence text = Integer.toString(num) + " matches were found";
					int duration = Toast.LENGTH_LONG;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
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
		builder.setTitle("Post Successfully Added!");
		builder
			.setMessage("Press OK")
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   getActivity().setResult(Activity.RESULT_OK);
		        	   getActivity().finish();
		           }
		       });
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		
	}
	
	//Display the image if the user took one
	@Override
	public void onResume() {
		super.onResume();
		ParseFile photoFile = ((PostBookActivity) getActivity())
				.getCurrentPost().getPhotoFile();
		if (photoFile != null) {
			postPreview.setParseFile(photoFile);
			postPreview.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					postPreview.setVisibility(View.VISIBLE);
				}
			});
		}
	}
	

}
