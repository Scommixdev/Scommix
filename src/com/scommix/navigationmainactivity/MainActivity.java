package com.scommix.navigationmainactivity;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.Utils;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.StuNotice;
import com.scommix.WebServices.Common.Updates;
import com.scommix.asynctasks.ClearLogoutTask;
import com.scommix.asynctasks.GetNotification;
import com.scommix.asynctasks.GetStatushome;
import com.scommix.feedetails.FeeDetailActivity;
import com.scommix.firstrun.Newdemo;

import com.scommix.friendsandsearch.SearchResultsActivity;
import com.scommix.homeandprofile.HomeFragment;
import com.scommix.homeandprofile.MyProfileActivity;
import com.scommix.homeandprofile.Status;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends ActionBarActivity {
	
	
	 
	    private DrawerLayout mDrawerLayout;
	    private PullToRefreshListView mDrawerList;
	    private ListView mDrawer1;
	    private ActionBarDrawerToggle mDrawerToggle;
        private CharSequence mDrawerTitle;
	    ActionBar actionbar;
        private CharSequence mTitle;
   	    Bitmap profilepic,coverpicture;
	    String nameofuser,emailofuser;
	    private String[] navMenuTitles;
	    private TypedArray navMenuIcons;
        private ArrayList<NavDrawerItem> navDrawerItems;
	    NavDrawerListAdapter adapter;
	    byte[] photo,cover;
	    GetNotification taskGetNotification;
		ImageView profileimageview;
        ImageView coverpic;
        PullToRefreshListView mDrawerList2;
        ListView mDrawer2;
        public ArrayList<String> notificationidlist,notificationtiming,notificationtext,notificationtype,notificationuserid;
		private int notificationcount;

		boolean loginsuccessful=true;
		ScommixSharedPref ppf;
		private String mess;
		private ArrayList<String> messagescount;
		private ArrayList<StuNotice> NoticeCount;
		private ArrayList<String> friendcount;
		MainApp appobj;
		

		 RefreshFirstList taskRefreshfirst;
		private String institueprofilepic;
		private String instituebacvkground;
		 
		 
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	
		  ppf=ScommixSharedPref.getInstance(MainActivity.this);
          appobj=(MainApp)getApplication();
		
		setContentView(R.layout.layout_mainactivity);
		
		if(ScommixSharedPref.getUSERID().equals(""))
		{
			Intent i=new Intent(MainActivity.this,Newdemo.class);
			startActivity(i);
			finish();
		}
		else{
	 	
			Intent i=getIntent();
			mess=i.getStringExtra("message");
			
	
		        
		        mTitle = mDrawerTitle = getTitle();
			   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		       
			   mDrawerList = (PullToRefreshListView) findViewById(R.id.list_slidermenu);
			  
			   mDrawer1=mDrawerList.getRefreshableView();
			   
			   LayoutInflater infl=getLayoutInflater();
				ViewGroup header = (ViewGroup)infl.inflate(R.layout.headermaindrawer, mDrawer1, false);
				mDrawer1.addHeaderView(header);
				//final RelativeLayout layout=(RelativeLayout)header.findViewById(R.id.headerprofile);
				ImageView profilepic=(ImageView)header.findViewById(R.id.insbackground);
				//TextView   userkanaam=(TextView)header.findViewById(R.id.headerusername);
			   // ImageView institutelogo=(ImageView)header.findViewById(R.id.institutelogo);
			  
			
			   instituebacvkground=ScommixSharedPref.getINSTITUTEBACKGROUND();
				     
				     Picasso.with(this) //
				     .load("http://scommix.cloudapp.net/InstituteLogo/"+ScommixSharedPref.getINSTITUTEBACKGROUND())
				        
				        .placeholder(R.drawable.ic_loading) //
				        .error(R.drawable.cross) //
				        .fit() //
				        .into(profilepic);
				     
				 
				 
				     
				
				     
				     
				     
				     
		       mDrawerList2=(PullToRefreshListView)findViewById(R.id.right_drawer);
		       mDrawer2=mDrawerList2.getRefreshableView();
		       
		       LayoutInflater inflate=getLayoutInflater();
				View emptylayout=inflate.inflate(R.layout.view_empty, null);
				mDrawer2.setEmptyView(emptylayout);
				Button tryagain=(Button)emptylayout.findViewById(R.id.buttonEmpty);
				tryagain.setText("Seems Empty!");
		       
		       taskRefreshfirst=new RefreshFirstList();
		 taskRefreshfirst.execute();
		 
		
		 mDrawerList2.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
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
					 taskGetNotification=new GetNotification(MainActivity.this,notificationidlist,notificationtiming,notificationtext,notificationtype,notificationuserid,notificationcount,mDrawerList2,mDrawer2);
					 taskGetNotification.execute();
		    	}
			}
		});
		 
		 
		 mDrawerList.setOnRefreshListener(new OnRefreshListener<ListView>() {

				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					// TODO Auto-generated method stub
					if(taskRefreshfirst.getStatus()==AsyncTask.Status.PENDING)
					{
						taskRefreshfirst.execute();
					}
					else if(taskRefreshfirst.getStatus()==AsyncTask.Status.RUNNING)
					{
						
					}
					else if(taskRefreshfirst.getStatus()==AsyncTask.Status.FINISHED)
					{
						taskRefreshfirst=null;
						taskRefreshfirst=new RefreshFirstList();
						taskRefreshfirst.execute();
					}
					
				}
			});
		       notificationidlist=new ArrayList<String>();
	        notificationtext=new ArrayList<String>();
				notificationtiming=new ArrayList<String>();
				notificationtype=new ArrayList<String>();
				notificationuserid=new ArrayList<String>();
				 taskGetNotification=new GetNotification(MainActivity.this,notificationidlist,notificationtiming,notificationtext,notificationtype,notificationuserid,notificationcount,mDrawerList2,mDrawer2);
                navDrawerItems = new ArrayList<NavDrawerItem>();
		        // load slide menu items
		        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		        messagescount=new ArrayList<String>();
		        NoticeCount=new ArrayList<StuNotice>();
		        StuNotice n=new StuNotice();
		        n.setNoticecount("0");
		        NoticeCount.add(0,n);
		        
		        messagescount.add(0, "0");
		        
		        friendcount=new ArrayList<String>();
		        friendcount.add(0, "0");
		        // nav drawer icons from resources
		        navMenuIcons = getResources()
		                .obtainTypedArray(R.array.nav_drawer_icons);
		        
		        
		       actionbar=getSupportActionBar();
		       actionbar.setDisplayShowHomeEnabled(true);
		       actionbar.setHomeButtonEnabled(true);
		       actionbar.setTitle(getResources().getString(R.string.app_name));
		     //  actionbar.setDisplayShowTitleEnabled(false);
			
		 
		        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
		                R.drawable.ic_navigation_drawer, //nav menu toggle icon
		                R.string.app_name, // nav drawer open - description for accessibility
		                R.string.app_name // nav drawer close - description for accessibility
		        ) 
		        
		        {
		            public void onDrawerClosed(View view) 
		            {
		                //actionbar.setTitle(getResources().getString(R.string.app_name));
		               // onPrepareOptionsMenu() //to show action bar icons
		            
		              actionbar.setTitle(mDrawerTitle);
		              
		            }
		 
		            public void onDrawerOpened(View drawerView) {
		                actionbar.setTitle(mDrawerTitle);
		                
		                // calling onPrepareOptionsMenu() to hide action bar icons
		              //  invalidateOptionsMenu();
		              
		            }
		            
		            
		        };
		        mDrawerLayout.setDrawerListener(mDrawerToggle);
		        
		        if (savedInstanceState == null) {
		            // on first time display view for first nav item
		        	if(mess!=null)
		        	{
		        		if(mess.equals("message"))
			             {
				            displayView(4);
			             }
		        	}
		        	else{
		        		 displayView(0);
		        	}
		          
		       	ScommixSharedPref.setFirstRun(false);
		        }
		
		  
		    	
		          	 	
		
			
		}
		

	        
		  taskGetNotification.execute();
   
	    }
	
	
	
	
	@Override
	protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	  
	}
	
	class RefreshFirstList extends AsyncTask<Void, Void, Void>
	{
		@Override
    	protected void onPreExecute() {
    		
			if(navDrawerItems!=null)
			{
				navDrawerItems.clear();
			}
			
			mDrawerList.setScrollingWhileRefreshingEnabled(false);
    		new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mDrawerList.setRefreshing(true);
				}
			}, 500);
    		
    		
    		
    	};
  
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try
			{
				    navDrawerItems.add(0,new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

			        navDrawerItems.add(1,new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
			
			        navDrawerItems.add(2,new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
			   
			        navDrawerItems.add(3,new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
			    
			        navDrawerItems.add(4,new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
			    
			        navDrawerItems.add(5,new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
			        
			        navDrawerItems.add(6,new NavDrawerItem(navMenuTitles[6],navMenuIcons.getResourceId(6, -1)));
			        
			        navDrawerItems.add(7,new NavDrawerItem(navMenuTitles[7],navMenuIcons.getResourceId(7, -1)));
			        navDrawerItems.add(8,new NavDrawerItem(navMenuTitles[8],navMenuIcons.getResourceId(8, -1)));
			        navDrawerItems.add(9,new NavDrawerItem(navMenuTitles[9],navMenuIcons.getResourceId(9, -1)));
			        navDrawerItems.add(10,new NavDrawerItem(navMenuTitles[10],navMenuIcons.getResourceId(10, -1)));
			        navDrawerItems.add(11,new NavDrawerItem(navMenuTitles[11],navMenuIcons.getResourceId(11, -1)));
			        
			        
				messagescount.add(0, new Common().GetUnReadMessageCount(ScommixSharedPref.getUSERID()).get(0).name);
			
				friendcount.add(0, new Common().GetFriendCount(ScommixSharedPref.getUSERID()).get(0).friendcount);
				
				NoticeCount.add(0,new Common().GetNoticeCount(ScommixSharedPref.getINSTITUTEID()).get(0));
				
				if(Integer.parseInt(messagescount.get(0))>0)
				{
					navDrawerItems.set(4, new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1),true,messagescount.get(0)));

				}
			     if(Integer.parseInt(friendcount.get(0))>0)
				{
					navDrawerItems.set(5, new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1),true,friendcount.get(0)));

				}
				if(Integer.parseInt(NoticeCount.get(0).getNoticecount())>0)
				{
					navDrawerItems.set(11, new NavDrawerItem(navMenuTitles[11], navMenuIcons.getResourceId(11, -1),true,NoticeCount.get(0).getNoticecount()));

				}
				
				
			
			}
			catch(Exception e)
			{
				
			}
			
			
		 
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			 mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
			 runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					mDrawerList.onRefreshComplete();
					adapter = new NavDrawerListAdapter(getApplicationContext(),
			                navDrawerItems);

					mDrawer1.setAdapter(adapter);
				
                     adapter.notifyDataSetChanged();

				}
			});
			
		}
		
	}
	
	
	  @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	    	// TODO Auto-generated method stub
	    	super.onConfigurationChanged(newConfig);
	        mDrawerToggle.onConfigurationChanged(newConfig);

	    	if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
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
	    

	
	 void serialize() {
		
		SharedPreferences appSharedPrefs = PreferenceManager
				  .getDefaultSharedPreferences(getApplicationContext());
				  Editor prefsEditor = appSharedPrefs.edit();
				  Gson gson = new Gson();
				
				 
				  for(int i=0;i<appobj.getAllupdates().size();i++)
				  {
					  String updates = gson.toJson(appobj.getAllupdates().get(i));
					  String commentcount=gson.toJson(appobj.getAllcommentcountlist().get(i));
					  String liketa=gson.toJson(appobj.getHomeliketag().get(i));
					
					  String likecount=gson.toJson(appobj.getAlllikecountlist().get(i));
					  
					  prefsEditor.putString("updates"+i, updates);
					  prefsEditor.putString("commentcount"+i, commentcount);
					  prefsEditor.putString("liketa"+i, liketa);
					  prefsEditor.putString("likecount"+i, likecount);
					
				  }
				  
				  prefsEditor.putInt("size",appobj.getAllupdates().size());
				  prefsEditor.commit();
				 System.out.println("consumed");
			
				 
	}
	
	

		/**
	     * Slide menu item click listener
	     * */
	    private class SlideMenuClickListener implements
	            ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position,
	                long id) {
	      
                   displayView(position-2);
	           
	        }
	    }
	    
	  
	 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	   
               return true;
	    }
	    
	    
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // toggle nav drawer on selecting action bar app icon/title
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        // Handle action bar actions click
	        switch (item.getItemId()) {
	       
	        case R.id.notification:
	        {
	        	//openclick();
	        	
	        	if (item != null && item.getItemId() == R.id.notification) {
	                if (mDrawerLayout.isDrawerOpen(Gravity.START)==true) {
	                    mDrawerLayout.closeDrawer(Gravity.START);
	                    mDrawerLayout.openDrawer(Gravity.RIGHT);
	                }
	                if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)==false)
	                {
	                	   mDrawerLayout.openDrawer(Gravity.RIGHT);
	                }
	                if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)==true)
	                {
	                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
	                }
	               
	               
	            }
	        }
	        break;
	        case R.id.status:
	        {
	        	Intent tostatus=new Intent(MainActivity.this,Status.class);
	        	startActivity(tostatus);
	       }
	        break;
	        case R.id.logout:
	        	{
	                ClearLogoutTask task=new ClearLogoutTask(getApplicationContext());
	        		task.execute();
	        		
	        		Toast.makeText(MainActivity.this, "Wait Clearing Account...", Toast.LENGTH_SHORT).show();
	              
	        	}
	        	break;
	        case R.id.search:
	        {
	        	Intent i=new Intent(MainActivity.this,SearchResultsActivity.class);
	        	startActivity(i);
	          	
	        }
	        break;
	        case R.id.quit:
	        {
	        	finish();
	        }
	        break;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	        }
			return false;
	    }
	 
	 

	    @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // if nav drawer is opened, hide the action items
	
	       
	      //  menu.findItem(R.id.logout).setVisible(!drawerOpen);
	        return super.onPrepareOptionsMenu(menu);
	    }
	 
	    /**
	     * Displaying fragment view for selected nav drawer list item
	     * */
	    private void displayView(int position) {
	        // update the main content by replacing fragments
	        Fragment fragment = null;
	        switch (position) {
	        
	        case 0:
	            fragment = new HomeFragment(); 
	       	  actionbar.setSubtitle("Home");
	            break;
	        case 1:
	          
	        	Intent se=new Intent(MainActivity.this,MyProfileActivity.class);
	 	       startActivity(se);
	 	      
	            break;
	        case 2:
	       Intent s=new Intent(MainActivity.this,TimeTable.class);
	       startActivity(s);
	    
	            break;
	        case 3:
	 
	        	fragment=new HomeWorkmain();
	        	actionbar.setSubtitle("Home Work");
	            break;
	        case 4:
	        	fragment=new MessageFragment();
	        	actionbar.setSubtitle("Messages");
	            break;
	        case 5:
	        fragment=new FriendsListFragment();
	        actionbar.setSubtitle("Friend List");
	            break;
	        case 6:
	        	 Intent tofees=new Intent(MainActivity.this,FeeDetailActivity.class);
	  	       startActivity(tofees);
	
	            break;
	        case 7:
	        	fragment=new Results();
	        	actionbar.setSubtitle("Results");
	        	break;
	        case 8:
	       fragment=new AttendanceFragment();
	       actionbar.setSubtitle("Attendance");
	        	break;
	        	
	        case 9:
	        	fragment=new EventsFragment();
	        	actionbar.setSubtitle("Events");
	        break;
	        
	        case 10:
	        	fragment=new FriendsRequestsFragment();
	        	actionbar.setSubtitle("Friend Requests");
	        break;
	        
	        case 11:
	        	fragment=new Notice();
	        	actionbar.setSubtitle("Notice");
	        
	        default:
	            break;
	        }
	 
	        if (fragment != null) {
	        	
	        	if(position==4)
	        	{
	        		
	        	}
	        	
	        	  FragmentManager fragmentManager = getSupportFragmentManager();
	        	
	        	    fragmentManager.beginTransaction()
	  	                    .replace(R.id.frame_container, fragment).commit();
	        	
	            // update selected item and title, then close the drawer
	            mDrawer1.setItemChecked(position, true);
	            mDrawer1.setSelection(position);
	            	setTitle(navMenuTitles[position]);
	            mDrawerLayout.closeDrawer(mDrawerList);
	            
	       
	            	   
	        } else {
	            // error in creating fragment
	           // Log.e("MainActivity", "Error in creating fragment");
	        }
	    }
	 
	    @Override
	    public void setTitle(CharSequence title) {
	        mTitle = title;
	        actionbar.setTitle(mTitle);
	    }
	 
	    /**
	     * When using the ActionBarDrawerToggle, you must call it during
	     * onPostCreate() and onConfigurationChanged()...
	     */
	 
	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }
	 
	    
	
	   
	  @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	
		  AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
		  alert.setTitle("Do you want to exit?");
		  alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//serialize();
			finish();
			}
		});
		  alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			
			}
		});
		  alert.show();
		//super.onBackPressed();
	}
	  


	


}
