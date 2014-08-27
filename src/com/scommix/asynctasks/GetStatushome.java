package com.scommix.asynctasks;


import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;

import org.kobjects.base64.Base64;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.Vectoronline;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.HomeAdapter;

import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.Utils;




public class GetStatushome extends AsyncTask<Void, Integer, Void>
{
	

	

	HomeAdapter adapter;
	PullToRefreshGridView yourstatuslist;


    
	FragmentActivity activity;
	ArrayList<Updates> allupdates;
	ArrayList<online> commentcountlist;
	ArrayList<online> liketag;
	ArrayList<online> likecountlist;
	String frompostupdate;
	


	GridView mGridView;


	
	public GetStatushome(FragmentActivity activity2, ArrayList<Updates> allupdates, HomeAdapter adapter, PullToRefreshGridView yourstatuslist,  ArrayList<online> commentcountlist,ArrayList<online> likecountlist,  ArrayList<online> liketag, GridView mGridView) {
		// TODO Auto-generated constructor stub
		
		this.activity=activity2;
		this.allupdates=allupdates;
		this.adapter=adapter;
		this.yourstatuslist=yourstatuslist;
        this.commentcountlist=commentcountlist;
       

	
		this.likecountlist=likecountlist;
		this.liketag=liketag;
	
		this.mGridView=mGridView;
		
	}


	
	@Override
	protected void onPreExecute()
	{
		// TODO Auto-generated method stub
		super.onPreExecute();
	
		System.out.println("getting updates getstatus");
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				yourstatuslist.setRefreshing(true);
			}
		},50);

	
		
		
}
	
	
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		cancel(true);
		System.out.println("cancelledddddds");

	
		
	
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
				
                temp=common.GetStatus(ScommixSharedPref.getUSERID());

			
			    
				int lastsize=temp.size();
				
			
				
				Log.i("status size", ""+lastsize);

				for(int i=0;i<temp.size();i++)
				{
					System.out.println(temp.get(i).userpic);
					if (isCancelled())  break;
					String useridd=temp.get(i).userstatusid;
				
					
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
						
				temp1.addAll(i, common.GetAllCommentsCount(useridd));
					
				temp2.addAll(i,common.CountAllSatatusLike(useridd));
					
			
				
				
				}
				if (!isCancelled())
				{
					if(allupdates.size()!=0)
					{
					  allupdates.clear();
					  commentcountlist.clear();
					  likecountlist.clear();
					  liketag.clear();

					}
					
					 allupdates.addAll(temp);
					commentcountlist.addAll(temp1);
					likecountlist.addAll(temp2);
					liketag.addAll(temp3);
					System.out.println("values added");
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

		
	
     new Handler().postDelayed(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		  yourstatuslist.onRefreshComplete();
	}
}, 500);

      	
      
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

             if(allupdates.size()==0)
             {
            	 
            	 allupdates.clear();
				  commentcountlist.clear();
				  likecountlist.clear();
				  liketag.clear();

				  adapter.notifyDataSetChanged();
            	 Toast.makeText(activity, "No Updates...", Toast.LENGTH_SHORT).show();
             }
             else{
    			
          		   mGridView.setAdapter(adapter); 
          		   
          		   adapter.notifyDataSetChanged();
             }
			
	}
			
		
		});
		
		
	}
	private void serialize() {
		
		SharedPreferences appSharedPrefs = PreferenceManager
				  .getDefaultSharedPreferences(activity.getApplicationContext());
				  Editor prefsEditor = appSharedPrefs.edit();
				  prefsEditor.clear().commit();
				  Gson gson = new Gson();
				  for(int i=0;i<allupdates.size();i++)
				  {
					  String updates = gson.toJson(allupdates.get(i));
					  String commentcount=gson.toJson(commentcountlist.get(i));
					  String liketa=gson.toJson(liketag.get(i));
					  System.out.println("mnfvjkfbjkvbfjkvjkf "+liketag.get(i).getLiketag());
					  String likecount=gson.toJson(likecountlist.get(i));
					  
					  prefsEditor.putString("updates"+i, updates);
					  prefsEditor.putString("commentcount"+i, commentcount);
					  prefsEditor.putString("liketa"+i, liketa);
					  prefsEditor.putString("likecount"+i, likecount);
					
				  }
				  
				  prefsEditor.putInt("size", allupdates.size());
				  prefsEditor.commit();
				 
			
				 
	}
	

	
}
