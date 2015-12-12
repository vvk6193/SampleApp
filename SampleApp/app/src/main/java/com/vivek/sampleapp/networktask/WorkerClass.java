package com.vivek.sampleapp.networktask;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Created by v.vekariya on 12/10/2015.
 */
public class WorkerClass<T extends BaseTask> implements Callable {

    private T task;

    public WorkerClass(T task) {
        this.task = task;
    }

    @Override
    public Object call() throws Exception {
        task.runTask();
        return new Object();
    }
}
