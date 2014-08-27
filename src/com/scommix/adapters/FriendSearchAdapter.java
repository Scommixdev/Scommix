package com.scommix.adapters;

import java.util.ArrayList;

import org.kobjects.base64.Base64;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scommix.friendsandsearch.SearchResultsActivity;
import com.scommix.homeandprofile.CommentBox;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;


public class FriendSearchAdapter extends BaseAdapter
{
	
	
	LayoutInflater inflater =null;
	SearchResultsActivity searchResultsActivity;
	ArrayList<String> friendidl; ArrayList<String> emailidl;
	ArrayList<String> cityl; ArrayList<String> namel;
	ArrayList<String> usernamel; ArrayList<String> classidl;
	ArrayList<String> userpic;

	
	public FriendSearchAdapter(SearchResultsActivity searchResultsActivity,
			ArrayList<String> friendidl, ArrayList<String> emailidl,
			ArrayList<String> cityl, ArrayList<String> namel,
			ArrayList<String> usernamel, ArrayList<String> classidl, ArrayList<String> userpic2) {
		// TODO Auto-generated constructor stub
		this.searchResultsActivity=searchResultsActivity;
		this.friendidl=friendidl;
		this.emailidl=emailidl;
		this.cityl=cityl;
		this.namel=namel;
		this.usernamel=usernamel;
		this.classidl=classidl;
		this.userpic=userpic2;
	    inflater=(LayoutInflater)searchResultsActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friendidl.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi=convertView;
		ViewHolder holder;
		
		if(convertView==null)
		{
			 vi=inflater.inflate(R.layout.friendsearchlistitem, null);
			 holder = new ViewHolder();
			 holder.usernamesearch=(TextView)vi.findViewById(R.id.usernamesearch);
			 holder.userpicsearch=(ImageView)vi.findViewById(R.id.userpicsearch);
			 vi.setTag(holder);
	       
	      
		}
		else{
			holder=(ViewHolder)vi.getTag();
		}
		holder.usernamesearch.setText(namel.get(position));
		
		 Picasso.with(searchResultsActivity) //
	        .load(Utils.url+userpic.get(position)) //
	        .placeholder(R.drawable.ic_loading) //
	        .error(R.drawable.cross) //
	        .fit() //
	         .transform(new RoundTrans())
	        .into(holder.userpicsearch);
	
	
		 return vi;
	}
	
	static class ViewHolder{
		TextView usernamesearch;
		ImageView userpicsearch;
	}
	
}

