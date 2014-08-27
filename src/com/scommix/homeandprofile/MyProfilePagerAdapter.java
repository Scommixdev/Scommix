package com.scommix.homeandprofile;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyProfilePagerAdapter extends FragmentPagerAdapter {
 
    public MyProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            
            return new MyProfileFragment();
        case 1:
            
            return new MyStatusFragment();
            
   
      
     
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
 
}