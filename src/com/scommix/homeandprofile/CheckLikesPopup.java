package com.scommix.homeandprofile;

import java.util.ArrayList;

import org.kobjects.base64.Base64;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.scommix.tools.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckLikesPopup extends ActionBarActivity {
	
	ListView likeslist;
	String userstatusid;
	ArrayList<online> peoplelist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checklikespopup);
		likeslist=(ListView)findViewById(R.id.likelist);
		peoplelist=new ArrayList<online>();
		userstatusid=getIntent().getStringExtra("userstatusid");
		
		new AsyncTask<Void, Void, Void>() {
			ProgressDialog pd;
			@Override
			protected void onPreExecute() {
				pd=new ProgressDialog(CheckLikesPopup.this);
				pd.setCancelable(true);
				pd.setMessage("Checking..");
				pd.show();
				
			};

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
			peoplelist=	new Common().GetStatusLikeName(userstatusid);
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				pd.dismiss();
				if(peoplelist!=null)
				{
					if(peoplelist.size()==0)
					{
						Toast.makeText(CheckLikesPopup.this, "No Likes", Toast.LENGTH_SHORT).show();
						finish();
					}
					else
						
					{
						getSupportActionBar().setTitle("People who like this");
						likeslist.setAdapter(new CustomAdapter());
					}
				}
				else{
					finish();
					Toast.makeText(CheckLikesPopup.this, "No Likes", Toast.LENGTH_SHORT).show();

				}
			
				
			};
		}.execute();
	}
	
	  @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	    	// TODO Auto-generated method stub
	    	super.onConfigurationChanged(newConfig);
	    	
	    	if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
	        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


	    	}
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {     
	    	 	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


	    	} 
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
	        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


	    	} 
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
	    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    	}
	    	else {
	    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    	}
	    }
	    
	
	class CustomAdapter extends BaseAdapter
	{
		
		public CustomAdapter()

		{
			
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return peoplelist.size();
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
			ViewHolder holder;
			if(convertView==null)
			{
				holder=new ViewHolder();
				LayoutInflater inflater=getLayoutInflater();
				convertView=inflater.inflate(R.layout.row_friendlist,null);
				holder.profilepic=(ImageView)convertView.findViewById(R.id.list_image);
				holder.friendname=(TextView)convertView.findViewById(R.id.name);

				convertView.setTag(holder);
			}
			else{
				holder=(ViewHolder)convertView.getTag();
			}
			
			 Picasso.with(CheckLikesPopup.this) //
		        .load(Utils.url+peoplelist.get(position).userpic) //
		        .placeholder(R.drawable.ic_loading) //
		        .error(R.drawable.cross) 
		        
		        .fit() //
		        .into(holder.profilepic);
		
			
			holder.friendname.setText(peoplelist.get(position).name);
			
			return convertView;
		}
	
		
		class ViewHolder{
			ImageView profilepic;
			TextView friendname;
		}
		
	}

}
