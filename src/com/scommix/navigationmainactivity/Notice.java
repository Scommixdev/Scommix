package com.scommix.navigationmainactivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.StuNotice;
import com.scommix.WebServices.Common.Vectoronline;
import com.scommix.WebServices.Common.online;
import com.scommix.homeandprofile.CommentBox;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.MainApp;
import com.svimedu.scommix.R;

public class Notice extends Fragment{
	
	PullToRefreshGridView mynoticelist;
	GridView mGridview;
	MainApp apObj;
	ArrayList<online> noticecommentcountlist;
	ArrayList<online> noticelikecountlist;
	ArrayList<StuNotice> noticeupdates;
    ArrayList<online> noticeprofileliketag;
    
    NoticeAdapter adapter;
    int tillnow;
    GetNotice taskgetnotice;
    GetNotice1 taskGetNotice1;
    TextToSpeech texttospeech;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
	View rooView=inflater.inflate(R.layout.homelayout, container, false);
//	sec 22 threevee market 9478763766 mr pradeep
//	ambika 9780885687 akshat
	
	mynoticelist = (PullToRefreshGridView)rooView.findViewById(R.id.statuslist);
	mGridview=mynoticelist.getRefreshableView();
	apObj=(MainApp)getActivity().getApplication();
	
	noticecommentcountlist=apObj.getNoticecommentcountlist();
	noticelikecountlist=apObj.getNoticelikecountlist();
	noticeprofileliketag=apObj.getNoticeprofileliketag();
	noticeupdates=apObj.getNoticeupdates();
	
	adapter=new NoticeAdapter();
	taskgetnotice=new GetNotice();
	taskGetNotice1=new GetNotice1();
	
	taskgetnotice.execute();
	
