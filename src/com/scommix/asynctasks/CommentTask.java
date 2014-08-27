package com.scommix.asynctasks;


import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.CommentBoxAdapter;
import com.scommix.homeandprofile.CommentBox;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.GetAllUserInfo;
import com.svimedu.scommix.MainApp;

public class CommentTask extends AsyncTask<String, Void, Void>
{
	ProgressDialog dialog;
	ProgressDialog progress;
	CommentBox commentBox;
	String statusid; 
	String statusfromedit;
	ArrayList<online> comments;
	ListView commentslist;
	CommentBoxAdapter adapter;
	EditText sendcommenteditetxt;
 boolean done=true;
	String position;
	 int successfulcomments;
	 
	public CommentTask(CommentBox commentBox, String statusid, String statusfromedit,
			ArrayList<online> comments, ListView commentslist, CommentBoxAdapter adapter, EditText sendcommenteditetxt, int successfulcomments, String position) {
		// TODO Auto-generated constructor stub
	this.commentBox=commentBox;	
	this.statusid=statusid;
	this.statusfromedit=statusfromedit;
	this.comments=comments;
	this.commentslist=commentslist;
	this.adapter=adapter;
     this.successfulcomments=successfulcomments;
    this.position=position;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		
		progress=new ProgressDialog(commentBox);
		progress.setCancelable(false);
		progress.setMessage("Commenting...");
		progress.show();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		try{
			String s=params[0];
			Common com=new Common();
			com.InsertComment(ScommixSharedPref.getUSERID(), statusid, s);
		}
		catch(Exception e)
		{
			done=false;
			e.printStackTrace();
		}

		
	return null;
	

		
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		
		commentBox.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(done==true)
				{
					successfulcomments=successfulcomments+1;
					MainApp appobj=(MainApp)commentBox.getApplication();
					appobj.setCommentcount(""+successfulcomments);
					System.out.println("dsnvjdsn" +successfulcomments);
				}
				else
				{
					
				}
				adapter.notifyDataSetChanged();
				//sendcommenteditetxt.setText("");
			    progress.dismiss();
			    new GetComment(commentBox,statusid,comments,adapter,commentslist).execute(); 
				
			}
		});
		
		
		
	}
}
