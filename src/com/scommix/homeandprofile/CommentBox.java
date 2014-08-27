package com.scommix.homeandprofile;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import com.scommix.WebServices.Common.online;
import com.scommix.adapters.CommentBoxAdapter;
import com.scommix.asynctasks.CommentTask;
import com.scommix.asynctasks.GetComment;
import com.scommix.friendsandsearch.FriendActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;
import com.scommix.tools.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class CommentBox extends ActionBarActivity implements OnClickListener
{
	String nameofuser,status,statustime,statusid;
	TextView nametextview,statustextview,timing,commentcountt;
	ListView commentslist;
    CommentBoxAdapter adapter;
	EditText sendcommenteditetxt;
	ImageView sendcommentbutton;
	//ArrayList<String>  name,comment,commentid,userid,userstatusid,commenttime;
    ArrayList<online> comments;
	 String nooflike;
	 CommentTask taskComment;
	 GetComment taskGetComment;
	 ImageView userpic;
	public String statusfromedit;


    ImageView commentboxprofilepic;
    String userid;
    int successfulcomments;
	 String position;

	String wherefrom;
	private String profilepicbyte;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		 supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.commentbox);
		commentslist=(ListView)findViewById(R.id.usercommentslist);
		sendcommenteditetxt=(EditText)findViewById(R.id.enter_comment);
		sendcommentbutton=(ImageView)findViewById(R.id.send_comment);

		//header config
		LayoutInflater inflaterr = getLayoutInflater();
		ViewGroup headerr = (ViewGroup)inflaterr.inflate(R.layout.header_commentbox, commentslist, false);
		commentslist.addHeaderView(headerr, null, false);
		commentboxprofilepic=(ImageView)headerr.findViewById(R.id.commentboxprofilepic);
		nametextview=(TextView)headerr.findViewById(R.id.commentboxstatususer);
		statustextview=(TextView)headerr.findViewById(R.id.commentboxtatustext);
		
		timing=(TextView)headerr.findViewById(R.id.statustimeincommentbox);
		
		sendcommentbutton.setOnClickListener(this);
		
		
		Intent fromcomment=getIntent();
		nameofuser=fromcomment.getStringExtra("nameofuser");
		status=fromcomment.getStringExtra("status");
		statustime=fromcomment.getStringExtra("statustime");
		position=fromcomment.getStringExtra("position");
		successfulcomments=fromcomment.getIntExtra("successfulcomments", 0);
		wherefrom=fromcomment.getStringExtra("wherefrom");
		
		System.out.println(wherefrom);
		 MainApp appobj=(MainApp)getApplication();
		appobj.setCommentcount(""+successfulcomments);
		System.out.println(successfulcomments);
		try 
		{
			statustime=parseDate(statustime);
		} catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		statusid=fromcomment.getStringExtra("statusid");
		userid=fromcomment.getStringExtra("userid");
		
		nametextview.setText(nameofuser);
		statustextview.setText(status);
		statustextview.setOnClickListener(this);
		
		
		Linkify.addLinks(statustextview, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);

		// Recognize all of the default link text patterns 
		Linkify.addLinks(statustextview, Linkify.ALL);

		// Disable all default link detection
		Linkify.addLinks(statustextview, 0);
		statustextview.setLinkTextColor(Color.BLUE);
		
		
		timing.setText(statustime);
		profilepicbyte=fromcomment.getStringExtra("profilepicbyte");
		comments=new ArrayList<online>();
	  
		 Picasso.with(CommentBox.this) //
	        .load(Utils.url+profilepicbyte) //
	        .placeholder(R.drawable.ic_loading) //
	        .error(R.drawable.cross) //
	        .fit() //
	        .into(commentboxprofilepic);
		 
		adapter=new CommentBoxAdapter(CommentBox.this,comments);
	    commentslist.setAdapter(adapter);
	    
	    taskComment=new CommentTask(CommentBox.this,statusid,statusfromedit,comments,commentslist,adapter,sendcommenteditetxt,successfulcomments,position);
		taskGetComment=new GetComment(CommentBox.this,statusid,comments,adapter,commentslist); 
		taskGetComment.execute();
		
     }
	
	  @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	    	// TODO Auto-generated method stub
	    	super.onConfigurationChanged(newConfig);
	    	
	    	if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
	        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


	    	}
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {     
	    	 	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


	    	} 
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
	        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


	    	} 
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
	    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    	}
	    	else {
	    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    	}
	    }
	    
	

	public String parseDate(String time)
		    throws ParseException
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
		                                                    Locale.US); 
		    
		    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		    long value=dateFormat.parse(time).getTime();  
			CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
					Long.parseLong(String.valueOf(value)),
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
			
		    return timeAgo.toString();
		}


	
	
	


