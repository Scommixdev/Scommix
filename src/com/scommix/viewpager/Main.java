		package com.scommix.viewpager;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.internal.widget.ScrollingTabContainerView.TabView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.asynctasks.ClearLogoutTask;
import com.scommix.friendsandsearch.SearchResultsActivity;
import com.scommix.homeandprofile.HomeFragment;
import com.scommix.homeandprofile.MyProfilePagerAdapter;
import com.scommix.homeandprofile.Status;
import com.scommix.navigationmainactivity.FriendsRequestsFragment;
import com.scommix.navigationmainactivity.MainActivity;
import com.scommix.navigationmainactivity.MessageFragment;
import com.scommix.sharedpref.ScommixSharedPref;

import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;

public class Main extends ActionBarActivity implements ActionBar.TabListener{

	ActionBar actionBar;
	private ViewPager viewPager;
	private MyProfilePagerAdapter1 mAdapter;
//    private String[] tabs = { "Home", "Profile","Time Table","Home Work","Messages","Friends List","Fee Details","Results","Attendance","Events","Friends Request"};
    private int[] tabs = { R.drawable.home_tab_drawable, R.drawable.firendreq_tab_drawable,R.drawable.messages_tab_drawable,R.drawable.notification_tab_drawable,R.drawable.ic_drawer_tab_drawable};

    ArrayList<Fragment> myfragments;
    
	private MainApp appobj;
	private ImageView iv1;
	private TextView tv1;
	private ImageView iv2;
	private TextView tv2;
	private ImageView iv3;
	private TextView tv3;
	private ImageView iv4;
	private TextView tv4;
	private ImageView iv5;
	private TextView tv5;
	
	
	private String frndreqcount;
	protected String unreadmessagecount;
	NotificationBroadcast receiver;
	IntentFilter filter;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
super.onCreate(savedInstanceState);
setContentView(R.layout.myprofileactivity);

ScommixSharedPref.setFirstRun(false);
receiver=new NotificationBroadcast();
filter=new IntentFilter();
filter.addAction("notificationcount");
filter.addCategory(Intent.CATEGORY_DEFAULT);

 viewPager = (ViewPager) findViewById(R.id.pager);
    actionBar = getSupportActionBar();
   // actionBar.setDisplayHomeAsUpEnabled(true);
   
    appobj=(MainApp)getApplication();
    
    myfragments=appobj.getMyFragments();
   
    mAdapter = new MyProfilePagerAdapter1(getSupportFragmentManager(),myfragments);

    viewPager.setAdapter(mAdapter);
    viewPager.setBackgroundColor(getResources().getColor(android.R.color.white));
    viewPager.setOffscreenPageLimit(4);
    myfragments.add(0,new HomeFragment());

    myfragments.add(1,new FriendsRequestsFragment());
    
    myfragments.add(2,new MessageFragment());
	
    myfragments.add(3,new NotificationFragment());
	
    myfragments.add(4,new RootFragment());
    
