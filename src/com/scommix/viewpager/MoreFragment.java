package com.scommix.viewpager;

import java.io.File;
import java.util.ArrayList;



import com.google.android.gms.internal.a;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.StuNotice;
import com.scommix.asynctasks.ClearLogoutTask;
import com.scommix.feedetails.FeeDetailActivity;
import com.scommix.homeandprofile.HomeFragment;
import com.scommix.homeandprofile.MyProfileActivity;
import com.scommix.navigationmainactivity.AttendanceFragment;
import com.scommix.navigationmainactivity.EventsFragment;
import com.scommix.navigationmainactivity.FriendsListFragment;
import com.scommix.navigationmainactivity.FriendsRequestsFragment;
import com.scommix.navigationmainactivity.HomeWorkmain;
import com.scommix.navigationmainactivity.MainActivity;
import com.scommix.navigationmainactivity.MessageFragment;
import com.scommix.navigationmainactivity.NavDrawerItem;
import com.scommix.navigationmainactivity.NavDrawerListAdapter;
import com.scommix.navigationmainactivity.Notice;
import com.scommix.navigationmainactivity.Results;
import com.scommix.navigationmainactivity.TimeTable;

import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.LoginFirstRun;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;
import com.svimedu.scommix.ScommixContentProvider;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MoreFragment extends Fragment {
	
	        private PullToRefreshListView mDrawerList;
	        private ListView mDrawer1;
		    RefreshFirstList taskRefreshfirst;
			private ArrayList<String> messagescount;
			private ArrayList<String> NoticeCount;
			private ArrayList<String> friendcount;
		    private String[] navMenuTitles;
		    private TypedArray navMenuIcons;
	        private ArrayList<NavDrawerItem> navDrawerItems;
		    NavDrawerListAdapter adapter;
		    MyProfilePagerAdapter1 mAdapter;
		
		    
		    MainApp appobj;
		    ArrayList<Fragment> myFragments;
			private ImageView profilepicimage;
			private TextView userkanaam;
			private String name;
			private String profilepic;
		
		    

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView=inflater.inflate(R.layout.morelayout, container,false);
		 mDrawerList = (PullToRefreshListView)rootView.findViewById(R.id.list_slidermenu);
		  
		   mDrawer1=mDrawerList.getRefreshableView();
		   
		appobj=(MainApp)getActivity().getApplication();
		myFragments=appobj.getMyFragments();
		
		LayoutInflater infl=getActivity().getLayoutInflater();
		ViewGroup header = (ViewGroup)infl.inflate(R.layout.header, mDrawer1, false);
		mDrawer1.addHeaderView(header);
		
	    profilepicimage=(ImageView)header.findViewById(R.id.profilepic);
	    userkanaam=(TextView)header.findViewById(R.id.headerusername);
	    
	    String[] projection={ScommixContentProvider.NAME,ScommixContentProvider.PROFILEPIC};
	    
	  Cursor c=  getActivity().getContentResolver().query(ScommixContentProvider.CONTENT_URI_UserInfo, projection, null, null, null);
				  
	    
      System.out.println(c.getCount());
	    if (c.moveToFirst()) {
            do {
            	 try{
           	  	  name=c.getString(c.getColumnIndexOrThrow(ScommixContentProvider.NAME));

           	    }
           	    catch(Exception e)
           	    {
           	    	name="undefined";
           	    }
           	    
             try{
           	  profilepic=c.getString(c.getColumnIndexOrThrow(ScommixContentProvider.PROFILEPIC));
           	
           	    }
           	    catch(Exception e)
           	    {
           	    	profilepic="undefined";
           	    }
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        
   	
		   
		   
	    userkanaam.setText(name);
	 
	    
	    getActivity().runOnUiThread(new Runnable() {
	    	
	    	@Override
	    	public void run() {
	    		// TODO Auto-generated method stub
	    		
	    		 Picasso.with(getActivity()) //
	    	        .load(Utils.url+profilepic) //
	    	        .placeholder(R.drawable.ic_loading) //
	    	        .error(R.drawable.cross) 
	    	        .transform(new RoundTrans())
	    	        .fit() //
	    	        .into(profilepicimage);
	    		
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
			 
			  messagescount=new ArrayList<String>();
		        NoticeCount=new ArrayList<String>();
		       
		        NoticeCount.add(0,"0");
		        
		        messagescount.add(0, "0");
		        
		        friendcount=new ArrayList<String>();
		        friendcount.add(0, "0");
		        // nav drawer icons from resources
		        navDrawerItems = new ArrayList<NavDrawerItem>();
		        navMenuIcons = getResources()
		                .obtainTypedArray(R.array.nav_drawer_images);
		       navMenuTitles=getResources().getStringArray(R.array.nav_drawer_text);
		
		   taskRefreshfirst=new RefreshFirstList();
			 taskRefreshfirst.execute();
		return rootView;
	}

	
	class RefreshFirstList extends AsyncTask<Void, Void, Void>
	{
		@Override
    	protected void onPreExecute() {
    		
			if(navDrawerItems!=null)
			{
				navDrawerItems.clear();
			}
			
			
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
			       
			        navDrawerItems.add(8, new NavDrawerItem("Logout",navMenuIcons.getResourceId(8, -1)));		
			        
				messagescount.add(0, new Common().GetUnReadMessageCount(ScommixSharedPref.getUSERID()).get(0).name);
			
				friendcount.add(0, new Common().GetFriendCount(ScommixSharedPref.getUSERID()).get(0).friendcount);
				
				NoticeCount.add(0,new Common().GetNoticeCount(ScommixSharedPref.getINSTITUTEID()).get(0).noticecount);
				
	
			     if(Integer.parseInt(friendcount.get(0))>0)
				{
					navDrawerItems.set(2, new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1),true,friendcount.get(0)));

				}
				if(Integer.parseInt(NoticeCount.get(0))>0)
				{
					navDrawerItems.set(7, new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1),true,NoticeCount.get(0)));

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
			 mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
			 
			 
			 try{
				 getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							mDrawerList.onRefreshComplete();
							adapter = new NavDrawerListAdapter(getActivity(),
					                navDrawerItems);

							mDrawer1.setAdapter(adapter);
						
		                     adapter.notifyDataSetChanged();

						}
					});
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
		
			
		}
		
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
    
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        
        case -1:
        {
        	Intent se=new Intent(getActivity(),MyProfileActivity.class);
	 	       startActivity(se);
        }
       break;
        case 0:
       Intent s=new Intent(getActivity(),TimeTable.class);
       startActivity(s);
    
            break;
        case 1:
 
        	fragment=new HomeWorkmain();
//        	actionbar.setSubtitle("Home Work");
            break;
       
        case 2:
       fragment=new FriendsListFragment();
//        actionbar.setSubtitle("Friend List");
            break;
        case 3:
       	 Intent tofees=new Intent(getActivity(),FeeDetailActivity.class);
  	       startActivity(tofees);

            break;
        case 4:
        	fragment=new Results();
//        	actionbar.setSubtitle("Results");
        	break;
        case 5:
       fragment=new AttendanceFragment();
//       actionbar.setSubtitle("Attendance");
        	break;
        	
        case 6:
        	fragment=new EventsFragment();
//        	actionbar.setSubtitle("Events");
        break;
        
       
        
        case 7:
       	fragment=new Notice();
//        	actionbar.setSubtitle("Notice");
        
        default:
            break;
            
        case 8:

     
    		
        Toast.makeText(getActivity(), "Logout...", Toast.LENGTH_SHORT).show();
		 new ClearLogoutTask(getActivity().getApplicationContext()).execute();
    		
    	break;
        }
 
        if (fragment != null) {
        	
        	FragmentTransaction trans = getFragmentManager()
					.beginTransaction();
			/*
			 * IMPORTANT: We use the "root frame" defined in
			 * "root_fragment.xml" as the reference to replace fragment
			 */
			trans.replace(R.id.root_frame, fragment);

			/*
			 * IMPORTANT: The following lines allow us to add the fragment
			 * to the stack and return to it later, by pressing back
			 */
			trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			trans.addToBackStack(null);

			trans.commit();
            ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(navMenuTitles[position]);
          
            
       
            	   
        } else {
            // error in creating fragment
           Log.e("MainActivity", "Error in creating fragment");
        }
    }
    
    public int clearApplicationData() 
    {
   
      File cache = getActivity().getApplicationContext().getCacheDir();
      File appDir = new File(cache.getParent());
       if (appDir.exists()) {
       String[] children = appDir.list();
       for (String s : children) {
           if (!s.equals("lib")) {
               deleteDir(new File(appDir, s));
               Log.i("TAG", "SCommix Data" + s + " DELETED ");
           }
       }
       
   }
	return 1;
}

   public static boolean deleteDir(File dir) {
       if (dir != null && dir.isDirectory()) {
           String[] children = dir.list();
           for (int i = 0; i < children.length; i++) {
               boolean success = deleteDir(new File(dir, children[i]));
               if (!success) {
                   return false;
               }
           }
       }
       return dir.delete();
   }
}
