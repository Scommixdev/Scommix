package com.scommix.asynctasks;

import java.util.ArrayList;

import org.kobjects.base64.Base64;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.Vectoronline;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.HomeAdapter;
import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.Utils;
import com.svimedu.scommix.MainApp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GetStatus1home extends AsyncTask<Void, Void, Void>{

	FragmentActivity activity;

	HomeAdapter adapter;
	PullToRefreshGridView yourstatuslist;


	int resultr=1;
	
	ArrayList<Updates> allupdates;
	ArrayList<online> commentcountlist;
	ArrayList<online> likecountlist;
    ArrayList<online> liketag;
    
    GridView mGridView;

	int tillnow;
	
	
	public GetStatus1home(FragmentActivity activity,
			ArrayList<Updates> allupdates, HomeAdapter adapter,
			PullToRefreshGridView yourstatuslist, ArrayList<online> commentcountlist,
			ArrayList<online> likecountlist,
			ArrayList<online> liketag, GridView mGridView) {
		
		this.activity=activity;
		this.allupdates=allupdates;
		this.adapter=adapter;
		this.yourstatuslist=yourstatuslist;

	
	
		this.commentcountlist=commentcountlist;
		this.likecountlist=likecountlist;
		this.mGridView=mGridView;
		this.liketag=liketag;

		
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		tillnow=allupdates.size();
	
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try{
			Common common=new Common();
			ArrayList<Updates> temp=new ArrayList<Updates>();
			ArrayList<online> temp1=new ArrayList<online>();
			ArrayList<online> temp2=new ArrayList<online>();
			ArrayList<online> temp3=new ArrayList<online>();
			
		if(allupdates.size()==0)
		{
			resultr=0;
		}
		else{
			
			temp=common.GetStatus1(ScommixSharedPref.getUSERID(),allupdates.get(allupdates.size()-1).statustime);
			if(temp.size()!=0)
			{
				for(int i=0;i<temp.size();i++)
				{
					String useridd=temp.get(i).userstatusid;
		
					if (isCancelled())  break;
					
					temp1.addAll(common.GetAllCommentsCount(useridd));
					temp2.addAll(common.CountAllSatatusLike(useridd));
					
					
					 Vectoronline vc=new Vectoronline();
						vc=common.GetStatusLikeName(useridd);
						if(vc!=null)
						{
							boolean iliked=checklike(vc);
							
							if(iliked==false)
							{
								Log.i("for status "+i, "not liked");
								online o=new online();
								o.setLiketag("Like");
								temp3.add(o);
							
							}else if(iliked==true)
							{
								Log.i("for status "+i, "liked");
								online o=new online();
								o.setLiketag("Liked");
								temp3.add(o);
							}
						}
						else{
							online o=new online();
							o.setLiketag("Like");
							temp3.add(o);

						}
						
					
				
			        
				
								
						
			 
					
				}
				
		
	      if(!isCancelled())
	      {
	  		allupdates.addAll(temp);
			commentcountlist.addAll(temp1);
			likecountlist.addAll(temp2);
			liketag.addAll(temp3);
			System.out.println("values added");
	      }
		
				
			}
			else{
				resultr=0;
			}
		}
			
			  // serialize();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					Toast.makeText(activity, "Check Connectivity!", Toast.LENGTH_LONG).show();
				}
			});
		}
		return null;
	}
	

	
	private boolean checklike(Vectoronline vc) {
		// TODO Auto-generated method stub
		boolean value = false;

		for(int i=0;i<vc.size();i++)
			{
		
				if(vc.get(i).userid.equals(ScommixSharedPref.getUSERID()))
				{
					value=true;
					Log.i("my status", "liked");
					break;
				}
			}
		
		
		
		return value;
	}
	


	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		 yourstatuslist.onRefreshComplete();
	      	if(resultr==0)
	      	{
	      		activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(activity, "No More Updates...", Toast.LENGTH_SHORT).show();
					}
				});
	      	}
	      	else{
	      		activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
				//adapter=new HomeAdapter(activity,allupdates,username,commentcountlist,likecountlist,mGridView,liketag);
		              	mGridView.setAdapter(adapter); 
				        mGridView.setSelection(tillnow);
		        	
					}
					
				
				});
	      	}
	      
			
	}

}
