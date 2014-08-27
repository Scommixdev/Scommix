package com.scommix.homeandprofile;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.MyProfileAdapter;
import com.scommix.asynctasks.GetStatus1home;
import com.scommix.asynctasks.GetStatushome;
import com.scommix.asynctasks.RefreshMyPost;
import com.scommix.homeandprofile.HomeFragment.MessageReceiver;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MyStatusFragment extends Fragment {
	
	
PullToRefreshGridView myprofilegridview;
GridView mGridView;
MainApp appobject;
ArrayList<online> mycommentcountlist;
ArrayList<online> mylikecountlist;
ArrayList<Updates> myupdates;

ArrayList<String> myprofileliketag;

RefreshMyPost taskGetStatus;
public MyProfileAdapter adapter;
 MyReceiver receiver;
 IntentFilter filter ;
 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView=inflater.inflate(R.layout.myprofilestatus, container,false);
		myprofilegridview=(PullToRefreshGridView)rootView.findViewById(R.id.myprofilestatuslist);
		
		mGridView=myprofilegridview.getRefreshableView();
		
		LayoutInflater inflate=getActivity().getLayoutInflater();
		View emptylayout=inflate.inflate(R.layout.view_empty, null);
		mGridView.setEmptyView(emptylayout);
		Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
		tryagain.setText("Seems Empty!");
		
		 appobject=(MainApp)getActivity().getApplication();
		 
			 mycommentcountlist=appobject.getMycommentcountlist();
			 mylikecountlist=appobject.getMylikecountlist();
			 myupdates=appobject.getMyupdates();
		
			 myprofileliketag=appobject.getMyprofileliketag();
			
			 filter = new IntentFilter("commentcountprofile");
		        filter.addCategory(Intent.CATEGORY_DEFAULT);
		        receiver=new MyReceiver();
	
		     asyntasks();
			 
			 return rootView;
			 
	}
	
	    @Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			System.out.println("on pause");
			System.out.println("cancelleedd");
			taskGetStatus.cancel(true);
			//getActivity().unregisterReceiver(receiver);
		}
	    
	    
	    
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			System.out.println("on resme");
		    getActivity().registerReceiver(receiver, filter);
			 
		}
	
		
	
		@Override
			public void onDestroyView() {
				// TODO Auto-generated method stub
				super.onDestroyView();
				getActivity().unregisterReceiver(receiver);
				System.out.println("destroy view");
				taskGetStatus.cancel(true);
				
			}
		
		@Override
			public void onDetach() {
				// TODO Auto-generated method stub
				super.onDetach();
				taskGetStatus.cancel(true);
				System.out.println("cancelled");
			}


	private void asyntasks() {
		// TODO Auto-generated method stub
		adapter=new MyProfileAdapter(getActivity(), myupdates, mycommentcountlist, mylikecountlist, myprofileliketag);

		taskGetStatus=new RefreshMyPost(getActivity(), adapter, myupdates, mylikecountlist, mycommentcountlist, myprofileliketag,myprofilegridview,mGridView);

		taskGetStatus.execute();
		
		myprofilegridview.setOnRefreshListener(new OnRefreshListener2<GridView>() {

						@Override
						public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
							if(taskGetStatus.getStatus()==AsyncTask.Status.RUNNING)
							{
							   Toast.makeText(getActivity(), "Getting Updates...", Toast.LENGTH_SHORT).show();
							   
							}
							else if(taskGetStatus.getStatus()==AsyncTask.Status.PENDING)
							{
								
								taskGetStatus.execute();
							}
							else if(taskGetStatus.getStatus()==AsyncTask.Status.FINISHED)
							{
								new RefreshMyPost(getActivity(), adapter, myupdates, mylikecountlist, mycommentcountlist, myprofileliketag, myprofilegridview, mGridView).execute();
								 
				            }
						}

						@Override
						public void onPullUpToRefresh(
								PullToRefreshBase<GridView> refreshView) {
							// TODO Auto-generated method stub
							   myprofilegridview.onRefreshComplete();
						}

					});
		

	}
	
	
	
	class MyReceiver extends BroadcastReceiver
	 {

		
		
		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			 String data=intent.getStringExtra("responsenewmessage");
			 final String position=intent.getStringExtra("position");
	         Log.i("from status profile"+data, position);
			 final online v=new online();
			 v.setCount(data);
		
			getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 mycommentcountlist.set(Integer.parseInt(position), v);
						
						 adapter.notifyDataSetChanged();
					}
				});
			
		}
		 
	 }

}
