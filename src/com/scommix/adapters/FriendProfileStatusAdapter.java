package com.scommix.adapters;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import org.kobjects.base64.Base64;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.online;
import com.scommix.asynctasks.LikeTask;
import com.scommix.homeandprofile.CommentBox;
import com.scommix.sharedpref.ScommixSharedPref;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;

public class FriendProfileStatusAdapter extends BaseAdapter
{
	
	/*
	 * This adapter is for the friend profile status items
	 * 
	 * includes timestamp
	 * 
	 * 
	 */

	FragmentActivity activity;

	ArrayList<Updates> friendupdates;
	ArrayList<online> friendlikecountlist;
	ArrayList<online> friendcommentcountlist;

	ArrayList<String> friendprofileliketag;
	
	public FriendProfileStatusAdapter(FragmentActivity activity,
			ArrayList<Updates> friendupdates,
			ArrayList<online> friendcommentcountlist,
			ArrayList<online> friendlikecountlist,
			ArrayList<String> friendprofileliketag
			) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.friendupdates=friendupdates;
		this.friendcommentcountlist=friendcommentcountlist;
		this.friendlikecountlist=friendlikecountlist;
		
		this.friendprofileliketag=friendprofileliketag;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friendupdates.size();
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
	
	
	
	void updateview(int position)
	{
		//increases the value to like count and  notifies the adapter
			int x= Integer.parseInt(friendlikecountlist.get(position).count)+Integer.parseInt("1") ;
			Log.i("Value", ""+x);
			String newlike=String.valueOf(x);
			friendlikecountlist.get(position).count=newlike;
		    Log.i("new value", friendlikecountlist.get(position).count);
		    this.notifyDataSetChanged();

	}

	void demoteview(int position)
	{
		// uper wale ka ulta :P
			 int x= Integer.parseInt(friendlikecountlist.get(position).count)-Integer.parseInt("1") ;
			  Log.i("Value", ""+x);
			String newlike=String.valueOf(x);
			friendlikecountlist.get(position).count=newlike;
		Log.i("new value", friendlikecountlist.get(position).count);
		this.notifyDataSetChanged();

	}
	
	void notifyme()
	{
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		
		if(convertView==null)
		{
			LayoutInflater inflaterr=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflaterr.inflate(R.layout.row_homelistitem,parent,false);
			 holder = new ViewHolder();
			holder.nametextview = (TextView)convertView.findViewById(R.id.homeitemnametext); // duration
			holder.profilepic=(ImageView)convertView.findViewById(R.id.homprofilepic);
			holder.statustextview=(TextView)convertView.findViewById(R.id.homestatustext);
			holder.statusidtextview=(TextView)convertView.findViewById(R.id.homeuseratatusidtext);
			holder.statustimetextview=(TextView)convertView.findViewById(R.id.homestatustime);
			holder.homeuseratatusidliketext=(TextView)convertView.findViewById(R.id.homeuseratatusidliketext);
			holder.comment=(Button)convertView.findViewById(R.id.commentpost);
			holder.star=(Button)convertView.findViewById(R.id.star);
			holder.delete=(ImageView)convertView.findViewById(R.id.deletepost);
			
			
			convertView.setTag(holder);
	     
	    
		}
		else
		{
			
			holder=(ViewHolder)convertView.getTag();
		}
		
		holder.delete.setVisibility(View.GONE);
	
		holder.nametextview.setText(friendupdates.get(position).name);
	    holder.comment.setVisibility(View.VISIBLE);
		holder.statustextview.setText(friendupdates.get(position).status);
		
		Linkify.addLinks(holder.statustextview, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);

		// Recognize all of the default link text patterns 
		Linkify.addLinks(holder.statustextview, Linkify.ALL);

		// Disable all default link detection
		Linkify.addLinks(holder.statustextview, 0);
		holder.statustextview.setLinkTextColor(Color.BLUE);
		
		
		
		
		holder.statusidtextview.setText(friendcommentcountlist.get(position).count+" Comments");
		holder.homeuseratatusidliketext.setText(friendlikecountlist.get(position).count+" likes");
		holder.statustimetextview.setText(friendupdates.get(position).statustime);
		
		
		try {
			holder.statustimetextview.setText(parseDate(position));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(friendprofileliketag.get(position).equals("Like"))
		{
	        
			holder.star.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0 );
			holder.star.setText(friendprofileliketag.get(position));
		}
		
		else
		{
			
	        holder.star.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liked, 0, 0, 0 );
	        holder.star.setText(friendprofileliketag.get(position));
		}
		
