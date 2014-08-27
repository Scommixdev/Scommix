package com.scommix.asynctasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;






import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.Vectoronline;
import com.scommix.WebServices.Common.online;
import com.scommix.adapters.MyProfileAdapter;
import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.Utils;
import com.svimedu.scommix.MainApp;


public class RefreshMyPost extends AsyncTask<Void,Void, Void>{


	//CHANGE THIS AS REQUIRED

	FragmentActivity activity;
	MyProfileAdapter adapter;
	ArrayList<Updates> myupdates2;
	ArrayList<online> mylikecountlist;
	ArrayList<online> mycommentcountlist;

	ArrayList<String> myprofileliketag;

	PullToRefreshGridView myprofilegridview;
	GridView mGridView;
	
	ProgressDialog pd;
	
			public RefreshMyPost(FragmentActivity activity,
				MyProfileAdapter adapter, ArrayList<Updates> myupdates2,
				ArrayList<online> mylikecountlist,
				ArrayList<online> mycommentcountlist,
				ArrayList<String> myprofileliketag, PullToRefreshGridView myprofilegridview, GridView mGridView) {
			// TODO Auto-generated constructor stub
				this.activity=activity;
				this.adapter=adapter;
				this.mycommentcountlist=mycommentcountlist;
				this.myupdates2=myupdates2;
				this.mylikecountlist=mylikecountlist;
			
				this.myprofileliketag=myprofileliketag;
				this.myprofilegridview=myprofilegridview;
				this.mGridView=mGridView;
		}
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				new Handler().postDelayed(new Runnable() {

			        @Override
			        public void run() {
			        	myprofilegridview.setRefreshing(true);
			        }
			    }, 500);
		
			
				if(myupdates2!=null)
				{
					mycommentcountlist.clear();
					myupdates2.clear();
					mylikecountlist.clear();
				    myprofileliketag.clear();
			
				 
				}
				
				super.onPreExecute();
			}
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
			
				try{
					Common common=new Common();
					ArrayList<Updates> temp=new ArrayList<Updates>();
					ArrayList<online> temp1=new ArrayList<online>();
					ArrayList<online> temp2=new ArrayList<online>();
				
					
						temp=common.GetProfileStatus(ScommixSharedPref.getUSERID());
						
					
				for(int i=0;i<temp.size();i++)
					{
						if(isCancelled()) break;
						String useridd=temp.get(i).userstatusid;
						
					    temp1.addAll(i, common.GetAllCommentsCount(useridd));
						temp2.addAll(i, common.CountAllSatatusLike(useridd));
					
						 Vectoronline vc=new Vectoronline();
							vc=common.GetStatusLikeName(useridd);
							boolean iliked=checklike(vc);
							
							if(iliked==false)
							{
								Log.i("for status "+i, "not liked");
								myprofileliketag.add("Like");
							}
							
							else if(iliked==true)
							{
								Log.i("for status "+i, "liked");
								myprofileliketag.add("Liked");
							}
						
				}
					
					
					myupdates2.addAll(temp);
					mycommentcountlist.addAll(temp1);
				  mylikecountlist.addAll(temp2);
				 // serialize();
				}
				catch(Exception e)
				{
					
					e.printStackTrace();
				
				}
				
				return null;
			}
			
			@Override
			protected void onCancelled() {
				// TODO Auto-generated method stub
				super.onCancelled();
				System.out.println("cancelled");
			}
			
			private Bitmap giveprofilepic(Updates updates) {
				// TODO Auto-generated method stub
				 byte[] profilebytearray=  Base64.decode(updates.getUserprofileimage());
				 //Decode image size
				 Bitmap bmp;
			        BitmapFactory.Options o = new BitmapFactory.Options();
			        o.inJustDecodeBounds = true;
					bmp=BitmapFactory.decodeByteArray(profilebytearray, 0, profilebytearray.length,o);

			        //The new size we want to scale to
			        final int REQUIRED_SIZE=70;

			        //Find the correct scale value. It should be the power of 2.
			        int scale=1;
			        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
			            scale*=2;

			        //Decode with inSampleSize
			        BitmapFactory.Options o2 = new BitmapFactory.Options();
			        o2.inSampleSize=scale;
			        
					bmp=BitmapFactory.decodeByteArray(profilebytearray, 0, profilebytearray.length,o2);

			        
				 bmp=Utils.getRoundedBitmap(bmp);
				return bmp;
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

				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						myprofilegridview.onRefreshComplete();
						if(myupdates2.size()>0)
						{
							
							mGridView.setAdapter(adapter);

						}
					}
				});
			
			

			
					
				
				
				
				
				
				
				
			
			}
			
//			public static void serialize() {
//				// TODO Auto-generated method stub
//				try {
//					
//					if(act.getDir("files", Context.MODE_PRIVATE).listFiles().length>0)
//			        {
//			        File[] file=act.getDir("files", Context.MODE_PRIVATE).listFiles();
//			        for(int i=0;i<file.length;i++)
//			        {
//			        	try{
//			        		file[i].delete();
//			        	}
//			        	catch(Exception e)
//			        	{
//			        		file[i].deleteOnExit();
//			        	}
//			        }
//			        
//			        }
//					
//			        FileOutputStream fos = act.openFileOutput ( "myupdates", Context.MODE_PRIVATE );
//			        FileOutputStream fos1=act.openFileOutput("commentcounts", Context.MODE_PRIVATE);
//			        FileOutputStream fos2=act.openFileOutput("likecount", Context.MODE_PRIVATE);
//			        FileOutputStream fos3=act.openFileOutput("myprofilepics", Context.MODE_PRIVATE);
//			        FileOutputStream fos4=act.openFileOutput("profileliketag", Context.MODE_PRIVATE);
//
//			        
//			        ObjectOutputStream oos = new ObjectOutputStream ( fos );
//			        ObjectOutputStream oos1 =new ObjectOutputStream(fos1);
//			        ObjectOutputStream oos2 =new ObjectOutputStream(fos2);
//			        ObjectOutputStream oos3 =new ObjectOutputStream(fos3);
//			        ObjectOutputStream oos4 =new ObjectOutputStream(fos4);
//			        
//			        oos.writeObject (myupdates);
//			        oos1.writeObject(commentcountlist);
//			        oos2.writeObject(likecountlist);
//
//			        oos4.writeObject(profileliketag);
//			        
//			        oos.close ();
//			        oos1.close();
//			        oos2.close();
//			        oos3.close();
//			        oos4.close();
//			        
//			    } catch ( Exception ex ) {
//			        ex.printStackTrace ();
//			    }
//			}
		
			public static void setListViewHeightBasedOnChildren(ListView listView) {
			    ListAdapter listAdapter = listView.getAdapter();
			    if (listAdapter == null) {
			        return;
			    }
			    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
			    int totalHeight = 0;
			    View view = null;
			    for (int i = 0; i < listAdapter.getCount(); i++) {
			        view = listAdapter.getView(i, view, listView);
			        if (i == 0) {
			            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
			        }
			        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			        totalHeight += view.getMeasuredHeight();
			    }
			    ViewGroup.LayoutParams params = listView.getLayoutParams();
			    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			    listView.setLayoutParams(params);
			    listView.requestLayout();
			}
}
