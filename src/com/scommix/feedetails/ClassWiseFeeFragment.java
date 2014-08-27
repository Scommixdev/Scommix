package com.scommix.feedetails;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;

import com.scommix.feedetails.StudentWiseFeeFragment.StudentFeeDetail;

import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;
import com.svimedu.scommix.ScommixContentProvider;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ClassWiseFeeFragment extends Fragment{
	
	 

	
	 PullToRefreshGridView feedetailslist;
	 GridView mGridView;
	 ArrayList<String> feeheadlist;
	 ArrayList<String> feesfrequencylist;
	 ArrayList<String> amountlist;
	 MyAdapter adapter;
	 View rootView;
		
     GetFeeDetails details;
	 
	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 rootView=inflater.inflate(R.layout.notice, container,false);
		 feedetailslist=(PullToRefreshGridView)rootView.findViewById(R.id.feedetailslist);
		 mGridView=feedetailslist.getRefreshableView();
		 
		  LayoutInflater inflate=getActivity().getLayoutInflater();
			View emptylayout=inflate.inflate(R.layout.view_empty, null);
			mGridView.setEmptyView(emptylayout);
			Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
			tryagain.setText("Seems Empty!");
			
			
		    feeheadlist=new ArrayList<String>();
		     feesfrequencylist=new ArrayList<String>();
		     amountlist=new ArrayList<String>();
		  
            details=new GetFeeDetails();
			
            feedetailslist.setOnRefreshListener(new OnRefreshListener<GridView>() {

    			


				@Override
				public void onRefresh(PullToRefreshBase<GridView> refreshView) {
					// TODO Auto-generated method stub
					if(details.getStatus()==AsyncTask.Status.RUNNING)
    				{
    				
    				
    				}
    				else if(details.getStatus()==AsyncTask.Status.PENDING)
    				{
    		
    					details.execute();
    				}
    				else if(details.getStatus()==AsyncTask.Status.FINISHED)
    				{
    			
    					new GetFeeDetails().execute();
    	            }
				}

    		});
		     
		     
			new GetFeeDetails().execute();
		
		
			
			
		return rootView;
	}

	
	class GetFeeDetails extends AsyncTask<Void, Void, Void>
	{
		
		final String SOAP_ACTION = "http://tempuri.org/GetFee";
		final String METHOD_NAME = "GetFee";
		final String NAMESPACE ="http://tempuri.org/";
		final String URL ="http://scommix.cloudapp.net/webservices/common.asmx";
		
		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd=new ProgressDialog(getActivity());
			pd.setCancelable(true);
			pd.setMessage("Getting Details...");
			feeheadlist.clear();
			feesfrequencylist.clear();
			amountlist.clear();
			pd.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
				}
			});
			pd.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 	
				PropertyInfo ClassId=new PropertyInfo();
				
				ClassId.setName("ClassId");
				ClassId.setValue(ScommixSharedPref.getCLASSID());
				request.addProperty(ClassId);

			    
				SoapSerializationEnvelope envelope = 
					new SoapSerializationEnvelope(SoapEnvelope.VER11); 
				 envelope.dotNet = true;

				envelope.setOutputSoapObject(request);
				Log.i("request", request.toString());
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

				try {
					androidHttpTransport.call(SOAP_ACTION, envelope);
					   SoapObject results =(SoapObject)envelope.getResponse();
	              Log.i("fee details response", results.toString())	;
	              for(int i=0;i<results.getPropertyCount();i++)
	              {
	            	  if (isCancelled())  break;
	            	  SoapObject online=(SoapObject)results.getProperty(i);  
	            	  Object feehead=(Object)online.getProperty(0);
	            	  Object feefrequence=(Object)online.getProperty(1);
	            	  Object amount=(Object)online.getProperty(2);
	            	  feeheadlist.add(i, feehead.toString());
	            	  feesfrequencylist.add(i, feefrequence.toString());
	            	  amountlist.add(i, amount.toString());
	            	  
	            	
	              }
	              
	 			 
	   
				
				  	
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			feedetailslist.onRefreshComplete();
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					pd.dismiss();
					if(feeheadlist.size()==0)
					{
						
					}
					else{
					  	adapter=new MyAdapter(ClassWiseFeeFragment.this,feeheadlist,feesfrequencylist,amountlist);
					  	mGridView.setAdapter(adapter);
					}
			
				}
			});
			
		}
		
	}
	

	
	class MyAdapter extends BaseAdapter
	{
		ClassWiseFeeFragment ctx;
		 LayoutInflater inflaterr=null;
		 ArrayList<String> feehead;
			ArrayList<String> feesfrequency; ArrayList<String> amount;
		public MyAdapter(ClassWiseFeeFragment notice, ArrayList<String> feehead,
				ArrayList<String> feesfrequency, ArrayList<String> amount) {
			// TODO Auto-generated constructor stub
			this.ctx=notice;
			this.feehead=feehead;
			this.feesfrequency=feesfrequency;
			this.amount=amount;
			inflaterr=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return feehead.size();
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
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View vi=convertView;
			ViewHolder holder;
			if(convertView==null)
			{
				vi=inflaterr.inflate(R.layout.row_feedetails, null);
				 holder = new ViewHolder();
				holder.nametextview = (TextView)vi.findViewById(R.id.homeitemnametext); // duration
				//holder.star=(Button)vi.findViewById(R.id.star);
				
				holder.statustextview=(TextView)vi.findViewById(R.id.homestatustext);
				holder.statustimetextview=(TextView)vi.findViewById(R.id.homestatustime);
				//holder.star=(Button)vi.findViewById(R.id.star);
				vi.setTag(holder);
		       
		      
			}
			else{
				holder=(ViewHolder)vi.getTag();
			}
			
			if(feehead.get(position).equals("anyType{}"))
			{
				holder.nametextview.setText("N/A");
			}
			else{
				holder.nametextview.setText(feehead.get(position));
			}
			
			if(feesfrequency.get(position).equals("anyType{}"))
			{
				holder.statustextview.setText("Fee Frequency : N/A");
			}
			else{
				holder.statustextview.setText("Fee Frequency :"+feesfrequency.get(position));
			}
			
			if(amount.get(position).equals("anyType{}")){
				   holder.statustimetextview.setText("Amount : N/A");
			}
			else{
				   holder.statustimetextview.setText("Amount :"+amount.get(position));
			}
		
			
	     
			 return vi;
		}
		
	}

	static class ViewHolder{
		TextView nametextview,statustextview,statustimetextview;
		
		Button star;
	}


}
