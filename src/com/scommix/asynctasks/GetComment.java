package com.scommix.asynctasks;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Vectoronline;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.CommentBoxAdapter;
import com.scommix.homeandprofile.CommentBox;

public class GetComment extends AsyncTask<Void, Void, Void>
{

	CommentBox commentBox;
	String statusid;
	ArrayList<online> comments;
	 CommentBoxAdapter adapter; ListView commentslist;
	 ArrayList<online> tmp;
	 
	// ProgressDialog pd;
	public GetComment(CommentBox commentBox,
			String statusid, ArrayList<online> comments,  CommentBoxAdapter adapter, ListView commentslist) {
		// TODO Auto-generated constructor stub
		this.commentBox=commentBox;
	
		this.statusid=statusid; 
		this.comments=comments;
        this.adapter=adapter; 
        this.commentslist=commentslist;
     
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(comments.size()!=0)
		{
			comments.clear();
		}
		commentBox.setSupportProgressBarIndeterminateVisibility(true);
//		pd=new ProgressDialog(commentBox);
//		pd.setMessage("Loading...");
//		pd.setCancelable(false);
//		pd.show();

	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try{
		Common com=new Common();
		tmp =new ArrayList<online>();
		tmp=com.GetComment(statusid);
		comments.addAll(tmp);
		
	
		}
		
		catch(Exception e){
			commentBox.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(commentBox, "Check Internet", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		//pd.dismiss();
		commentBox.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
		
				adapter.notifyDataSetChanged();
	            commentBox.setSupportProgressBarIndeterminateVisibility(false);
				//new GetCommentsCountByStatusId().execute();
			}
		});
	}
	
}
