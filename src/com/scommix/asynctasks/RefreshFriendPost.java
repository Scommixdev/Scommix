package com.scommix.asynctasks;

import java.util.ArrayList;

import org.kobjects.base64.Base64;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.Vectoronline;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.FriendProfileStatusAdapter;
import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.Utils;

public class RefreshFriendPost extends AsyncTask<Void, Void, Void>
{

	FragmentActivity activity;
	FriendProfileStatusAdapter adapter; 
	ArrayList<Updates> friendupdates;
	ArrayList<online> friendlikecountlist;
	ArrayList<online> friendcommentcountlist;

	ArrayList<String> friendprofileliketag;
	PullToRefreshGridView myprofilegridview; 
	GridView mGridView;
	 int i=0;
	 String userid;
	 
	public RefreshFriendPost(FragmentActivity activity,
			FriendProfileStatusAdapter adapter, ArrayList<Updates> friendupdates,
			ArrayList<online> friendlikecountlist,
			ArrayList<online> friendcommentcountlist,
			ArrayList<String> friendprofileliketag,
			PullToRefreshGridView myprofilegridview, GridView mGridView,String userid) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.adapter=adapter;
		this.friendupdates=friendupdates;
		this.friendcommentcountlist=friendcommentcountlist;
		this.friendlikecountlist=friendlikecountlist;

		this.friendprofileliketag=friendprofileliketag;
		this.myprofilegridview=myprofilegridview;
		this.mGridView=mGridView;
		this.userid=userid;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	        	myprofilegridview.setRefreshing(true);
	        }
	    }, 100);
		
		if(friendupdates!=null)
		{
			friendcommentcountlist.clear();
			friendupdates.clear();
			friendlikecountlist.clear();
		    friendprofileliketag.clear();
	    }
	
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try
		
		{
			Common common=new Common();
			ArrayList<Updates> temp=new ArrayList<Updates>();
			ArrayList<online> temp1=new ArrayList<online>();
			ArrayList<online> temp2=new ArrayList<online>();
			
			temp=common.GetProfileStatus(userid);
		
		
			for(int i=0;i<temp.size();i++)
			{
				if(isCancelled()) break;
				String useridd=temp.get(i).userstatusid;
				
			
			    temp1.addAll(i, common.GetAllCommentsCount(useridd));
				temp2.addAll(i, common.CountAllSatatusLike(useridd));
			
			        Vectoronline vc=new Vectoronline();
					vc=common.GetStatusLikeName(useridd);
					boolean iliked=checklike(vc);
					
					if(iliked==false)
					{
						Log.i("for status "+i, "not liked");
						friendprofileliketag.add("Like");
					}
					
					else if(iliked==true)
					{
						Log.i("for status "+i, "liked");
						friendprofileliketag.add("Liked");
					}
				
				
			}
			
			
			friendupdates.addAll(temp);
			friendcommentcountlist.addAll(temp1);
		   friendlikecountlist.addAll(temp2);
		 // serialize();
		}
		catch(Exception e)
		{
			i=1;
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
	
	private Bitmap giveprofilepic(Updates updates) {
		// TODO Auto-generated method stub
		 byte[] profilebytearray=  Base64.decode(updates.getUserprofileimage());
		 //Decode image size
		 Bitmap bmp;
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
			bmp=BitmapFactory.decodeByteArray(profilebytearray, 0, profilebytearray.length,o);

	        //The new size we want to scale to
	        final int REQUIRED_SIZE=70;

	        //Find the correct scale value. It should be the power of 2.
	        int scale=1;
	        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	            scale*=2;

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        
			bmp=BitmapFactory.decodeByteArray(profilebytearray, 0, profilebytearray.length,o2);

	        
		 bmp=Utils.getRoundedBitmap(bmp);
		return bmp;
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
		myprofilegridview.onRefreshComplete();
		
		if(i==1)
		{
			
		}
		else{
			if(friendupdates.size()==0)
			{
				Toast.makeText(activity, "No Updates!", Toast.LENGTH_SHORT).show();
			}
			mGridView.setAdapter(adapter);
		}

	}
	
}
