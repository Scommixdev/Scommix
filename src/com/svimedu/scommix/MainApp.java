package com.svimedu.scommix;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

//import org.acra.annotation.ReportsCrashes;
//import org.acra.ACRA;
//import org.acra.ReportingInteractionMode;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.scommix.WebServices.Common.StuNotice;
import com.scommix.WebServices.Common.Updates;
import com.scommix.WebServices.Common.online;

import com.scommix.sharedpref.ScommixSharedPref;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;


public class MainApp extends Application {

	// array list for home
	
	ArrayList<online> allcommentcountlist;
	ArrayList<online> alllikecountlist;
	ArrayList<Updates> allupdates;
	
	ArrayList<online> homeliketag;

	
	// arraylist for my profile
	ArrayList<online> mycommentcountlist;
	ArrayList<online> mylikecountlist;
	ArrayList<Updates> myupdates;
    ArrayList<String> myprofileliketag;
	
	
	
	//arraylist for notice
	ArrayList<online> noticecommentcountlist;
	ArrayList<online> noticelikecountlist;
	ArrayList<StuNotice> noticeupdates;
    ArrayList<online> noticeprofileliketag;
 
    ArrayList<Fragment> myFragments;
	

	String commentcount;
    // Google cloud messaging code
	
    private GoogleCloudMessaging gcm;
    static Context ctx=null;
    private AtomicInteger msgId = new AtomicInteger();
    private String regid;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000; 
	
     
        
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		super.onCreate();
		
		ScommixSharedPref pref=ScommixSharedPref.getInstance(getApplicationContext());
		
		
	
		ctx=getApplicationContext();
	
		Parse.initialize(this, "N3C8jzSpLdFkUkmBHdQpirkCPFSnLwhtzrcuxBd5", "uXmJnVjD1HNe4fv2GaSgG7ty7XfZDpeamhwPQSyr");
		if(pref.getUSERID().equals(""))
		{
			
		}
		else{
			PushService.setDefaultPushCallback(this, Welcome.class);
		}
		myFragments=new ArrayList<Fragment>();
		allupdates=new ArrayList<Updates>();
		alllikecountlist=new ArrayList<online>();
		allcommentcountlist=new ArrayList<online>();
		homeliketag=new ArrayList<online>();
	   
	    
	    mycommentcountlist=new ArrayList<online>();
		 mylikecountlist=new ArrayList<online>();
		 myupdates=new ArrayList<Updates>();
		myprofileliketag=new ArrayList<String>();
		
		 
		 commentcount=new String();
		 
		 
		 
		 noticecommentcountlist =new ArrayList<online>();
		 noticelikecountlist=new ArrayList<online>();
		 noticeupdates=new ArrayList<StuNotice>();
		 noticeprofileliketag=new ArrayList<online>();
		 
	}



	
	
	public ArrayList<Fragment> getMyFragments() {
		return myFragments;
	}





	public void setMyFragments(ArrayList<Fragment> myFragments) {
		this.myFragments = myFragments;
	}





	public ArrayList<online> getNoticecommentcountlist() {
		return noticecommentcountlist;
	}





	public void setNoticecommentcountlist(ArrayList<online> noticecommentcountlist) {
		this.noticecommentcountlist = noticecommentcountlist;
	}





	public ArrayList<online> getNoticelikecountlist() {
		return noticelikecountlist;
	}





	public void setNoticelikecountlist(ArrayList<online> noticelikecountlist) {
		this.noticelikecountlist = noticelikecountlist;
	}





	public ArrayList<StuNotice> getNoticeupdates() {
		return noticeupdates;
	}





	public void setNoticeupdates(ArrayList<StuNotice> noticeupdates) {
		this.noticeupdates = noticeupdates;
	}





	public ArrayList<online> getNoticeprofileliketag() {
		return noticeprofileliketag;
	}





	public void setNoticeprofileliketag(ArrayList<online> noticeprofileliketag) {
		this.noticeprofileliketag = noticeprofileliketag;
	}







	


	

	public ArrayList<online> getMycommentcountlist() {
		return mycommentcountlist;
	}



	public void setMycommentcountlist(ArrayList<online> mycommentcountlist) {
		this.mycommentcountlist = mycommentcountlist;
	}



	public ArrayList<online> getMylikecountlist() {
		return mylikecountlist;
	}



	public void setMylikecountlist(ArrayList<online> mylikecountlist) {
		this.mylikecountlist = mylikecountlist;
	}



	public ArrayList<Updates> getMyupdates() {
		return myupdates;
	}



	public void setMyupdates(ArrayList<Updates> myupdates) {
		this.myupdates = myupdates;
	}





	public ArrayList<String> getMyprofileliketag() {
		return myprofileliketag;
	}



	public void setMyprofileliketag(ArrayList<String> myprofileliketag) {
		this.myprofileliketag = myprofileliketag;
	}







	



	public ArrayList<online> getHomeliketag() {
		return homeliketag;
	}






	public void setHomeliketag(ArrayList<online> homeliketag) {
		this.homeliketag = homeliketag;
	}


	



	public ArrayList<online> getAllcommentcountlist() {
		return allcommentcountlist;
	}
	public void setAllcommentcountlist(ArrayList<online> allcommentcountlist) {
		this.allcommentcountlist = allcommentcountlist;
	}

	public ArrayList<online> getAlllikecountlist() {
		return alllikecountlist;
	}
	public void setAlllikecountlist(ArrayList<online> alllikecountlist) {
		this.alllikecountlist = alllikecountlist;
	}

	public ArrayList<Updates> getAllupdates() {
		return allupdates;
	}
	public void setAllupdates(ArrayList<Updates> allupdates) {
		this.allupdates = allupdates;
	}





	public String getCommentcount() {
		return commentcount;
	}





	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}







}
