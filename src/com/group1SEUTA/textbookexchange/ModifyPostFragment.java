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


public class ModifyPostFragment extends Fragment {
	
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
	private PostTextInfo post;
	private String postid;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_post, parent, false);
		
		// get input from text fields
		post = ((ViewPostsActivity) getActivity()).getSelectedPost();
		post.fetchInBackground(new GetCallback<PostTextInfo>() {
			  public void done(PostTextInfo object, ParseException e) {
			    if (e == null) {
			      // Success!
			    } else {
			      // Failure!
			    }
			  }
			});
		
		postTitle = (EditText) v.findViewById(R.id.editTextTitle);
		postTitle.setText(post.getTitle());
		postAuthor = (EditText) v.findViewById(R.id.editTextAuthor);
		postAuthor.setText(post.getAuthor());
		postEdition = (EditText) v.findViewById(R.id.editTextEdition);
		postEdition.setText(post.getEdition());
		postPrice = (EditText) v.findViewById(R.id.editTextPrice);
		postPrice.setText(post.getPrice());
		postProfessor = (EditText) v.findViewById(R.id.editTextProfessor);
		postProfessor.setText(post.getProfessor());
		postSubject = (EditText) v.findViewById(R.id.editTextSubject);
		postSubject.setText(post.getSubject());
		postISBN = (EditText) v.findViewById(R.id.editTextISBN);
		postISBN.setText(post.getISBN());
		postDescription = (EditText) v.findViewById(R.id.editTextDescription);
		postDescription.setText(post.getDescription());
		
		
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
		deleteButton.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  // if modify get from viewposts else get from postactivity
		        deletePost();
		      }
		    });
		
		postPreview = (ParseImageView) v.findViewById(R.id.post_preview_image);
		postPreview.setVisibility(View.INVISIBLE);
		
		return v;
	}
	
	
	public void startCamera() {
		Fragment cameraFragment = new CameraFragmentModify();
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragmentContainerViewPosts, cameraFragment);
		transaction.addToBackStack("ModifyPostFragment");
		transaction.commit();
	}
	
	private void post() {
		//get parseobject PostTextInfo from parent activity
		
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
		builder.setTitle("Post Successfully Modified!");
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
	
	//Display the image if the user took one
	@Override
	public void onResume() {
		super.onResume();
		ParseFile photoFile = ((ViewPostsActivity) getActivity())
				.getSelectedPost().getPhotoFile();
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
