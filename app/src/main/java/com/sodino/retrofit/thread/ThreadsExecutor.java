package com.sodino.retrofit.thread;

import java.lang.*;
import java.lang.Runnable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/12/10.
 */
public class ThreadsExecutor extends ThreadPoolExecutor {


    public ThreadsExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<java.lang.Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    @Override
    protected void beforeExecute(Thread t, java.lang.Runnable r) {
        if (r instanceof com.sodino.retrofit.thread.Runnable) {
            ((com.sodino.retrofit.thread.Runnable)r).performBeforeRun(t);
        }
    }

    @Override
    protected void afterExecute(java.lang.Runnable r, Throwable t) {
        if (r instanceof com.sodino.retrofit.thread.Runnable) {
            ((com.sodino.retrofit.thread.Runnable)r).performAfterRun(Thread.currentThread());
        }
    }
}
