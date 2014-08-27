package com.scommix.adapters;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import org.kobjects.base64.Base64;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;



import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.online;
import com.scommix.asynctasks.LikeTask;
import com.scommix.homeandprofile.CommentBox;
import com.scommix.homeandprofile.HomeFragment;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;


public class HomeAdapter extends BaseAdapter 
{
	
     FragmentActivity ctx;
  
     LayoutInflater inflater=null;
     ArrayList<online> commentcountlist;
     ArrayList<Updates> allupdates;
     ArrayList<online> likecountlist;
     GridView mGridView ;
     ArrayList<online> liketag;

     
	public HomeAdapter(FragmentActivity ctx2, ArrayList<Updates> allupdates, ArrayList<online> commentcountlist,ArrayList<online> likecountlist, GridView mGridView, ArrayList<online> liketag
			) 
	{
		// TODO Auto-generated constructor stub
		this.ctx=ctx2;
		this.allupdates=allupdates;
		

		this.commentcountlist=commentcountlist;
		this.likecountlist=likecountlist;
		this.mGridView=mGridView;
		inflater=LayoutInflater.from(ctx);
		this.liketag=liketag;

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allupdates.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
void updateview(int position)
{
	
		int x= Integer.parseInt(likecountlist.get(position).count)+Integer.parseInt("1") ;
		Log.i("Value", ""+x);
		String newlike=String.valueOf(x);
		likecountlist.get(position).count=newlike;
	    Log.i("new value", likecountlist.get(position).count);
	    this.notifyDataSetChanged();

}



void demoteview(int position)
{
	
		 int x= Integer.parseInt(likecountlist.get(position).count)-Integer.parseInt("1") ;
		  Log.i("Value", ""+x);
		String newlike=String.valueOf(x);
		likecountlist.get(position).count=newlike;
	Log.i("new value", likecountlist.get(position).count);
	this.notifyDataSetChanged();

}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	
	


	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		  ViewHolder holder;
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.row_homelistitem,parent, false);
			holder = new ViewHolder();	
			
			holder.nametextview = (TextView)convertView.findViewById(R.id.homeitemnametext); // duration
			holder.profilepic=(ImageView)convertView.findViewById(R.id.homprofilepic);
			holder.statustextview=(TextView)convertView.findViewById(R.id.homestatustext);
			holder.statusidtextview=(TextView)convertView.findViewById(R.id.homeuseratatusidtext);
			holder.statustimetextview=(TextView)convertView.findViewById(R.id.homestatustime);
			holder.homeuseratatusidliketext=(TextView)convertView.findViewById(R.id.homeuseratatusidliketext);
			
			holder.tell=(Button)convertView.findViewById(R.id.commentpost);
		    holder.star=(Button)convertView.findViewById(R.id.star);
		    
			
		    convertView.setTag(holder);
	       
	      
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
		
		
		holder.statustextview.setAutoLinkMask(0); 
	
		holder.nametextview.setText(allupdates.get(position).name);
		holder.statustextview.setText(allupdates.get(position).status);
		Linkify.addLinks(holder.statustextview, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);

		// Recognize all of the default link text patterns 
		Linkify.addLinks(holder.statustextview, Linkify.ALL);

		// Disable all default link detection
		Linkify.addLinks(holder.statustextview, 0);
		holder.statustextview.setLinkTextColor(Color.BLUE);
		holder.statusidtextview.setText(commentcountlist.get(position).count+" Comments");
		holder.homeuseratatusidliketext.setText(likecountlist.get(position).count+" likes");
		
		try 
		{
		   holder.statustimetextview.setText(parseDate(position));
		} 
		
		catch (ParseException e)
		
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(liketag.get(position).getLiketag().equals("Like"))
		{
	        holder.star.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0 );
	       holder.star.setText(liketag.get(position).getLiketag());
		}
		else{
	        holder.star.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liked, 0, 0, 0 );
	        holder.star.setText(liketag.get(position).getLiketag());
		}


		
			 Picasso.with(ctx) //
		        .load(Utils.url+allupdates.get(position).userpic) //
		        .placeholder(R.drawable.ic_loading) //
		        .error(R.drawable.cross) //
		         .transform(new RoundTrans())
		        .fit() //
		        .into(holder.profilepic);
		
        holder.tell.setText("Comment");
	
		holder.star.setOnClickListener(mClickListener);
		holder.star.setTag(position);
		
		
		holder.tell.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("home tell", "tell listener");
				String status1,personname1,timing1,statusid1,userid;
				String profilepicbyte;
				status1=allupdates.get(position).status;
				personname1=allupdates.get(position).name;
				timing1=allupdates.get(position).statustime;
				statusid1=allupdates.get(position).userstatusid;
				timing1=allupdates.get(position).statustime;
				profilepicbyte=allupdates.get(position).userpic;
                userid=allupdates.get(position).userid;
				
				Intent tocommentbox=new Intent(ctx,CommentBox.class);
				tocommentbox.putExtra("nameofuser", personname1);
				tocommentbox.putExtra("status", status1);
				tocommentbox.putExtra("statustime", timing1);
				tocommentbox.putExtra("statusid", statusid1);
				tocommentbox.putExtra("profilepicbyte", profilepicbyte);
				tocommentbox.putExtra("userid", userid);
				tocommentbox.putExtra("position", ""+position);
				tocommentbox.putExtra("wherefrom", "myhome");
				System.out.println(Integer.parseInt(commentcountlist.get(position).count));
				tocommentbox.putExtra("successfulcomments", Integer.parseInt(commentcountlist.get(position).count));
			    ctx.startActivity(tocommentbox);
				
			}
		});
		
		
		 return convertView;
		 
	}
	
	public String parseDate(int position)
		    throws ParseException
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
		                                                    Locale.getDefault()); 
		    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		    long value=dateFormat.parse(allupdates.get(position).statustime).getTime();  
			CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
					Long.parseLong(String.valueOf(value)),
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
			
		    return timeAgo.toString();
		}
	
	
	 

	private OnClickListener mClickListener = new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position = (Integer) v.getTag();
				if (liketag.get(position).getLiketag().equals("Liked")) {
					   System.out.println(position);
					   
						online o=new online();
						o.setLiketag("Like");
						liketag.add(o);
		                liketag.set(position, o);
		               
		                demoteview(position);
		                
		                new LikeTask(ctx,"Liked").execute(allupdates.get(position).userstatusid);
		                
	            } else {
	             
	                
	                System.out.println(position);
	                
	            	online o=new online();
					o.setLiketag("Liked");
					liketag.add(o);
	  	          liketag.set(position, o);
	  	          updateview(position);
	  	          
	  	        new LikeTask(ctx,"Like").execute(allupdates.get(position).userstatusid);
	  	               
	            }
		
	

			
			}
			 
		 };
		
	static class ViewHolder{
		TextView nametextview,statusidtextview,statustimetextview,homeuseratatusidliketext;
		TextView statustextview;
		Button star,tell;
		ImageView profilepic;
	}


	
}