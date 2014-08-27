package com.scommix.homeandprofile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.HomeAdapter;
import com.scommix.asynctasks.GetStatus1home;
import com.scommix.asynctasks.GetStatushome;

import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.ConnectionDetector;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;
import com.svimedu.scommix.ScommixContentProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

	GetStatushome taskGetStatus;
	GetStatus1home taskLoadmore;
     ArrayList<online> commentcountlist;
    public  HomeAdapter adapter;
    ArrayList<Updates> allupdates;
    ArrayList<online> likecountlist;
    ArrayList<online> liketag;
    String temp="a";
	PullToRefreshGridView yourstatuslist;
	GridView mGridView;
	ProgressBar progress;



    View headerhomelist;

    MainApp appobject;

     View rootView;

     int resultx=0;

     MessageReceiver receiver;
     IntentFilter filter;
     

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		
		
      rootView=inflater.inflate(R.layout.homelayout, container, false);
	  initViews();
  	  filter = new IntentFilter("commentcounthome");
      filter.addCategory(Intent.CATEGORY_DEFAULT);
      receiver=new MessageReceiver();
      return rootView;
      
	}
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("resume register receiver");
	    getActivity().registerReceiver(receiver, filter);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		System.out.println("on attach");
	}
	

	
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		System.out.println("unregister destroy view");
		getActivity().unregisterReceiver(receiver);
		taskGetStatus.cancel(true);
		taskLoadmore.cancel(true);
	}
	
	



	private void scroll_listeners() {
		// TODO Auto-generated method stub
	
		     yourstatuslist.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			

					@Override
					public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
						
						
						if(taskGetStatus.getStatus()==AsyncTask.Status.RUNNING)
						{
						
						
						}
						else if(taskGetStatus.getStatus()==AsyncTask.Status.PENDING)
						{
							Log.i("pending", "pending");
							taskGetStatus.execute();
						}
						else if(taskGetStatus.getStatus()==AsyncTask.Status.FINISHED)
						{
							System.out.println("finished");
							taskGetStatus=null;
							
							taskGetStatus=new GetStatushome(getActivity(), allupdates,adapter,yourstatuslist,commentcountlist,likecountlist,liketag,mGridView);
							 taskGetStatus.execute();
			            }
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
						Log.i("scommioxz", "load more");
						
						
						if(allupdates.size()==0)
						{
						   yourstatuslist.onRefreshComplete();
						}
						
						else
						{
							if(taskLoadmore.getStatus()==AsyncTask.Status.RUNNING)
							{
								Log.i("running", "running");
								taskLoadmore.cancel(true);

							}
							else if(taskLoadmore.getStatus()==AsyncTask.Status.PENDING)
							{
								Log.i("pending", "pending");

								taskLoadmore.execute();
							}
							
							else if(taskLoadmore.getStatus()==AsyncTask.Status.FINISHED)
							{
								System.out.println("finished");
								taskLoadmore=null;
								taskLoadmore=new GetStatus1home(getActivity(), allupdates,adapter,yourstatuslist,commentcountlist,likecountlist,liketag,mGridView);
								taskLoadmore.execute();
				            }
						}
						
						
					}

				});
	}




	private void asynctasks() 
	{
		

	    scroll_listeners();
	    
    	adapter=new HomeAdapter(getActivity(),allupdates,commentcountlist,likecountlist,mGridView,liketag);

		taskLoadmore=new GetStatus1home(getActivity(), allupdates,adapter,yourstatuslist,commentcountlist,likecountlist,liketag,mGridView);
		taskGetStatus=new GetStatushome(getActivity(), allupdates,adapter,yourstatuslist,commentcountlist,likecountlist,liketag,mGridView);
   
		
		try {
			resultx=deserializeUPDATES();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(resultx==1)
		{
		   adapter=new HomeAdapter(getActivity(),allupdates,commentcountlist,likecountlist,mGridView,liketag);
	       mGridView.setAdapter(adapter);
		}
		
		else if(resultx==2)
		{
			taskGetStatus.execute();
			
		}
				
				
		
				
	
	
	}









	private void initjavaobjects() {
		// TODO Auto-generated method stub
		appobject=(MainApp)getActivity().getApplication();
        
	        allupdates=appobject.getAllupdates();
            likecountlist=appobject.getAlllikecountlist();
			commentcountlist=appobject.getAllcommentcountlist();
			liketag=appobject.getHomeliketag();
		
		     
		      asynctasks();
	}

	private void initViews() {
	
		yourstatuslist=(PullToRefreshGridView)rootView.findViewById(R.id.statuslist);
		mGridView = yourstatuslist.getRefreshableView();
	
	
		LayoutInflater inflate=getActivity().getLayoutInflater();
		View emptylayout=inflate.inflate(R.layout.view_empty, null);
		mGridView.setEmptyView(emptylayout);
		Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
		tryagain.setText("Seems Empty!");
        initjavaobjects(); 
			
	}



	
	private int deserializeUPDATES() throws JSONException{
    
           
             allupdates=appobject.getAllupdates();
		     likecountlist=appobject.getAlllikecountlist();
			 commentcountlist=appobject.getAllcommentcountlist();
			 liketag=appobject.getHomeliketag();
			 
			allupdates.clear();
			likecountlist.clear();
			commentcountlist.clear();
			liketag.clear();
		
			
			 SharedPreferences appSharedPrefs = PreferenceManager
					  .getDefaultSharedPreferences(getActivity().getApplicationContext());
				
		              int size=appSharedPrefs.getInt("size", 0);
					  
					  
					  for(int i = 0;i<size;i++) {
					      String update = appSharedPrefs.getString("updates"+i, "");
					      String commentcount=appSharedPrefs.getString("commentcount"+i, "");
						  String liketa=appSharedPrefs.getString("liketa"+i, "");
						  String likecount=appSharedPrefs.getString("likecount"+i, "");
						
						  // my updates
						  
						  JSONObject c = new JSONObject(update);
					    
					      Updates up=new Updates();
					      up.setActive(c.getString("active"));
					      up.setName(c.getString("name"));
					      up.setStatus(c.getString("status"));
					      up.setStatustime(c.getString("statustime"));
					      up.setUserid(c.getString("userid"));
					      up.setUsername(c.getString("username"));
					      up.setUserpic(c.getString("userpic"));
					      
					      up.setUserstatusid(c.getString("userstatusid"));
					      allupdates.add(i,up);
					      
					      // comment count
					      
					    
					      		 JSONObject forcomm1 = new JSONObject(commentcount);
						    	  online onlineobj =new online();
						    	  onlineobj.setCount(forcomm1.getString("count"));
						    	  commentcountlist.add(i,onlineobj);
					      	
					    	 
					      //like tag
					      
					      		 JSONObject forlike1 = new JSONObject(liketa);
						    	  online onlineobj1 =new online();
						    	  onlineobj1.setLiketag(forlike1.getString("liketag"));
						    	  liketag.add(i, onlineobj1);
					      	
					      	
					      	// like count
					      	
                                  JSONObject forlikecount = new JSONObject(likecount);
						    	  online onlineobj2 =new online();
						    	  onlineobj2.setCount(forlikecount.getString("count"));
						    	  likecountlist.add(i,onlineobj2);
					      	
					      
					      
					   }
			
           
		
		if(allupdates.size()==0)
		{
			return 2;
		}
		else
		{
			return 1;
		}
    }
	


	class MessageReceiver extends BroadcastReceiver
	 {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			 String data=intent.getStringExtra("responsenewmessage");
			 final String position=intent.getStringExtra("position");
	
			 Log.i("from home"+data, position);
			 final online v=new online();
			 v.setCount(data);
		
			 getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					 commentcountlist.set(Integer.parseInt(position), v);
					 adapter.notifyDataSetChanged();
				}
			});
		
			
		}
		 
	 }

	

	
	

}
