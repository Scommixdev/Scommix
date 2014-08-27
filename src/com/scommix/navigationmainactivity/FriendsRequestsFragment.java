package com.scommix.navigationmainactivity;

import java.util.ArrayList;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.navigationmainactivity.EventsFragment.GetEvents;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;
import com.scommix.tools.ConnectionDetector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.scommix.adapters.FriendRequestAdapter;
import com.scommix.asynctasks.GetStatushome;

import com.scommix.friendsandsearch.FriendActivity;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class FriendsRequestsFragment extends Fragment{
	
	
	FriendRequestAdapter adapter;
	GetFriendRequest taskGetFriendRequest;
	ArrayList<online> requestarraylist;
    PullToRefreshGridView friendrequestlist;
	GridView mGridView;
	 
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView=inflater.inflate(R.layout.friendsrequest, container,false);
		friendrequestlist=(PullToRefreshGridView)rootView.findViewById(R.id.friendrequestlist);
		mGridView=friendrequestlist.getRefreshableView();
		requestarraylist=new ArrayList<online>();
		taskGetFriendRequest=new GetFriendRequest(getActivity(), requestarraylist);
		
		LayoutInflater inflate=getActivity().getLayoutInflater();
		View emptylayout=inflate.inflate(R.layout.view_empty, null);
		mGridView.setEmptyView(emptylayout);
		Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
		tryagain.setText("Seems Empty!");
		taskGetFriendRequest.execute();
		
	
		
		friendrequestlist.setOnRefreshListener(new OnRefreshListener<GridView>() {

				

				@Override
				public void onRefresh(PullToRefreshBase<GridView> refreshView) {
					// TODO Auto-generated method stub
					
					
					if(taskGetFriendRequest.getStatus()==AsyncTask.Status.RUNNING)
					{
					
					
					}
					else if(taskGetFriendRequest.getStatus()==AsyncTask.Status.PENDING)
					{
						Log.i("pending", "pending");
						taskGetFriendRequest.execute();
					}
					else if(taskGetFriendRequest.getStatus()==AsyncTask.Status.FINISHED)
					{
						System.out.println("finished");	
						new GetFriendRequest(getActivity(),requestarraylist).execute();
		            }
				}

			});

    
		
		
		return rootView;
	}

	
	public class GetFriendRequest extends AsyncTask<Void, Void, Void>
	{
		private String count;
		
		Context baseContext;
		
		ArrayList<online> requestarraylist;

		

		public GetFriendRequest(Context baseContext, ArrayList<online> requestarraylist) {
			// TODO Auto-generated constructor stub
			this.baseContext=baseContext;
			this.requestarraylist=requestarraylist;
			
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					friendrequestlist.setRefreshing(true);
				}
			}, 100);
			
				if(requestarraylist!=null)
				{
					requestarraylist.clear();
				}
			
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
			
				requestarraylist=new Common().GetFriendRequest(ScommixSharedPref.getUSERID());
				
				for(int i=0;i<requestarraylist.size();i++)
				{
					System.out.println("userid value"+ requestarraylist.get(i).userid);
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try{
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						friendrequestlist.onRefreshComplete();
					}
				});
				
			
				if(requestarraylist!=null)
				{
					
					adapter=new FriendRequestAdapter(getActivity(),requestarraylist);
					mGridView.setAdapter(adapter);
				}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		
	
			
			
			
		}
		

		
	}
}
