package com.svimedu.scommix;

import java.io.UnsupportedEncodingException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scommix.animation.Techniques;
import com.scommix.animation.YoYo;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;
import com.scommix.tools.OSUtil;





public class LoginFirstRun extends Activity implements OnClickListener {

	 public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
	  ScommixSharedPref pref;
      private EditText username,password;
     ActionBar action;
       TextView forgotpassword;
       Button loginbutton;


	@Override
	public void onCreate(Bundle savedInstanceState) {
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
	
		
		 setContentView(R.layout.activity_to_do);
	

			username = (EditText) findViewById(R.id.username);
			password=(EditText)findViewById(R.id.passwordconfirm);
			
			loginbutton=(Button)findViewById(R.id.registerbutton);
			
		
			  loginbutton.setOnClickListener(this);
		
		
		
		
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
	    



//	private void setOrientation() {
//		// TODO Auto-generated method stub
//		if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) 
//		{     
//
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//		}
//		else if((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
//		{
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		}
//		else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
//
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//		} 
//		else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
//
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		}
//		else {
//
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		}
//	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("username", username.getText().toString());
		outState.putString("password", password.getText().toString());
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		String usernamee=savedInstanceState.getString("username");
		String Password=savedInstanceState.getString("password");
		username.setText(usernamee);
		password.setText(Password);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.profilepicmenu, menu);
		return true;
	}
	
	
	
	/**
	 * Select an option from the menu
	 */





	class MyTask extends AsyncTask<Void, Void, Void>
	{
		 ProgressDialog pd;
		 String temp,useridd,usertypee;
		 byte[] b;
		 final String SOAP_ACTION = "http://tempuri.org/HelloWorld";
		    final String METHOD_NAME = "HelloWorld";
		    final String NAMESPACE =
		             "http://tempuri.org/";
		    final String URL =
		             "http://scommix.cloudapp.net/loginservice1.asmx";
		public MyTask()
		{
			pd=new ProgressDialog(LoginFirstRun.this);
			pd.setCancelable(false);
			pd.setMessage("Signing in!");
		
		}
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	pd.show();
}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
		
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 	
			PropertyInfo pi1=new PropertyInfo();
		    PropertyInfo pi2=new PropertyInfo();
		
		    pi1.setName("email");
		    pi2.setName("pwd");
		    if(ScommixSharedPref.getisRegistered()==true)
		    {
		    	pi1.setValue(ScommixSharedPref.getEMAILID());
		    }
		    else{
		    pi1.setValue(username.getText().toString());
		    ScommixSharedPref.setEMAILID(username.getText().toString());
		    }
		
		   pi2.setName("pwd");
		   
		   if(ScommixSharedPref.getisRegistered()==true)
		   {
			   pi2.setValue(ScommixSharedPref.getPASSWORD());
		   }
		   else{
			   String userenteredpass=password.getText().toString();
			 
			   try {
				b = userenteredpass.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			   String encoded = new String(Base64.encode(b,Base64.DEFAULT));
			   pi2.setValue(encoded);
			   ScommixSharedPref.setPASSWORD(encoded);
		   }
		   
		
		   
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
			}
			return null;
		}
		@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
				//Log.i("the response", temp);
					int type = 0;
					pd.dismiss();
					if(usertypee!=null)
					{
						type=Integer.parseInt(usertypee);
					}
					
					switch(type)
					{
					case 0:
					{
						Toast t=Toast.makeText(getApplicationContext(), 
								"Something Wrong!", Toast.LENGTH_SHORT); 
						t.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
						t.show();
						Vibrator vir=(Vibrator)LoginFirstRun.this.getSystemService(Context.VIBRATOR_SERVICE);
						vir.vibrate(200);
						YoYo.with(Techniques.shakeanimator).duration(1500).playOn(username);
						YoYo.with(Techniques.shakeanimator).duration(1500).playOn(password);

					}
					break;
					case 1:
					{
						Toast.makeText(LoginFirstRun.this, "Admin logged in!", Toast.LENGTH_SHORT).show();
						ScommixSharedPref.setisRegistered(true);
						ScommixSharedPref.setUSERID(useridd);
					}
					break;
					
					case 2:
					{
						Toast.makeText(LoginFirstRun.this, "Institute logged in!", Toast.LENGTH_SHORT).show();
						ScommixSharedPref.setisRegistered(true);
						ScommixSharedPref.setUSERID(useridd);
					}
					break;
					case 3:
					{
						ScommixSharedPref.setisRegistered(true);
						ScommixSharedPref.setUSERID(useridd);
						
                        Intent notfirstrun=new Intent(LoginFirstRun.this,GetAllUserInfo.class);
							startActivity(notfirstrun);
							finish();
						
					}
					break;
					default:
					{
						Toast t=Toast.makeText(getApplicationContext(), 
								"Something Wrong!", Toast.LENGTH_SHORT); 
						t.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
						t.show();
					}
					break;
					}
					
						
									}

		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.registerbutton:
		{
			
		if(OSUtil.IsNetworkAvailable(LoginFirstRun.this)==true)
			{
			if(username.getText().toString().equals(""))
			{
				
			}
			else if(password.getText().toString().equals(""))
			{
				
			}
			else{
				new MyTask().execute();

			}
			
			
		}
			else{
				
				Toast.makeText(LoginFirstRun.this, "No Network!", Toast.LENGTH_SHORT).show();
			}
			
		}
		break;
		}
	}

	

	
}