package com.example.hoang.ss14.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hoang.ss14.fragments.DownloadFragment;
import com.example.hoang.ss14.fragments.FavoriteFragment;
import com.example.hoang.ss14.fragments.MusicTypeFragment;

/**
 * Created by hoang on 4/15/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new MusicTypeFragment();
            case 1 : return new FavoriteFragment();
            case 2 : return new DownloadFragment();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
