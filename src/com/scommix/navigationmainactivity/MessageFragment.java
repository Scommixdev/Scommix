package com.scommix.navigationmainactivity;


import java.util.ArrayList;

import org.kobjects.base64.Base64;



import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.asynctasks.GetStatus1home;
import com.scommix.asynctasks.GetStatushome;
import com.scommix.chatmessage.ChatWithFriend;
import com.scommix.firstrun.Newdemo;

import com.scommix.sharedpref.ScommixSharedPref;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.scommix.tools.Utils;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MessageFragment extends Fragment implements OnItemClickListener{
	PullToRefreshGridView friendslist;
	 GridView mGridView;
	
	
	ArrayList<online> d;
	CustomAdapter adapter;
    View rootView;
    GetFriendsList frns;
	 MenuItem refresh;
	private ScommixSharedPref pref;
ArrayList<online> isunread;
	 
//	 @Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setHasOptionsMenu(true);
//	}
//	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView=inflater.inflate(R.layout.chatterlayout, container,false);
		isunread=new ArrayList<online>();
		pref=ScommixSharedPref.getInstance(getActivity());
		if(pref.getUSERID().equals(""))
		{
			Intent i=new Intent(getActivity(),Newdemo.class);
			startActivity(i);
			getActivity().finish();
		}
		else
		{
			friendslist=(PullToRefreshGridView)rootView.findViewById(R.id.friendslist);
		
			mGridView = friendslist.getRefreshableView();
			d=new ArrayList<online>();
			
			LayoutInflater inflate=getActivity().getLayoutInflater();
			View emptylayout=inflate.inflate(R.layout.view_empty, null);
			mGridView.setEmptyView(emptylayout);
			Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
			tryagain.setText("Seems Empty!");
			friendslist.setOnItemClickListener(this);
			frns=new GetFriendsList();
			
			frns.execute();
			
			friendslist.setOnRefreshListener(new OnRefreshListener<GridView>() {

					


					@Override
					public void onRefresh(
							PullToRefreshBase<GridView> refreshView) {
						// TODO Auto-generated method stub
						if(frns.getStatus()==AsyncTask.Status.RUNNING)
						{
						
						
						}
						else if(frns.getStatus()==AsyncTask.Status.PENDING)
						{
						
							frns.execute();
						}
						else if(frns.getStatus()==AsyncTask.Status.FINISHED)
						{
					
							new GetFriendsList().execute();
			            }
					}

				});
			
			
			
			
			
			
			
	
					 
		
		}
	
	
	return rootView;
	}
	
	
	
	private String getprofilebase(byte[] mypic) {
		// TODO Auto-generated method stub
		String bas=Base64.encode(mypic);
		return bas;
	}



	class GetFriendsList extends AsyncTask<Void, Void, Void>
	{
	
		
		@Override
		protected void onPreExecute() {
		
			super.onPreExecute();
		
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					friendslist.setRefreshing(true);
				}
			}, 100);
			
			
			if(d!=null)
			{
				d.clear();
			}
			
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				d=new Common().GetFriendList(ScommixSharedPref.getUSERID());
				  //d=new Common().GetFriendList(ScommixSharedPref.getUSERID());
				  
					for(int i=0;i<d.size();i++)
					{
						//active isonline name userpic username userid gender classid institiute name
						
					    isunread.addAll(i,new Common().GetUnReadMessageCountByFriend(ScommixSharedPref.getUSERID(), d.get(i).userid));	
					  System.out.println(isunread.get(i).name);
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
	
			// TODO Auto-generated method stub
		
			try{
getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						friendslist.onRefreshComplete();
						if(d.size()==0)
						{
							Toast.makeText(getActivity(), "No Friends..", Toast.LENGTH_SHORT).show();
						}
						else
						{
					
						adapter=new CustomAdapter(getActivity(), d,isunread);
						mGridView.setAdapter(adapter);
						}
					}
				});
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
				
			
		

		}
		
	}

	class CustomAdapter extends BaseAdapter
	{
		FragmentActivity fragmentActivity;
	ArrayList<online> myfriends;
	LayoutInflater inflater=null;
	ArrayList<online> isunread;
		public CustomAdapter(FragmentActivity fragmentActivity, ArrayList<online> friends, ArrayList<online> isunread) {
			// TODO Auto-generated constructor stub
			this.fragmentActivity=fragmentActivity;
			this.myfriends=friends;
			this.isunread=isunread;
			inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myfriends.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi=convertView;
			ViewHolder holder;
			if(convertView==null)
			{
				vi=inflater.inflate(R.layout.row_friendlist, null);
				 holder = new ViewHolder();
				holder.duration = (TextView)vi.findViewById(R.id.name); // duration
				holder.iv=(ImageView)vi.findViewById(R.id.list_image);
				holder.counter=(TextView)vi.findViewById(R.id.counter);
				vi.setTag(holder);
		       
		      
			}
			else{
				holder=(ViewHolder)vi.getTag();
			}
			
			if(Integer.parseInt(isunread.get(position).name)>0)
			{
				holder.counter.setVisibility(View.VISIBLE);
				holder.counter.setText(isunread.get(position).name);
			}
				
				holder.duration.setText(d.get(position).name);
				
				 Picasso.with(getActivity()) //
			        .load(Utils.url+d.get(position).userpic) //
			        .placeholder(R.drawable.ic_loading) //
			        .error(R.drawable.cross) 
			        
			        .fit() //
			        .into(holder.iv);
			
			
			
			
			 return vi;
		}

		
	}

	static class ViewHolder
	{
		ImageView iv;
		TextView duration,counter;
		ImageView onlinetext;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, final int position, long arg3) {
		// TODO Auto-generated method stub
		
		
		new AsyncTask<Void, Void, Void>()
		{

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				new Common().UpdateMessage(ScommixSharedPref.getUSERID(), d.get(position).userid);
				return null;
			}
			
		}.execute();
		
		
		Intent chathead=new Intent(getActivity(),ChatWithFriend.class);
		chathead.putExtra("friendid", d.get(position).userid);
	
		chathead.putExtra("friends", d.get(position).name);
		startActivity(chathead);
		//overridePendingTransition(R.anim.anim_ltr, R.anim.anim_rtl);
		
	}
	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//	    // TODO Add your menu entries here
//	    super.onCreateOptionsMenu(menu, inflater);
//	    refresh = menu.add("Refresh friends");
//	  
//        refresh.setIcon(R.drawable.ic_action_refresh);
//        refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				// TODO Auto-generated method stub
//				new GetFriendsList().execute();
//				
//				return false;
//			}
//		});
//	}

}
