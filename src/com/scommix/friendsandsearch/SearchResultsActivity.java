package com.scommix.friendsandsearch;



import java.util.ArrayList;

import org.kobjects.base64.Base64;


import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.FriendSearchAdapter;
import com.scommix.navigationmainactivity.FriendsRequestsFragment.GetFriendRequest;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;


import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;




public class SearchResultsActivity extends ActionBarActivity implements OnQueryTextListener {
	
	
	SearchFriendTask taskSearchFriend;
	String query;
	ActionBar action;

    boolean success=true;
    PullToRefreshGridView txtQuerylist;
	 GridView mGridView;
    ArrayList<String> friendidl,emailidl,cityl,namel,usernamel,classidl;
    ArrayList<String> userpic;
  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_file);
 
        // get the action bar
        action = getSupportActionBar();
        
        action.setDisplayHomeAsUpEnabled(true);
       
       txtQuerylist = (PullToRefreshGridView) findViewById(R.id.searchresultlist);
        mGridView=txtQuerylist.getRefreshableView();
        
        LayoutInflater inflate=getLayoutInflater();
		View emptylayout=inflate.inflate(R.layout.view_empty, null);
		mGridView.setEmptyView(emptylayout);
		Button tryagain=(Button)findViewById(R.id.buttonEmpty);
		tryagain.setText("No Search Results...");
        
        txtQuerylist.setOnItemClickListener(new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			String id=	friendidl.get(arg2);
			if(id.equals(ScommixSharedPref.getUSERID()))
			{
				finish();
			}
			else{
				Intent tofriendprofile=new Intent(SearchResultsActivity.this,FriendActivity.class);
				tofriendprofile.putExtra("id", id);
				
				startActivity(tofriendprofile);
			}
				
			}
		});
        friendidl=new ArrayList<String>();
		emailidl=new ArrayList<String>();
		cityl=new ArrayList<String>();
		namel=new ArrayList<String>();
		usernamel=new ArrayList<String>();
		classidl=new ArrayList<String>();
      userpic=new ArrayList<String>();
        
	    	
			
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
    public boolean onCreateOptionsMenu(Menu menu) {
    	 getMenuInflater().inflate(R.menu.searchfriendsmenu, menu);
        
        MenuItem item=menu.findItem(R.id.searchit);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
               (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =(SearchView)MenuItemCompat.getActionView(item);
       searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);   
       searchView.setQueryHint("Search Friends");
       	searchView.setSoundEffectsEnabled(true);
       	
       searchView.setOnQueryTextListener(this);
       
        return true;
    }
 
    
  
    @Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	        	this.finish();
	        	
	            return true;
	            
	            default:
	            return super.onOptionsItemSelected(item); 
	    }
	}



	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		if(newText.equals(""))
		{
			friendidl.clear();
			emailidl.clear();
			cityl.clear();
			namel.clear();
			usernamel.clear();
			classidl.clear();
			userpic.clear();
			
		}
		return false;
	}
	
	



	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		if(friendidl.size()>0)
		{
			friendidl.clear();
			emailidl.clear();
			cityl.clear();
			namel.clear();
			usernamel.clear();
			classidl.clear();
			userpic.clear();
			txtQuerylist.setVisibility(View.GONE);
		}
	
		 taskSearchFriend=new SearchFriendTask(query);
         if(taskSearchFriend.getStatus()==AsyncTask.Status.RUNNING)
			{
				 
			}
			else if(taskSearchFriend.getStatus()==AsyncTask.Status.PENDING)
			{
				taskSearchFriend.execute();
			}
			else if(taskSearchFriend.getStatus()==AsyncTask.Status.FINISHED)
			{
				new SearchFriendTask(query).execute();		            
			}
		return false;
	}
	
	public class SearchFriendTask extends AsyncTask<Void, Void, Void>{


	
		PullToRefreshGridView txtQuerylist;
	
		
	    FriendSearchAdapter adapter;
	    ProgressDialog pd;
	    String query;

	    public SearchFriendTask(String query) {
			// TODO Auto-generated constructor stub
	    	this.query=query;
		}
	    
	    @Override
	    protected void onPreExecute() {
	    	// TODO Auto-generated method stub
	    	
	    	super.onPreExecute();
	    	success=true;
	    	pd=new ProgressDialog(SearchResultsActivity.this);
	    	pd.setCancelable(false);
	    	pd.setMessage("Searching...");
	    	pd.show();
	    
	    }
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
		
					  Common c=new Common();
					  ArrayList<online> arr=new ArrayList<online>();
					  arr=c.GetSearchResult(query,ScommixSharedPref.getINSTITUTEID());
					  System.out.println(arr.size()+query);
	          
	           for(int i=0;i<arr.size();i++)
	           { 
	   
	             friendidl.add(i, arr.get(i).friendid);
	         	 emailidl.add(i, arr.get(i).email);
	             cityl.add(i,arr.get(i).city);
	             namel.add(i, arr.get(i).name);
	             usernamel.add(i, arr.get(i).username);
	             classidl.add(i, arr.get(i).classIdField);
	           
	            	 
	                 userpic.add(i,arr.get(i).userpic);

	             
	            
	             
	            
	           }
	          
		  	
				}
				catch(Exception e)
				{
				
					e.printStackTrace();
					success=false;
					
				}
				
			
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			
			if(success==true)
			{
				
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run()
					{
						// TODO Auto-generated method stub
						if(friendidl.size()==0)
						{
						     
						}
						else{
							adapter=new FriendSearchAdapter(SearchResultsActivity.this,friendidl,emailidl,cityl,namel,usernamel,classidl,userpic);
							mGridView.setAdapter(adapter);	
						}
						
					}
				});
			}
			else{
				Toast.makeText(SearchResultsActivity.this, "Nothing here...", Toast.LENGTH_SHORT).show();
			}
		
		}

	}
}