@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	 MainApp appobj=(MainApp)getApplication();
	 successfulcomments=Integer.parseInt(appobj.getCommentcount());
	 
	 if(wherefrom.equals("myprofile"))
	 {
		 System.out.println("myprofile found");
			Intent broadcastIntent = new Intent();
		    broadcastIntent.setAction("commentcountprofile");
		    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		    broadcastIntent.putExtra("responsenewmessage", ""+successfulcomments);
		    broadcastIntent.putExtra("position", position);
		    sendBroadcast(broadcastIntent);
	 }
	 else if(wherefrom.equals("myhome"))
	 {
		    System.out.println("myhome found");
			Intent broadcastIntent = new Intent();
		    broadcastIntent.setAction("commentcounthome");
		    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		    broadcastIntent.putExtra("responsenewmessage", ""+successfulcomments);
		    broadcastIntent.putExtra("position", position);
		    sendBroadcast(broadcastIntent);
	 }
	 else if(wherefrom.equals("friendprofile")){
	
		    System.out.println("friend found");
			Intent broadcastIntent = new Intent() ;
		    broadcastIntent.setAction("commentcountfriend");
		    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		    broadcastIntent.putExtra("responsenewmessage", ""+successfulcomments);
		    broadcastIntent.putExtra("position", position);
		    sendBroadcast(broadcastIntent);
	 }

  
}

	@Override
	public void onClick(View view) 
	{
		switch(view.getId())
		{
		case R.id.commentboxtatustext:
		{

			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					CommentBox.this);
	 
	 
				// set dialog message
				alertDialogBuilder
					.setMessage(statustextview.getText().toString())
					.setCancelable(false)
					.setPositiveButton("Close",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
						
						}
					  });
					
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
				
		}
		break;
		
		case R.id.send_comment:
		{

			if(sendcommenteditetxt.getText().toString().equals(""))
			{
				sendcommenteditetxt.setHint("Type Your Comment!");
			}
			else{
				statusfromedit=sendcommenteditetxt.getText().toString();
				sendcommenteditetxt.setText("");
				
				if(taskComment.getStatus()==AsyncTask.Status.RUNNING)
				{
					 Toast.makeText(CommentBox.this, "Wait.....running", Toast.LENGTH_SHORT).show();
				}
				else if(taskComment.getStatus()==AsyncTask.Status.PENDING)
				{
					taskComment.execute(statusfromedit);
				}
				else if(taskComment.getStatus()==AsyncTask.Status.FINISHED)
				{
					taskComment=new CommentTask(CommentBox.this,statusid,statusfromedit,comments,commentslist,adapter,sendcommenteditetxt,successfulcomments,position);
					taskComment.execute(statusfromedit);
	            }
				
			
			}
		
		}
		break;
		}
		
		}
		


	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.commentmenu, menu);
   
           return true;
    }
    
    
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
      
        // Handle action bar actions click
        switch (item.getItemId()) {
        
        case android.R.id.home:
        {
        	this.finish();
        }
        break;
       
      
        case R.id.checklikes:
        {
        	Intent i=new Intent(CommentBox.this,CheckLikesPopup.class);
        	i.putExtra("userstatusid", statusid);
        	startActivity(i);
        }
        break;
        default:
            return super.onOptionsItemSelected(item);
        }
		return false;
    }






	
}
