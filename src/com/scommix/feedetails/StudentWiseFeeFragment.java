package com.scommix.feedetails;

import java.util.ArrayList;





import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;

import com.scommix.feedetails.ClassWiseFeeFragment.MyAdapter;

import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;

public class StudentWiseFeeFragment extends Fragment{
	
	 
	    PullToRefreshGridView feedetailslist;
	    GridView mGridView;
	    View rootView;
		ArrayList<online> answer;
		StudentAdapter adapt;
		StudentFeeDetail frns;

	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 rootView=inflater.inflate(R.layout.notice, container,false);
		 feedetailslist=(PullToRefreshGridView)rootView.findViewById(R.id.feedetailslist);
		 mGridView=feedetailslist.getRefreshableView();
		 answer=new ArrayList<online>();
		   frns=new StudentFeeDetail();
		   
		   
		 LayoutInflater inflate=getActivity().getLayoutInflater();
			View emptylayout=inflate.inflate(R.layout.view_empty, null);
			mGridView.setEmptyView(emptylayout);
			Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
			tryagain.setText("Seems Empty!");
	
		 feedetailslist.setOnRefreshListener(new OnRefreshListener<GridView>() {

				


				@Override
				public void onRefresh(PullToRefreshBase<GridView> refreshView) {
					// TODO Auto-generated method stub
					if(frns.getStatus()==AsyncTask.Status.RUNNING)
					{
					
					
					}
					else if(frns.getStatus()==AsyncTask.Status.PENDING)
					{
						Log.i("pending", "pending");
						frns.execute();
					}
					else if(frns.getStatus()==AsyncTask.Status.FINISHED)
					{
						System.out.println("finished");
						new StudentFeeDetail().execute();
		            }
				}

			});
		 
		 
		 
		new StudentFeeDetail().execute();
			
	
			
			
		return rootView;
	}

	
	
	
	class StudentFeeDetail extends AsyncTask<Void, Void, Void>{
		
		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd=new ProgressDialog(getActivity());
			pd.setCancelable(true);
			pd.setMessage("Getting Details...");
			answer.clear();
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
		
	
			answer=new Common().GetStuFeeDetail(ScommixSharedPref.getSVIMEDUID(),ScommixSharedPref.getCLASSID());
		Log.i(ScommixSharedPref.getSVIMEDUID(),ScommixSharedPref.getCLASSID());
			Log.i(ScommixSharedPref.getSVIMEDUID(), ScommixSharedPref.getCLASSID());
			Log.i("size", ""+answer.size());
			for(int i=0;i<answer.size();i++)
			{
				if (isCancelled())  break;
				Log.i("result"+i, answer.get(i).name);
				Log.i("result"+i, answer.get(i).totalamount);
				Log.i("result"+i, answer.get(i).totalconcession);
				Log.i("result"+i, answer.get(i).amount);
				Log.i("result"+i, answer.get(i).previousbalance);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			feedetailslist.onRefreshComplete();
			pd.dismiss();
			if(answer.size()==0)
			{
	           
			}
			adapt=new StudentAdapter(getActivity(),answer);
			mGridView.setAdapter(adapt);
		}
		
	}
	
	class StudentAdapter extends BaseAdapter
	{

		FragmentActivity activity;
		ArrayList<online> answer;
	 LayoutInflater inflaterr;
		public StudentAdapter(FragmentActivity activity,
				ArrayList<online> answer) {
			// TODO Auto-generated constructor stub
			this.activity=activity;
			this.answer=answer;
			inflaterr=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return answer.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			View vi=convertView;
			ViewHolder1 holder;
			if(convertView==null)
			{
				vi=inflaterr.inflate(R.layout.row_stuwisefee, null);
				 holder = new ViewHolder1();
				holder.nametextview = (TextView)vi.findViewById(R.id.homeitemnametext); // duration
				//holder.star=(Button)vi.findViewById(R.id.star);
				holder.totalconsession=(TextView)vi.findViewById(R.id.totalconcession);
				holder.amount=(TextView)vi.findViewById(R.id.amount);
				holder.statustextview=(TextView)vi.findViewById(R.id.homestatustext);
				holder.statustimetextview=(TextView)vi.findViewById(R.id.homestatustime);
				//holder.star=(Button)vi.findViewById(R.id.star);
				vi.setTag(holder);
		       
		      
			}
			else{
				holder=(ViewHolder1)vi.getTag();
			}
			holder.nametextview.setText(answer.get(position).name);
			holder.statustextview.setText("Previous Balance : "+answer.get(position).previousbalance);
	        holder.statustimetextview.setText("Total Amount :"+answer.get(position).totalamount);
			 holder.totalconsession.setText("Total Concession :"+answer.get(position).totalconcession);
	        holder.amount.setText("Amount :"+answer.get(position).amount);
	        return vi;
		}
		class ViewHolder1{
			TextView nametextview,statustextview,statustimetextview,totalconsession,amount;
			
		}
		
	}
	
	


}