		holder.star.setText("Like");
		holder.star.setOnClickListener(mClickListener);
		holder.star.setTag(position);
		
		 Picasso.with(activity) //
	        .load(Utils.url+friendupdates.get(position).userpic) //
	        .placeholder(R.drawable.ic_loading) //
	        .error(R.drawable.cross) 
	         .transform(new RoundTrans())
	        .fit() //
	        .into(holder.profilepic);
	

	holder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("home tell", "tell listener");
				String status1,personname1,timing1,statusid1,userid;
				String profilepicbyte;
				status1=friendupdates.get(position).status;
				personname1=friendupdates.get(position).name;
				timing1=friendupdates.get(position).statustime;
				statusid1=friendupdates.get(position).userstatusid;
				timing1=friendupdates.get(position).statustime;
				profilepicbyte=friendupdates.get(position).userpic;
				
			
				userid=friendupdates.get(position).userid;
				
				
				Intent tocommentbox=new Intent(activity,CommentBox.class);
				tocommentbox.putExtra("nameofuser", personname1);
				tocommentbox.putExtra("status", status1);
				tocommentbox.putExtra("statustime", timing1);
				tocommentbox.putExtra("statusid", statusid1);
				tocommentbox.putExtra("profilepicbyte", profilepicbyte);
				tocommentbox.putExtra("userid", userid);
				tocommentbox.putExtra("position", ""+position);
				tocommentbox.putExtra("wherefrom", "friendprofile");
				tocommentbox.putExtra("successfulcomments", Integer.parseInt(friendcommentcountlist.get(position).count));
				activity.startActivity(tocommentbox);
					
				
			}
		});
		
		

	
		
		
		
		holder.delete.setOnClickListener(new OnClickListener() {
		
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			new AsyncTask<Void, Void, Void>()
			{
				@Override
				protected void onPreExecute() {
					Toast.makeText(activity, "Deleting...", Toast.LENGTH_SHORT).show();
				};
				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					new Common().DeleteStatus(ScommixSharedPref.getUSERID(), friendupdates.get(position).userstatusid);
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					Toast.makeText(activity, "Deleted!!!", Toast.LENGTH_SHORT).show();
					friendupdates.remove(position);
					friendcommentcountlist.remove(position);
					friendlikecountlist.remove(position);
					friendprofileliketag.remove(position);

					notifyme();
				};
				
			}.execute();
				
			}
		});
		
		 return convertView;
	}
	
	

	
	public String parseDate(int position) throws ParseException
	{
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
	                                                    Locale.US); 
	    
	    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    long value=dateFormat.parse(friendupdates.get(position).statustime).getTime();  
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(String.valueOf(value)),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
	    return timeAgo.toString();
	}

	




	 class ViewHolder{
		
		TextView nametextview,statustextview,statusidtextview,statustimetextview,homeuseratatusidliketext;
		Button star,comment;
		
		ImageView profilepic,delete;
	
	}
	
	 private OnClickListener mClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position = (Integer) v.getTag();
				if (friendprofileliketag.get(position).equals("Liked")) {
					   System.out.println(position);
					   friendprofileliketag.set(position, "Like");
		               
		                demoteview(position);
		                new LikeTask(activity,"Liked").execute(friendupdates.get(position).userstatusid);
		                
	            } else {
	             
	                
	                System.out.println(position);
	                friendprofileliketag.set(position, "Liked");
	  	          updateview(position);
	  	        new LikeTask(activity,"Like").execute(friendupdates.get(position).userstatusid);
	  	               
	            }
		
	

			
			}
			 
		 };

	
}
