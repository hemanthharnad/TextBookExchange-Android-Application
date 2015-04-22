package com.group1SEUTA.textbookexchange;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
//import com.parse.ParseQuery;
import com.parse.ParseUser;


/**
 * This class extends the ParseObject to make a PostTextInfo class. It contains get and put methods.
 */
@ParseClassName("Post")
public class PostTextInfo extends ParseObject {
  public String getTitle() {
    return getString("title");
  }
  
  public void setTitle(String value) {
	 put("title", value);
 }
  
  public String getTitleLower() {
	  return getString("titleLower");
  }
  
  public void setTitleLower(String value){
	  put("titleLower", value.toLowerCase());
  }
 
  public String getAuthor() {
	    return getString("Author");
	  }
	  
  public void setAuthor(String value) {
		put("Author", value);
	 }
  
  public String getAuthorLower() {
	    return getString("AuthorLower");
	  }
	  
public void setAuthorLower(String value) {
		put("AuthorLower", value.toLowerCase());
	 }
	 
  public String getEdition() {
		 return getString("Edition");
		  }
		  
  public void setEdition(String value) {
		put("Edition", value);
		 }

  public String getPrice() {
		 return getString("SellingPrice");
		  }
		  
  public void setPrice(String value) {
		put("SellingPrice", value);
		 }
		 
  public String getProfessor() {
		 return getString("Professor");
		  }
		  
  public void setProfessor(String value) {
		put("Professor", value);
		 }
  
  public String getProfessorLower() {
		 return getString("ProfessorLower");
		  }
		  
public void setProfessorLower(String value) {
		put("ProfessorLower", value.toLowerCase());
		 }
  
  public String getSubject() {
		 return getString("Subject");
		  }
		  
  public void setSubject(String value) {
		put("Subject", value);
		 }
  
  public String getSubjectLower() {
		 return getString("SubjectLower");
		  }
		  
public void setSubjectLower(String value) {
		put("SubjectLower", value.toLowerCase());
		 }
  
  public String getISBN() {
		 return getString("ISBN");
		  }
		  
  public void setISBN(String value) {
		put("ISBN", value);
		 }
  
  public ParseUser getUser() {
	    return getParseUser("user");
	  }

	  public void setUser(ParseUser value) {
	    put("user", value);
	  }
	  
	  public ParseFile getPhotoFile() {
	        return getParseFile("photo");
	    }
	 
	    public void setPhotoFile(ParseFile file) {
	        put("photo", file);
	    }
	    
	    public void setDescription(String value) {
	    	put("description", value);
	    }
	    
	    public String getDescription(){
	    	return getString("description");
	    }
	    
	    public void setSchool(String value) {
	    	put("school", value);
	    }
	    
	    public String getSchool(){
	    	return getString("school");
	    }
	    
		 	 

}