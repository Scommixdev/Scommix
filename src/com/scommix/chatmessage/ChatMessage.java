package com.scommix.chatmessage;

import java.util.Date;

public class ChatMessage {
    public boolean incoming;
    public String text;
//    public Date time;
    public String time;
    public String sender;

    
    //Message Time with Date as data type is commented 
//    public ChatMessage(String text, Date time, boolean incoming) {
//        this(text, null, time, incoming);
//    }
//
//    public ChatMessage(String text, String sender, Date time, boolean incoming) {
//        this.text = text;
//        this.sender = sender;
//        this.time = time;
//        this.incoming = incoming;
//    }
    
    public ChatMessage(String text, String time, boolean incoming) {
        this(text, null, time, incoming);
    }

    public ChatMessage(String text, String sender, String time, boolean incoming) {
        this.text = text;
        this.sender = sender;
        this.time = time;
        this.incoming = incoming;
    }

    public ChatMessage() {
		// TODO Auto-generated constructor stub
	}

	public boolean isIncoming() {
		return incoming;
	}

	public void setIncoming(boolean incoming) {
		this.incoming = incoming;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	//Message Time with Date as data type is commented 
//	public Date getTime() {
//		return time;
//	}
//
//	public void setTime(Date time) {
//		this.time = time;
	
	
	//we are using as time in string format as we are reciving
	public String getTime() {
		return time;
	}

	public void setTime(String senttime) {
		this.time = senttime;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}


}
