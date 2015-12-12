package com.vivek.sampleapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class BaseActivity extends AppCompatActivity {

    final static String TAG = "vvk";

    List<Future<?>> futureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        futureList = new ArrayList<>();
    }

    @Override
    protected void onDestroy()
    {   int size = futureList.size();
        Future<?> future;
        for(int i =0;i<size;i++) {
            future = futureList.get(i);
            if(!future.isDone() && !future.isCancelled()) {
                future.cancel(true);
                Log.d("vvk","cancelled");
            }
        }
        Log.i(TAG, "onDestroy enter");
        super.onDestroy();
        Log.i(TAG, "onDestroy exit");
    }

    @Override
    protected void onPause()
    {
        Log.i(TAG, "onPause enter");
        super.onPause();
        Log.i(TAG, "onPause exit");
    }

    @Override
    protected void onResume()
    {
        Log.i(TAG, "onResume enter");
        super.onResume();
        Log.i(TAG, "onResume exit");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Log.i(TAG, "onSaveInstanceState enter");
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState exit");
    }

    @Override
    protected void onStart()
    {
        Log.i(TAG, "onStart enter");
        super.onStart();
        Log.i(TAG, "onStart exit");
    }

    @Override
    protected void onStop()
    {
        Log.i(TAG, "onStop enter");
        super.onStop();
        Log.i(TAG, "onStop exit");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.i(TAG, "onRestoreInstanceState enter");
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState exit");
    }


}
