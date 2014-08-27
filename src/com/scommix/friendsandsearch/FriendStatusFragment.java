package com.scommix.friendsandsearch;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import org.kobjects.base64.Base64;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.FriendProfileStatusAdapter;
import com.scommix.asynctasks.RefreshFriendPost;
import com.scommix.asynctasks.LikeTask;
import com.scommix.asynctasks.RefreshMyPost;
import com.scommix.homeandprofile.CommentBox;

import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;
import com.scommix.tools.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendStatusFragment extends Fragment{
	
	PullToRefreshGridView myprofilegridview;
	GridView mGridView;
	MainApp appobject;
	ArrayList<online> friendcommentcountlist;
	ArrayList<online>friendlikecountlist;
	ArrayList<Updates> friendupdates;

	ArrayList<String> friendprofileliketag;

	RefreshFriendPost taskGetStatus;
	FriendProfileStatusAdapter adapter;
	String userid;
	 MyReceiver receiver;
	 IntentFilter filter ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView=inflater.inflate(R.layout.myprofilestatus, container,false);
		myprofilegridview=(PullToRefreshGridView)rootView.findViewById(R.id.myprofilestatuslist);
		
		mGridView=myprofilegridview.getRefreshableView();
		LayoutInflater inflate=getActivity().getLayoutInflater();
		View emptylayout=inflate.inflate(R.layout.view_empty, null);
		mGridView.setEmptyView(emptylayout);
		Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
		tryagain.setText("Seems Empty!");
		
		userid=getActivity().getIntent().getStringExtra("id");
		Log.i("userid", userid);
		friendcommentcountlist=new ArrayList<online>();
		friendlikecountlist=new ArrayList<online>();
		friendupdates=new ArrayList<Updates>();
		
		friendprofileliketag=new ArrayList<String>();
		
		 filter = new IntentFilter("commentcountfriend");
	        filter.addCategory(Intent.CATEGORY_DEFAULT);
	        receiver=new MyReceiver();
		asynctask();
		
		return rootView;
	}
	
	
	@Override
		public void onDestroyView() {
			// TODO Auto-generated method stub
			super.onDestroyView();
			getActivity().unregisterReceiver(receiver);
			System.out.println("destroy view");
		}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	 getActivity().registerReceiver(receiver, filter);
		 
	}
	
	private void asynctask() {
		// TODO Auto-generated method stub
		adapter=new FriendProfileStatusAdapter(getActivity(), friendupdates, friendcommentcountlist, friendlikecountlist, friendprofileliketag);

		taskGetStatus=new RefreshFriendPost(getActivity(), adapter,friendupdates, friendlikecountlist, friendcommentcountlist, friendprofileliketag,myprofilegridview,mGridView,userid);
		taskGetStatus.execute();

		
		myprofilegridview.setOnRefreshListener(new OnRefreshListener2<GridView>() {

						@Override
						public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
							if(taskGetStatus.getStatus()==AsyncTask.Status.RUNNING)
							{
							}
							else if(taskGetStatus.getStatus()==AsyncTask.Status.PENDING)
							{
								
								taskGetStatus.execute();
							}
							else if(taskGetStatus.getStatus()==AsyncTask.Status.FINISHED)
							{
							 new RefreshFriendPost(getActivity(), adapter, friendupdates, friendlikecountlist, friendcommentcountlist, friendprofileliketag, myprofilegridview, mGridView,userid).execute();								 
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


	//commentcountfriend
	class MyReceiver extends BroadcastReceiver
	 {

		
		
		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			 String data=intent.getStringExtra("responsenewmessage");
			 final String position=intent.getStringExtra("position");
	         Log.i("from friend status profile"+data, position);
			 final online v=new online();
			 v.setCount(data);
		
			getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 friendcommentcountlist.set(Integer.parseInt(position), v);
						 adapter.notifyDataSetChanged();
					}
				});
			
		}
		 
	 }

}
