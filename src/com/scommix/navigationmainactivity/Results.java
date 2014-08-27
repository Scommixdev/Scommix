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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class Results extends Fragment
{
	
	ResultsAdapter adapter;
	
	PullToRefreshGridView resultslist;
	 GridView mGridView;
 GetResult taskgetresult;
    View rootView;
    ArrayList<online> results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	rootView=inflater.inflate(R.layout.results, container,false);
    	taskgetresult=new GetResult();
		results=new ArrayList<online>();
		resultslist=(PullToRefreshGridView)rootView.findViewById(R.id.resultslist);
		mGridView=resultslist.getRefreshableView();
	
		   LayoutInflater inflate=getActivity().getLayoutInflater();
			View emptylayout=inflate.inflate(R.layout.view_empty, null);
			mGridView.setEmptyView(emptylayout);
			Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
			tryagain.setText("Seems Empty!");
			
			taskgetresult.execute();
			
			resultslist.setOnRefreshListener(new OnRefreshListener<GridView>() {

				

				

				@Override
				public void onRefresh(PullToRefreshBase<GridView> refreshView) {
					// TODO Auto-generated method stub
					if(taskgetresult.getStatus()==AsyncTask.Status.RUNNING)
					{
					
					
					}
					else if(taskgetresult.getStatus()==AsyncTask.Status.PENDING)
					{
						
						taskgetresult.execute();
					}
					else if(taskgetresult.getStatus()==AsyncTask.Status.FINISHED)
					{
						
						new GetResult().execute();
		            }
				}

			});
		
	
		
         return rootView;
    }


	class GetResult extends AsyncTask<Void, Void, Void>
	{
		
		
		
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
			Calendar now = Calendar.getInstance();
			int mm = now.get(Calendar.MONTH);
			String month = null;
			
			 switch (mm)
		        {
		        case 0:
		        {
		        	month="January";
		        }break;
		        case 1:
		        {
		        	month="February";
		        }break;
		        case 2:
		        {
		        	month="March";
		        }break;
		        case 3:
		        {
		        	month="April";
		        }break;
		        case 4:
		        {
		        	month="May";
		        }break;
		        case 5:
		        {
		        	month="June";
		        }break;
		        case 6:
		        {
		        	month="July";
		        }break;
		        case 7:
		        {
		        	month="August";
		        }break;
		        case 8:
		        {
		        	month="September";
		        }break;
		        case 9:
		        {
		        	month="October";
		        }break;
		        case 10:
		        {
		        	month="November";
		        }break;
		        case 11:
		        {
		        	month="December";
		        }break;
		        
		        }
			
			 
			 try{
				 results=new Common().GetResultByMonth(ScommixSharedPref.getSVIMEDUID(), month);
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
				

				}
				else
				{
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
				holder.year=(TextView)convertView.findViewById(R.id.academicyear);
				holder.grade=(TextView)convertView.findViewById(R.id.finalgrade);
				holder.date=(TextView)convertView.findViewById(R.id.date);
				holder.percentage=(TextView)convertView.findViewById(R.id.finalpercentage);
				holder.total=(TextView)convertView.findViewById(R.id.marks);
				holder.obtained=(TextView)convertView.findViewById(R.id.obtainedmarks);
				convertView.setTag(holder);
			}
			else{
				
				holder=(ViewHolder)convertView.getTag();
			}
			//name //class /date examtype academic year finalgrade finalpercentage marka obtained marks total marks subject subjectmarks
			
		
			
			holder.subject.setText("Subject: "+results.get(position).subject);
			holder.year.setText("Academic Year: "+results.get(position).academicyear);
			holder.grade.setText("Final Grade: "+results.get(position).finalgrade);
			holder.date.setText("Date: "+results.get(position).date);
			holder.percentage.setText("Percentage: "+results.get(position).finalpercentage);
			holder.total.setText("Total Marks: "+results.get(position).totalmarks);
			holder.obtained.setText("Obtained Marks: "+results.get(position).obtainedmarks);
			
			return convertView;
		}
		
	}
	
	static class ViewHolder
	{
		TextView subject,year,grade,date,percentage,total,obtained;
		
	}
}
