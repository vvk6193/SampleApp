package com.vivek.sampleapp.basetask;

import com.vivek.imageloader.ExecutorFramework.MyExecutor;
import com.vivek.imageloader.tasks.BaseTask;
import com.vivek.imageloader.tasks.WorkerClass;
import com.vivek.sampleapp.interfaces.Response;
import com.vivek.sampleapp.resttemplate.RequestExecutor;

import org.springframework.http.HttpMethod;

import java.util.concurrent.Future;

/**
 * Created by v.vekariya on 12/10/2015.
 */
public abstract class BaseNetworkTaskRestTemplate extends BaseTask {

    private Response.SuccessListener successListener;

    private Response.ErrorListener errorListener;

    public BaseNetworkTaskRestTemplate(Response.SuccessListener successListener, Response.ErrorListener errorListener) {
        this.successListener = successListener;
        this.errorListener = errorListener;
    }

    public void runNetworkOperation() {
//        Log.d("vvk","runnetworkoperation");
        new RequestExecutor(provideUri(), org.springframework.http.HttpMethod.GET,null,"",successListener,errorListener)
        .executeRequest();
    }

    public Future<?> execute() {
        WorkerClass<BaseNetworkTaskRestTemplate> worker = new WorkerClass<BaseNetworkTaskRestTemplate>(this,1);
        return MyExecutor.getInstance().submit(worker);
    }

    public abstract String provideUri();

    public abstract HttpMethod getMethodType();

    @Override
    public void runTask() {
        runNetworkOperation();
    }
}
