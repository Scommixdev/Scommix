package com.scommix.navigationmainactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scommix.homeandprofile.HomeFragment;
import com.scommix.homeandprofile.MyProfileFragment;
import com.scommix.homeandprofile.MyProfilePagerAdapter;
import com.scommix.homeandprofile.MyStatusFragment;
import com.scommix.viewpager.MoreFragment;

public class MyProfilePagerAdapter1 extends FragmentPagerAdapter {
 
    public MyProfilePagerAdapter1(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            
            return new HomeFragment();
        case 1:
            
            return new MyStatusFragment();
            
        case 2:
        	
        	return new MessageFragment();
        	
        case 3:
        	
        	return new MoreFragment();
   
      
     
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
 
}