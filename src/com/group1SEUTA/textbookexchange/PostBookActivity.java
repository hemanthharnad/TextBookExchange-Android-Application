package com.group1SEUTA.textbookexchange;

import com.parse.Parse;
import com.parse.ParseObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class PostBookActivity extends Activity{
	
	private PostTextInfo post;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		ParseObject.registerSubclass(PostTextInfo.class);
		Parse.initialize(this, "aMoKFCkcN3PmUYryTT3fBwDZYeFW72OQSyYOgAs0", "EYKNmPaZLTOVmkDPoOlNxkza7qbA1Xr542vLJYz5");
		post = new PostTextInfo();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		
		
		
		setContentView(R.layout.activity_post_book);
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
		
		if(fragment == null) {
			fragment = new PostBookFragment();
			manager.beginTransaction().add(R.id.fragmentContainer,  fragment).commit();
		}
		
	}
	
	public PostTextInfo getCurrentPost() {
		return post;
	}

}
