package com.scommix.homeandprofile;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.asynctasks.UpdateUserProfilePic;
import com.scommix.friendsandsearch.FriendProfileFragment;


import com.scommix.sharedpref.ScommixSharedPref;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;
import com.svimedu.scommix.ScommixContentProvider;
import com.svimedu.scommix.Welcome;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

// PROFILE ACTIVITY

public class MyProfileFragment extends Fragment  {
	 ContentResolver resolver;
    
     private static final int PICK_FROM_GALLARY = 2;
 	 ScommixSharedPref pref;
 	 ImageView profilepic;
 	 MyProfileAdapter adapter;
    
	 ProgressBar progressbar;

	RelativeLayout headerprofile;

	 ExpandableListView lv;

		 String institueprofilepic;

	
	 byte[] byteArray;
	 Common service1;
	 View profileheader;
	
	 MainApp appobject;
	
	 View rootView;
     ArrayList<String> header;
     HashMap<String,ArrayList<String>> hash;
	 Bitmap myBitmap;
	 String nameofuser,city,emailofuser,state;
	private String gender;
	private String dateofbirth;
	private String country;
	private String fathersname;
	private String mothersname;
	private String fathersoccupation;
	private String mothersoccupation;
	private String siblingsno;
	private String mobileno;
	private TextView userkanaam;
	private String profilepicname;
	
	public GetUserDetail taskGetUserDetail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView=inflater.inflate(R.layout.profilelayout, container,false);
		pref=ScommixSharedPref.getInstance(getActivity());
		if(pref.getUSERID().equals(""))
		{
			Intent i=new Intent(getActivity(),Welcome.class);
			startActivity(i);
			getActivity().finish();
		}
		else
		
