package com.scommix.navigationmainactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.scommix.navigationmainactivity.Results.GetResult;
import com.svimedu.scommix.R;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class TimeTableFragmentInside extends Fragment {
	public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    String teachernames, pid,subjects,roomno;
    ArrayList<String> days;
    ExpandableListView dayslistview;
    TimeTableExpandableAdapter adapter;
    private HashMap<String, ArrayList<String>> daywisedata;

    
     public static TimeTableFragmentInside create(int page, String teachernames, String pid, String subjects, String roomno) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("teachernames", teachernames);
        args.putString("pid", pid);
        args.putString("subjects", subjects);
        args.putString("roomno", roomno);
      //  args.putString("subject", subject);
        TimeTableFragmentInside fragment = new TimeTableFragmentInside();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	Log.i("oncreate", "called pager");
    	mPage = getArguments().getInt(ARG_PAGE);
    	teachernames=getArguments().getString("teachernames");
    	pid=getArguments().getString("pid");
    	  subjects=getArguments().getString("subjects");
    	  roomno=getArguments().getString("roomno");
    	preparedata();
    //	subject=getArguments().getString("subject");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
      dayslistview=(ExpandableListView)view.findViewById(R.id.listtimetabledays);
      adapter=new TimeTableExpandableAdapter(getActivity(),days,daywisedata);
      dayslistview.setAdapter(adapter);
      
      return view;
      
    }

    private void preparedata() {
    	
		// TODO Auto-generated method stub
    	days=new ArrayList<String>();
    	daywisedata =new HashMap<String, ArrayList<String>>();
    	  String[] test=teachernames.split("###");
    	  
    	  if(test.length==6)
    	  {
    		  String names[]=new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    	        for(int i=0;i<names.length;i++)
    	        {
    	     	   days.add(i, names[i]);
    	        }
    	        
    	        ArrayList<String> mondaydata=new ArrayList<String>();
    	        String[] splitnames1=teachernames.split("###");
    	        String[] splitsubjects=subjects.split("###");
    	        String[] splitroom=roomno.split("###");
    	        
    	        try{
    	        	
    	        	if(splitnames1[0].equals("null"))
    	        	{
    	        		mondaydata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		mondaydata.add(0, "Teacher name :"+splitnames1[0]);
    	        	}
    	        	 
    	        }
    	        catch(Exception e)
    	        {
   	        	 mondaydata.add(0, "Teacher name :"+"N/A");

    	        }
    	        
                try{
                	if(splitsubjects[0].equals("null"))
                	{
                		 mondaydata.add(1,"Subject name: "+"N/A");
                	}
                	else{
                		 mondaydata.add(1,"Subject name: "+splitsubjects[0]);
                	}
    	           	
    	        }
    	        catch(Exception e)
    	        {
    	        	 mondaydata.add(1,"Subject name: "+"N/A");	
    	        }
    
    try{
    	if(splitroom[0].equals("null"))
    	{
    		 mondaydata.add(2,"Room no: "+"N/A");
    	}
    	else{
    		 mondaydata.add(2,"Room no: "+splitroom[0]);
    	}
       

    }
    catch(Exception e)
    {
        mondaydata.add(2,"Room no: "+"N/A");

    }
    	       
    	       
    	        
    	        ArrayList<String> tuesdaydata=new ArrayList<String>();
    	        String[] splitnames2=teachernames.split("###");
    	        String[] splitsubjects2=subjects.split("###");
    	        String[] splitroom2=roomno.split("###");
    	        
    	        try{
    	        	
    	        	if(splitnames2[1].equals("null"))
    	        	{
    	        		 tuesdaydata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		 tuesdaydata.add(0, "Teacher name :"+splitnames2[1]);
    	        	}
    	        	
    	    	    
    	        }
    	        catch(Exception e)
    	        {
    	        	 tuesdaydata.add(0, "Teacher name :"+"N/A");
    	    	   
    	        }
    	        
    	        
    	        try{
   	        
    	        	if(splitsubjects2[1].equals("null"))
    	        	{
    	        		 tuesdaydata.add(1,"Subject name: "+"N/A");
    	        	}
    	        	else{
    	        		 tuesdaydata.add(1,"Subject name: "+splitsubjects2[1]);
    	        	}
   	    	       
   	    	       
   	        }
   	        catch(Exception e)
   	        {
   	        
   	    	        tuesdaydata.add(1,"Subject name: "+"N/A");
   	    	    
   	        }
    	        
    	        try{
   	        
    	        	if(splitroom2[1].equals("null"))
    	        	{
    	        		 tuesdaydata.add(2,"Room no: "+"N/A");
    	        	}
    	        	else
    	        	{
    	        		 tuesdaydata.add(2,"Room no: "+splitroom2[1]);
    	        	}
   	    	       
   	        }
   	        catch(Exception e)
   	        {
   	        	
   	    	        tuesdaydata.add(2,"Room no: "+"N/A");
   	        }
    	       
    	        
    	        ArrayList<String> wednesdaydata=new ArrayList<String>();
    	        String[] splitnames3=teachernames.split("###");
    	        String[] splitsubjects3=subjects.split("###");
    	        String[] splitroom3=roomno.split("###");
    	        
    	        try{
    	        	
    	        	if(splitnames3[2].equals("null"))
    	        	{
    	        		  wednesdaydata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		  wednesdaydata.add(0, "Teacher name :"+splitnames3[2]);
    	        	}
    	        	
    	    	  
    	        }
    	        catch(Exception e)
    	        {
    	        	  wednesdaydata.add(0, "Teacher name :"+"N/A");
    	    	      
    	        }
    	        
    	        try{
  	        	
    	        	if(splitsubjects3[2].equals("null"))
    	        	{
      	    	        wednesdaydata.add(1,"Subject name: "+"N/A");

    	        	}
    	        	else{
      	    	        wednesdaydata.add(1,"Subject name: "+splitsubjects3[2]);
	
    	        	}
  	    	  
  	        }
  	        catch(Exception e)
  	        {
  	        	
  	    	        wednesdaydata.add(1,"Subject name: "+"N/A");
  	    	      
  	        }
    	        
    	        try{
  	        	 
    	        	if(splitroom3[2].equals("null"))
    	        	{
    	        		wednesdaydata.add(2,"Room no: "+"N/A");
    	        	}
    	        	else{
    	        		wednesdaydata.add(2,"Room no: "+splitroom3[2]);
    	        	}
  	    	        
  	        }
  	        catch(Exception e)
  	        {
  	    	        wednesdaydata.add(2,"Room no: "+"N/A");
  	        }
    	      
    	        
    	        ArrayList<String> thursdaydata=new ArrayList<String>();
    	        String[] splitnames4=teachernames.split("###");
    	        String[] splitsubjects4=subjects.split("###");
    	        String[] splitroom4=roomno.split("###");
    	        
    	       
    	        
    	        try{
    	        	
    	        	if(splitnames4[3].equals("null"))
    	        	{
    	        		thursdaydata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		thursdaydata.add(0, "Teacher name :"+splitnames4[3]);
    	        	}
    	        
  	    	  
  	        }
  	        catch(Exception e)
  	        {
  	        	thursdaydata.add(0, "Teacher name :"+"N/A");
  	    	      
  	        }
  	        
  	        try{
  	        	if(splitsubjects4[3].equals("null"))
  	        	{
  	  	        	thursdaydata.add(1,"Subject name: "+"N/A");

  	        	}
  	        	else{
  	  	        	thursdaydata.add(1,"Subject name: "+splitsubjects4[3]);

  	        	}
	        	
	    	  
	        }
	        catch(Exception e)
	        {
	        	
	        	thursdaydata.add(1,"Subject name: "+"N/A");
	    	      
	        }
  	        
  	        try{
	        	 if(splitroom4[3].equals("null"))
	        	 {
	        			thursdaydata.add(2,"Room no: "+"N/A");
	        	 }
	        	 else{
	        			thursdaydata.add(2,"Room no: "+splitroom4[3]); 
	        	 }
  	        
	        }
	        catch(Exception e)
	        {
	        	thursdaydata.add(2,"Room no: "+"N/A");
	        }
  	      
    	        
    	        ArrayList<String> fridaydata=new ArrayList<String>();
    	        String[] splitnames5=teachernames.split("###");
    	        String[] splitsubjects5=subjects.split("###");
    	        String[] splitroom5=roomno.split("###");
    	        
    	        
    	        try{
    	        	  if(splitnames5[4].equals("null"))
    	        	  {
    	        		  fridaydata.add(0, "Teacher name :"+"N/A");
    	        	  }
    	        	  else{
    	        		  fridaydata.add(0, "Teacher name :"+splitnames5[4]);
    	        	  }
        	       
        	       
    	        }
    	        catch(Exception e)
    	        {
    	        	  
        	        fridaydata.add(0, "Teacher name :"+"N/A");
        	       
    	        }
    	        
    	        try{
  	        	  
        	       if(splitsubjects5[4].equals("null"))
        	       {
        	    	   fridaydata.add(1,"Subject name: "+"N/A");
        	       }
        	       else{
        	    	   fridaydata.add(1,"Subject name: "+splitsubjects5[4]);
        	       }
        	       
        	     
    	        }
    	        catch(Exception e)
    	        {
    	        	  
        	        
        	        fridaydata.add(1,"Subject name: "+"N/A");
        	    
    	        }
    	        
    	        try{
  	        	
    	        	if(splitroom5[4].equals("null"))
    	        	{
    	        		  fridaydata.add(2,"Room no: "+"N/A");
    	        	}
    	        	else{
    	        		  fridaydata.add(2,"Room no: "+splitroom5[4]);
    	        	}
        	      
    	        }
    	        catch(Exception e)
    	        {
    	        
        	        fridaydata.add(2,"Room no: "+"N/A");
    	        }
    	      
    	        
    	        
    	        ArrayList<String> satdata=new ArrayList<String>();
    	   
    	        String[] splitnames6=teachernames.split("###");
    	        String[] splitsubjects6=subjects.split("###");
    	        String[] splitroom6=roomno.split("###");
    	        
    	        try{
    	        	
    	        	if(splitnames6[5].equals("null"))
    	        	{
    	        		 satdata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		 satdata.add(0, "Teacher name :"+splitnames6[5]);
    	        	}
    	        	
    	    	       	
    	        }
    	        catch(Exception e)
    	        {
    	        	 satdata.add(0, "Teacher name :"+"N/A");
    	    	    
    	        }
    	        
    	        try{
   	        	 if(splitsubjects6[5].equals("null"))
   	        	 {
   	        		 satdata.add(1,"Subject name: "+"N/A");
   	        	 }
   	        	 else{
   	        		 satdata.add(1,"Subject name: "+splitsubjects6[5]);
   	        	 }
   	    	       
   	    	       // satdata.add(2,"Room no: "+splitroom6[5]);	
   	        }
   	        catch(Exception e)
   	        {
   	        	// satdata.add(0, "Teacher name :"+"N/A");
   	    	        satdata.add(1,"Subject name: "+"N/A");
   	    	      //  satdata.add(2,"Room no: "+"N/A");
   	        }
    	        try{
   	        	if(splitroom6[5].equals("null"))
   	        	{
   	        	   satdata.add(2,"Room no: "+"N/A");
   	        	}
   	        	else{
   	        	   satdata.add(2,"Room no: "+splitroom6[5]);
   	        	}
   	    	     	
   	        }
   	        catch(Exception e)
   	        {
   	        	
   	    	        satdata.add(2,"Room no: "+"N/A");
   	        }
    	       
    	        
    	       
    	        
    	        daywisedata.put(days.get(0), mondaydata);
    	        daywisedata.put(days.get(1), tuesdaydata);
    	        daywisedata.put(days.get(2), wednesdaydata);
    	        daywisedata.put(days.get(3), thursdaydata);
    	        daywisedata.put(days.get(4), fridaydata);
    	        daywisedata.put(days.get(5), satdata);
    	        
    	  }
    	  else if(test.length==5)
    	  {

    		  String names[]=new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday"};
    	        for(int i=0;i<names.length;i++)
    	        {
    	     	   days.add(i, names[i]);
    	        }
    	        
    	        ArrayList<String> mondaydata=new ArrayList<String>();
    	        String[] splitnames1=teachernames.split("###");
    	        String[] splitsubjects=subjects.split("###");
    	        String[] splitroom=roomno.split("###");
    	        
    	        try{
    	        	
    	        	if(splitnames1[0].equals("null"))
    	        	{
    	        		mondaydata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		mondaydata.add(0, "Teacher name :"+splitnames1[0]);
    	        	}
    	        	 
    	        }
    	        catch(Exception e)
    	        {
   	        	 mondaydata.add(0, "Teacher name :"+"N/A");

    	        }
    	        
                try{
                	if(splitsubjects[0].equals("null"))
                	{
                		 mondaydata.add(1,"Subject name: "+"N/A");
                	}
                	else{
                		 mondaydata.add(1,"Subject name: "+splitsubjects[0]);
                	}
    	           	
    	        }
    	        catch(Exception e)
    	        {
    	        	 mondaydata.add(1,"Subject name: "+"N/A");	
    	        }
    
    try{
    	if(splitroom[0].equals("null"))
    	{
    		 mondaydata.add(2,"Room no: "+"N/A");
    	}
    	else{
    		 mondaydata.add(2,"Room no: "+splitroom[0]);
    	}
       

    }
    catch(Exception e)
    {
        mondaydata.add(2,"Room no: "+"N/A");

    }
    	       
    	       
    	        
    	        ArrayList<String> tuesdaydata=new ArrayList<String>();
    	        String[] splitnames2=teachernames.split("###");
    	        String[] splitsubjects2=subjects.split("###");
    	        String[] splitroom2=roomno.split("###");
    	        
    	        try{
    	        	
    	        	if(splitnames2[1].equals("null"))
    	        	{
    	        		 tuesdaydata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		 tuesdaydata.add(0, "Teacher name :"+splitnames2[1]);
    	        	}
    	        	
    	    	    
    	        }
    	        catch(Exception e)
    	        {
    	        	 tuesdaydata.add(0, "Teacher name :"+"N/A");
    	    	   
    	        }
    	        
    	        
    	        try{
   	        
    	        	if(splitsubjects2[1].equals("null"))
    	        	{
    	        		 tuesdaydata.add(1,"Subject name: "+"N/A");
    	        	}
    	        	else{
    	        		 tuesdaydata.add(1,"Subject name: "+splitsubjects2[1]);
    	        	}
   	    	       
   	    	       
   	        }
   	        catch(Exception e)
   	        {
   	        
   	    	        tuesdaydata.add(1,"Subject name: "+"N/A");
   	    	    
   	        }
    	        
    	        try{
   	        
    	        	if(splitroom2[1].equals("null"))
    	        	{
    	        		 tuesdaydata.add(2,"Room no: "+"N/A");
    	        	}
    	        	else
    	        	{
    	        		 tuesdaydata.add(2,"Room no: "+splitroom2[1]);
    	        	}
   	    	       
   	        }
   	        catch(Exception e)
   	        {
   	        	
   	    	        tuesdaydata.add(2,"Room no: "+"N/A");
   	        }
    	       
    	        
    	        ArrayList<String> wednesdaydata=new ArrayList<String>();
    	        String[] splitnames3=teachernames.split("###");
    	        String[] splitsubjects3=subjects.split("###");
    	        String[] splitroom3=roomno.split("###");
    	        
    	        try{
    	        	
    	        	if(splitnames3[2].equals("null"))
    	        	{
    	        		  wednesdaydata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		  wednesdaydata.add(0, "Teacher name :"+splitnames3[2]);
    	        	}
    	        	
    	    	  
    	        }
    	        catch(Exception e)
    	        {
    	        	  wednesdaydata.add(0, "Teacher name :"+"N/A");
    	    	      
    	        }
    	        
    	        try{
  	        	
    	        	if(splitsubjects3[2].equals("null"))
    	        	{
      	    	        wednesdaydata.add(1,"Subject name: "+"N/A");

    	        	}
    	        	else{
      	    	        wednesdaydata.add(1,"Subject name: "+splitsubjects3[2]);
	
    	        	}
  	    	  
  	        }
  	        catch(Exception e)
  	        {
  	        	
  	    	        wednesdaydata.add(1,"Subject name: "+"N/A");
  	    	      
  	        }
    	        
    	        try{
  	        	 
    	        	if(splitroom3[2].equals("null"))
    	        	{
    	        		wednesdaydata.add(2,"Room no: "+"N/A");
    	        	}
    	        	else{
    	        		wednesdaydata.add(2,"Room no: "+splitroom3[2]);
    	        	}
  	    	        
  	        }
  	        catch(Exception e)
  	        {
  	    	        wednesdaydata.add(2,"Room no: "+"N/A");
  	        }
    	      
    	        
    	        ArrayList<String> thursdaydata=new ArrayList<String>();
    	        String[] splitnames4=teachernames.split("###");
    	        String[] splitsubjects4=subjects.split("###");
    	        String[] splitroom4=roomno.split("###");
    	        
    	       
    	        
    	        try{
    	        	
    	        	if(splitnames4[3].equals("null"))
    	        	{
    	        		thursdaydata.add(0, "Teacher name :"+"N/A");
    	        	}
    	        	else{
    	        		thursdaydata.add(0, "Teacher name :"+splitnames4[3]);
    	        	}
    	        
  	    	  
  	        }
  	        catch(Exception e)
  	        {
  	        	thursdaydata.add(0, "Teacher name :"+"N/A");
  	    	      
  	        }
  	        
  	        try{
  	        	if(splitsubjects4[3].equals("null"))
  	        	{
  	  	        	thursdaydata.add(1,"Subject name: "+"N/A");

  	        	}
  	        	else{
  	  	        	thursdaydata.add(1,"Subject name: "+splitsubjects4[3]);

  	        	}
	        	
	    	  
	        }
	        catch(Exception e)
	        {
	        	
	        	thursdaydata.add(1,"Subject name: "+"N/A");
	    	      
	        }
  	        
  	        try{
	        	 if(splitroom4[3].equals("null"))
	        	 {
	        			thursdaydata.add(2,"Room no: "+"N/A");
	        	 }
	        	 else{
	        			thursdaydata.add(2,"Room no: "+splitroom4[3]); 
	        	 }
  	        
	        }
	        catch(Exception e)
	        {
	        	thursdaydata.add(2,"Room no: "+"N/A");
	        }
  	      
    	        
    	        ArrayList<String> fridaydata=new ArrayList<String>();
    	        String[] splitnames5=teachernames.split("###");
    	        String[] splitsubjects5=subjects.split("###");
    	        String[] splitroom5=roomno.split("###");
    	        
    	        
    	        try{
    	        	  if(splitnames5[4].equals("null"))
    	        	  {
    	        		  fridaydata.add(0, "Teacher name :"+"N/A");
    	        	  }
    	        	  else{
    	        		  fridaydata.add(0, "Teacher name :"+splitnames5[4]);
    	        	  }
        	       
        	       
    	        }
    	        catch(Exception e)
    	        {
    	        	  
        	        fridaydata.add(0, "Teacher name :"+"N/A");
        	       
    	        }
    	        
    	        try{
  	        	  
        	       if(splitsubjects5[4].equals("null"))
        	       {
        	    	   fridaydata.add(1,"Subject name: "+"N/A");
        	       }
        	       else{
        	    	   fridaydata.add(1,"Subject name: "+splitsubjects5[4]);
        	       }
        	       
        	     
    	        }
    	        catch(Exception e)
    	        {
    	        	  
        	        
        	        fridaydata.add(1,"Subject name: "+"N/A");
        	    
    	        }
    	        
    	        try{
  	        	
    	        	if(splitroom5[4].equals("null"))
    	        	{
    	        		  fridaydata.add(2,"Room no: "+"N/A");
    	        	}
    	        	else{
    	        		  fridaydata.add(2,"Room no: "+splitroom5[4]);
    	        	}
        	      
    	        }
    	        catch(Exception e)
    	        {
    	        
        	        fridaydata.add(2,"Room no: "+"N/A");
    	        }
    	      
    	        
    	        
    	 
    	       
    	        
    	       
    	        
    	        daywisedata.put(days.get(0), mondaydata);
    	        daywisedata.put(days.get(1), tuesdaydata);
    	        daywisedata.put(days.get(2), wednesdaydata);
    	        daywisedata.put(days.get(3), thursdaydata);
    	        daywisedata.put(days.get(4), fridaydata);
    	       
    	        
    	  
    	  }
    	
    	
       
        
	}

	public class TimeTableExpandableAdapter extends BaseExpandableListAdapter
    {
    	FragmentActivity activity;
    	ArrayList<String> days;
    	HashMap<String, ArrayList<String>> daywisedata;
    	
		public TimeTableExpandableAdapter(FragmentActivity activity,
				ArrayList<String> days, HashMap<String, ArrayList<String>> daywisedata) {
			// TODO Auto-generated constructor stub
			this.activity=activity;
		this.days=days;
		this.daywisedata=daywisedata;
		}
		
		
		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			// TODO Auto-generated method stub
			return this.daywisedata.get(this.days.get(groupPosition))
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
				LayoutInflater infalInflater = (LayoutInflater) this.activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			return this.daywisedata.get(this.days.get(groupPosition))
					.size();
		}
		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return this.days.get(groupPosition);
		}
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return this.daywisedata.size();
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
				LayoutInflater infalInflater = (LayoutInflater) this.activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.exp_list_group, null);
			}
				dayslistview.expandGroup(groupPosition);
			TextView lblListHeader = (TextView) convertView
					.findViewById(R.id.lblListHeader);
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
    
	
}
