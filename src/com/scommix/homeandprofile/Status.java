package com.scommix.homeandprofile;

import java.io.ByteArrayInputStream;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.IWsdl2CodeEvents;
import com.scommix.asynctasks.PostUpdateTask;

import com.scommix.firstrun.Newdemo;
import com.scommix.sharedpref.ScommixSharedPref;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.svimedu.scommix.ScommixContentProvider;
import com.svimedu.scommix.Welcome;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Status extends ActionBarActivity{
	
	 ContentResolver resolver;
     ContentValues cv;
     Cursor c;
     ProgressDialog pd;
	private Bitmap profilepicc;
	ImageView userpic;
	EditText statusedit;
TextView name;
Button post;
ActionBar ac;
ScommixSharedPref ppf;
private String profilepicname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		  ppf=ScommixSharedPref.getInstance(Status.this);
		 
		setContentView(R.layout.status);
		
		if(ppf.getUSERID().equals(""))
		{
			Intent i=new Intent(Status.this,Newdemo.class);
			startActivity(i);
			finish();
		}
		else{
			ac=getSupportActionBar();
			ac.hide();
			userpic=(ImageView)findViewById(R.id.statusimageview);
			statusedit=(EditText)findViewById(R.id.stausedit);
			Intent intent=getIntent();
			String action = intent.getAction();
		    String type = intent.getType();
		    
			 if (Intent.ACTION_SEND.equals(action) && type != null) {
			        if ("text/plain".equals(type)) {
			          statusedit.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
			        } 
			    }
			name=(TextView)findViewById(R.id.StatusNameofuser);
			post=(Button)findViewById(R.id.postupdatebutton);
			post.setOnClickListener(new OnClickListener() {
		
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					if(statusedit.getText().toString().equals(""))
					{
						statusedit.setHint("Write something...");
						statusedit.setHintTextColor(Color.RED);
					}
					else{
						pd=new ProgressDialog(Status.this);
							new AsyncTask<Void, Void, Void>()
							{
								
								@Override
								protected void onPreExecute() {
									// TODO Auto-generated method stub
									 
									pd.setCancelable(true);
									pd.setMessage("Posting Update...");
									pd.show();
									super.onPreExecute();
								}
								@Override
								protected Void doInBackground(Void... params) {
									// TODO Auto-generated method stub
								
									Common com=new Common();
									
									String status=statusedit.getText().toString();
											status=status.trim();
									com.InsertStatus(ScommixSharedPref.getUSERID(), status);
									return null;
								}
								
								@Override
								protected void onPostExecute(Void result) {
									// TODO Auto-generated method stub
									super.onPostExecute(result);
									pd.dismiss();
									Toast.makeText(getBaseContext(),"Status Updated",Toast.LENGTH_SHORT).show();
									finish();
								}
								
							}.execute();
							
							
						
						
						
					}

				
				}
			});
		
			 resolver=getApplicationContext().getContentResolver();
		
				cv=new ContentValues();
				String[] columns={
		        		ScommixContentProvider.NAME,
		        		ScommixContentProvider.PROFILEPIC
		        		
		        		};
				 c=Status.this.getContentResolver().query(ScommixContentProvider.CONTENT_URI_UserInfo, columns, null, null, null);

			        c.moveToFirst();
			        int columnindex5=c.getColumnIndexOrThrow(ScommixContentProvider.PROFILEPIC);
			        int columnindex1=c.getColumnIndexOrThrow(ScommixContentProvider.NAME);
			        name.setText(c.getString(columnindex1));
			      
			       profilepicname=c.getString(columnindex5);
			
			       
			        //edited after no use of database as the profilepicisnotcoming as it is
			        	
			       c.close();
			        	
			        	 Picasso.with(Status.this) //
					        .load(Utils.url+profilepicname) //
					        .placeholder(R.drawable.ic_loading) //
					        .error(R.drawable.cross) 
					       .transform(new RoundTrans())
					        .fit() //
					        .into(userpic);
		}
	
		        
		
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
	    




	


	
	
	

	
	

}
