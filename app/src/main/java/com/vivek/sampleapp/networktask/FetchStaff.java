package com.vivek.sampleapp.networktask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.vivek.sampleapp.basetask.BaseNetworkTask;
import com.vivek.sampleapp.modal.StaffInformation;
import com.vivek.sampleapp.volley.VolleyRequest;
import com.vivek.sampleapp.volley.VolleyRequestQueue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v.vekariya on 12/9/2015.
 */
public class FetchStaff extends BaseNetworkTask<List<StaffInformation>> {
    public FetchStaff() {
        super();
    }

    public FetchStaff(Response.Listener<List<StaffInformation>> successListener) {
        super(Request.Method.POST, successListener);
        Type listType = new TypeToken<ArrayList<StaffInformation>>() {
        }.getType();
        super.setResponseClassType(listType);
    }

    @Override
    public void executeNetworkRequest(int type, String uri, Map<String, String> header, Map<String, String> params, Type clazz, Response.Listener succListener, Response.ErrorListener errorListener) {
        VolleyRequestQueue.getInstance().getRequestQueue().add(
                new VolleyRequest<List<StaffInformation>>(type, uri, getRequestBody(), header, params, clazz, succListener, errorListener));
    }

    @Override
    public String provideUri() {
        return "http://107.110.77.237/ipdweb/patient/get_staff";
    }

    @Override
    public Map<String, String> provideHeader(int type) {
        return null;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("patient_id", "p101");
        return params;
    }

    @Override
    public String getRequestBody() {
        return null;
    }
}
