package com.scommix.asynctasks;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;






import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.MyProfileAdapter;
import com.scommix.homeandprofile.MyProfileFragment;
import com.scommix.navigationmainactivity.MainActivity;

import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteStatus extends AsyncTask<String, String, String> {

	NotificationManager mNotificationManager;
	 Notification note;
	 NotificationCompat.Builder mBuilder;
     String positionof;

	FragmentActivity profile; 
    
	// RefreshMyPost taskRefreshMyPost;
	
	 ListView profilelist; Bitmap profilepicc; 
	 MyProfileAdapter adapter;
	ProgressDialog dialog;
	String fromdelete;
	
	 ArrayList<Updates> myupdates;
	ArrayList<online> commentcountlist;
	ArrayList<online> likecountlist;
	ArrayList<String> profileliketag;


	
	public DeleteStatus(FragmentActivity profile, ArrayList<Updates> myupdates,
			Bitmap profilepicc2, MyProfileAdapter adapter2, ListView profilelist2, ArrayList<online> commentcountlist, ArrayList<online> likecountlist,String positionof, ArrayList<String> profileliketag) {
		// TODO Auto-generated constructor stub
		this.profile=profile;
		this.myupdates=myupdates;
		this.profilepicc=profilepicc2;
		this.adapter=adapter2;
		this.profilelist=profilelist2;
		this.commentcountlist=commentcountlist;
		this.likecountlist=likecountlist;
		this.positionof=positionof;
	    this.profileliketag=profileliketag;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
//		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentapiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
           
        //}
//		Intent intent = new Intent(ctx, Profile.class);
//	    PendingIntent pendIntent = PendingIntent.getActivity(ctx, 0, intent, 0);
//		mNotificationManager=(NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//		mBuilder = new NotificationCompat.Builder(ctx);
//		mBuilder.setContentTitle("Deleting Update...")
//		        .setContentIntent(pendIntent)
//		    .setSmallIcon(R.drawable.ic_launcher);
//		
//		mBuilder.setProgress(0, 0, true);
//		note = mBuilder.build();
//		note.defaults |= Notification.DEFAULT_VIBRATE;
//	
//		mNotificationManager.notify(0, note);
            dialog=new ProgressDialog(profile);
            dialog.setCancelable(false);
            
            dialog.setMessage("Deleting...");
            dialog.show();
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String idd=arg0[0];
		try{
			Common com=new Common();
			com.DeleteStatus(ScommixSharedPref.getUSERID(), idd);
			
			  
				//RefreshMyPost.serialize();
		}
	
		catch(Exception e)
		{
			profile.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					Toast.makeText(profile, "Problem Deleting Update..", Toast.LENGTH_SHORT).show();
				}
			});
		}

		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
//		mBuilder.setProgress(0, 0, false);
//		mBuilder.setContentTitle("Deleted Update!");
//		note=mBuilder.build();
//		mNotificationManager.notify(0, note);
//	     fromdelete="fromdelete";

		
		dialog.dismiss();
	
	     // taskRefreshMyPost=new RefreshMyPost(ctx, adapter,myupdates, profilelist, profilepicc,dialog,fromdelete,commentcountlist,likecountlist);
		     //taskRefreshMyPost.execute();
	}
	
	

}