 //  viewPager.setBackgroundColor(getResources().getColor(android.R.color.white));
    
    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    	 
        @Override
        public void onPageSelected(int position) {
            // on changing the page
            // make respected tab selected
            actionBar.setSelectedNavigationItem(position);
            if(position==0)
            {
            	actionBar.setTitle("Home");
            }
            if(position==1)
            {
            	actionBar.setTitle("Friend Requests");

            	//tv2.setVisibility(View.GONE);
            }
            if(position==2)
            {
            	actionBar.setTitle("Messages");

            	//tv3.setVisibility(View.GONE);
            	
            }
            if(position==3)
            {
            	actionBar.setTitle("Notifications");

            	//tv4.setVisibility(View.GONE);
            }
            if(position==4)
            {
            	actionBar.setTitle("More");

            }
            
        }
     
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
     
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    });

	LayoutInflater inflater=getLayoutInflater();
	
	View homeview=inflater.inflate(R.layout.custom_tab, null);
	iv1=(ImageView)homeview.findViewById(R.id.tabicon);
	tv1=(TextView)homeview.findViewById(R.id.tabtext);
	iv1.setImageResource(tabs[0]);
	tv1.setVisibility(View.GONE);
	
	View profile=inflater.inflate(R.layout.custom_tab, null);
	iv2=(ImageView)profile.findViewById(R.id.tabicon);
	tv2=(TextView)profile.findViewById(R.id.tabtext);
	iv2.setImageResource(tabs[1]);
	
	
	View message=inflater.inflate(R.layout.custom_tab, null);
	iv3=(ImageView)message.findViewById(R.id.tabicon);
	tv3=(TextView)message.findViewById(R.id.tabtext);
	iv3.setImageResource(tabs[2]);
	
	
	View notification=inflater.inflate(R.layout.custom_tab, null);
	iv4=(ImageView)notification.findViewById(R.id.tabicon);
	tv4=(TextView)notification.findViewById(R.id.tabtext);
	iv4.setImageResource(tabs[3]);
	
	View more=inflater.inflate(R.layout.custom_tab, null);
	iv5=(ImageView)more.findViewById(R.id.tabicon);
	tv5=(TextView)more.findViewById(R.id.tabtext);
	iv5.setImageResource(tabs[4]);
	tv5.setVisibility(View.GONE);
	
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
    
    Tab hometab=actionBar.newTab().setCustomView(homeview).setTabListener(this);
    Tab profiletab=actionBar.newTab().setCustomView(profile).setTabListener(this);
    Tab messagetab=actionBar.newTab().setCustomView(message).setTabListener(this);
    Tab notificationtab=actionBar.newTab().setCustomView(notification).setTabListener(this);
    Tab moretab=actionBar.newTab().setCustomView(more).setTabListener(this);
    
    
    actionBar.addTab(hometab);
    actionBar.addTab(profiletab);
    actionBar.addTab(messagetab);
    actionBar.addTab(notificationtab);
    actionBar.addTab(moretab);
    
  
    	
    
    	
       
    

    
    new AsyncTask<Void, Void, Void>() {
    	
    	

		@Override
        protected Void doInBackground(Void... params) {
    	// TODO Auto-generated method stub
    		ArrayList<online> count=new ArrayList<online>();
    		
    		try{
    	 		
        		count=new Common().GetFriendRequestCount(ScommixSharedPref.getUSERID());
        		unreadmessagecount=new Common().GetUnReadMessageCount(ScommixSharedPref.getUSERID()).get(0).name;
        		frndreqcount=count.get(0).name;
        		
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
    		
    		try{
    			if(Integer.parseInt(frndreqcount)>0)
        		{
        			tv2.setText(frndreqcount);
        			tv2.setVisibility(View.VISIBLE);
        		}
        		else{
        			tv2.setVisibility(View.GONE);
        		}
        		
        		if(Integer.parseInt(unreadmessagecount)>0)
        		{
        			tv3.setText(unreadmessagecount);
        			tv2.setVisibility(View.VISIBLE);
        		}
        		else{
        			
        			tv3.setVisibility(View.GONE);
        		}
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    		
    		
    		
    		
    		
    		
    	}
   
    
	}.execute();

    
}

@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	registerReceiver(receiver, filter);
}

@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
	unregisterReceiver(receiver);
}

	



@Override
public void onConfigurationChanged(Configuration newConfig) {
	// TODO Auto-generated method stub
	super.onConfigurationChanged(newConfig);
	
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

@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	
	
	if(viewPager.getCurrentItem()>0)
	{
		
		if(getSupportFragmentManager().getBackStackEntryCount()>0)
		{
			super.onBackPressed();
			actionBar.setTitle("More");
		}
		else{
			viewPager.setCurrentItem(0);
			actionBar.setTitle("Home");
		}
	
	}
	else if(viewPager.getCurrentItem()==0)
	{
		super.onBackPressed();
	}
	
}


@Override
public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
// TODO Auto-generated method stub
	if(getSupportFragmentManager().getBackStackEntryCount()>0)
	{
		super.onBackPressed();
	}
}
@Override
public void onTabSelected(Tab tab, FragmentTransaction arg1) {
// TODO Auto-generated method stub
viewPager.setCurrentItem(tab.getPosition());

if(tab.getPosition()==0)
{
	actionBar.setTitle("Home");
}
if(tab.getPosition()==1)
{
	actionBar.setTitle("Friend Requests");

	tv2.setVisibility(View.GONE);
}
if(tab.getPosition()==2)
{
	actionBar.setTitle("Messages");

	tv3.setVisibility(View.GONE);
	
}
if(tab.getPosition()==3)
{
	actionBar.setTitle("Notifications");

	tv4.setVisibility(View.GONE);
}
if(tab.getPosition()==4)
{
	actionBar.setTitle("More");

}

}

@Override
public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
// TODO Auto-generated method stub

}




@Override
public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main1, menu);

       return true;
}



@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // toggle nav drawer on selecting action bar app icon/title
   
    // Handle action bar actions click
    switch (item.getItemId()) {
   
    
    case R.id.status:
    {
    	Intent tostatus=new Intent(Main.this,Status.class);
    	startActivity(tostatus);
   }
    break;

    case R.id.search:
    {
    	Intent i=new Intent(Main.this,SearchResultsActivity.class);
    	startActivity(i);
      	
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


class NotificationBroadcast extends BroadcastReceiver
{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		
		int value=arg1.getIntExtra("count", 0);
		
		tv4.setText(""+value);
		if(value>0)
		{
			tv4.setVisibility(View.VISIBLE);
		}
		
		
		
	}
	
}







}