		{
			
			lv=(ExpandableListView)rootView.findViewById(R.id.expandableprofile);
			LayoutInflater infl=getActivity().getLayoutInflater();
			ViewGroup header = (ViewGroup)infl.inflate(R.layout.header, lv, false);
			lv.addHeaderView(header);
			
		    profilepic=(ImageView)header.findViewById(R.id.profilepic);
		    userkanaam=(TextView)header.findViewById(R.id.headerusername);
		    headerprofile=(RelativeLayout)header.findViewById(R.id.headerprofile);
		  
		  
		    javacode();
		    taskGetUserDetail=new GetUserDetail();
		    taskGetUserDetail.execute();
		    Intent intent = getActivity().getIntent();
		    String action = intent.getAction();
		    String type = intent.getType();
		    
		    if (Intent.ACTION_SEND.equals(action) && type != null) {
		         if (type.startsWith("image/")) {
		            handleSendImage(intent); // Handle single image being sent
		        }
		    }
		}

	    
		return rootView;
	}
	
	 @Override
	 public void onPause() {
	 	// TODO Auto-generated method stub
	 	 
	 	 taskGetUserDetail.cancel(true);
	 	super.onPause();
	 }
	private void handleSendImage(Intent intent) {
		// TODO Auto-generated method stub
		final Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	    if (imageUri != null) {
	        // Update UI to reflect image being shared
	    	
	    	 String what="profilepic";
	    	  Intent i=new Intent(getActivity(),CropImageActivity.class);
	    	  i.putExtra("picuri", getPath(imageUri));
	    	  i.putExtra("what",what);
	    	  startActivityForResult(i, 124);
				}
	    	 
	    	
	    	
	    }
	

	private void preparedata() {
		// TODO Auto-generated method stub
		header=new ArrayList<String>();
		hash=new HashMap<String, ArrayList<String>>();
		
		header.add(0, "General Profile");
		header.add(1, "Personal Information");
		
		
		ArrayList<String> generaldata=new ArrayList<String>();
		generaldata.add(0,"Name:      "+nameofuser);
		generaldata.add(1,"Gender:    "+gender);
		generaldata.add(2,"D.O.B:     "+dateofbirth);
		generaldata.add(3,"Email:     "+emailofuser);
		generaldata.add(4, "City:        "+city);
		generaldata.add(5,"Country:   "+state);
		
		ArrayList<String> personaldata=new ArrayList<String>();
		personaldata.add(0, "Fathers Name:      "+fathersname);
		personaldata.add(1,"Mothers Name:       "+mothersname);
		personaldata.add(2,"Fathers Occupation: "+fathersoccupation);
		personaldata.add(3,"Mothers Occupation: "+mothersoccupation);
		personaldata.add(4,"Number Of Siblings: "+siblingsno);
		personaldata.add(5,"Contact No:      "+mobileno);
		
		hash.put(header.get(0), generaldata);
		hash.put(header.get(1), personaldata);
		
		adapter=new MyProfileAdapter(getActivity(),header,hash);
		lv.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	
	}

	

	private void javacode() {
		// TODO Auto-generated method stub
		appobject=(MainApp)getActivity().getApplicationContext();
		 resolver=getActivity().getContentResolver();
		 
		
	
	}

	
	class GetUserDetail extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
	
		private String profilebytearray;



		
		@Override
		protected void onPreExecute() {
			
		Log.i("Get all user info", "get all user basic info");
	
		pd=new ProgressDialog(getActivity());
		pd.setMessage("Wait...");
		pd.setCancelable(true);
		pd.show();
		};
		
		@Override
		protected Void doInBackground(Void... params) {
			
			try {
			Common c=new Common();
			ArrayList<online> resp=new ArrayList<online>();
			resp=c.GetUserDetailnew(ScommixSharedPref.getUSERID());
			
			for(int i=0;i<resp.size();i++)
			{
				if(resp.get(i).fathername==null)
				{
					fathersname="N/A";
				}
				else
				{
					fathersname=resp.get(i).fathername;
				}
			
			
				mothersname=resp.get(i).mothername;
				
				if(mothersname==null)
				{
					mothersname="N/A";
				}
				mobileno=resp.get(i).mobileno;
				
				if(mobileno==null)
				{
					mobileno="N/A";
				}
				
				
				siblingsno=resp.get(i).numberofsiblings;
				
				if(siblingsno==null)
				{
					siblingsno="N/A";
				}
				
				fathersoccupation=resp.get(i).fatheroccupation;
				
				
				if(fathersoccupation==null)
				{
					fathersoccupation="N/A";
				}
				
				
				
				mothersoccupation=resp.get(i).motheroccupation;
				
				if(mothersoccupation==null)
				{
					mothersoccupation="N/A";
				}
				
				if(fathersoccupation==null)
				{
					fathersoccupation="N/A";
				}
				
				String fm = " ",lm = " ";
				
					fm=resp.get(i).firstname;
				
			
					
					lm=resp.get(i).lastname;

				
				if(lm==null)
				{
					lm=" ";
				}
				nameofuser=fm+" "+lm;
				
				gender=resp.get(i).gender;
				if(gender==null)
				{
					gender="N/A";
				}
				dateofbirth=resp.get(i).dob;
				if(dateofbirth==null)
				{
					dateofbirth="N/A";
				}
				emailofuser=resp.get(i).email;
				if(emailofuser==null)
				{
					emailofuser="N/A";
				}
				city=resp.get(i).city;
				if(city==null)
				{
					city="N/A";
				}
				
				state=resp.get(i).countryname;
				
				if(state==null)
				{
					state="N/A";
				}
				
				profilebytearray= resp.get(i).userpic;
				
			
			}
			

			preparedata();
			
		    } 
			
			catch (Exception e)
			
			{
				Log.i("problem", e.toString());
			}
			return null;
		
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
		pd.dismiss();
		userkanaam.setText(nameofuser);
		
		adapter=new MyProfileAdapter(getActivity(),header,hash);
			lv.setAdapter(adapter);
			
			ContentValues cv=new ContentValues();
			
			 cv.put("PROFILEPIC", profilebytearray);
			getActivity().getContentResolver().update(ScommixContentProvider.CONTENT_URI_UserInfo, cv, "USERID "+"= '"+ScommixSharedPref.getUSERID()+"'", null);
			
getActivity().runOnUiThread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		 Picasso.with(getActivity()) //
	        .load(Utils.url+profilebytearray) //
	        .transform(new RoundTrans())
	        .placeholder(R.drawable.ic_loading) //
	        .error(R.drawable.cross) 
	        
	        .fit() //
	        .into(profilepic);
		
	}
});
			
		}
		
	}
	




		
	  	 
	
	
	
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	 Log.i("activity result", "called");
	  //if request code is same we pass as argument in startActivityForResult
	 
	  switch(requestCode)
	  {

	  case 124:
	  {
		 
		  if(data!=null&& resultCode==124)
		  {
			 
			final String filename=ScommixSharedPref.getPROFILEPICNAME();
			
			new AsyncTask<Void, Void, Void>(){

				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					BitmapFactory.Options options=null;
			 	      options = new BitmapFactory.Options();
			 	     options.inSampleSize = 2;
			 	    
				myBitmap = BitmapFactory.decodeFile(filename,options);
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() 
						{
							// TODO Auto-generated method stub
							 profilepic.setImageBitmap(Utils.getRoundedBitmap(myBitmap));
						}
					});
					
				};
				
			}.execute();
			 // byte[] byteArray=data.getByteArrayExtra("filepath");
		
			 
		
			  
		  }
	  }
	  break;
	 
	
	  case PICK_FROM_GALLARY:
	  {
		  if(data!=null)
		  {
			  Uri selectedImageUri = data.getData();
				
		      String tempPath = getPath(selectedImageUri);
		      
		      
		    
		    	  
		    	  // for profile pic
		    	  String what="profilepic";
		    	  Intent i=new Intent(getActivity(),CropImageActivity.class);
		    	  i.putExtra("picuri", tempPath);
		    	  i.putExtra("what",what);
		    	  startActivityForResult(i, 124);
		      
		      
		     
		  }
		  
		 

	      
		   
	  }
	  break;
	  
	
	
	  }
	 }

