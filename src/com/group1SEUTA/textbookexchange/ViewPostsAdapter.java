package com.group1SEUTA.textbookexchange;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class ViewPostsAdapter extends ParseQueryAdapter<PostTextInfo> {
	
	private LayoutInflater inflater;

	public ViewPostsAdapter(Context context,
			ParseQueryAdapter.QueryFactory<PostTextInfo> queryFactory) {
		super(context, queryFactory);
	}
	
	@Override
	public View getItemView(PostTextInfo post, View v, ViewGroup parent) {
	  if (v == null) {
	    v = View.inflate(getContext(), R.layout.view_posts_list_item, null);
	  }
	 
	  // Take advantage of ParseQueryAdapter's getItemView logic for
	  // populating the main TextView/ImageView.
	  // The IDs in your custom layout must match what ParseQueryAdapter expects
	  // if it will be populating a TextView or ImageView for you.
	  super.getItemView(post, v, parent);
	 
	  // Do additional configuration before returning the View.
	  TextView title = (TextView) v.findViewById(R.id.textViewPostsTitle);
	  title.setText(post.getTitle());
	  TextView price = (TextView) v.findViewById(R.id.textViewPostsPrice);
	  price.setText("$" + post.getPrice());
	  TextView date = (TextView) v.findViewById(R.id.textViewPostsDate);
	  java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
	  date.setText("Date Added:" + dateFormat.format(post.getCreatedAt()));
	  
	  
	  return v;
	}
}