	mynoticelist.setOnRefreshListener(new OnRefreshListener2<GridView>(){

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
			// TODO Auto-generated method stub
        if(taskgetnotice.getStatus()==AsyncTask.Status.PENDING)
				
			{
				taskgetnotice.execute();
			}
			else if(taskgetnotice.getStatus()==AsyncTask.Status.RUNNING)
			{
				
			}
			else if(taskgetnotice.getStatus()==AsyncTask.Status.FINISHED)
			{
				taskgetnotice=null;
				taskgetnotice=new GetNotice();
				taskgetnotice.execute();
						
			}
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
			// TODO Auto-generated method stub
			 if(taskGetNotice1.getStatus()==AsyncTask.Status.PENDING)
					
				{
				 taskGetNotice1.execute();
				}
				else if(taskGetNotice1.getStatus()==AsyncTask.Status.RUNNING)
				{
					
				}
				else if(taskGetNotice1.getStatus()==AsyncTask.Status.FINISHED)
				{
					taskGetNotice1=null;
					taskGetNotice1=new GetNotice1();
					taskGetNotice1.execute();
							
				}
		}
		
	});
	
	texttospeech=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
		
		@Override
		public void onInit(int status) {

			if(status!=TextToSpeech.ERROR){
				texttospeech.setLanguage(Locale.UK);
				
			}
		}
	});

	mynoticelist.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			String tospeak = Html.fromHtml(noticeupdates.get(position).notice).toString();
			
			if (tospeak.length() > 1) {
				tospeak = tospeak.toLowerCase(Locale.UK);
		    } 
			System.out.println(tospeak);
			
			texttospeech.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
						
		}
	});
	

	
		return rooView;
	
		
	}
	
	
	class GetNotice1 extends AsyncTask<Void, Void, Void>{
		
		
		private int resultr=1;



		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			tillnow=noticeupdates.size();
		
		}


		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try{
				Common common=new Common();
				ArrayList<StuNotice> temp=new ArrayList<StuNotice>();
				ArrayList<online> temp1=new ArrayList<online>();
				ArrayList<online> temp2=new ArrayList<online>();
				ArrayList<online> temp3=new ArrayList<online>();
				
			if(noticeupdates.size()==0)
			{
				resultr=0;
			}
			else{
				temp=common.GetNotice1(ScommixSharedPref.getINSTITUTEID(), ScommixSharedPref.getCLASSID(), noticeupdates.get(noticeupdates.size()-1).posteddate);
		     
				if(temp.size()!=0)
				{
					for(int i=0;i<temp.size();i++)
					{
						String useridd=temp.get(i).noticeid;
			
						if (isCancelled())  break;
						
						
						
						temp1.addAll(i, common.GetAllNoticeCommentsCount(useridd));
						
						temp2.addAll(i,common.CountAllNoticeLike(useridd));
						
						 Vectoronline vc=new Vectoronline();
							vc=common.GetStatusLikeName(useridd);
							if(vc!=null)
							{
								boolean iliked=checklike(vc);
								
								if(iliked==false)
								{
									Log.i("for status "+i, "not liked");
									online o=new online();
									o.setLiketag("Like");
									temp3.add(o);
								
								}else if(iliked==true)
								{
									Log.i("for status "+i, "liked");
									online o=new online();
									o.setLiketag("Liked");
									temp3.add(o);
								}
							}
							else{
								online o=new online();
								o.setLiketag("Like");
								temp3.add(o);

							}
							
						
					
				        
					
									
							
				 
						
					}
					
			
		      if(!isCancelled())
		      {
		  		noticeupdates.addAll(temp);
				noticecommentcountlist.addAll(temp1);
				noticelikecountlist.addAll(temp2);
				noticeprofileliketag.addAll(temp3);
			
		      }
			
					
				}
				else{
					resultr=0;
				}
			}
				
				  // serialize();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}
			return null;
		}
		

		
		private boolean checklike(Vectoronline vc) {
			// TODO Auto-generated method stub
			boolean value = false;

			for(int i=0;i<vc.size();i++)
				{
			
					if(vc.get(i).userid.equals(ScommixSharedPref.getUSERID()))
					{
						value=true;
						Log.i("my status", "liked");
						break;
					}
				}
			
			
			
			return value;
		}
		


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try{
				 mynoticelist.onRefreshComplete();
			      	if(resultr==0)
			      	{
			      		getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(getActivity(), "No More notice...", Toast.LENGTH_SHORT).show();
							}
						});
			      	}
			      	else{
			      		getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
						//adapter=new HomeAdapter(activity,allupdates,username,commentcountlist,likecountlist,mGridView,liketag);
				              	mGridview.setAdapter(adapter); 
				              	mGridview.setSelection(tillnow);
				        	
							}
							
						
						});
			      	}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
		      
				
		}


		
	}
	
	
	class GetNotice extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mynoticelist.setRefreshing(true);
				}
			}, 100);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				Common common=new Common();
				
				ArrayList<StuNotice> temp=new ArrayList<StuNotice>();
				ArrayList<online> temp1=new ArrayList<online>();
				ArrayList<online> temp2=new ArrayList<online>();
				ArrayList<online> temp3=new ArrayList<online>();
				
                temp=common.GetNotice(ScommixSharedPref.getINSTITUTEID(), ScommixSharedPref.getCLASSID());

			
			    
				int lastsize=temp.size();
				
			
				
				Log.i("notice size", ""+lastsize);

				for(int i=0;i<temp.size();i++)
				{
				
					if (isCancelled())  break;
					String useridd=temp.get(i).noticeid;
				
					 Vectoronline vc=new Vectoronline();
						vc=common.GetStatusLikeName(useridd);
						if(vc!=null)
						{
							boolean iliked=checklike(vc);
							if(iliked==false)
							{
								Log.i("for status "+i, "not liked");
								online o=new online();
								o.setLiketag("Like");
								temp3.add(o);
							}else if(iliked==true)
							{
								Log.i("for status "+i, "liked");
							
								online o=new online();
								o.setLiketag("Liked");
								temp3.add(o);
							}
						}
						else{
							online o=new online();
							o.setLiketag("Like");
							temp3.add(o);
						}
						
						
				temp1.addAll(i, common.GetAllNoticeCommentsCount(useridd));
					
				temp2.addAll(i,common.CountAllNoticeLike(useridd));
					
			
				
				
				}
				if (!isCancelled())
				{
					if(noticeupdates.size()!=0)
					{
					noticeupdates.clear();
					  noticecommentcountlist.clear();
					  noticelikecountlist.clear();
					  noticeprofileliketag.clear();

					}
					
					noticeupdates.addAll(temp);
					noticecommentcountlist.addAll(temp1);
					noticelikecountlist.addAll(temp2);
					noticeprofileliketag.addAll(temp3);
				}
				
				
		
				  // serialize();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}
		
		
			
			
			return null;
		}
		
		private boolean checklike(Vectoronline vc) {
			// TODO Auto-generated method stub
			boolean value = false;

			for(int i=0;i<vc.size();i++)
				{
			
					if(vc.get(i).userid.equals(ScommixSharedPref.getUSERID()))
					{
						value=true;
						Log.i("my status", "liked");
						break;
					}
				}
			
			
			
			return value;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			  new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 mynoticelist.onRefreshComplete();
					}
				}, 50);

				      	
				      
						getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub

				             if(noticeupdates.size()==0)
				             {
				            	 
				            	
				            	 
				            	 Toast.makeText(getActivity(), "No Notices...", Toast.LENGTH_SHORT).show();
				             }
				             else{
				    			
				          		   mGridview.setAdapter(adapter); 
				          		   
				          		   adapter.notifyDataSetChanged();
				             }
							
					}
							
						
						});
			
			
		}

		
	}
	
	public class NoticeAdapter extends BaseAdapter 
	{
		
	     

	     
	
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return noticeupdates.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
	void updateview(int position)
	{
		
			int x= Integer.parseInt(noticelikecountlist.get(position).count)+Integer.parseInt("1") ;
			Log.i("Value", ""+x);
			String newlike=String.valueOf(x);
			noticelikecountlist.get(position).count=newlike;
		    Log.i("new value", noticelikecountlist.get(position).count);
		    this.notifyDataSetChanged();

	}



	void demoteview(int position)
	{
		
			 int x= Integer.parseInt(noticelikecountlist.get(position).count)-Integer.parseInt("1") ;
			  Log.i("Value", ""+x);
			String newlike=String.valueOf(x);
			noticelikecountlist.get(position).count=newlike;
		Log.i("new value", noticelikecountlist.get(position).count);
		this.notifyDataSetChanged();

	}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		
		


		@Override
		public View getView( final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			  ViewHolder holder;
			if(convertView==null)
			{
				LayoutInflater inflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView=inflater.inflate(R.layout.row_homelistitem,parent, false);
				holder = new ViewHolder();
				
				holder.nametextview = (TextView)convertView.findViewById(R.id.homeitemnametext); // duration
				holder.profilepic=(ImageView)convertView.findViewById(R.id.homprofilepic);
				holder.statustextview=(TextView)convertView.findViewById(R.id.homestatustext);
				holder.statusidtextview=(TextView)convertView.findViewById(R.id.homeuseratatusidtext);
				holder.statustimetextview=(TextView)convertView.findViewById(R.id.homestatustime);
				holder.homeuseratatusidliketext=(TextView)convertView.findViewById(R.id.homeuseratatusidliketext);
				
				holder.tell=(Button)convertView.findViewById(R.id.commentpost);
			    holder.star=(Button)convertView.findViewById(R.id.star);
			    
				holder.tell.setVisibility(View.GONE);
				holder.star.setVisibility(View.GONE);
			    convertView.setTag(holder);
		       
		      
			}
			else
			{
				holder=(ViewHolder)convertView.getTag();
			}
			
			
			holder.statustextview.setAutoLinkMask(0); 
			holder.nametextview.setText(noticeupdates.get(position).signature__c);
			holder.statustextview.setText(Html.fromHtml(noticeupdates.get(position).notice));
			Linkify.addLinks(holder.statustextview, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);

			// Recognize all of the default link text patterns 
			Linkify.addLinks(holder.statustextview, Linkify.ALL);

			// Disable all default link detection
			Linkify.addLinks(holder.statustextview, 0);
			holder.statustextview.setLinkTextColor(Color.BLUE);
			holder.statusidtextview.setText(noticecommentcountlist.get(position).count+" Comments");
			holder.homeuseratatusidliketext.setText(noticelikecountlist.get(position).count+" likes");
			holder.statusidtextview.setVisibility(View.GONE);
			holder.homeuseratatusidliketext.setVisibility(View.GONE);
		
			try 
			{
			   holder.statustimetextview.setText(parseDate(position));
			} 
			
			catch (ParseException e)
			
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(noticeprofileliketag.get(position).getLiketag().equals("Like"))
			{
		        holder.star.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0 );
		       holder.star.setText(noticeprofileliketag.get(position).getLiketag());
			}
			else{
		        holder.star.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liked, 0, 0, 0 );
		        holder.star.setText(noticeprofileliketag.get(position).getLiketag());
			}


			
				holder.profilepic.setVisibility(View.GONE);
			
	        holder.tell.setText("Comment");
		
			holder.star.setOnClickListener(mClickListener);
			holder.star.setTag(position);
			
			
			holder.tell.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Log.i("home tell", "tell listener");
					String status1,personname1,timing1,statusid1,userid;
					String profilepicbyte;
					status1=noticeupdates.get(position).notice;
					personname1=noticeupdates.get(position).name;
					timing1=noticeupdates.get(position).posteddate;
					statusid1=noticeupdates.get(position).noticeid;
					
					
					Intent tocommentbox=new Intent(getActivity(),CommentBox.class);
					tocommentbox.putExtra("nameofuser", personname1);
					tocommentbox.putExtra("status", status1);
					tocommentbox.putExtra("statustime", timing1);
					tocommentbox.putExtra("statusid", statusid1);
					
					tocommentbox.putExtra("position", ""+position);
					tocommentbox.putExtra("wherefrom", "myhome");
					System.out.println(Integer.parseInt(noticecommentcountlist.get(position).count));
					tocommentbox.putExtra("successfulcomments", Integer.parseInt(noticecommentcountlist.get(position).count));
				    getActivity().startActivity(tocommentbox);
					
				}
			});
			
			
			 return convertView;
			 
		}
		
		public String parseDate(int position)
			    throws ParseException
			{
			
               // "2014-07-29 11:59:00.000"
			// "7/29/2014 5:29:00 M" (at offset 1)

			    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss",
			                                                    Locale.getDefault()); 
			    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			 long value=dateFormat.parse(noticeupdates.get(position).posteddate).getTime();  
				CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(String.valueOf(value)),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
				
			    return timeAgo.toString();
			}
		
		
		 

		private OnClickListener mClickListener = new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final int position = (Integer) v.getTag();
					if (noticeprofileliketag.get(position).getLiketag().equals("Liked")) {
						   System.out.println(position);
						   online o=new online();
						   o.setLiketag("Like");
						   noticeprofileliketag.add(o);
						   noticeprofileliketag.set(position, o);
			               demoteview(position);
			               
			               new AsyncTask<Void,Void,Void>(){
			            	   
			            	   ProgressDialog pd;
			            	   
			            	   @Override
			            	   protected void onPreExecute() 
			            	   {
			            		   pd=new ProgressDialog(getActivity());
			            		   pd.setMessage("Dislike...");
			            		   pd.show();
			            	   };

							@Override
							protected Void doInBackground(Void... params) {
								// TODO Auto-generated method stub
					  	          new Common().UpdateNoticeLikeNew(ScommixSharedPref.getUSERID(), noticeupdates.get(position).noticeid);

								return null;
							}
							
							@Override
							protected void onPostExecute(Void result) {
								pd.dismiss();
							};
			            	   
			               }.execute();
			            
			              
			                
			               
			                
		            } else {
		             
		                
		                System.out.println(position);
		                
		            	online o=new online();
						o.setLiketag("Liked");
						noticeprofileliketag.add(o);
						noticeprofileliketag.set(position, o);
		  	          updateview(position);
		  	          
		  	          
		  	          new AsyncTask<Void, Void, Void>()
		  	          {
		  	        	   ProgressDialog pd;
		            	   
		            	   @Override
		            	   protected void onPreExecute() {
		            		   pd=new ProgressDialog(getActivity());
		            		   pd.setMessage("Dislike...");
		            		   pd.show();
		            	   };
						@Override
						protected Void doInBackground(Void... params) {
							// TODO Auto-generated method stub
				               new Common().InsertNoticeLikeNew(ScommixSharedPref.getUSERID(), noticeupdates.get(position).noticeid);

							return null;
						}
						@Override
						protected void onPostExecute(Void result) {
							pd.dismiss();
						};
		  	        	  
		  	          }.execute();
		  	   
		  	               
		            }
			
		

				
				}
				 
			 };
			
		 class ViewHolder{
			TextView nametextview,statusidtextview,statustimetextview,homeuseratatusidliketext;
			TextView statustextview;
			Button star,tell;
			ImageView profilepic;
		}


		
	}
	
	
	
}