public String getPath(Uri uri) {
        String[] projection = { MediaColumns.DATA };
        
        Cursor cursor = getActivity()
        		.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	


@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// TODO Auto-generated method stub
	inflater = getActivity().getMenuInflater();
    inflater.inflate(R.menu.profilepicmenu, menu);

	super.onCreateOptionsMenu(menu, inflater);
}

	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Take appropriate action for each action item click
	        switch (item.getItemId()) {
	      
//	        case R.id.profileeditinfo:
//	        {
//	        	Intent i=new Intent(Profile.this,EditProfile.class);
//	        	startActivity(i);
//	        	overridePendingTransition(R.anim.anim_ltr, R.anim.anim_rtl);
//	        }
//	        break;
	        case R.id.profilepic:
	        {
	        	
	            startActivityForResult(new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
	                    PICK_FROM_GALLARY);
	        }
	        break;
	       
	        
	        
	      
	        }
			return true;
	    }
	
	 
class MyProfileAdapter extends BaseExpandableListAdapter
{

	FragmentActivity activity;
	ArrayList<String> header; 
	HashMap<String, ArrayList<String>> hash;
	
	public MyProfileAdapter(FragmentActivity activity,
			ArrayList<String> header, HashMap<String, ArrayList<String>> hash) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.header=header;
		this.hash=hash;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		// TODO Auto-generated method stub
		return this.hash.get(this.header.get(groupPosition))
				.get(childPosititon);
	}
	@Override
	public long getChildId(int arg0, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.timetable_list_item, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);
	
		

		txtListChild.setText(childText);
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return this.hash.get(this.header.get(groupPosition))
				.size();
	}
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this.header.get(groupPosition);
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.hash.size();
	}
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.exp_list_group, null);
		}
			lv.expandGroup(groupPosition);
		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}
	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	


}
}

