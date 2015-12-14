package com.vivek.sampleapp.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vivek.sampleapp.fragment.NewsFragment;
import com.vivek.sampleapp.modal.NewsItem;

import java.util.List;

/**
 * Created by vivek-pc on 12/13/2015.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {

    List<NewsItem> newsList;
    Activity context;

    public NewsPagerAdapter(FragmentManager fm,List<NewsItem> newsList,Activity context) {
        super(fm);
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new NewsFragment(context,newsList.get(position));
    }

    @Override
    public int getCount() {
        return newsList.size();
    }
}
