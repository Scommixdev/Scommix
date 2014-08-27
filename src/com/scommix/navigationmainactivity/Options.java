package com.scommix.navigationmainactivity;

import java.util.ArrayList;

import com.scommix.WebServices.Common.Common;
import com.scommix.feedetails.FeeDetailActivity;
import com.scommix.homeandprofile.HomeFragment;
import com.scommix.homeandprofile.MyProfileActivity;

import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class Options extends Fragment{
	
	private ArrayList<String> messagescount;
	private ArrayList<String> friendcount;
    private ListView mDrawerList;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    NavDrawerListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView=inflater.inflate(R.layout.options, container,false);
		   mDrawerList = (ListView)rootView.findViewById(R.id.list_slidermenu);
		   navDrawerItems = new ArrayList<NavDrawerItem>();
	        // load slide menu items
	        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
	        messagescount=new ArrayList<String>();
	        messagescount.add(0, "0");
	        
	        friendcount=new ArrayList<String>();
	        friendcount.add(0, "0");
	        // nav drawer icons from resources
	        navMenuIcons = getResources()
	                .obtainTypedArray(R.array.nav_drawer_icons);
		  new AsyncTask<Void, Void, Void>()
	        {
	        	@Override
	        	protected void onPreExecute() {
	        		
	        		

	        		
	        		
	        		
	        	};
	      
				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					
					try
					{
						 navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
					
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
					   
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
					    
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
					    
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
					        
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6],navMenuIcons.getResourceId(6, -1)));
					        
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7],navMenuIcons.getResourceId(7, -1)));
					        
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8],navMenuIcons.getResourceId(8, -1)));
					        
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[9],navMenuIcons.getResourceId(9, -1)));
					        navDrawerItems.add(new NavDrawerItem(navMenuTitles[10],navMenuIcons.getResourceId(10, -1)));
						messagescount.add(0, new Common().GetUnReadMessageCount(ScommixSharedPref.getUSERID()).get(0).name);
					
						friendcount.add(0, new Common().GetFriendCount(ScommixSharedPref.getUSERID()).get(0).friendcount);
						
						if(Integer.parseInt(messagescount.get(0))>0)
						{
							navDrawerItems.set(4, new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1),true,messagescount.get(0)));

						}
						else if(Integer.parseInt(friendcount.get(0))>0)
						{
							navDrawerItems.set(5, new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1),true,friendcount.get(0)));

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
					 
					 
					 try{
						 
						 getActivity().runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									adapter = new NavDrawerListAdapter(getActivity().getApplicationContext(),
							                navDrawerItems);

							       mDrawerList.setAdapter(adapter);
								
			                         adapter.notifyDataSetChanged();

								}
							});
					 }
					 catch(Exception e)
					 {
						 e.printStackTrace();
					 }
				
					
				};
	        	
	        }.execute();
	        
	        
		return rootView;
	}
	
	/**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
      
               displayView(position);
           
        }
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
       
            break;
        case 1:
          
        	Intent se=new Intent(getActivity(),MyProfileActivity.class);
 	       startActivity(se);
 	      
            break;
        case 2:
       Intent s=new Intent(getActivity(),TimeTable.class);
       startActivity(s);
    
            break;
        case 3:
 
        	fragment=new HomeWorkmain();
       
            break;
        case 4:
        	fragment=new MessageFragment();
        	
            break;
        case 5:
        fragment=new FriendsListFragment();
  
            break;
        case 6:
        	 Intent tofees=new Intent(getActivity(),FeeDetailActivity.class);
  	       startActivity(tofees);

            break;
        case 7:
        	fragment=new Results();
        	
        	break;
        case 8:
       fragment=new AttendanceFragment();
    
        	break;
        	
        case 9:
        	fragment=new EventsFragment();
        	
        break;
        
        case 10:
        	fragment=new FriendsRequestsFragment();
        	
        break;
        
        default:
            break;
        }
 
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
 
            // update selected item and title, then close the drawer
            		
        
            
            mDrawerList.setItemChecked(position, true);
            	mDrawerList.setSelection(position);
            
          
            
       
            	   
        } else {
            // error in creating fragment
           // Log.e("MainActivity", "Error in creating fragment");
        }
    }

}
