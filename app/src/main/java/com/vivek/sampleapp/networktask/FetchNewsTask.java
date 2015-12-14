package com.vivek.sampleapp.networktask;

import com.vivek.sampleapp.interfaces.Response;

import org.springframework.http.HttpMethod;

/**
 * Created by vivek-pc on 12/12/2015.
 */
public class FetchNewsTask extends BaseNetworkClass  {


    public FetchNewsTask(Response.SuccessListener successListener, Response.ErrorListener errorListener) {
        super(successListener, errorListener);
    }

    @Override
    public String provideUri() {
        return "http://timesofindia.indiatimes.com/feeds/newsdefaultfeeds.cms?feedtype=sjson";
    }

    @Override
    public HttpMethod getMethodType() {
        return org.springframework.http.HttpMethod.GET;
    }

//    @Override
//    public void runTask() {
//        runNetworkOperation();
//    }
}
