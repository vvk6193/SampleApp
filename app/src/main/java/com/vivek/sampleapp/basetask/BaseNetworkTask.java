package com.vivek.sampleapp.basetask;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vivek.sampleapp.ExecutorFramework.MyExecutor;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.android.volley.Request.Method;
import static com.android.volley.Response.ErrorListener;
import static com.android.volley.Response.Listener;

/**
 * Created by vivek-pc on 12/5/2015.
 */
public abstract class BaseNetworkTask<T> implements Callable, Listener<T> , Response.ErrorListener {

    private MyExecutor executor;

    private final Gson gson = new Gson();

    private Type clazz;

//    private String uri;

    private Response.Listener<T> successListener;

    private int requestType;

    public Type getResponseClassType() {
        return responseClassType;
    }

    public void setResponseClassType(Type responseClassType) {
        this.responseClassType = responseClassType;
    }

    private Type responseClassType;

    public BaseNetworkTask() {}

    public BaseNetworkTask(int type ,Listener<T> successListener) {
//        this.errorListener = errorListener;
        this.requestType = type;
        this.clazz = responseClassType;
        this.successListener = successListener;
    }

    @Override
    public Object call() throws Exception {
        this.executeNetworkRequest(requestType,provideUri(),getHeader(requestType),clazz ,this, this);
        return new Object();
    }

    public void execute () {
        executor = MyExecutor.getInstance();
        executor.submit(this);
    }

    public Map<String,String> getHeader(int type) {
        Map<String,String> header = new HashMap<>();
        switch(type) {
            case Method.GET:

                break;
            case Method.POST:

                break;
            case Method.PATCH:

                break;
            case Method.PUT:

                break;
            default:
                break;
        }
        return header;
    }

//    @Override
//    public void onResponse( response) {
//
//    }


    @Override
    public void onResponse(Object response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public abstract void executeNetworkRequest(int type,String uri,Map<String,String> header, Type clazz,Listener succListener, ErrorListener errorListener);

    public abstract String provideUri();

    public abstract Map<String,String> provideHeader(int type);

}
