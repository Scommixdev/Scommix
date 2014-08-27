package com.scommix.viewpager;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scommix.homeandprofile.HomeFragment;
import com.scommix.homeandprofile.MyProfileFragment;
import com.scommix.homeandprofile.MyProfilePagerAdapter;
import com.scommix.homeandprofile.MyStatusFragment;
import com.scommix.navigationmainactivity.FriendsRequestsFragment;
import com.scommix.navigationmainactivity.MessageFragment;

public class MyProfilePagerAdapter1 extends FragmentPagerAdapter {
	
	
	ArrayList<Fragment> myfragments;
	
    public MyProfilePagerAdapter1(FragmentManager fm, ArrayList<Fragment> myfragments) {
        super(fm);
        this.myfragments=myfragments;
    }
 
    @Override
    public Fragment getItem(int index) {
 
       
            	   return myfragments.get(index);
     
         
       
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }
 
}