package com.vivek.sampleapp.application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.vivek.sampleapp.sharedpreference.MySharedPreferenceManager;
import com.vivek.sampleapp.sharedpreference.MySharedPreferenceObject;

/**
 * Created by vivek-pc on 12/6/2015.
 */
public class MyApplication extends Application{

    private static MySharedPreferenceObject prefs;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
        context = getApplicationContext();
        prefs = new MySharedPreferenceObject();
        MySharedPreferenceManager sm = MySharedPreferenceManager.getInstance(context);
        prefs.setUid(sm.getUserId());
        prefs.setFname(sm.getFirstName());
        prefs.setWeight(sm.getWeight());
        prefs.setSignIn(sm.getSignIn());
        prefs.setS(sm);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyDialog()
                .build());
    }

    public static Context getContext() {
        return context;
    }

    public static MySharedPreferenceObject getPreferenceObject() {
        return prefs;
    }
}
