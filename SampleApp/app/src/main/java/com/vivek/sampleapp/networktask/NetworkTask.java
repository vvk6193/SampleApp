package com.vivek.sampleapp.networktask;

import android.util.Log;

import com.squareup.okhttp.internal.http.HttpMethod;
import com.vivek.sampleapp.interfaces.Response;
import com.vivek.sampleapp.resttemplate.RequestExecutor;

/**
 * Created by v.vekariya on 12/10/2015.
 */
public class NetworkTask extends BaseNetworkClass{

    public NetworkTask(Response.SuccessListener successListener, Response.ErrorListener errorListener) {
        super(successListener, errorListener);
    }

//    @Override
//    public void runNetworkOperation() {
//
//    }

    @Override
    public String provideUri() {
        return "http://107.110.76.84:8080/vopd/patient";
    }

    @Override
    public HttpMethod getMethodType() {
        return null;
    }

    @Override
    public void runTask() {
//        Log.d("vvk","executed network task");
        runNetworkOperation();
    }
}
