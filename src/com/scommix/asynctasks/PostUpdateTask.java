package com.scommix.asynctasks;


import java.util.ArrayList;





import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;



import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.HomeAdapter;
import com.scommix.customviews.QuickReturnListView;
import com.scommix.navigationmainactivity.MainActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;



public class PostUpdateTask extends AsyncTask<Void, Void, Void>
{
	//GetStatusOfFriends taskGetStatus;
	final String SOAP_ACTION = "http://tempuri.org/InsertStatus";
    final String METHOD_NAME = "InsertStatus";
    final String NAMESPACE =
             "http://tempuri.org/";
    final String URL =
             "http://scommix.cloudapp.net/webservices/common.asmx";
    String userid=ScommixSharedPref.getUSERID();
    NotificationManager mNotificationManager;
    Notification note;
    NotificationCompat.Builder mBuilder;
    Activity activity; ArrayList<Updates> allupdates;
	 EditText postedit;
	HomeAdapter adapter;
	ListView yourstatuslist; 
	int h=0;
	ArrayList<online> commentcountlist;
	String username;
	Bitmap profilepicc;
	String temp="a";
	ArrayList<String> useridd;
ArrayList<online> likecountlist;
//	ProgressDialog dialog;
	
	String frompostupdate="frompostupdate";
	public PostUpdateTask(Activity activity, ArrayList<Updates> allupdates, EditText postedit, HomeAdapter adapter, ListView yourstatuslist, String username, Bitmap profilepicc,ArrayList<String> useridd,ArrayList<online> commentcountlist,ArrayList<online> likecountlist) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.allupdates=allupdates;
		this.postedit=postedit;
		this.adapter=adapter;
		this.yourstatuslist=yourstatuslist;
		this.useridd=useridd;
	this.commentcountlist=commentcountlist;
	this.likecountlist=likecountlist;
		this.username=username;
		
		this.profilepicc=profilepicc;
		//taskGetStatus=new GetStatusOfFriends(activity, allupdates, adapter, yourstatuslist, username, profilepicc,temp,useridd,commentcountlist,likecountlist);
		
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		

			
		
		mNotificationManager=(NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(activity);
		Intent intent = new Intent(activity, MainActivity.class);
	    PendingIntent pendIntent = PendingIntent.getActivity(activity, 0, intent, 0);
		mBuilder.setContentTitle("Posting Update...")
		    .setContentText(postedit.getText().toString())
		    .setContentIntent(pendIntent)
		    .setSmallIcon(R.drawable.ic_launcher);
																															
		mBuilder.setProgress(0, 0, true);
		note = mBuilder.build();
		note.defaults |= Notification.DEFAULT_VIBRATE;
	
		mNotificationManager.notify(0, note);
//		 dialog=new ProgressDialog(activity);
//         dialog.setCancelable(false);
//         dialog.setMessage("Posting...");
//         dialog.show();
		super.onPreExecute();
	}
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
         allupdates.clear();


	
		try
		{
			Common com=new Common();
			com.InsertStatus(userid, postedit.getText().toString());
	 
		}
		catch (Exception e)
		{
			
			activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(activity, "Cant Update Task!`", Toast.LENGTH_SHORT).show();
					h=1;
				}
			});
			
			e.printStackTrace();
		}
		
		
	
		
		
		return null;
		
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		//Toast.makeText(SimpleUser.this, "Check log!", Toast.LENGTH_SHORT).show();
		
			
		if(h==1)
		{
			postedit.setText("");
			mBuilder.setProgress(0, 0, false);
			mBuilder.setContentTitle("Problem Updating Update");
			
			note=mBuilder.build();
			mNotificationManager.notify(0, note);
		}
		else{
			postedit.setText("");
			mBuilder.setProgress(0, 0, false);
			mBuilder.setContentTitle("Posted Update!");
			
			note=mBuilder.build();
			mNotificationManager.notify(0, note);
	      
			//new GetStatusOfFriends(activity, allupdates, adapter, yourstatuslist, username, profilepicc,temp,useridd,frompostupdate,commentcountlist,likecountlist).execute();
		}
	
		
        
	}
}
