package com.scommix.navigationmainactivity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;

import com.scommix.friendsandsearch.FriendActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.svimedu.scommix.ScommixContentProvider;
import com.scommix.tools.Utils;

import android.app.Activity;
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
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsListFragment extends Fragment{
	
	PullToRefreshGridView friendslist;
	 GridView mGridView;
	

    CustomAdapter adapter;
    View rootView;
	ContentResolver resolver;
    ContentValues cv;
    Cursor cursor;
    ArrayList<online> d;
    MenuItem refresh;

    GetFriendsList frns;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView=inflater.inflate(R.layout.friendslistfragmentlayout, container,false);
		
        friendslist=(PullToRefreshGridView)rootView.findViewById(R.id.friendslistsimple);
        
        mGridView=friendslist.getRefreshableView();
        frns=new GetFriendsList();
        
		frns.execute();
        LayoutInflater inflate=getActivity().getLayoutInflater();
		View emptylayout=inflate.inflate(R.layout.view_empty, null);
		mGridView.setEmptyView(emptylayout);
		Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
		tryagain.setText("Seems Empty!");
		
        cv=new ContentValues();
        resolver=getActivity().getApplicationContext().getContentResolver();
	
		d=new ArrayList<online>();
	
		friendslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),FriendActivity.class);
				i.putExtra("id", d.get(position).userid);
			
				startActivity(i);
			}
		});
		
		friendslist.setOnRefreshListener(new OnRefreshListener<GridView>() {

			

		

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
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
		
		
			
		       
		       
		
		
	 
		return rootView;
	}
	

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
			Common c=new Common();
				
			try{
				d=c.GetFriendList(ScommixSharedPref.getUSERID());
				
				
			
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
							friendslist.onRefreshComplete();
							if(d.size()!=0)
							{
								adapter=new CustomAdapter(FriendsListFragment.this,d);
								mGridView.setAdapter(adapter);
								
							}
							else
							{
								
								Toast.makeText(getActivity(), "No Friends..", Toast.LENGTH_SHORT).show();
								if(d!=null)
								{
									d.clear();
									adapter.notifyDataSetChanged();
								}
							
							}
						}
					});
			}
			catch(Exception e)
			{
				friendslist.onRefreshComplete();
				e.printStackTrace();
			}
			
    
			
			
			
		}
		
	}
	class CustomAdapter extends BaseAdapter
	{
	FriendsListFragment ctx;
	
	LayoutInflater inflater=null;
	ArrayList<online> d;
		public CustomAdapter(FriendsListFragment friends, ArrayList<online> d) {
			// TODO Auto-generated constructor stub
			this.ctx=friends;
		  
		     this.d=d;
			inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return d.size();
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
				vi.setTag(holder);
		       
		      
			}
			
			else
			{
				holder=(ViewHolder)vi.getTag();
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
		private Bitmap giveBitmap(int position) {
			// TODO Auto-generated method stub
			 byte[] profilebytearray=  Base64.decode(d.get(position).userprofileimage);
			 Bitmap bmp=BitmapFactory.decodeByteArray(profilebytearray, 0, profilebytearray.length);
		
			 bmp=Utils.getRoundedBitmap(bmp);
			return bmp;
		}
		
	}
	
	static class ViewHolder
	{
		ImageView iv;
		TextView duration;
		TextView institutenametextview;
	}
	
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    // TODO Add your menu entries here
	    super.onCreateOptionsMenu(menu, inflater);
	    refresh = menu.add("Refresh friends");
        refresh.setIcon(R.drawable.ic_action_refresh);
        refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				new GetFriendsList().execute();
				
				return false;
			}
		});
	}


	
	
}
