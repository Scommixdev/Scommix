package com.scommix.tools;

import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.scommix.firstrun.Newdemo;
import com.scommix.navigationmainactivity.MainActivity;
import com.svimedu.scommix.R;
import com.svimedu.scommix.Welcome;

public class GenerateNotification {
	

	
	
	@SuppressWarnings("deprecation")
	public static void generateNotification(Context context, String message) {
	         int icon = R.drawable.ic_launcher;
	         long when = System.currentTimeMillis();
	        NotificationManager notificationManager = (NotificationManager)
	                 context.getSystemService(Context.NOTIFICATION_SERVICE);
	         @SuppressWarnings("deprecation")
			Notification notification = new Notification(icon, message, when);
	         String title = context.getString(R.string.app_name);
	         Intent notificationIntent = new Intent(context, Welcome.class);
	         // set intent so it does not start a new activity
	      
	         PendingIntent intent =
	                 PendingIntent.getActivity(context, 0, notificationIntent, 0);
	         notification.setLatestEventInfo(context, title, message, intent);
	         notification.flags |= Notification.FLAG_AUTO_CANCEL;
	         notification.flags |= notification.flags |
	        		 Notification.DEFAULT_SOUND|
		             Notification.FLAG_SHOW_LIGHTS;
	         notification.ledARGB = Color.GREEN;
	         notification.defaults |= Notification.DEFAULT_ALL;
	         notificationManager.notify(0, notification);
	     }

}
