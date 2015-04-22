package com.group1SEUTA.textbookexchange;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class SearchResultsAdapter extends ParseQueryAdapter<PostTextInfo> {
	


	public SearchResultsAdapter(Context context,
			ParseQueryAdapter.QueryFactory<PostTextInfo> queryFactory) {
		super(context, queryFactory);
	}
	
	@Override
	public View getItemView(PostTextInfo post, View v, ViewGroup parent) {
	  if (v == null) {
	    v = View.inflate(getContext(), R.layout.search_result_item, null);
	  }
	 
	  // Take advantage of ParseQueryAdapter's getItemView logic for
	  // populating the main TextView/ImageView.
	  // The IDs in your custom layout must match what ParseQueryAdapter expects
	  // if it will be populating a TextView or ImageView for you.
	  super.getItemView(post, v, parent);
	 
	  // Do additional configuration before returning the View.
	  TextView title = (TextView) v.findViewById(R.id.searchItemTitle);
	  title.setText("Title: " + post.getTitle());
	  TextView author = (TextView) v.findViewById(R.id.searchItemAuthor);
	  author.setText("Author: " + post.getAuthor());
	  TextView edition = (TextView) v.findViewById(R.id.searchItemEdition);
	  edition.setText("Edition: " + post.getEdition());
	  TextView price = (TextView) v.findViewById(R.id.searchItemPrice);
	  price.setText("$" + post.getPrice());
	  return v;
	}
}

