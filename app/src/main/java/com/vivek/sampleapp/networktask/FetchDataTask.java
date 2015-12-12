package com.vivek.sampleapp.networktask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.vivek.sampleapp.Student;
import com.vivek.sampleapp.basetask.BaseNetworkTask;
import com.vivek.sampleapp.volley.VolleyRequest;
import com.vivek.sampleapp.volley.VolleyRequestQueue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vivek-pc on 12/5/2015.
 */
public class FetchDataTask extends BaseNetworkTask<List<Student>>{


    public FetchDataTask(Response.Listener<List<Student>> successListener) {
        super(Request.Method.GET,successListener);
        Type listType = new TypeToken<ArrayList<Student>>() {
        }.getType();
        super.setResponseClassType(listType);
    }

    public interface DeliverResponse{
        public void onResponse(List<Student> list);
    }

    @Override
    public void executeNetworkRequest(int type, java.lang.String uri,Map<String,String> header, Type clazz, Response.Listener succListener, Response.ErrorListener errorListener) {

        VolleyRequestQueue.getInstance().getRequestQueue().add(
        new VolleyRequest<List<Student>>(type,uri,header,clazz,succListener,errorListener));
    }

    @Override
    public java.lang.String provideUri() {
        return "vvk";
    }

    @Override
    public Map<java.lang.String, java.lang.String> provideHeader(int type) {
        return null;
    }
}
