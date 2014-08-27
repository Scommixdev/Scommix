package com.scommix.friendsandsearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.homeandprofile.CommentBox;
import com.scommix.navigationmainactivity.MainActivity;
import com.scommix.sharedpref.ScommixSharedPref;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.GetAllUserInfo;
import com.svimedu.scommix.R;
import com.svimedu.scommix.ScommixContentProvider;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendProfileFragment extends Fragment implements OnClickListener{
	
	ExpandableListView lv;
	private ImageView profilepic;
	private TextView userkanaam;
	private ArrayList<String> header;
	private HashMap<String, ArrayList<String>> hash;
	private String nameofuser;
	private String genderr;
	private String dateofbirth;
	private String emailofuser;
	private String city;
	private String state;
	private String fathersname;
	private String mothersname;
	private String fathersoccupation;
	private String mothersoccupation;
	private String siblingsno;
	private String mobileno;
	private MyProfileAdapter adapter;
	String idd;
	Button sendfrndreq,sendmessagetofriend;
	ArrayList<online> check;
	
 String isfriend;
 CheckFriend asyncCheckFriend;
public GetUserDetail taskGetUserDetail;
 
 @Override
public void onPause() {
	// TODO Auto-generated method stub
	 asyncCheckFriend.cancel(true);
	 taskGetUserDetail.cancel(true);
	super.onPause();
}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView =inflater.inflate(R.layout.friendprofilelayout, container, false);
		
		lv=(ExpandableListView)rootView.findViewById(R.id.expandableprofile);
		LayoutInflater infl=getActivity().getLayoutInflater();
		ViewGroup header = (ViewGroup)infl.inflate(R.layout.headerfriendlayout, lv, false);
		lv.addHeaderView(header);
		 userkanaam=(TextView)header.findViewById(R.id.headerusername);
	    profilepic=(ImageView)header.findViewById(R.id.profilepic);
	    sendfrndreq=(Button)header.findViewById(R.id.sendfriendrequestbutton);
	    sendmessagetofriend=(Button)header.findViewById(R.id.sendmessagetofriend);
	    asyncCheckFriend=new CheckFriend();
		   taskGetUserDetail=new GetUserDetail();
	    sendmessagetofriend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				final EditText input = new EditText(getActivity());
				 input.setHeight(100);
				 input.setWidth(340);
				 input.setGravity(Gravity.CENTER);

				 input.setImeOptions(EditorInfo.IME_ACTION_DONE);
		 
					// set dialog message
					alertDialogBuilder
					.setTitle("Message")
						.setMessage(nameofuser)
						.setCancelable(false)
						.setPositiveButton("Send",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
							
								new AsyncTask<Void, Void, Void>()
								{
									
									

									@Override
									protected Void doInBackground(
											Void... params) {
										// TODO Auto-generated method stub
										new Common().SendMessage(ScommixSharedPref.getUSERID(), idd, input.getText().toString());
										return null;
									}
									@Override
									protected void onPostExecute(Void result) {
									
										Toast.makeText(getActivity(), "Message Sent!", Toast.LENGTH_SHORT).show();
									};
									
								}.execute();
							
							}
						  });
					
					alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							
						}
					});
					
					 alertDialogBuilder.setView(input);
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
			}
		});
	    Intent i=getActivity().getIntent();
	    idd= i.getStringExtra("id");
	  
	   asyncCheckFriend.execute();
	  
	
	   
	  
	   	
	
	  
	 sendfrndreq.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isfriend.equals("1"))
			{
				new AsyncTask<Void, Void, Void>()
				{
					ProgressDialog pd;
					
					@Override
					protected void onPreExecute() 
					{
						pd=new ProgressDialog(getActivity());
						pd.setMessage("Deleting..");
						pd.setCancelable(false);
						pd.show();
						
					};

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						
						new Common().DeleteFriend(ScommixSharedPref.getUSERID(), idd);
						
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						pd.dismiss();
					
					    getActivity().finish();
					   
					};
					
				}.execute();
			}
			
			else if(isfriend.equals("4"))
			{
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
		 
		 
					// set dialog message
					alertDialogBuilder
					.setTitle("Friend Request !")
						.setMessage("Accept or Reject?")
						.setCancelable(false)
						.setPositiveButton("Accept",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								new AsyncTask<Void, Void, Void>()
								{
									ProgressDialog pd;
									
									@Override
									protected void onPreExecute() 
									{
										pd=new ProgressDialog(getActivity());
										pd.setMessage("Confirming..");
										pd.setCancelable(false);
										pd.show();
										
									};


									@Override
									protected Void doInBackground(
											Void... params) {
										// TODO Auto-generated method stub
										new Common().ConfrimFriendRequest(ScommixSharedPref.getUSERID(),idd );

										return null;
									}
									@Override
									protected void onPostExecute(Void result) {
										isfriend="1";
										sendfrndreq.setText("Remove !");
										pd.dismiss();
									};
									
								}.execute();
							
							}
							
						  });
						alertDialogBuilder.setNegativeButton("Reject",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {

								new AsyncTask<Void, Void, Void>()
								{
									
										ProgressDialog pd;
									
									@Override
									protected void onPreExecute() 
									{
										pd=new ProgressDialog(getActivity());
										pd.setMessage("Cancelling..");
										pd.setCancelable(false);
										pd.show();
										
									};

									@Override
									protected Void doInBackground(Void... params) {
										// TODO Auto-generated method stub
										new Common().CancelFriendRequest(idd);
										return null;
									}
									@Override
									protected void onPostExecute(Void result) {
										isfriend="2";
										sendfrndreq.setText("Send Request");
										pd.dismiss();
									
									};
									
								}.execute();
							
							}
						  });
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
				
		
	
			}
			else if(isfriend.equals("3")){
				new AsyncTask<Void, Void, Void>()
				{
					
						ProgressDialog pd;
					
					@Override
					protected void onPreExecute() 
					{
						pd=new ProgressDialog(getActivity());
						pd.setMessage("Cancelling..");
						pd.setCancelable(false);
						pd.show();
						
					};

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						new Common().CancelFriendRequest(idd);
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						isfriend="2";
						sendfrndreq.setText("Add Friend");
						pd.dismiss();
					
					};
					
				}.execute();
			}
			else if(isfriend.equals("2"))
			{
				new AsyncTask<Void, Void, Void>()
				{
					
					ProgressDialog pd;
					
					@Override
					protected void onPreExecute() 
					{
						pd=new ProgressDialog(getActivity());
						pd.setMessage("Sending..");
						pd.setCancelable(false);
						pd.show();
						
					};

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						new Common().SendFriendRequest(ScommixSharedPref.getUSERID(), idd);
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						isfriend="3";
						sendfrndreq.setText("Request Sent!");
						pd.dismiss();
					
					};
					
				}.execute();
			}
		}
	});
	    
	  return rootView;
	  
	}
	
	
	class CheckFriend extends AsyncTask<Void, Void, Void>
	{

		ProgressDialog pd;
		ArrayList<online> checkreceived=new ArrayList<online>();
		@Override
		protected void onPreExecute() 
		{
			pd=new ProgressDialog(getActivity());
			pd.setMessage("Checking..");
			pd.setCancelable(true);
			pd.show();
			
		};
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<online> result=new ArrayList<online>();
			
			
			result=new Common().CheckFriend(ScommixSharedPref.getUSERID(), idd);
			checkreceived=new Common().GetFriendRequest(ScommixSharedPref.getUSERID());
			for(int i=0;i<result.size();i++)
			{
				if(isCancelled()) break;
				isfriend=result.get(i).statusid;
				Log.i("result", result.get(i).statusid);
			}
			if(isfriend==null)
			{
				isfriend="2";
			}
			if(checkreceived!=null)
			{
				for(int i=0;i<checkreceived.size();i++)
				{
					if(isCancelled()) break;
						if(checkreceived.get(i).userid.equals(idd))
						{
							isfriend="4";
						}
					
				}
			}
			
		
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			
			
			if(isfriend.equals("1"))
			{
				sendfrndreq.setText("Remove !");
			
			}
			else if(isfriend.equals("3"))
			{
				sendfrndreq.setText("Request Sent!");
				
			}
			else if(isfriend.equals("2"))
			{
				sendfrndreq.setText("Add Friend");
			}
			else if(isfriend.equals("4"))
			{
				sendfrndreq.setText("Respond");
			}
			
			if(!isCancelled())
			{
				
				taskGetUserDetail.execute();

			}
			
		 
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
		generaldata.add(1,"Gender:    "+genderr);
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
		
		
	}
	
	class MyProfileAdapter extends BaseExpandableListAdapter
	{

		FriendProfileFragment friendProfile;
		ArrayList<String> header; 
		HashMap<String, ArrayList<String>> hash;
		
		public MyProfileAdapter(
				FriendProfileFragment friendProfile, ArrayList<String> header, HashMap<String, ArrayList<String>> hash) {
			// TODO Auto-generated constructor stub
			this.friendProfile=friendProfile;
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
				LayoutInflater infalInflater = (LayoutInflater)
						getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
				LayoutInflater infalInflater = (LayoutInflater)getActivity()
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
	
	
	class GetUserDetail extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		 SoapObject results;
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
			resp=c.GetUserDetailnew(idd);
			
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
				
				genderr=resp.get(i).gender;
				if(genderr==null)
				{
					genderr="N/A";
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
			adapter=new MyProfileAdapter(FriendProfileFragment.this,header,hash);
			lv.setAdapter(adapter);
			
getActivity().runOnUiThread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		 Picasso.with(getActivity()) //
	        .load(Utils.url+profilebytearray) //
	        .placeholder(R.drawable.ic_loading) //
	        .error(R.drawable.cross) 
	        .transform(new RoundTrans())
	        .fit() //
	        .into(profilepic);
		
	}
});
			
		}
		
	}
	
	

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view.getId()==R.id.sendfriendrequestbutton)
		{
			new AsyncTask<Void,Void, Void>() {
				
				ProgressDialog pd;
				
				@Override
				protected void onPreExecute() {
					pd=new ProgressDialog(getActivity());
					pd.setCancelable(false);
					pd.setMessage("Sending...");
					pd.show();
				};

				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					new Common().SendFriendRequest(ScommixSharedPref.getUSERID(), idd);
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					pd.dismiss();
					sendfrndreq.setText("Request Sent!");
				};
				
			}.execute();

		}
	}
	
	
}
