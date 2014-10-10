package com.scommix.navigationmainactivity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.svimedu.scommix.R;

public class showHomeWork extends Fragment {
	ListView homeListView;
	MyAdapter homeworkadapter;
	ArrayList<String> workdetail;
	ArrayList<String> date;
	
	public showHomeWork(ListView homeListView, ArrayList<String> workdetail, ArrayList<String> date) {
		// TODO Auto-generated constructor stub
		this.homeListView=homeListView;
		
		this.workdetail=workdetail;
		this.date=date;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView=inflater.inflate(R.layout.attendancepopup, container, false);
		homeListView=(ListView)rootView.findViewById(R.id.attendancelist);
		homeworkadapter=new MyAdapter(getActivity(), date, workdetail);
		homeListView.setAdapter(homeworkadapter);
		return rootView;
	}
	
	class MyAdapter extends BaseAdapter
	{
		  HomeWorkmain ctx;
		 LayoutInflater inflaterr=null;
		
		 ArrayList<String> date;
		 
//			ArrayList<String> subject; 
			ArrayList<String> workdetail;
			FragmentActivity activity;
		
			

public MyAdapter(FragmentActivity activity, ArrayList<String> date,
		ArrayList<String> workdetail) { 
		// TODO Auto-generated constructor stub
		this.date=date;
//		this.subject=subject;
		this.workdetail=workdetail;
		this.activity=activity;

}


		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return workdetail.size();
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
			inflaterr=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View vi=convertView;
			ViewHolder holder;
			if(convertView==null)
			{
				vi=inflaterr.inflate(R.layout.row_homeworklist, parent, false);
				 holder = new ViewHolder();
				holder.timing = (TextView)vi.findViewById(R.id.timing); // duration
//		        holder.subject=(TextView)vi.findViewById(R.id.subject);
				holder.workdetail=(TextView)vi.findViewById(R.id.workdetails);
				vi.setTag(holder);
		   
			}
			else{
				holder=(ViewHolder)vi.getTag();
			}
			
				holder.timing.setText(date.get(position));
			
			
//			holder.subject.setText(subject.get(position));
		
			holder.workdetail.setText(Html.fromHtml(workdetail.get(position)));
			 return vi;
		}


	
		
	}
	static class ViewHolder{
		TextView timing,workdetail;
		
	}
	

}
