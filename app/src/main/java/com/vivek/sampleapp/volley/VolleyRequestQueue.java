//package com.vivek.sampleapp.volley;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
//import com.vivek.sampleapp.application.MyApplication;
//
///**
// * Created by vivek-pc on 12/6/2015.
// */
//public class VolleyRequestQueue {
//    private static VolleyRequestQueue mInstance = null;
//    private RequestQueue mRequestQueue;
//
//    private VolleyRequestQueue(){
//        mRequestQueue = Volley.newRequestQueue(MyApplication.getContext());
//    }
//
//    public static synchronized VolleyRequestQueue getInstance(){
//        if(mInstance == null){
//            mInstance = new VolleyRequestQueue();
//        }
//        return mInstance;
//    }
//
//    public RequestQueue getRequestQueue(){
//        return this.mRequestQueue;
//    }
//
//}
