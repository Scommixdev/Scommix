package com.scommix.navigationmainactivity;

import java.util.ArrayList;
import java.util.Calendar;

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

import com.scommix.navigationmainactivity.FriendsListFragment.GetFriendsList;
import com.scommix.navigationmainactivity.FriendsRequestsFragment.GetFriendRequest;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Results extends Fragment
{
	
	ResultsAdapter adapter;
	 Spinner resultSpinner;
	PullToRefreshGridView resultslist;
	 GridView mGridView;
 GetResult taskgetresult;
 String rmonth;
    View rootView;
    ArrayList<online> results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	rootView=inflater.inflate(R.layout.results, container,false);
    	
    	resultSpinner=(Spinner)rootView.findViewById(R.id.r_month);
		results=new ArrayList<online>();
		resultslist=(PullToRefreshGridView)rootView.findViewById(R.id.resultslist);
		mGridView=resultslist.getRefreshableView();
	
		   LayoutInflater inflate=getActivity().getLayoutInflater();
			View emptylayout=inflate.inflate(R.layout.view_empty, null);
			mGridView.setEmptyView(emptylayout);
			
			Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
			tryagain.setText("Seems Empty!");
			
			

			
			resultSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					rmonth=String.valueOf(resultSpinner.getSelectedItem());
					
					System.out.println(rmonth);
					if(!rmonth.equals("--Select Month--")){
						
//						Toast.makeText(getActivity(), rmonth, Toast.LENGTH_LONG).show();
						taskgetresult=null;
						taskgetresult=new GetResult(rmonth);
						taskgetresult.execute();
					}
					else {
						
					}
					
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			}
			);
			
			
			
			resultslist.setOnRefreshListener(new OnRefreshListener<GridView>() {

				

				

				@Override
				public void onRefresh(PullToRefreshBase<GridView> refreshView) {
					// TODO Auto-generated method stub
					if(taskgetresult.getStatus()==AsyncTask.Status.RUNNING)
					{
					
					
					}
					else if(taskgetresult.getStatus()==AsyncTask.Status.PENDING)
					{
						
//						taskgetresult.execute();
					}
					else if(taskgetresult.getStatus()==AsyncTask.Status.FINISHED)
					{
						
//						new GetResult(rmonth).execute();
		            }
				}

			});
		
	
		
         return rootView;
    }


	class GetResult extends AsyncTask<Void, Void, Void>
	{
		
		String rmonth;
		
		public GetResult(String rmonth) {
			// TODO Auto-generated constructor stub
			this.rmonth=rmonth;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					resultslist.setRefreshing(true);
				}
			}, 100);
			
			if(results!=null)
			{
				results.clear();
			}
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
//			Calendar now = Calendar.getInstance();
//			int mm = now.get(Calendar.MONTH);
//			String month = null;
//			
//			 switch (mm)
//		        {
//		        case 0:
//		        {
//		        	month="January";
//		        }break;
//		        case 1:
//		        {
//		        	month="February";
//		        }break;
//		        case 2:
//		        {
//		        	month="March";
//		        }break;
//		        case 3:
//		        {
//		        	month="April";
//		        }break;
//		        case 4:
//		        {
//		        	month="May";
//		        }break;
//		        case 5:
//		        {
//		        	month="June";
//		        }break;
//		        case 6:
//		        {
//		        	month="July";
//		        }break;
//		        case 7:
//		        {
//		        	month="August";
//		        }break;
//		        case 8:
//		        {
//		        	month="September";
//		        }break;
//		        case 9:
//		        {
//		        	month="October";
//		        }break;
//		        case 10:
//		        {
//		        	month="November";
//		        }break;
//		        case 11:
//		        {
//		        	month="December";
//		        }break;
//		        
//		        }
			
			 
			 try{
				 results=new Common().GetResultByMonth(ScommixSharedPref.getSVIMEDUID(), rmonth);
			 }
			 catch(Exception e)
			 {
				 
			 }
			
			
			 
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try{
				resultslist.onRefreshComplete();
				if(results.size()==0)
				{
					Toast.makeText(getActivity(), "No Results For " + rmonth +" month", Toast.LENGTH_LONG).show();

					
					mGridView.setVisibility(View.GONE);
					

				}
				else
				{
					mGridView.setVisibility(View.VISIBLE);
					adapter=new ResultsAdapter(getActivity(),results);
					resultslist.setAdapter(adapter);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
	
		}
		
	}
	class ResultsAdapter extends BaseAdapter
	{
          FragmentActivity ctx;
          ArrayList<online> results;
          
          LayoutInflater inflater;
		public ResultsAdapter(FragmentActivity fragmentActivity, ArrayList<online> results) {
			// TODO Auto-generated constructor stub
			this.ctx=fragmentActivity;
			
			this.results=results;
			
			
					
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return results.size();
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
			
			ViewHolder holder;
			if(convertView==null)
			{
				inflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
				convertView=inflater.inflate(R.layout.resultsitem, null);
				holder=new ViewHolder();
				
				holder.subject=(TextView)convertView.findViewById(R.id.subjecttextview);
				holder.testtype=(TextView)convertView.findViewById(R.id.testtype);
				holder.total=(TextView)convertView.findViewById(R.id.marks);
				holder.obtained=(TextView)convertView.findViewById(R.id.obtainedmarks);
//				holder.month=(TextView)convertView.findViewById(R.id.finalgrade);

				
//				holder.year=(TextView)convertView.findViewById(R.id.academicyear);
//				holder.date=(TextView)convertView.findViewById(R.id.date);
//				holder.percentage=(TextView)convertView.findViewById(R.id.finalpercentage);
				
				convertView.setTag(holder);
			}
			else{
				
				holder=(ViewHolder)convertView.getTag();
			}
			//name //class /date examtype academic year finalgrade finalpercentage marka obtained marks total marks subject subjectmarks
			
		
			System.out.println("Subject: "+results.get(position).subject);
			System.out.println("Test Type: "+results.get(position).examinationtypes);
			System.out.println("Total Marks: "+results.get(position).totalmarks);
			System.out.println("Obtained Marks: "+results.get(position).obtainedmarks);
			System.out.println("Final Grade: "+results.get(position).finalgrade);
			System.out.println("           ");
			
		
			
			
//			holder.month.setText("Month: "+results.get(position).finalgrade);
			holder.subject.setText("Subject: "+results.get(position).subject);
			holder.testtype.setText("Test Type: "+results.get(position).examinationtypes);
			holder.total.setText("Total Marks: "+results.get(position).totalmarks);
			holder.obtained.setText("Obtained Marks: "+results.get(position).obtainedmarks);
			

			
			
//			holder.year.setText("Academic Year: "+results.get(position).academicyear);
//			holder.date.setText("Date: "+results.get(position).date);
//			holder.percentage.setText("Percentage: "+results.get(position).finalpercentage);
			
			return convertView;
		}
		
	}
	
	static class ViewHolder
	{
//		TextView subject,year,grade,date,percentage,total,obtained;
		TextView subject, testtype ,total,obtained  ,month;
		
	}
}
