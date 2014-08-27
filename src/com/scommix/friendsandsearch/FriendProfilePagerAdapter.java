package com.scommix.friendsandsearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scommix.homeandprofile.MyProfileFragment;
import com.scommix.homeandprofile.MyStatusFragment;

public class FriendProfilePagerAdapter extends FragmentPagerAdapter {
 
    public FriendProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            
            return new FriendProfileFragment();
        case 1:
            
            return new FriendStatusFragment();
     
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
 
}