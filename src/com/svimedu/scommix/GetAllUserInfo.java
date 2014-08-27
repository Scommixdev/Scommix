package com.svimedu.scommix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.asynctasks.ClearLogoutTask;
import com.scommix.navigationmainactivity.MainActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.GenerateNotification;
import com.scommix.tools.OSUtil;
import com.scommix.tools.Utils;
import com.scommix.viewpager.Main;
import com.svimedu.scommix.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class GetAllUserInfo extends Activity {
	
			int resulta=1;
		     Handler hand;
	int cc;
	SQLiteDatabase mDatabase;
      ContentResolver resolver;
      ContentValues cv;
     ProgressBar pg;
        String regid;
        ScommixSharedPref pref;
	    String TAG="SCOMMIX";
         TextView tv;
         ArrayList<online> responceinstitute;
         private ArrayList<online> responsebackgroundinstuitue;
         
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
		setContentView(R.layout.getuserinfolayout);
		pref=ScommixSharedPref.getInstance(getApplicationContext());
		pref=ScommixSharedPref.getInstance(this);
		hand=new Handler();
        pg=(ProgressBar)findViewById(R.id.progressbargetuserinfoo);
        resolver=getContentResolver();
		cv=new ContentValues();
		tv=(TextView)findViewById(R.id.scommixtext);
		Typeface type = Typeface.createFromAsset(getAssets(), "bahamal.ttf");
		tv.setTypeface(type);
		
		if(OSUtil.IsNetworkAvailable(GetAllUserInfo.this)==true)
		{
			 new GetUserDetail().execute();
		}
		else
		{
			Toast.makeText(GetAllUserInfo.this, "Check Your Internet!", Toast.LENGTH_SHORT).show();
			System.exit(0);
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
	    
	
	@Override
	protected void onDestroy() 
	
	{
	// TODO Auto-generated method stub
	super.onDestroy();

	}
	
	@Override
	protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	
	}

	

	
	

	    
	
		
		 
	
		   
			class GetUserDetail extends AsyncTask<Void, Void, Void>
			{
				
				 SoapObject results;
				
		
				@Override
				protected void onPreExecute() {
					pg.setVisibility(View.VISIBLE);
			
				
				
				
				};
				
				@Override
				protected Void doInBackground(Void... params) {
					
					try{
						
					Common c=new Common();
					ArrayList<online> responce=new ArrayList<online>();
					responce=c.GetUserDetailnew(ScommixSharedPref.getUSERID());
					
				
					
					
					
					 cv.put("PROFILEPIC", responce.get(0).userpic);
					 cv.put("USERID", ScommixSharedPref.getUSERID());
						 
						 if( responce.get(0).username==null)
						 {
							 cv.put("USERNAME","N/A");
						 }
						 else{
							 cv.put(ScommixContentProvider.USERNAME, responce.get(0).username);
						 }
						
						  cv.put("USERTYPE", ScommixSharedPref.getUSERTYPE());
						    
						    if(responce.get(0).email==null)
						    {
						      	cv.put("EMAIL", "N/A");
						    }
						    else{
						    	cv.put("EMAIL", responce.get(0).email);

						    }
						 
						      cv.put("PASSWORD", ScommixSharedPref.getPASSWORD());

						
						    	if(responce.get(0).gender==null)
						    	{
							    	cv.put("GENDER", "N/A");

						    	}
						    	else{
							    	cv.put("GENDER", responce.get(0).gender);

						    	}
						
						    	if(responce.get(0).dob==null)
						    	{
						    	    cv.put("DATEOFBIRTH","N/A");
						    	}
						    	else{
								    cv.put("DATEOFBIRTH",responce.get(0).dob);

						    	}
						 
						    	if(responce.get(0).fathername==null)
						    	{
								    cv.put("FATHERNAME", "N/A");

						    	}
						    	else{
								    cv.put("FATHERNAME", responce.get(0).fathername);

						    	}
						 
						    	
						
						    if(responce.get(0).mothername!=null){
						    	cv.put("MOTHERNAME", responce.get(0).mothername);

						    }
						    else
						    {
						    	cv.put("MOTHERNAME","N/A");

						    }
						
						    if(responce.get(0).mobileno!=null){
								cv.put("MOBILENO", responce.get(0).mobileno);

						    }
						   else{
								cv.put("MOBILENO", "N/A");

						    }
					
						    if(responce.get(0).landlineno!=null){
						    	cv.put("LANDLINE", responce.get(0).landlineno);
						    }
						    else
						    {
						    	cv.put("LANDLINE","N/A");
						    }
						    
						    if(responce.get(0).siblings!=null){
								cv.put("SIBLINGS", responce.get(0).siblings);

						    }
						    else
						    {
								cv.put("SIBLINGS", "N/A");

						    }
						    
						    if( responce.get(0).numberofsiblings!=null){
								cv.put("SIBLINGSNO", responce.get(0).numberofsiblings);

						    }
						    else
						    {
								cv.put("SIBLINGSNO", "N/A");

						    }
						   if(responce.get(0)._classField!=null){
								cv.put("_CLASS",responce.get(0)._classField);

						    }
						   else
						    {
								cv.put("_CLASS","N/A");

						    }
							
							
							
							
								if(responce.get(0).fatheroccupation==null)
								{
									cv.put("FATHERSOCCUPATION", "N/A");

								}
								else{
									cv.put("FATHERSOCCUPATION", responce.get(0).fatheroccupation);

								}
								
							
							
						
								if(responce.get(0).motheroccupation==null)
								{
									cv.put("MOTHERSOCCUPATION", "N/A");

								}
								else{
									cv.put("MOTHERSOCCUPATION", responce.get(0).motheroccupation);

								}

								
							
						
							
                            if(responce.get(0).countryname!=null)
                            {
								cv.put("COUNTRY", responce.get(0).countryname);
							}
                            else
							{
								cv.put("COUNTRY", "N/A");
							}
							
						
								
								String fm = " ",lm = " ";
								if(responce.get(0).firstname!=null)
								{
									fm=responce.get(0).firstname;
								}
								else{
									fm=" ";
								}
								
								if(responce.get(0).lastname==null)
									{
										lm=" ";
									}
									else{
										lm=responce.get(0).lastname;
									}
									

							
					
						
								
									cv.put("NAME", fm+" "+lm);

								
								
							if(responce.get(0).addressline1!=null){
								cv.put("ADDRESS1", responce.get(0).addressline1);

							}
							else
							{
								cv.put("ADDRESS1", "N/A");

							}
							if(responce.get(0).addressline2!=null){
								cv.put("ADDRESS2", responce.get(0).addressline2);

							}
							else
							{
								cv.put("ADDRESS2", "N/A");
							}
							
							
							if(responce.get(0).state!=null)
							{
								cv.put("STATE", responce.get(0).state);

							}
							else
							{
								cv.put("STATE", "N/A");

							}
							
							
							if(responce.get(0).city!=null){
								
								cv.put("CITY", responce.get(0).city);

							}
							else
							{
								cv.put("CITY", "N/A");

							}
							
							
						   if(responce.get(0).c_addressline1!=null){
							    cv.put("CADDRESS1", responce.get(0).c_addressline1);

							}
						   else
							{
							    cv.put("CADDRESS1", "N/A");

							}
							if(responce.get(0).c_addressline2!=null){
								cv.put("CADDRESS2", responce.get(0).c_addressline2);

							}
							else
							{
								cv.put("CADDRESS2", "N/A");

							}
							try{
								ScommixSharedPref.setINSTITUTEID(responce.get(0).instituteid);
								
								
								
								responceinstitute=new ArrayList<online>();
								responceinstitute=c.GetLogoImageByInstituteId(ScommixSharedPref.getINSTITUTEID());
								
								
								
								cv.put(ScommixContentProvider.INSTITUTEPIC, responceinstitute.get(0).userprofileimage);

								
								
							Log.i("name", responceinstitute.get(0).userprofileimage);
								
								
								
								
								ScommixSharedPref.setCOVERPICNAME(responceinstitute.get(0).userprofileimage);
								
								
								
								
								
								
								
							
							}
							
							catch(Exception e)
							{
								
								cv.put(ScommixContentProvider.INSTITUTEPIC, "noimage.jpg");

				
							
								ScommixSharedPref.setCOVERPICNAME("noimage.jpg");
								
							
							}
							
							
							try {
								

								responsebackgroundinstuitue=new ArrayList<online>();
								responsebackgroundinstuitue=c.GetBackgroundImageByInstituteId(ScommixSharedPref.getINSTITUTEID());
								
								
								

								ScommixSharedPref.setINSTITUTEBACKGROUND(responsebackgroundinstuitue.get(0).userprofileimage);
								
								
								
							} catch (Exception e) {
								// TODO: handle exception
								
								

								ScommixSharedPref.setINSTITUTEBACKGROUND("noimage.jpg");
								
								
							}
							
							
						  
						
							 try{
								 
								 ScommixSharedPref.setCLASSID(responce.get(0)._classField);
								 System.out.println(responce.get(0)._classField);
								}
								catch(Exception e)
								{
									 ScommixSharedPref.setCLASSID(" ");

								}
							 
							   try{
								   
								   ScommixSharedPref.setSVIMEDUID(responce.get(0).svimclassidField);
								   System.out.println(responce.get(0).svimclassidField);
								}
								catch(Exception e)
								{
									   ScommixSharedPref.setSVIMEDUID(" ");

								}
						 
					
					
					
					 resolver.insert(ScommixContentProvider.CONTENT_URI_UserInfo, cv);  
				System.out.println(""+	cv.size());
					}
					catch(Exception e)
					{
						e.printStackTrace();
						resulta=0;
					}
				
					return null;
		
				}
				
				
				
				
				@Override
				protected void onPostExecute(Void result) {
				
				if(resulta==0)
				{
					// getContentResolver().delete(ScommixContentProvider.CONTENT_URI_UserInfo, null, null);
				
					 new ClearLogoutTask(getApplicationContext()).execute();
					finish();
    				GenerateNotification.generateNotification(GetAllUserInfo.this, "Please Try again!");

				}
				else{
					Intent i=new Intent(GetAllUserInfo.this,Main.class);
    				startActivity(i);
    				//generateNotification(GetAllUserInfo.this, "Welcome to Scommix");
    				finish();
				}
				
			}
                       
			
			}
			
		
			
			
			}

	
	
	


