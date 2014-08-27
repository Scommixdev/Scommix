package com.scommix.asynctasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.ScommixContentProvider;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class UpdateUserProfilePic extends AsyncTask<Void, Void, Void>{

	
    String strBase64;
    final String SOAP_ACTION = "http://tempuri.org/UpdateUserProfilePic";
	final String METHOD_NAME = "UpdateUserProfilePic";
	final String NAMESPACE ="http://tempuri.org/";
	final String URL ="http://scommix.cloudapp.net/webservices/common.asmx";
	ProgressDialog profilepicupdatedialog;
	boolean gotit=true;
	String strFilter ;
	FragmentActivity profile; String filename;
	ContentValues cv; ContentResolver resolver;
    byte[] byteArray;
    
	public UpdateUserProfilePic(FragmentActivity fragmentActivity, String filename,
			ContentValues cv, ContentResolver resolver) {
		// TODO Auto-generated constructor stub
		this.profile=fragmentActivity;
		this.filename=filename;
		this.cv=cv;
		this.resolver=resolver;
	}


	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		profilepicupdatedialog=new ProgressDialog(profile);
		profilepicupdatedialog.setCancelable(false);
		profilepicupdatedialog.setMessage("Uploading ...");
		profilepicupdatedialog.show();
	}
	
	  public static byte[] readBytesFromFile(String path) throws IOException {
		  FileInputStream fileInputStream=null;
		  
	        File file = new File(path);
	 
	        byte[] bFile = new byte[(int) file.length()];
	 
	        try {
	            //convert file into array of bytes
		    fileInputStream = new FileInputStream(file);
		    fileInputStream.read(bFile);
		    fileInputStream.close();
	 
		    for (int i = 0; i < bFile.length; i++) {
		       
	            }
	 
		    System.out.println("Done");
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	      return bFile;
	  }
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub

			
			try {
				 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 	
				PropertyInfo pi1=new PropertyInfo();
				PropertyInfo pi2=new PropertyInfo();
			 pi1.setName("userid");
			  pi1.setValue(ScommixSharedPref.getUSERID());
			  pi2.setName("ProfilePic");
			
			  strBase64 = Base64.encodeToString(readBytesFromFile(filename), Base64.DEFAULT);
			  pi2.setValue(strBase64);
		     request.addProperty(pi1);
		     request.addProperty(pi2);
			
	
			    
				SoapSerializationEnvelope envelope = 
		        new SoapSerializationEnvelope(SoapEnvelope.VER11); 
				 envelope.dotNet = true;
				// new MarshalBase64().register(envelope);
				envelope.setOutputSoapObject(request);
				Log.i("request", request.toString());
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);
			
			

				
			
			}
			catch(Exception e)
			{
				e.printStackTrace();	
				gotit=false;
			}
		return null;
	}
	 @Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			profile.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(gotit=false)
					{
						Toast.makeText(profile, "Check Your Connectivity!", Toast.LENGTH_SHORT).show();
					}
					else
					{
						try {
							cv.put("PROFILEPIC",readBytesFromFile(filename));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						   strFilter = "USERID=" + ScommixSharedPref.getUSERID();
						resolver.update(ScommixContentProvider.CONTENT_URI_UserInfo, cv, strFilter, null);
						Toast.makeText(profile, "Uploaded profile pic", Toast.LENGTH_SHORT).show();
						ScommixSharedPref.setPROFILEPICNAME("");
//						if(isMyServiceRunning()==true)
//						{
//							
//						}
//						else{
//							Intent getpics=new Intent(profile,GetPictures.class);
//							profile.startService(getpics);
//						}
					}
	  		        	profilepicupdatedialog.dismiss();
				}
			});
		
		
			
			
		}
		private boolean isMyServiceRunning() {
		    
			ActivityManager manager = (ActivityManager)profile.getSystemService(Context.ACTIVITY_SERVICE);
		    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
		        if ("com.scommix.myfragmentsmain.GetPictures".equals(service.service.getClassName())) {
		            return true;
		        }
		        
		    }
		    return false;
		}
}
