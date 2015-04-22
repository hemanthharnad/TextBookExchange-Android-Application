package com.group1SEUTA.textbookexchange;

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.PushService;
import com.parse.SaveCallback;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();

	// Initialize the Parse SDK.
	Parse.initialize(this, "aMoKFCkcN3PmUYryTT3fBwDZYeFW72OQSyYOgAs0", "EYKNmPaZLTOVmkDPoOlNxkza7qbA1Xr542vLJYz5"); 

	// Specify an Activity to handle all pushes by default.
	
	
	ParsePush.subscribeInBackground("", new SaveCallback() {
		  @Override
		  public void done(ParseException e) {
		    if (e == null) {
		      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
		    } else {
		      Log.e("com.parse.push", "failed to subscribe for push", e);
		    }
		  }
		});
  }
}
