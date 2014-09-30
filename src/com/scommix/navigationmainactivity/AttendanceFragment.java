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
import android.view.ViewGroup;
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

public class AttendanceFragment extends Fragment{
	
	
	DatePicker dt;
	Button search;
	String today;
	ArrayList<online> attendance;
	MyAttendanceAdapter adapter;
	View rootView;
	
	private CaldroidFragment caldroidFragment;
	private ListView listview;
	
	
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
	 rootView=inflater.inflate(R.layout.attendance, container,false);
		attendance=new ArrayList<online>();

		

		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		caldroidFragment = new CaldroidFragment();
           
		// //////////////////////////////////////////////////////////////////////
		// **** This is to show customized fragment. If you want customized
		// version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

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
				
				
				new GetStudentAttendanceByDate().execute();

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
	



	class GetStudentAttendanceByDate extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(attendance.size()>0)
			{
				attendance.clear();
			
			}
			pd=new ProgressDialog(getActivity());
			pd.setMessage("Getting Attendance...");
			pd.setCancelable(false);
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
		
			 attendance=new Common().GetStudentAttendanceByDate(ScommixSharedPref.getSVIMEDUID(), today);
			 try{
					for(int i=0;i<attendance.size();i++)
					{
						// name .class date examinationtypes academic year final grade marks obtained marks
						Log.i("name", attendance.get(i).name);
						Log.i("class", attendance.get(i)._classField);
						Log.i("date", attendance.get(i).date);
						Log.i("exam marks", attendance.get(i).examinationtypes);
						Log.i("academic", attendance.get(i).academicyear);
						Log.i("final grade", attendance.get(i).finalgrade);
						Log.i("marks`", attendance.get(i).marks);
						Log.i("obtained marks", attendance.get(i).obtainedmarks);
					}
					
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
	
		
		
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			
			if(attendance.size()!=0)
			{
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
		 
					// set title
					alertDialogBuilder.setTitle("Attendance ");
					LayoutInflater inflater = (LayoutInflater) getActivity()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							View layout = inflater.inflate(R.layout.attendancepopup,
							(ViewGroup) rootView.findViewById(R.id.popup_element));
					alertDialogBuilder.setView(layout);
					
					listview=(ListView)layout.findViewById(R.id.attendancelist);
					
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
					
				  adapter=new MyAttendanceAdapter(getActivity(), attendance);
				  listview.setAdapter(adapter);
			      alertDialog.show();
			}
			else
			{

				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
		 
					// set title
					alertDialogBuilder.setTitle("Attendance ");
					alertDialogBuilder.setMessage("No Attendance for the date!");
				
			
					
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
	
	
	class MyAttendanceAdapter extends BaseAdapter
	{
		FragmentActivity activity;
		ArrayList<online> attendance;
		 LayoutInflater inflater;
		
		public MyAttendanceAdapter(FragmentActivity activity,
				ArrayList<online> attendance) {
			// TODO Auto-generated constructor stub
			this.activity=activity;
			this.attendance=attendance;
			inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return attendance.size();
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
			
			ViewHolder holder;
			
			if(convertView==null)
			{
				holder=new ViewHolder();
				convertView=inflater.inflate(R.layout.row_attendance_item, null);
		
				holder.attendanceofclass=(TextView)convertView.findViewById(R.id.attendanceStudentName);
				holder.attendancetype=(TextView)convertView.findViewById(R.id.attendancetype);
				holder.attendanceperiod=(TextView)convertView.findViewById(R.id.attendanceperiod);
				holder.attendancesubject=(TextView)convertView.findViewById(R.id.attendancesubject);
				holder.Dateattendance=(TextView)convertView.findViewById(R.id.Dateattendance);
				
				convertView.setTag(holder);
			}
			else{
				holder=(ViewHolder)convertView.getTag();
			}
			
			holder.attendanceofclass.setText("Class :"+attendance.get(position)._classField);
			holder.attendancetype.setText("Presence :"+attendance.get(position).examinationtypes);
			holder.attendanceperiod.setText(attendance.get(position).finalgrade);
			holder.attendancesubject.setText("Teacher's Name :"+attendance.get(position).academicyear);
			holder.Dateattendance.setText(attendance.get(position).date);
			
			return convertView;
		}
		
		
		
	
		
	}
	static public class ViewHolder{
		TextView attendanceStudentName,attendanceofclass,attendancetype,attendanceperiod,attendancesubject,Dateattendance;
	}

	



	

	
	
}
