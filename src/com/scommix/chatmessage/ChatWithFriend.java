package com.scommix.chatmessage;



import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.sharedpref.ScommixSharedPref;
import com.svimedu.scommix.R;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class ChatWithFriend extends ActionBarActivity{
	
	MessageAdapter adapter;
	List<ChatMessage> msgs;
	String mText;
	 ListView lv;
	AdapterView.OnItemClickListener mSmileySelectListener;
     ImageButton sendmessage;
     String friendid;
     String friendname;
     EditText message ;
     MessageReceiver receiver;
   
    
     ActionBar action;
     ChatService s;
     
     Intent gg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chattofriend);
	    lv=(ListView)findViewById(R.id.chatlist);
	    Intent fromchat=getIntent();
		friendid=fromchat.getStringExtra("friendid");
		friendname=fromchat.getStringExtra("friends");
		
	
		
		
		 gg=new Intent(ChatWithFriend.this,ChatService.class);
		    gg.putExtra("friendid", friendid);
			gg.putExtra("friendname", friendname);
		    startService(gg);
		      
		
		action=getSupportActionBar();
		action.setSubtitle(friendname);
		//new GetMessage().execute();
		sendmessage=(ImageButton)findViewById(R.id.send_button);
		message= (EditText)findViewById(R.id.text_editor);
		  msgs =  new ArrayList<ChatMessage>();
		  adapter = new MessageAdapter(ChatWithFriend.this, msgs);
	    lv.setAdapter(adapter);
		//new GetMessage().execute();
		
		sendmessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 mText = message.getText().toString();
                   if(mText.equals("")==false)
                    {
                	Date valuetime=Calendar.getInstance().getTime();
                    	msgs.add(new ChatMessage(mText,valuetime , true));
                      adapter.notifyDataSetChanged();
                    
                    message.setText("");
                    lv.setSelection(adapter.getCount());
                    new sendmessagetofriend().execute();
                   
                     }
			        
			}
		});
		

	        IntentFilter filter = new IntentFilter("newmessageaction");
	        filter.addCategory(Intent.CATEGORY_DEFAULT);
	        receiver=new MessageReceiver();
	        registerReceiver(receiver, filter);
	        
	        
	      
	        
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Intent intent= new Intent(this, ChatService.class);
		intent.putExtra("friendid", friendid);
		intent.putExtra("friendname", friendname);
	    bindService(intent, mConnection,
	        Context.BIND_AUTO_CREATE);
	    
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unbindService(mConnection);
		stopService(gg);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

	    public void onServiceConnected(ComponentName className, 
	        IBinder binder) {
	      ChatService.MyBinder b = (ChatService.MyBinder) binder;
	      s = b.getService();
	      Toast.makeText(ChatWithFriend.this, "Connected", Toast.LENGTH_SHORT)
	          .show();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	      s = null;
	    }
	  };
	
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
	    
	
		private void onKeyboardStateChanged(boolean isKeyboardOpen) {
		if (isKeyboardOpen) {
			message.setFocusableInTouchMode(true);
			message.setHint("Tap to compose");
		}
		else {
			message.setFocusableInTouchMode(false);
			message.setHint("Open Keyboard");
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	    
	}
	

	 public class MessageAdapter extends BaseAdapter {

		    private static final String DATE_FORMAT = "hh:mm a";
		    private final List<ChatMessage> chatMessages;
		    ChatWithFriend chatWithFriend;

		    public MessageAdapter(ChatWithFriend chatWithFriend,
					List<ChatMessage> msgs) {
				// TODO Auto-generated constructor stub
		    	this.chatMessages=msgs;
		    	this.chatWithFriend=chatWithFriend;
			}

			
		    @Override
		    public int getCount() {
		        if (chatMessages != null) {
		            return chatMessages.size();
		        } else {
		            return 0;
		        }
		    }

		    @Override
		    public ChatMessage getItem(int position) {
		        if (chatMessages != null) {
		            return chatMessages.get(position);
		        } else {
		            return null;
		        }
		    }

		    @Override
		    public long getItemId(int position) {
		        return position;
		    }

		    @Override
		    public View getView(final int position, View convertView, ViewGroup parent) {
		        ViewHolder holder;
		        ChatMessage chatMessage = getItem(position);
		        LayoutInflater vi = (LayoutInflater) chatWithFriend.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		        if (convertView == null) {
		            convertView = vi.inflate(R.layout.message_list, null);
		            holder = createViewHolder(convertView);
		            convertView.setTag(holder);
		        } else {
		            holder = (ViewHolder) convertView.getTag();
		        }
		        setAlignment(holder, chatMessage.isIncoming());
		        holder.txtMessage.setText(chatMessage.getText());
		        if (chatMessage.getSender() != null) {
		            holder.txtInfo.setText(chatMessage.getSender() + ": " + getTimeText(chatMessage.getTime()));
		        } else {
		            holder.txtInfo.setText(getTimeText(chatMessage.getTime()));
		        }

		        return convertView;
		    }

		    public void add(ChatMessage message) {
		        chatMessages.add(message);
		    }

		    public void add(List<ChatMessage> messages) {
		        chatMessages.addAll(messages);
		    }

		    private void setAlignment(ViewHolder holder, boolean isIncoming) {
		        if (isIncoming) {
		            holder.contentWithBG.setBackgroundResource(R.drawable.balloon_classic_outgoing);

		            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
		            layoutParams.gravity = Gravity.RIGHT;
		            holder.contentWithBG.setLayoutParams(layoutParams);

		            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
		            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
		            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		            holder.content.setLayoutParams(lp);
		            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
		            layoutParams.gravity = Gravity.RIGHT;
		            holder.txtMessage.setLayoutParams(layoutParams);

		            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
		            layoutParams.gravity = Gravity.RIGHT;
		            holder.txtInfo.setLayoutParams(layoutParams);
		        } else {
		            holder.contentWithBG.setBackgroundResource(R.drawable.balloon_classic_incoming);

		            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
		            layoutParams.gravity = Gravity.LEFT;
		            holder.contentWithBG.setLayoutParams(layoutParams);

		            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
		            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
		            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		            holder.content.setLayoutParams(lp);
		            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
		            layoutParams.gravity = Gravity.LEFT;
		            holder.txtMessage.setLayoutParams(layoutParams);

		            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
		            layoutParams.gravity = Gravity.LEFT;
		            holder.txtInfo.setLayoutParams(layoutParams);
		        }
		    }

		    private ViewHolder createViewHolder(View v) {
		        ViewHolder holder = new ViewHolder();
		        holder.txtMessage = (com.scommix.customviews.MessageItemTextView) v.findViewById(R.id.txtMessage);
		        holder.content = (LinearLayout) v.findViewById(R.id.content);
		        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
		        holder.txtInfo = (com.scommix.customviews.MessageItemTextView) v.findViewById(R.id.txtInfo);
		        return holder;
		    }

		    private String getTimeText(Date date) {
		        return DateFormat.format(DATE_FORMAT, date).toString();
		    }

		    private  class ViewHolder {
		        public com.scommix.customviews.MessageItemTextView txtMessage;
		        public com.scommix.customviews.MessageItemTextView txtInfo;
		        public LinearLayout content;
		        public LinearLayout contentWithBG;
		    }
	  
	 
	 
	 }
	 
	 class sendmessagetofriend extends AsyncTask<Void, Void, Void>
	  {
	
			
		  @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
				try {
					String xml = null;
					try {
						xml = convertStreamToString(getAssets().open("message.xml"));
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					String request1 = String.format(xml, ScommixSharedPref.getUSERID(),friendid,mText); 
				
					
					request1=MessageSenderGCM.getResponseByXML(request1);
				
			
							//MessageSenderGCM.AndroidPush(mText);
						
	 			 } catch (Exception e) {
					e.printStackTrace();
				}
			return null;
		}
		
		private String convertStreamToString(InputStream is) throws IOException {
			  BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			    StringBuilder sb = new StringBuilder();
			    String line = null;
			    while ((line = reader.readLine()) != null) {
			        sb.append(line+"\n");
			    }
			    is.close();
			    return sb.toString();
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		
		}
		  
	  }
	 
	 
	 class MessageReceiver extends BroadcastReceiver
	 {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			 String data=intent.getStringExtra("responsenewmessage");
		            runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
							if(msgs.size()>0)
							{
								msgs.clear();
								 msgs.addAll(s.getMsgs());
							}
							else{
								 msgs.addAll(s.getMsgs());
								
							}
							
						 
						// TODO Auto-generated method stub
						adapter.notifyDataSetChanged();
						
					    lv.setSelection(adapter.getCount());
					}
				});
			 
		}
		 
	 }
	 
	 
	 class GetMessage extends AsyncTask<Void, Void, Void>
	 {
	
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//fvknfadvnbfjkvjkfavl
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
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			action.setSubtitle(friendname);
			adapter=new MessageAdapter(ChatWithFriend.this, msgs);
			lv.setAdapter(adapter);
	
			
		}
		 
	 }
	 
	 
}
