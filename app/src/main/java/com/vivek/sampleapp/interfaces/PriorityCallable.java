package com.vivek.sampleapp.interfaces;

import java.util.concurrent.Callable;

/**
 * Created by vivek-pc on 12/15/2015.
 */
public interface PriorityCallable<T> extends Callable<T> {

    int getPriority();

}
