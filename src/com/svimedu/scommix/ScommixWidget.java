package com.svimedu.scommix;

import com.scommix.homeandprofile.Status;
import com.scommix.navigationmainactivity.MainActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class ScommixWidget extends AppWidgetProvider{
	
	Intent intent;
	  
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
	
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for(int i=0; i<appWidgetIds.length; i++){
			int currentWidgetId = appWidgetIds[i];
		
		intent=new Intent(context,Status.class);
		 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 
		 Intent tomessage=new Intent(context,MainActivity.class);
		 tomessage.putExtra("message", "message");
		 tomessage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 
		PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
		PendingIntent pending1 = PendingIntent.getActivity(context, 0, tomessage, 0);

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);
		 views.setOnClickPendingIntent(R.id.post, pending);
		 views.setOnClickPendingIntent(R.id.messagewidget, pending1);

		 

		 
		 
		appWidgetManager.updateAppWidget(currentWidgetId,views);
		}
	}

}
