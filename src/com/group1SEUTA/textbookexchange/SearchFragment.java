package com.group1SEUTA.textbookexchange;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import com.parse.FindCallback;
//import com.parse.Parse;
//import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
//import com.parse.ParseException;
import com.parse.ParseUser;

//import android.annotation.SuppressLint;
//import android.app.Activity;
import android.app.Fragment;
//import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.ListView;
//import android.widget.TextView;

/* This is the search fragment. Here a user will enter search info and click the button to submit.
 * The user will be able to search by title, author, edition, isbn, or by course and professor.
 * */

public class SearchFragment extends Fragment {
	
	// This is a variable for the button to submit search by title
	private Button searchTitle;
	// This is a variable for the title field
	private EditText editTextTitle;
	private EditText editTextAuthor;
	private EditText editTextEdition;
	private EditText editTextISBN;
	// String to hold title from text field
	private String title;
	private String author;
	private String edition;
	private String isbn;
	private Button SearchCourse;
	private String courseName;
	private String profName;
	private EditText editTextcourse;
    private EditText editTextprof;
    boolean titleEntered, authorEntered, editionEntered, isbnEntered, courseEntered, profEntered;
    private String userSchool;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle SavedInstanceState) {
		// This sets the view to the layout defined in the fragment_search file
		View v = inflater.inflate(R.layout.fragment_search, parent, false);
		
		// These lines create objects (Button, EditText) linked to those defined in the layout files
		// If you look at the fragment_search xml file, you will see buttonSearchTitle is the button's id, etc.
		 searchTitle = (Button) v.findViewById(R.id.buttonSearchTitle);
		 editTextTitle = (EditText) v.findViewById(R.id.editTextSearchTitle);
		 editTextAuthor = (EditText) v.findViewById(R.id.editTextSearchAuthor);
		 editTextEdition = (EditText) v.findViewById(R.id.editTextSearchEdition);
		 editTextISBN = (EditText) v.findViewById(R.id.editTextSearchISBN);
		 SearchCourse= (Button) v.findViewById(R.id.buttonSearchCourse);
         editTextcourse= (EditText) v.findViewById(R.id. editTextSearchCourse);
         editTextprof= (EditText) v.findViewById(R.id. editTextSearchProfessor);
		  
		 // This is how you make a button do something when you click it. Here it performs the searchTitle() method.
		  searchTitle.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  searchTitle();
		      }
		    });
		  
		  SearchCourse.setOnClickListener (new OnClickListener() {
              public void onClick(View v) {
                      searchByCourseProf();
              }
      });
		userSchool = ParseUser.getCurrentUser().getString("school");  
		return v;
	}
	
	// This method searches by title, etc. Currently it creates an adapter that searches by title.
	// It then sets the adapter from SearchActivity, so it can be used by the results fragment.
	// Then it starts the search results fragment
	private void searchTitle() {
		// assume user entered nothing
		titleEntered = false;
		authorEntered = false;
		editionEntered = false;
		isbnEntered = false;
		//get what user entered
		title = editTextTitle.getText().toString().trim();
		author = editTextAuthor.getText().toString().trim();
		edition = editTextEdition.getText().toString().trim();
		isbn = editTextISBN.getText().toString().trim();
		// determine which fields user entered
		if(title.length() > 0) titleEntered = true;
		if(author.length() > 0) authorEntered = true;
		if(edition.length() > 0) editionEntered = true;
		if(isbn.length() > 0) isbnEntered = true;
		// ask user to enter info if they entered nothing
		if(!titleEntered && ! authorEntered && !editionEntered && !isbnEntered){
			Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please enter search criteria into at least one field.", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		
		 // Adapter created here if user entered something
		
		 SearchResultsAdapter adapter = new SearchResultsAdapter(getActivity(), new ParseQueryAdapter.QueryFactory<PostTextInfo>() {
		    public ParseQuery<PostTextInfo> create() {
		      // This query will and title, author, and edition if they are entered
		      ParseQuery<PostTextInfo> query = new ParseQuery<PostTextInfo>("Post");
		      if(titleEntered) query.whereContains("titleLower", title.toLowerCase(Locale.US));
		      if(authorEntered) query.whereContains("AuthorLower", author.toLowerCase(Locale.US));
		      if(editionEntered) query.whereEqualTo("Edition", edition);
		      //query.whereEqualTo("school", userSchool);
		      
		      // if no isbn entered, return above query
		    /*  if(!isbnEntered) {
		    	  return query;
		      }*/
		      
		      ParseQuery<PostTextInfo> queryISBN = new ParseQuery<PostTextInfo>("Post");
		      queryISBN.whereEqualTo("ISBN", isbn);
		      // if user only enters isbn, return queryISBN
		      
		      List<ParseQuery<PostTextInfo>> queries = new ArrayList<ParseQuery<PostTextInfo>>();
		      if(titleEntered || authorEntered || editionEntered) queries.add(query);
		      
		      if(isbnEntered){
		      queries.add(queryISBN);
		      }
		      
		      ParseQuery<PostTextInfo> mainQuery = ParseQuery.or(queries);
		      mainQuery.whereEqualTo("school", userSchool);
		      return mainQuery;
		    }
		  });
		 
		 // Set the adapter created above to the one from SearchActivity
		 ((SearchActivity) getActivity()).setParseAdapter(adapter);
		 
		 // Start the results fragment
		 Fragment fragment = new SearchResultsFragment();
		 FragmentTransaction transaction = getFragmentManager().beginTransaction();
		 transaction.replace(R.id.fragmentContainerSearch, fragment);
		 transaction.addToBackStack(null);
		 transaction.commit();
			
		
	}
	
	 private void searchByCourseProf() {
         courseName= editTextcourse.getText().toString().trim();
         profName= editTextprof.getText().toString().trim();
         // assume nothing was entered
         courseEntered = false;
         profEntered = false;
         
         // check if something was entered
         if(courseName.length() > 0) {
        	 courseEntered = true;
         }
         if(profName.length() > 0) {
        	 profEntered = true;
         }
         
         // if neither entered
         if(!courseEntered && !profEntered) {
        	 Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please enter course, professor, or both.", Toast.LENGTH_LONG);
 			toast.show();
        	 return;
         }
         SearchResultsAdapter adapter = new SearchResultsAdapter(getActivity(), new ParseQueryAdapter.QueryFactory<PostTextInfo>() {
                   public ParseQuery<PostTextInfo> create() {
                     // Here is where you do the query stuff
                     ParseQuery<PostTextInfo> query = new ParseQuery<PostTextInfo>("Post");
                     
                     // only add to query if field was entered
                     if(courseEntered) {
                    	 query.whereEqualTo("SubjectLower", courseName.toLowerCase(Locale.US));
                     }
                     if(profEntered)
                     {
                     query.whereEqualTo("ProfessorLower", profName.toLowerCase(Locale.US));
                     }
                     query.whereEqualTo("school", userSchool);
                     return query;
                   }
                 });



// Set the adapter created above to the one from SearchActivity

         ((SearchActivity) getActivity()).setParseAdapter(adapter);
                // Start the results fragment
                Fragment fragment = new SearchResultsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerSearch, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
       }



}


