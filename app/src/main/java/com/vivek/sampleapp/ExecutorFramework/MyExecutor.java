package com.vivek.sampleapp.ExecutorFramework;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by vivek-pc on 12/5/2015.
 */
public class MyExecutor {

    private static MyExecutor instance;

    static  {
//        instance = new MyExecutor();
    }

    private MyExecutor() {
        workerQueue = new LinkedBlockingQueue<Runnable>();
        executor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES * 2,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workerQueue);
    }

    public static synchronized MyExecutor getInstance() {
        if(instance == null) {
                if(instance == null)
                instance = new MyExecutor();
                return instance;
        } else {
            return instance;
        }
    }

    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();

    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final BlockingQueue<Runnable> workerQueue;
    // Instantiates the queue of Runnables as a LinkedBlockingQueue
    // Creates a thread pool manager
    private final ExecutorService executor;

    public Future submit(Callable callable) {
        synchronized (this) {
            return executor.submit(callable);
        }
    }

}
