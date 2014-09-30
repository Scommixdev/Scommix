package com.scommix.navigationmainactivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;

public class HomeWorkmain extends Fragment implements OnClickListener{
	
	private CaldroidFragment caldroidFragment;
	private String today;
	private ListView homeListView;

ArrayList<String> workdetail;
ArrayList<String> date;

ArrayList<String> subject;


MyAdapter homeworkadapter;

DatePicker dt;
Button search;
View rootView;

private void setCustomResourceForDates() {
	Calendar cal = Calendar.getInstance();

	// Min date is last 7 days
	cal.add(Calendar.DATE, -18);
	Date blueDate = cal.getTime();

	// Max date is next 7 days
	cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 16);
	Date greenDate = cal.getTime();

	if (caldroidFragment != null) {
		caldroidFragment.setBackgroundResourceForDate(R.color.blue,
				blueDate);
		caldroidFragment.setBackgroundResourceForDate(R.color.gplus_color_1,
				greenDate);
		caldroidFragment.setTextColorForDate(android.R.color.white, blueDate);
		caldroidFragment.setTextColorForDate(android.R.color.white, greenDate);
	}
}

@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 rootView =inflater.inflate(R.layout.homework, container,false);
	
	workdetail=new ArrayList<String>();
	date=new ArrayList<String>();
	subject=new ArrayList<String>();
	final SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

	// Setup caldroid fragment
	// **** If you want normal CaldroidFragment, use below line ****
	caldroidFragment = new CaldroidFragment();
       
	// //////////////////////////////////////////////////////////////////////
	// **** This is to show customized fragment. If you want customized
	// version, uncomment below line ****
//	 caldroidFragment = new CaldroidSampleCustomFragment();

	// Setup arguments

	// If Activity is created after rotation
	if (savedInstanceState != null) {
		caldroidFragment.restoreStatesFromKey(savedInstanceState,
				"CALDROID_SAVED_STATE");
	}
	// If activity is created from fresh
	else {
		Bundle args = new Bundle();
		Calendar cal = Calendar.getInstance();
		args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
		args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
		args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
		args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

		// Uncomment this to customize startDayOfWeek
		// args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
		// CaldroidFragment.TUESDAY); // Tuesday
		caldroidFragment.setArguments(args);
	}

	setCustomResourceForDates();

	// Attach to the activity
	FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
	t.replace(R.id.calendar1, caldroidFragment);
	t.commit();
	
	// Setup listener
	final CaldroidListener listener = new CaldroidListener() {

		

		@Override
		public void onSelectDate(Date date, View view) {
			
			
			
			
			today=formatter.format(date);
		
			String arr[]=today.split(" ");
			
			String day=arr[0];
			String month=arr[1];
			String year=arr[2];
			
			if(month.equals("January"))
			{
				month="01";
			}
			else if(month.equals("February"))
			{
				month="02";
			}
			else if(month.equals("March"))
			{
				month="03";
			}
			else if(month.equals("April"))
			{
				month="04";
			}
			else if(month.equals("May"))
			{
				month="05";
			}
			else if(month.equals("June"))
			{
				month="06";
			}
			else if(month.equals("July"))
			{
				month="07";
			}
			else if(month.equals("August"))
			{
				month="08";
			}
			else if(month.equals("September"))
			{
				month="09";
			}
			else if(month.equals("October"))
			{
				month="10";
			}
			else if(month.equals("November"))
			{
				month="11";
			}
			else if(month.equals("December"))
			{
				month="12";
			}
			
			today=month+"/"+day+"/"+year;
			
			System.out.println(today);
			
			new GetHomeworkbydate().execute();
			
		}

		@Override
		public void onChangeMonth(int month, int year) {
			String text = "month: " + month + " year: " + year;
		
		}

		@Override
		public void onLongClickDate(Date date, View view) {
			
		}

		@Override
		public void onCaldroidViewCreated() {
			if (caldroidFragment.getLeftArrowButton() != null) 
			{
			
			}
		}

	};

	// Setup Caldroid
	caldroidFragment.setCaldroidListener(listener);
	
		return rootView;
	}


