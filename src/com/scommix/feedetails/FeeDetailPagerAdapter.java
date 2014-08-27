package com.scommix.feedetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scommix.friendsandsearch.FriendProfileFragment;
import com.scommix.friendsandsearch.FriendProfilePagerAdapter;
import com.scommix.friendsandsearch.FriendStatusFragment;

public class FeeDetailPagerAdapter extends FragmentPagerAdapter {
 
    public FeeDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            
            return new ClassWiseFeeFragment();
        case 1:
            
            return new StudentWiseFeeFragment();
     
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
 
}