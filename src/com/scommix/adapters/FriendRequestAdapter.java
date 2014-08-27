package com.scommix.adapters;

import java.util.ArrayList;

import org.kobjects.base64.Base64;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.drive.internal.ac;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.friendsandsearch.FriendActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;

public class FriendRequestAdapter extends BaseAdapter
{
	LayoutInflater inflater =null;
	FragmentActivity activity;
	ArrayList<online> requestarraylist;

	public FriendRequestAdapter(FragmentActivity activity,
			ArrayList<online> requestarraylist) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.requestarraylist=requestarraylist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return requestarraylist.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		
		if(convertView==null)
		{
			inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.friendrequestlistitems, parent,false);
			holder = new ViewHolder();
			holder.accept=(ImageView)convertView.findViewById(R.id.acceptfriendrequest);
			holder.delete=(ImageView)convertView.findViewById(R.id.deletefriendrequest);
			holder.iv=(ImageView)convertView.findViewById(R.id.friendrequestprofilepic);
			holder.friendrequestusername=(TextView)convertView.findViewById(R.id.friendrequestpersonname);
			convertView.setTag(holder);
	    }
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
		
		 Picasso.with(activity) //
	        .load(Utils.url+requestarraylist.get(position).userpic) //
	        .placeholder(R.drawable.ic_loading) //
	        .error(R.drawable.cross) 
	         .transform(new RoundTrans())
	        .fit() //
	        .into(holder.iv);
		
		holder.friendrequestusername.setText(requestarraylist.get(position).name);
		
	
		
		holder.friendrequestusername.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
	            Intent i=new Intent(activity,FriendActivity.class);
				i.putExtra("id", requestarraylist.get(position).userid);
				activity.startActivity(i);
			}
		});
		
		
		holder.accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				new AsyncTask<Void, Void, Void>() {
					
					ProgressDialog pd;
					
					@Override
					protected void onPreExecute() {
						pd=new ProgressDialog(activity);
						pd.setMessage("Waiting for Response...");
						pd.setCancelable(false);
						pd.show();
					};
					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						System.out.println("id again "+requestarraylist.get(position).userid);
						new Common().ConfrimFriendRequest(ScommixSharedPref.getUSERID(), requestarraylist.get(position).userid);
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						Toast.makeText(activity, "Added..", Toast.LENGTH_SHORT).show();
						pd.dismiss();
						requestarraylist.remove(position);
						doit();
					};
				}.execute();
		
			}
		});
		
		
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {
					
					ProgressDialog pd;
					
					@Override
					protected void onPreExecute() {
						
						pd=new ProgressDialog(activity);
						pd.setMessage("Waiting for Response...");
						pd.setCancelable(false);
						pd.show();
					};

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						new Common().DeleteFriend(ScommixSharedPref.getUSERID(), requestarraylist.get(position).userid);
						return null;
					}
					
					@Override
					protected void onPostExecute(Void result) {
						Toast.makeText(activity, "Deleted..", Toast.LENGTH_SHORT).show();
						requestarraylist.remove(position);
						doit();
						pd.dismiss();
						
					};
				}.execute();
				
			}
		});
		 return convertView;
		

	}
	
	
	
	

	void doit()

	{
		this.notifyDataSetChanged();
	}
	
	static class ViewHolder
	{
		TextView friendrequestusername;
		ImageView accept,delete;
		ImageView iv;
	}
}
