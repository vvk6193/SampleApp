package com.vivek.sampleapp.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by vivek-pc on 12/6/2015.
 */
public class MyApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
