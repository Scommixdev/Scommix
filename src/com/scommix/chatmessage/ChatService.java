package com.scommix.chatmessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.chatmessage.ChatWithFriend.MessageAdapter;
import com.scommix.sharedpref.ScommixSharedPref;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class ChatService extends Service{

	private final IBinder mBinder = new MyBinder();
	String friendid,friendname;
	List<ChatMessage> msgs=new ArrayList<ChatMessage>();
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
			
		
    friendid=intent.getStringExtra("friendid");
    friendname=intent.getStringExtra("friendname");
    new GetMessageService().execute();
	
		return Service.START_NOT_STICKY;
	}
	
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	
	public class MyBinder extends Binder {
		ChatService getService() {
			
	      return ChatService.this;
	    }
	  }


	public List<ChatMessage> getMsgs() {
		return msgs;
	}
	
	 class GetMessageService extends AsyncTask<Void, Void, Void>
	 {
		 
		 
		 @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(msgs.size()>0)
			{
				msgs.clear();
			}
		}
	
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<online> ab=new ArrayList<online>();
			ab=new Common().GetMessage(ScommixSharedPref.getUSERID(), friendid);
			msgs=new ArrayList<ChatMessage>();
			for(int i=0;i<ab.size();i++)
			{
				
				ChatMessage obj=new ChatMessage();
				
				if(ab.get(i).name.equals(friendname))
				{
					obj.setIncoming(false);
				}
				else{
					obj.setIncoming(true);
				}
			
				obj.setText(ab.get(i).message);
				Date valuetime = null;
				try {
					valuetime = new SimpleDateFormat("HH:mm a", Locale.ENGLISH).parse(ab.get(i).senttime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obj.setTime(valuetime);
				msgs.add(i, obj);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Intent broadcastIntent = new Intent();
		    broadcastIntent.setAction("newmessageaction");
		    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		    broadcastIntent.putExtra("responsenewmessage", "new thing");
		    sendBroadcast(broadcastIntent);
		}
		 
	 }

}
