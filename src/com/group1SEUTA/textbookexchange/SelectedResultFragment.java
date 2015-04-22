package com.group1SEUTA.textbookexchange;

import com.parse.GetDataCallback;
//import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.AlertDialog;
//import android.annotation.SuppressLint;
//import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
import android.widget.TextView;

public class SelectedResultFragment extends Fragment {
	
	private TextView textTitle;
	private TextView textAuthor;
	private TextView textEdition;
	private TextView textISBN;
	private TextView textCourse;
	private TextView textProfessor;
	private TextView textDescription;
	private TextView textPrice;
	private TextView textEmail;
	private TextView textNumber;
	private PostTextInfo post;
	private ParseUser seller;
	private String phoneNumber;
	private String email;
	private ParseImageView resultPreview;
	private Button buttonCall;
	private Button buttonText;
	private Button buttonEmail;
	private Button buttonAddInterested;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle SavedInstanceState) {
		// This sets the view to the layout defined in the fragment_search file
		View v = inflater.inflate(R.layout.fragment_selected_result, parent, false);
		
		// get the post selected in previous activity
		post = ((SearchActivity) getActivity()).getSelectedPost();
		
		resultPreview = (ParseImageView) v.findViewById(R.id.result_preview_image);
		resultPreview.setVisibility(View.INVISIBLE);
		
		// Add the post info to the text views
		// The first three are required, so will be there
		textTitle = (TextView) v.findViewById(R.id.TextViewTitle);
		textTitle.setText("Title:   " + post.getTitle());
		
		textAuthor = (TextView) v.findViewById(R.id.TextViewAuthor);
		textAuthor.setText("Author:  " + post.getAuthor());
		
		textEdition = (TextView) v.findViewById(R.id.TextViewEdition);
		textEdition.setText("Edition: " + post.getEdition());
		
		
		
		// These are not required, so will display not provided if they weren't provided by seller
		textISBN = (TextView) v.findViewById(R.id.TextViewISBN);
		String isbn = post.getISBN();
		if(isbn.length() > 0)
		{
			textISBN.setText("ISBN:    " + post.getISBN());
		}
		else {
			textISBN.setText("ISBN:    Not Provided");
		}
		
		textCourse = (TextView) v.findViewById(R.id.TextViewSubject);
		String Course = post.getSubject();
		if(Course.length() > 0)
		{
			textCourse.setText("Course: " + post.getSubject());
		}
		else {
			textCourse.setText("Course: Not Provided");
		}
		
		textProfessor = (TextView) v.findViewById(R.id.TextViewProfessor);
		String Professor = post.getProfessor();
		if(Professor.length() > 0)
		{
			textProfessor.setText("Professor: " + post.getProfessor());
		}
		else {
			textProfessor.setText("Professor: Not Provided");
		}
		
		textPrice = (TextView) v.findViewById(R.id.TextViewPrice);
		String Price = post.getPrice();
		if(Price.length() > 0)
		{
			textPrice.setText("Price: $" + post.getPrice());
		}
		else {
			textPrice.setText("Price: Not Provided");
		}
		
		textDescription = (TextView) v.findViewById(R.id.TextViewDescription);
		String Description = post.getDescription();
		if(Description.length() > 0)
		{
			textDescription.setText("Description: " + post.getDescription());
		}
		else {
			textDescription.setText("Description: Not Provided");
		}
		
		
		// Get the user who posted the book
		seller = post.getUser();
		// you have to call this fetch method to actually get the info
		try {
			seller.fetchIfNeeded();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get the number and email
		phoneNumber = seller.getString("phone");
		email = seller.getEmail();
		
		// write email to screen
		textEmail = (TextView) v.findViewById(R.id.TextViewEmail);
		textEmail.setText("Seller's Email: " + email);
		
		// write number to screen if it exists
		textNumber = (TextView) v.findViewById(R.id.TextViewNumber);
		if(phoneNumber.length() > 0) {
			
			textNumber.setText("Seller's Number: " + phoneNumber );
		}
		else {
			textNumber.setText("Seller's Number: Not Provided");
		}
		
		ParseFile photo = post.getPhotoFile();
		if(photo != null) {
			resultPreview.setParseFile(photo);
			resultPreview.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					resultPreview.setVisibility(View.VISIBLE);
				}
			});
		}
		
		buttonCall = (Button) v.findViewById(R.id.buttonCall);
		buttonText = (Button) v.findViewById(R.id.buttonSendText);
		buttonEmail = (Button) v.findViewById(R.id.buttonSendEmail);
		
		buttonEmail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
			            "mailto",email, null));
			
			startActivity(Intent.createChooser(emailIntent, "Send email..."));
			}
		});
		
		if(phoneNumber.length() > 0) {
			
			
			 buttonCall.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						 Context context = getActivity();
							AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setTitle("Are you sure you want to call the seller?");
							builder
								
								.setCancelable(false)
								.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							           public void onClick(DialogInterface dialog, int id) {

											Intent callIntent = new Intent(Intent.ACTION_CALL);
											callIntent.setData(Uri.parse("tel:" + phoneNumber));
											startActivity(callIntent);
							        	   
							           }
							       })
							    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							           public void onClick(DialogInterface dialog, int id) {
							        	   return;
							           }
							       });
							    
							AlertDialog alertDialog = builder.create();
							alertDialog.show();
					}
				});
			 
			 buttonText.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						//Uri smsUri = Uri.parse("tel:" + phoneNumber);
						
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setType("vnd.android-dir/mms-sms");
						intent.putExtra("address", phoneNumber);
						startActivity(intent);
					}
				});
		}
		else {
			buttonCall.setEnabled(false);
			buttonText.setEnabled(false);
		}
		
		
		buttonAddInterested = (Button) v.findViewById(R.id.buttonAddInterested);
		buttonAddInterested.setVisibility(View.GONE);
		
		
		
		
		
		return v;
	}

}
