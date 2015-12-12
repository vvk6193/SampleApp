package com.vivek.sampleapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vivek.sampleapp.fragment.DetailFragment;

/**
 * Created by vivek-pc on 11/29/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + position;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance("vvk","vekariya");
    }

    @Override
    public int getCount() {
        return 5;
    }
}
