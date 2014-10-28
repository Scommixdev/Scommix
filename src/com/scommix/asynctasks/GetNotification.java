package com.scommix.asynctasks;


import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.scommix.WebServices.Common.Common;
import com.scommix.adapters.NotificationAdapter;


import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;


public class GetNotification extends AsyncTask<Void, Void, Void>
{
	final String SOAP_ACTION = "http://tempuri.org/GetNotification";
    final String METHOD_NAME = "GetNotification";
    final String NAMESPACE =
             "http://tempuri.org/";
    final String URL =
             "http://scommix.cloudapp.net/webservices/common.asmx";
    FragmentActivity activity;
	ArrayList<String> notificationidlist;
	ArrayList<String> notificationtiming;
	ArrayList<String> notificationtext;
	ArrayList<String> notificationtype;
	ArrayList<String> notificationuserid;
	int notificationcount;
	PullToRefreshListView mDrawerList22;
	ListView mDrawerList2;
	private NotificationAdapter adapter;
	
	  public static final String REQUEST_STRING = "myRequest";
	    public static final String RESPONSE_STRING = "myResponse";
	  
	
	
	public GetNotification(FragmentActivity fragmentActivity,ArrayList<String> notificationidlist,ArrayList<String> notificationtiming,ArrayList<String> notificationtext,ArrayList<String> notificationtype,ArrayList<String> notificationuserid, int notificationcount, PullToRefreshListView mDrawerList22, ListView mDrawerList2) {
		// TODO Auto-generated constructor stub
		this.activity=fragmentActivity;
		this.notificationidlist=notificationidlist;
		this.notificationtiming=notificationtiming;
		this.notificationtext=notificationtext;
		this.notificationtype=notificationtype;
		this.notificationuserid=notificationuserid;
		this.notificationcount=notificationcount;
		this.mDrawerList22=mDrawerList22;
		this.mDrawerList2=mDrawerList2;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		if(notificationidlist!=null)
		{
			notificationidlist.clear();
			notificationtiming.clear();
			notificationtext.clear();
			notificationtype.clear();
			notificationuserid.clear();
		}
		
	
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mDrawerList22.setRefreshing(true);
			}
		}, 500);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
		PropertyInfo pi=new PropertyInfo();
		
		pi.setName("userid");
		        pi.setValue(ScommixSharedPref.getUSERID());
		     
		        request.addProperty(pi);
	

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
		SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);
		
		
		HttpTransportSE httpTransport = new HttpTransportSE(URL);
	
		try
		{
		httpTransport.call(SOAP_ACTION, envelope);
		SoapObject results=(SoapObject)envelope.getResponse();
		
		System.out.println("values---->>>" + results);
		int i;

		for(i=0;i<results.getPropertyCount();i++)
		{
			SoapObject online=(SoapObject)results.getProperty(i);
			Object notificationid=(Object)online.getProperty(0);
			Object postedtime=(Object)online.getProperty(1);
			Object text=(Object)online.getProperty(2);
			Object ntype=(Object)online.getProperty(3);
			Object name=(Object)online.getProperty(4);
			Object userid=(Object)online.getProperty(5);
			
			notificationidlist.add(i, notificationid.toString());
			notificationtiming.add(i, postedtime.toString());
			
			System.out.println("Notification time --->" +postedtime.toString());
			
			notificationtext.add(i, name.toString()+" "+text.toString());
			notificationtype.add(i, ntype.toString());
			notificationuserid.add(i, userid.toString());
		}
		
		new Common().UpadteSeenNotification(ScommixSharedPref.getUSERID());
		
	
//				notificationcount=i;
		}
		catch(Exception e)
		{
			activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				Toast.makeText(activity, "Cant reach Notifications!", Toast.LENGTH_SHORT).show();	
			
				}
			});
			e.printStackTrace();
		}
		
	
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		try{
			activity.runOnUiThread(new Runnable() {
				
				

				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					mDrawerList22.onRefreshComplete();
					adapter=new NotificationAdapter(activity,notificationidlist,notificationtext,notificationtiming,notificationtype,notificationuserid);
					mDrawerList2.setAdapter(adapter);
					
					
				}
			});
			
//			Intent i=new Intent();
//			i.setAction("notificationcount");
//			i.addCategory(Intent.CATEGORY_DEFAULT);
//			i.putExtra("count", notificationcount);
//			activity.sendBroadcast(i);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		
		
	}
	
}
