package com.sodino.retrofit.thread;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.AndroidRuntimeException;

/**
 * Created by Administrator on 2015/12/11.
 */
public abstract class Runnable implements java.lang.Runnable {
    protected String preThreadName;
    protected int prePriority;
    /**Runnable的名称。*/
    protected String name;
    protected final int priority;
    private volatile boolean mCalled = false;
    /**
     * 初始值为false;
     * performBeforeRun()后，值为true；
     * performAfterRun()后，值为false。
     * */
    protected boolean isRunning = false;
    protected Thread currentThread;
    public static final String THREAD_NAME_PREFIX = "VoV#";
    public static final String CrashUI = "CrashUI";

    /**
     * 默认Runnable用于构建由UI线程执行的内容。在非UI线程执行会直接抛异常提示。
     * 非UI线程的工作请使用 {@link #Runnable(String name, int priority)}
     * */
    public Runnable() {
        this.name = "main";
        this.priority = ThreadPriority.UI;
    }

    public Runnable(String name, int priority) {
        if (name == null || name.length() == 0) {
            throw new AndroidRuntimeException("Runnable's name canot be empty.");
        }
        this.name = name;
        this.priority = priority;
    }

    final void performBeforeRun(@NonNull Thread currentThread) {
        mCalled = false;
        beforeRun(currentThread);
        if (!mCalled) {
            throw new AndroidRuntimeException(this.getClass().getName() + "#" + name + " did not call through to super.beforeRun()");
        }

        if (priority == ThreadPriority.UI) {
            String currentName = currentThread.getName();
            if ((THREAD_NAME_PREFIX + CrashUI).equals(currentName) == false
                    && currentThread != Looper.getMainLooper().getThread()) {
                throw new AndroidRuntimeException(this.getClass().getName() + "#" + name + " is a UI runnable, can't be launched by no-UI Thread.");
            }
        }
        isRunning = true;

        this.currentThread = currentThread;

        ThreadPool.addRunnableRecord(this);
    }

    final void performAfterRun(Thread currentThread) {
        mCalled = false;
        afterRun(currentThread);
        if (!mCalled) {
            throw new AndroidRuntimeException(this.getClass().getName() + "#" + name + " did not call through to super.afterRun()");
        }
        isRunning = false;
        this.currentThread = null;

        ThreadPool.removeRunnableRecord(this);
    }

    final void performStopRun() {
        mCalled = false;
        stopRun();
        if (!mCalled) {
            throw new AndroidRuntimeException(this.getClass().getName() + "#" + name + " did not call through to super.stopRun()");
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**Runnable.run()方法执行之前的操作。*/
    public void beforeRun(Thread currentThread) {
        mCalled = true;
        preThreadName = currentThread.getName();
        int tid = android.os.Process.myTid();
        prePriority = android.os.Process.getThreadPriority(tid);
        currentThread.setName(THREAD_NAME_PREFIX + name);
        android.os.Process.setThreadPriority(priority);
    }

    /**Runnable.run()方法执行之后的操作。*/
    public void afterRun(Thread currentThread) {
        mCalled = true;
        currentThread.setName(preThreadName);
        android.os.Process.setThreadPriority(prePriority);
    }

    /**要把正在运行的业务停止掉，请在这里对停止操作进行实现。*/
    public void stopRun() {
        mCalled = true;
        if (currentThread != null) {
            // 执行interrupt之后，会引起Thread.sleep()等方法抛出InterruptedException
            currentThread.interrupt();
        }
    }

    public String getName() {
        return name;
    }
}
