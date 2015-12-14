package com.vivek.sampleapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vivek.sampleapp.fragment.ImageFragment;

import java.io.File;

/**
 * Created by vivek-pc on 12/15/2015.
 */
public class GalleryPagerAdapter extends FragmentPagerAdapter {


    File[] file;
    Context context;
    public GalleryPagerAdapter(FragmentManager fm,Context context,File[] file) {
        super(fm);
        this.file = file;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new ImageFragment(file[position],context);
    }

    @Override
    public int getCount() {
        return file.length;
    }
}
