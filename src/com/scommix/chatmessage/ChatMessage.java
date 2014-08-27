package com.scommix.chatmessage;

import java.util.Date;

public class ChatMessage {
    public boolean incoming;
    public String text;
    public Date time;
    public String sender;

    public ChatMessage(String text, Date time, boolean incoming) {
        this(text, null, time, incoming);
    }

    public ChatMessage(String text, String sender, Date time, boolean incoming) {
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}


}
