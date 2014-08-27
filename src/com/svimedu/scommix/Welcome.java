package com.svimedu.scommix;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.scommix.asynctasks.ClearLogoutTask;
import com.scommix.firstrun.Newdemo;
import com.scommix.homeandprofile.MyProfileFragment;

import com.scommix.navigationmainactivity.MainActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.GenerateNotification;
import com.scommix.tools.Utils;
import com.scommix.viewpager.Main;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends Activity {

	Typeface type;
	TextView tv;
	Handler hand;
	ScommixSharedPref pref;
    ActionBar action;
    ProgressBar loginstatus;
    ImageView profilepic;
    
    ImageView profilepic1;
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
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 if (Build.VERSION.SDK_INT >= 19) {
				getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		
			 requestWindowFeature(Window.FEATURE_NO_TITLE);
		        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 }
		 else{
			 requestWindowFeature(Window.FEATURE_NO_TITLE);
		        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 }
		 
		 
			pref=ScommixSharedPref.getInstance(getApplicationContext());
			pref=ScommixSharedPref.getInstance(this);

		setContentView(R.layout.welcome);
		
		profilepic=(ImageView)findViewById(R.id.inslogo);
		profilepic1=(ImageView)findViewById(R.id.background);
		
		
		loginstatus=(ProgressBar)findViewById(R.id.welcomeprogress);
		tv=(TextView)findViewById(R.id.scommixtext);
	
	    hand=new Handler();
	
		
		
		type=Typeface.createFromAsset(getAssets(), "bahamal.ttf");
		
		tv.setTypeface(type);
		if(ScommixSharedPref.getFirstRun()==true)
		{
			
			Runnable r=new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				       Intent i=new Intent(Welcome.this,Newdemo.class);
	    				startActivity(i);
	    				finish();
				
				}
			};
			hand.post(r);
		}
		else{
			loginstatus.setVisibility(View.VISIBLE);
			profilepic.setVisibility(View.VISIBLE);
			
			 Picasso.with(this) //
		     .load(Utils.url+ScommixSharedPref.getCOVERPICNAME())
		        
		        .placeholder(R.drawable.ic_loading) //
		        
		        .fit() //
		        .into(profilepic);
			 
			 Picasso.with(this) //					
		     .load(Utils.url1+ScommixSharedPref.getINSTITUTEBACKGROUND())
		        
		        .placeholder(R.drawable.ic_loading) //
		        
		        .fit() //
		        .into(profilepic1);
			 
			new Handler().post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			});
			 new AsyncTask<Void, Void, Void>()
			    {
			    	
			   	
				 String temp,useridd,usertypee;
				 byte[] b;
				 final String SOAP_ACTION = "http://tempuri.org/HelloWorld";
				    final String METHOD_NAME = "HelloWorld";
				    final String NAMESPACE =
				             "http://tempuri.org/";
				    final String URL =
				             "http://scommix.cloudapp.net/loginservice1.asmx";
				 boolean loginsuccessful=true;
					
				    

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						

						// TODO Auto-generated method stub
						 
					    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 	
						PropertyInfo pi1=new PropertyInfo();
					    PropertyInfo pi2=new PropertyInfo();
					
					    pi1.setName("email");
					    pi2.setName("pwd");
					   
					    	pi1.setValue(ScommixSharedPref.getEMAILID());
					   
				
					    
					
					   pi2.setName("pwd");
					 
						   pi2.setValue(ScommixSharedPref.getPASSWORD());
					   
					 
					   
					
					   
					 request.addProperty(pi1);
					 request.addProperty(pi2);

					    
						SoapSerializationEnvelope envelope = 
							new SoapSerializationEnvelope(SoapEnvelope.VER11); 
						 envelope.dotNet = true;

						envelope.setOutputSoapObject(request);
						
						
						HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

						try {
							androidHttpTransport.call(SOAP_ACTION, envelope);
							   SoapObject results =(SoapObject)envelope.getResponse();
							   
							   Object usertype=(Object)results.getProperty(0);
							   Object userid=(Object)results.getProperty(1);
							  
							   //to get the data String resultData=result.getProperty(0).toString();
							 
							   useridd=userid.toString();
							   usertypee=usertype.toString();
							
							  
							  
						} catch (Exception e) {
							e.printStackTrace();
							loginsuccessful=false;
				
						}
						return null;
					
					
					}
					
					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
					//Log.i("the response", temp);
						
							int type = 0;
							if(loginsuccessful==false)
							{
								finish();
								GenerateNotification.generateNotification(Welcome.this, "Please try after some time..");
							}
							
								if(usertypee!=null)
								{
									type=Integer.parseInt(usertypee);
									if(type==3)
									{
										//Intent i=new Intent(Welcome.this,MainActivity.class);
										Intent i=new Intent(Welcome.this,Main.class);

										startActivity(i);
									
										finish();
									}
									else
									{
									  new ClearLogoutTask(getApplicationContext()).execute();
										GenerateNotification.generateNotification(Welcome.this, "Session Expired...");
									}
								}
								
								
							
						
						
					
				
						
					}
			    	
			    }.execute();
			
		}
		

		
		
	}



	
}
