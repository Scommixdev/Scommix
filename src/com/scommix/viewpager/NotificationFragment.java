package com.scommix.viewpager;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.scommix.WebServices.Common.Common;
import com.scommix.adapters.NotificationAdapter;
import com.scommix.asynctasks.GetNotification;
import com.scommix.navigationmainactivity.MainActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class NotificationFragment extends Fragment {
	
	
	  PullToRefreshListView mDrawerList2;
      ListView mDrawer2;
      GetNotification taskGetNotification;
      GetNotification1 taskGetNotification1;
      public ArrayList<String> notificationidlist,notificationtiming,notificationtext,notificationtype,notificationuserid;
	private int notificationcount;
	private NotificationAdapter adapter;		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView =inflater.inflate(R.layout.notificationslist, container,false);
		
			 mDrawerList2=(PullToRefreshListView)rootView.findViewById(R.id.right_drawer);
	       mDrawer2=mDrawerList2.getRefreshableView();
	       
	       LayoutInflater inflate=getActivity().getLayoutInflater();
			View emptylayout=inflate.inflate(R.layout.view_empty, null);
			mDrawer2.setEmptyView(emptylayout);
			Button tryagain=(Button)emptylayout.findViewById(R.id.buttonEmpty);
			tryagain.setText("Seems Empty!");
			
			 notificationidlist=new ArrayList<String>();
		        notificationtext=new ArrayList<String>();
					notificationtiming=new ArrayList<String>();
					notificationtype=new ArrayList<String>();
					notificationuserid=new ArrayList<String>();
					
					 taskGetNotification=new GetNotification(getActivity(),notificationidlist,notificationtiming,notificationtext,notificationtype,notificationuserid,notificationcount,mDrawerList2,mDrawer2);
					
					 taskGetNotification1=new GetNotification1();
					 
			taskGetNotification.execute();
		
			 mDrawerList2.setOnRefreshListener(new OnRefreshListener2<ListView>() {

				@Override
				public void onPullDownToRefresh(
						PullToRefreshBase<ListView> refreshView) {
					// TODO Auto-generated method stub
					if(taskGetNotification.getStatus() == AsyncTask.Status.PENDING){
			    	    // My AsyncTask has not started yet
			    		  taskGetNotification.execute();
			    	}

			    	else if(taskGetNotification.getStatus() == AsyncTask.Status.RUNNING){
			    	    // My AsyncTask is currently doing work in doInBackground()
			    			
			    	
			    	}

			    	else if(taskGetNotification.getStatus() == AsyncTask.Status.FINISHED){
			    	    // My AsyncTask is done and onPostExecute was called
			    		taskGetNotification=null;
						 taskGetNotification=new GetNotification(getActivity(),notificationidlist,notificationtiming,notificationtext,notificationtype,notificationuserid,notificationcount,mDrawerList2,mDrawer2);
						 taskGetNotification.execute();
			    	}
				}

				@Override
				public void onPullUpToRefresh(
						PullToRefreshBase<ListView> refreshView) {
					// TODO Auto-generated method stub
					if(taskGetNotification1.getStatus() == AsyncTask.Status.PENDING){
			    	    // My AsyncTask has not started yet
			    		  taskGetNotification1.execute();
			    	}

			    	else if(taskGetNotification1.getStatus() == AsyncTask.Status.RUNNING){
			    	    // My AsyncTask is currently doing work in doInBackground()
			    			
			    	
			    	}

			    	else if(taskGetNotification1.getStatus() == AsyncTask.Status.FINISHED){
			    	    // My AsyncTask is done and onPostExecute was called
			    		taskGetNotification1=null;
						 taskGetNotification1=new GetNotification1();
						 taskGetNotification1.execute();
			    	}
				}

				 
				 
				 
			 });
		
	       
		return rootView;
	}
	
	class GetNotification1 extends AsyncTask<Void, Void, Void>
	{
		
		final String SOAP_ACTION = "http://tempuri.org/GetNotification1";
	    final String METHOD_NAME = "GetNotification1";
	    final String NAMESPACE =
	             "http://tempuri.org/";
	    final String URL =
	             "http://scommix.cloudapp.net/webservices/common.asmx";
		

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
			PropertyInfo pi=new PropertyInfo();
			
		         	pi.setName("userid");
			        pi.setValue(ScommixSharedPref.getUSERID());
			        PropertyInfo pi1=new PropertyInfo();     
			        pi1.setName("postedtime");
			        pi1.setValue(notificationtiming.get(notificationtiming.size()-1));
			     
			        request.addProperty(pi);
			        request.addProperty(pi1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
			SoapEnvelope.VER11);
			envelope.dotNet = true;

			envelope.setOutputSoapObject(request);
		
			
			HttpTransportSE httpTransport = new HttpTransportSE(URL);
		
			try
			{
			httpTransport.call(SOAP_ACTION, envelope);
			SoapObject results=(SoapObject)envelope.getResponse();
			
			
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
				
				notificationidlist.add(notificationid.toString());
				notificationtiming.add(postedtime.toString());
				notificationtext.add(name.toString()+" "+text.toString());
				notificationtype.add(ntype.toString());
				notificationuserid.add(userid.toString());
				
			}
			new Common().UpadteSeenNotification(ScommixSharedPref.getUSERID());
			
		
					notificationcount=i;
			}
			catch(Exception e)
			{
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "Cant reach Notifications!", Toast.LENGTH_SHORT).show();	
				
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
			
			mDrawerList2.onRefreshComplete();
			adapter=new NotificationAdapter(getActivity(),notificationidlist,notificationtext,notificationtiming,notificationtype,notificationuserid);
			mDrawerList2.setAdapter(adapter);
			
		}
		
	}

}
