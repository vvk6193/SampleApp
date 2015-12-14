//package com.vivek.sampleapp.basetask;
//
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.vivek.sampleapp.ExecutorFramework.MyExecutor;
//import com.vivek.sampleapp.interfaces.Response;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Type;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.Callable;
//
//
///**
// * Created by vivek-pc on 12/5/2015.
// */
//public abstract class BaseNetworkTask<T> implements Callable {
//
//
//    private final static MyExecutor executor = MyExecutor.getInstance();
//
//    private final Gson gson = new Gson();
//
//    private Type clazz;
//
////    private String uri;
//
//    private Response.Listener<T> successListener;
//
//    private int requestType;
//
//    public Type getResponseClassType() {
//        return responseClassType;
//    }
//
//    public void setResponseClassType(Type responseClassType) {
//        this.responseClassType = responseClassType;
//        this.clazz = responseClassType;
//    }
//
//    private Type responseClassType;
//
//    public BaseNetworkTask() {}
//
//    public BaseNetworkTask(int type ,Listener<T> successListener) {
////        this.errorListener = errorListener;
//        this.requestType = type;
//        this.successListener = successListener;
//    }
//
//    @Override
//    public Object call() throws Exception {
//        Log.d("vvk","call called");
//        this.executeNetworkRequest(requestType,provideUri(),getHeader(requestType),getParams(),clazz ,successListener, l);
//        return new Object();
//    }
//
//    public void execute () {
//        Log.d("vvk","execute called");
//        MyExecutor executor1 = MyExecutor.getInstance();
//        executor1.submit(this);
//    }
//
//    public void print() {
//        Log.d("vvk", "print called");
//    }
//
//    public Map<String,String> getHeader(int type) {
//        Map<String,String> header = new HashMap<>();
//        switch(type) {
//            case Method.GET:
//
//                break;
//            case Method.POST:
//
//                break;
//            case Method.PATCH:
//
//                break;
//            case Method.PUT:
//
//                break;
//            default:
//                break;
//        }
//        return header;
//    }
//
////    @Override
////    public void onResponse( response) {
////
////    }
//
//
//    ErrorListener l = new ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            Log.d("vvk","errord occured " + error.toString() + error.getMessage());
//        }
//    };
//
//    public abstract void executeNetworkRequest(int type,String uri,Map<String,String> header,Map<String,String> params, Type clazz,Listener succListener, ErrorListener errorListener);
//
//    public abstract String provideUri();
//
//    public abstract Map<String,String> provideHeader(int type);
//
//    public abstract Map<String,String> getParams();
//
//    public abstract String getRequestBody();
//
//}
