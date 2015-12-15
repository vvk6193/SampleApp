package com.vivek.sampleapp.networktask;

import android.util.Log;

import com.vivek.sampleapp.interfaces.PriorityCallable;

import java.util.concurrent.Callable;

/**
 * Created by v.vekariya on 12/10/2015.
 */
public class WorkerClass<T extends BaseTask> implements Callable,PriorityCallable {

    private T task;

    int priority;

//    public WorkerClass(T task) {
//        this.task = task;
//    }

    public WorkerClass(T task,int priority) {
        this.task = task;
        this.priority = priority;
    }

    @Override
    public Object call() throws Exception {
        Log.d("maripriority", "executing " + getPriority());
        task.runTask();
        return new Object();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