class GetHomeworkbydate extends  AsyncTask<Void, Void, Void>
{
	ProgressDialog pd;
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		workdetail.clear();
		subject.clear();
		date.clear();
		
		pd=new ProgressDialog(getActivity());
		pd.setMessage("Getting by date...");
		pd.setCancelable(false);
		pd.show();
	};
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
	
		Common c=new Common();
		ArrayList<online> temp=new ArrayList<online>();
		
	temp=c.GetHomeWorkByDate(ScommixSharedPref.getINSTITUTEID(), ScommixSharedPref.getCLASSID(), today);
		for(int i=0;i<temp.size();i++)
		{
			date.add(i, temp.get(i).date);
			Log.i("date", temp.get(i).date);
			workdetail.add(i, temp.get(i).workdetail);
			Log.i("work", temp.get(i).workdetail);
			subject.add(i, temp.get(i).subject);
			Log.i("subject", temp.get(i).subject);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		
		pd.dismiss();
		
		if(date.size()!=0)
		{
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());
	 
				// set title
				alertDialogBuilder.setTitle("Attendance");
				LayoutInflater inflater = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						View layout = inflater.inflate(R.layout.attendancepopup,
						(ViewGroup) rootView.findViewById(R.id.popup_element));
				alertDialogBuilder.setView(layout);
				
				homeListView=(ListView)layout.findViewById(R.id.attendancelist);
				homeListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
						// TODO Auto-generated method stub
						
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								getActivity());
				 
							// set title
							alertDialogBuilder.setTitle(subject.get(position));
							alertDialogBuilder.setMessage(workdetail.get(position));
						
					
							
							alertDialogBuilder.setCancelable(false)
								
								.setNegativeButton("Close",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});
				 
								// create alert dialog
								AlertDialog alertDialog = alertDialogBuilder.create();
				 
					      alertDialog.show();
					}
				});
			
				alertDialogBuilder.setCancelable(false)
					
					.setNegativeButton("Close",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
				

					homeworkadapter=new MyAdapter(getActivity(),date,subject,workdetail);
					homeListView.setAdapter(homeworkadapter);
		      alertDialog.show();
		}
		else
		{

			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());
	 
				// set title
				alertDialogBuilder.setTitle("Homework ");
				alertDialogBuilder.setMessage("No Homework for the date!");
			
		
				
				alertDialogBuilder.setCancelable(false)
					
					.setNegativeButton("Close",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
		      alertDialog.show();
		
		}
	
			
		

		
	}
}


	
	class MyAdapter extends BaseAdapter
	{
		  HomeWorkmain ctx;
		 LayoutInflater inflaterr=null;
		
		 ArrayList<String> date;
		 
			ArrayList<String> subject; 
			ArrayList<String> workdetail;

		
			

		public MyAdapter(FragmentActivity activity, ArrayList<String> date,
				ArrayList<String> subject, ArrayList<String> workdetail) {
			// TODO Auto-generated constructor stub
			this.date=date;
			this.subject=subject;
			this.workdetail=workdetail;
			inflaterr=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return subject.size();
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
			ViewHolder holder;
			if(convertView==null)
			{
				vi=inflaterr.inflate(R.layout.row_homeworklist, null);
				 holder = new ViewHolder();
				holder.timing = (TextView)vi.findViewById(R.id.timing); // duration
		        holder.subject=(TextView)vi.findViewById(R.id.subject);
				holder.workdetail=(TextView)vi.findViewById(R.id.workdetails);
				vi.setTag(holder);
		   
			}
			else{
				holder=(ViewHolder)vi.getTag();
			}
			
				holder.timing.setText(date.get(position));
			
			
			holder.subject.setText(subject.get(position));
		
			holder.workdetail.setText(workdetail.get(position));
			 return vi;
		}


	
		
	}
	static class ViewHolder{
		TextView timing,subject,workdetail;
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
}
	


	

