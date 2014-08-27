package com.scommix.adapters;


import java.util.ArrayList;

import org.kobjects.base64.Base64;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.scommix.WebServices.Common.online;
import com.scommix.homeandprofile.CommentBox;
import com.squareup.picasso.Picasso;
import com.svimedu.scommix.R;
import com.scommix.tools.RoundTrans;
import com.scommix.tools.Utils;

/**
 * This adapter is used to add comments to the list view of comment box
 * it uses linkify and gets a rounded bitmap of the profile pic of user
 * 
 * 
 * @author anmol
 *
 */
public class CommentBoxAdapter extends BaseAdapter
{
	
	Context ctx;
	ArrayList<online> comments;

	LayoutInflater inflater =null;
	
	public CommentBoxAdapter(CommentBox commentBox, ArrayList<online> comments) {
		// TODO Auto-generated constructor stub
		this.ctx=commentBox;
		this.comments=comments;
        inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
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
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi=convertView;
		ViewHolder holder;
		
		if(convertView==null)
		{
			vi=inflater.inflate(R.layout.commentboxlistitem, null);
			 holder = new ViewHolder();
			 holder.nameofuser=(TextView)vi.findViewById(R.id.commentlistitemstatus);
			 holder.status=(TextView)vi.findViewById(R.id.statuscommentlistitem);
			 holder.profilepic=(ImageView)vi.findViewById(R.id.commentlistitempic);
			vi.setTag(holder);
	       
	      
		}
		else{
			holder=(ViewHolder)vi.getTag();
		}
		holder.nameofuser.setText(comments.get(position).name);
		
		
		
		holder.status.setText(comments.get(position).comment);
		Linkify.addLinks(holder.status, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);

		// Recognize all of the default link text patterns 
		Linkify.addLinks(holder.status, Linkify.ALL);

		// Disable all default link detection
		Linkify.addLinks(holder.status, 0);
		holder.status.setLinkTextColor(Color.BLUE);
		
		
		 Picasso.with(ctx) //
	        .load(Utils.url+comments.get(position).userpic) //
	        .placeholder(R.drawable.ic_loading) //
	        .error(R.drawable.cross) //
	        .fit() //
	         .transform(new RoundTrans())
	        .into(holder.profilepic);
	
		
	
		

		
		 return vi;
	}
	static class ViewHolder{
		 TextView  nameofuser,status;
		ImageView profilepic;
	}
	
	
	
}

