package com.scommix.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;






import com.scommix.WebServices.Common.Common;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;

public class NotificationAdapter extends BaseAdapter
{

	Context ctx;
	ArrayList<String> notificationidlist,notificationtext,notificationtiming,notificationtype,notificationuserid;
	LayoutInflater inflater;
	public NotificationAdapter(FragmentActivity activity,
			ArrayList<String> notificationidlist,
			ArrayList<String> notificationtext,
			ArrayList<String> notificationtiming,
			ArrayList<String> notificationtype,
			ArrayList<String> notificationuserid) {
		// TODO Auto-generated constructor stub
		this.ctx=activity;
		this.notificationidlist=notificationidlist;
		this.notificationtext=notificationtext;
		this.notificationtiming=notificationtiming;
		this.notificationtype=notificationtype;
		this.notificationuserid=notificationuserid;
		inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	private void update() {
		// TODO Auto-generated method stub
this.notifyDataSetChanged();
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notificationidlist.size();
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
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		View vi=convertView;
		ViewHolder holder;
		if(convertView==null)
		{
			vi=inflater.inflate(R.layout.notificationlistitems, null);
			holder = new ViewHolder();
			holder.notificationtext=(TextView)vi.findViewById(R.id.notificationtext);
			holder.notificationtiming=(TextView)vi.findViewById(R.id.notificationtime);
			
			
		
			vi.setTag(holder);
	    }
		else{
			holder=(ViewHolder)vi.getTag();
		}
		holder.notificationtext.setText(notificationtext.get(position));
		
		//set imageview 
		if (notificationtype.get(position).equals("1")) {
			
			ImageView notificationimage= (ImageView)vi.findViewById(R.id.notificationimage);
			notificationimage.setVisibility(View.VISIBLE);
			notificationimage.setImageResource(R.drawable.likeicon);
		}
		if (notificationtype.get(position).equals("2")) {
			
			ImageView notificationimage= (ImageView)vi.findViewById(R.id.notificationimage);
			notificationimage.setVisibility(View.VISIBLE);
			notificationimage.setImageResource(R.drawable.commenticon);
		}

		
		
		Log.i("Notifications", notificationtext.get(position));
		try {
			holder.notificationtiming.setText(parseDate(position));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		holder.deletenotification.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				
//				new AsyncTask<Void, Void, Void>()
//				{
//					ProgressDialog pd;
//					@Override
//					protected void onPreExecute() {
//						pd=new ProgressDialog(ctx);
//						pd.setMessage("Removing...");
//						pd.setCancelable(false);
//						pd.show();
//						notificationidlist.remove(position);
//						notificationtext.remove(position);;
//						notificationtiming.remove(position);;
//						notificationtype.remove(position);
//						notificationuserid.remove(position);
//						update();
//					};
//
//					@Override
//					protected Void doInBackground(Void... params) {
//						// TODO Auto-generated method stub
//						new Common().deletenotification(ScommixSharedPref.getUSERID(), notificationidlist.get(position));
//						return null;
//					}
//					@Override
//					protected void onPostExecute(Void result) {
//						
//						pd.dismiss();
//					};
//					
//				}.execute();
//			}
//		});
		
		
		 return vi;
		

	}
	
	public String parseDate(int position) throws ParseException
	{
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
	                                                    Locale.US); 
	    
	    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    long value=dateFormat.parse(notificationtiming.get(position)).getTime();  
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(String.valueOf(value)),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
	    return timeAgo.toString();
	}
	static class ViewHolder
	{
		ImageView deletenotification;
		TextView notificationtext;
		TextView notificationtiming;
	}
	
}
