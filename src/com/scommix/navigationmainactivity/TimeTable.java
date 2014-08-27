package com.scommix.navigationmainactivity;

import java.util.ArrayList;
import java.util.Calendar;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;

import com.scommix.navigationmainactivity.Results.GetResult;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class TimeTable extends ActionBarActivity {

	ActionBar action;
	ArrayList<String> periods;
	ArrayList<online> pidresplist;
	ArrayList<String> pids;
	ArrayList<String> period123;
	ArrayList<online> timetable;
	MyAdapter timetableadapter;
    ViewPager mViewPager;
  
    MainApp appiobj;
    ArrayList<String> teachernames;
    ArrayList<String> subjects;
    ArrayList<String> roomno;

    @Override
    protected void onCreate(Bundle arg0) {
    	// TODO Auto-generated method stub
    	super.onCreate(arg0);
    	setContentView(R.layout.timetable);
        appiobj=(MainApp)getApplication();
 		action=getSupportActionBar();
 	    action.setDisplayHomeAsUpEnabled(true);
 			
 		
 		
 		action.setTitle("Time Table");
 		
 		//action.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#484848")));
 		mViewPager = (ViewPager)findViewById(R.id.pager);
 		teachernames=new ArrayList<String>();
 		periods=new ArrayList<String>();
 		pidresplist=new ArrayList<online>();
 		period123=new ArrayList<String>();
 		pids=new ArrayList<String>();
 		subjects=new ArrayList<String>();
 		roomno=new ArrayList<String>();
 		timetable=new ArrayList<online>();
 		
 		new GetTimeTable().execute();
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
//	        	Intent i=new Intent(MyProfileActivity.this,MainActivity.class);
//	        	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	        	startActivity(i);
	        	this.finish();
	            return true;
	            default:
	            return super.onOptionsItemSelected(item); 
	    }
	}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	// TODO Auto-generated method stub
    	super.onConfigurationChanged(newConfig);
    	
    	if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) 
    	{     
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }
    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {     
    	 	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


    	} 
    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    	} 
    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	}
    	else {
    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	}
    }
    
	

	
	
	class GetTimeTable extends AsyncTask<Void, Void, Void>
	{
		
ProgressDialog pd=new ProgressDialog(TimeTable.this);

	

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd.setMessage("Loading TimeTable...");
			pd.setCancelable(true);
			pd.setButton(ProgressDialog.BUTTON_POSITIVE, "Cancel",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					cancel(true);
					  
				}
			});
			pd.show();
			super.onPreExecute();
		}
		
		        @Override
				protected void onCancelled() {
					// TODO Auto-generated method stub
					super.onCancelled();
					
				}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
				try{
					Common c=new Common();
					
					
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
				Log.i("month", month);
					System.out.println(ScommixSharedPref.getCLASSID());
					System.out.println(ScommixSharedPref.getINSTITUTEID());
				 pidresplist =	c.GetAllPeroids(month, ScommixSharedPref.getCLASSID(), ScommixSharedPref.getINSTITUTEID());
				 // pidresplist =	c.GetAllPeroids("April", ScommixSharedPref.getCLASSID(), ScommixSharedPref.getINSTITUTEID());

				  System.out.println(""+pidresplist.size());
				  
					if(pidresplist!=null)
					{
						
						for(int a=0;a<pidresplist.size();a++)
						{
							
							if(isCancelled()) break;
							period123.add(a, pidresplist.get(a).text);
							pids.add(a,pidresplist.get(a).name);
						
						}
					}
					
					 	
					for(int i=0;i<pids.size();i++)
					{
						
					if(isCancelled()) break;
					
                   timetable.addAll(i, c.GetTimeTable(month, ScommixSharedPref.getCLASSID(), ScommixSharedPref.getINSTITUTEID(), pids.get(i)));
                   // timetable.addAll(i, c.GetTimeTable("April", ScommixSharedPref.getCLASSID(), ScommixSharedPref.getINSTITUTEID(), pids.get(i)));

					}
				
					if(timetable!=null)
					{
						for(int j=0;j<pids.size();j++)
						{
							if(isCancelled()) break;

							if(timetable.get(j)._classField==null)
							{
								
							}
							else{
							
								roomno.add(j, timetable.get(j)._classField);
								
							}
							
							if(timetable.get(j).text==null)
							{
								
							}
							else{
								  String teachersstring=new String(timetable.get(j).text);
				                   teachernames.add(j, teachersstring);
				            
							}
							
							if(timetable.get(j).subject==null)
							{
								
							}
							else{
								
								subjects.add(j,  timetable.get(j).subject);
							
							}
							
							
						if(timetable.get(j).timings==null)
						{
							
						}
						else
						{
						
							periods.add(j, timetable.get(j).timings);
						}
							
						}
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
			pd.dismiss();
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					if(timetable.size()==0)
					{
						finish();
						Toast.makeText(TimeTable.this, "TimeTable not available!", Toast.LENGTH_SHORT).show();
					}
					else{
						 //TODO Auto-generated method stub
						 timetableadapter =
					  new MyAdapter(getSupportFragmentManager(),timetable,teachernames,periods,roomno);
					      mViewPager.setAdapter(timetableadapter);
					}
				 	
				
				}
			});
			super.onPostExecute(result);
			
			
		}
		
	}
	

	public class MyAdapter extends FragmentPagerAdapter {
		
		 ArrayList<online> timetable; 
		 ArrayList<String> teachernames, periods;
		 final int PAGE_COUNT;
		 ArrayList<String> roomno;
	    public MyAdapter(FragmentManager fm, ArrayList<online> timetable, ArrayList<String> teachernames, ArrayList<String> periods, ArrayList<String> roomno) {
	        super(fm);
	        this.timetable=timetable;
	        this.teachernames=teachernames;
	        this.periods=periods;
	        this.roomno=roomno;
	        PAGE_COUNT =periods.size() ;
	    }

	    @Override
	    public Fragment getItem(int i) {
	    	

	    	System.out.println(periods.get(i));
	    	if(subjects.size()==0)
	    	{
	    		for(int temp=0;temp<teachernames.size();temp++)
	    		{
	    			subjects.add(temp, "No Subject");
	    		}
	    	}
	    	
	    	if(roomno.size()==0)
	    	{
	    		for(int temp=0;temp<teachernames.size();temp++)
	    		{
	    			roomno.add(temp, "Missing");
	    		}
	    	}
	    	return TimeTableFragmentInside.create(i,teachernames.get(i),periods.get(i),subjects.get(i),roomno.get(i));
	  
	  }

	    @Override
	    public int getCount() {
	    	return PAGE_COUNT;
	    }

	    @Override
	    public CharSequence getPageTitle(int position) {
	    	
	    	
	        return periods.get(position);
	    }
	}

	
}
