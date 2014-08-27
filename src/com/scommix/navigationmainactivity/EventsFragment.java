package com.scommix.navigationmainactivity;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EventsFragment extends Fragment {
	
	 ArrayList<online> events;
     PullToRefreshGridView eventslist;
	 GridView mGridView;
	 EventAdapter adapter;
	
	GetEvents gettask;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView=inflater.inflate(R.layout.events, container,false);
		events=new ArrayList<online>();
		gettask=new GetEvents();
		eventslist=(PullToRefreshGridView)rootView.findViewById(R.id.eventslist);
		mGridView=eventslist.getRefreshableView();

		
		 LayoutInflater inflate=getActivity().getLayoutInflater();
			View emptylayout=inflate.inflate(R.layout.view_empty, null);
			mGridView.setEmptyView(emptylayout);
			Button tryagain=(Button)rootView.findViewById(R.id.buttonEmpty);
			tryagain.setText("Seems Empty!");
		
		gettask.execute();
		
		eventslist.setOnRefreshListener(new OnRefreshListener<GridView>() {

				

				

				@Override
				public void onRefresh(PullToRefreshBase<GridView> refreshView) {
					// TODO Auto-generated method stub
					if(gettask.getStatus()==AsyncTask.Status.RUNNING)
					{
					
					
					}
					else if(gettask.getStatus()==AsyncTask.Status.PENDING)
					{
					
						gettask.execute();
					}
					else if(gettask.getStatus()==AsyncTask.Status.FINISHED)
					{
						
						new GetEvents().execute();
		            }
				}

			});
		
		
		return rootView;
	}
	
	class GetEvents extends AsyncTask<Void, Void, Void>
	{

		
		@Override
		protected void onPreExecute() {
			
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					eventslist.setRefreshing(true);
				}
			}, 100);
			
			
			if(events!=null)
			{
				events.clear();
			}
			
		};

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			events=new Common().GetBirthday(ScommixSharedPref.getUSERID());
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			
			try{
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						eventslist.onRefreshComplete();
					}
				});
		
				adapter=new EventAdapter(getActivity(),events);
				mGridView.setAdapter(adapter);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
		};
		
	}
	
	class EventAdapter extends BaseAdapter{

		FragmentActivity activity;
		ArrayList<online> events;
		 LayoutInflater inflaterr;
		public EventAdapter(FragmentActivity activity, ArrayList<online> events) {
			// TODO Auto-generated constructor stub
			this.activity=activity;
			this.events=events;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return events.size();
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
			EventViewHolder holder;
			if(convertView==null)
			{
				holder=new EventViewHolder();
				inflaterr=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView=inflaterr.inflate(R.layout.events_list_item, arg2,false);
                holder.iv=(ImageView)convertView.findViewById(R.id.eventsimage);
                holder.tv=(TextView)convertView.findViewById(R.id.eventstext);
				convertView.setTag(holder);
			}
			else
			{
				holder=(EventViewHolder)convertView.getTag();
			}
			
			
			holder.tv.setText(events.get(position).name);
			holder.iv.setVisibility(View.GONE);
			return convertView;
		}
		
		class EventViewHolder{
			ImageView iv;
			TextView tv;
		}
		
	}

}
