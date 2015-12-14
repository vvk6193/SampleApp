package com.vivek.sampleapp.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vivek-pc on 12/13/2015.
 */
public class MySharedPreferenceManager {

    private static SharedPreferences prefs;

    private static MySharedPreferenceManager sharedPreferenceManager;

    private MySharedPreferenceManager(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static MySharedPreferenceManager getInstance(Context context) {
        if(sharedPreferenceManager == null) {
            sharedPreferenceManager = new MySharedPreferenceManager(context);
        }
        return sharedPreferenceManager;
    }

    public void putUserId(String uid) {
        prefs.edit().putString("uid",uid).apply();
    }

    public String getUserId() {
        return prefs.getString("uid","");
    }

    public void putFirstName(String uid) {
        prefs.edit().putString("fname",uid).apply();
    }

    public String getFirstName() {
        return prefs.getString("fname","");
    }

    public void putWeight(long weight) {
        prefs.edit().putLong("weight", weight).apply();
    }

    public long getWeight() {
        return prefs.getLong("weight", 0L);
    }


    public void putSignIn(Boolean issignin) {
        prefs.edit().putBoolean("signin", issignin).apply();
    }

    public Boolean getSignIn() {
        return prefs.getBoolean("signin", false);
    }

}
