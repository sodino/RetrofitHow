package com.sodino.retrofit.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2015/12/10.
 */
public class ThreadPool {
    public static final int STATE_NOT_FOUND = 0;
    public static final int STATE_BLOCKING = STATE_NOT_FOUND + 1;
    public static final int STATE_RUNNING = STATE_BLOCKING + 1;

    /**线程池中保留长驻线程数*/
    private final static int CORE_POOL_SIZE = 5;
    /**线程池中最大的工作线程数*/
    private final static int MAX_POOL_SIZE = 64;
    /**非长驻线程工作结束后，保留在线程池内不terminate的时间长度。单位TimeUnit.SECONDS。*/
    private final static int KEEP_ALIVE_TIME = 10;

    private static volatile ThreadPool instance;

    private ThreadsExecutor threadsExecutor;
    private BlockingQueue<java.lang.Runnable> blockingQueue;

    private LinkedList<WeakReference<Runnable>> listRunnable = new LinkedList<WeakReference<Runnable>>();

    private static volatile int index = 0;

    private static final ReentrantLock mainLock = new ReentrantLock();

    private static volatile Handler UIHandler;
    private static volatile Handler handlerLocalEvent;
    private static volatile Handler monitorHandler;
    /** 本地耗时事务的处理线程。*/
    private static HandlerThread hThreadLocalEvent;
    private static HandlerThread monitorHandlerThread;

    /**对线程池中的工作线程进行重命名。*/
    private ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(java.lang.Runnable r) {
            index++;
            String threadName = "Retrofit#" + index;
            Thread thread = new Thread(r);
            thread.setName(threadName);
            return thread;
        }
    };

    private ThreadPool() {
        blockingQueue = new LinkedBlockingDeque<java.lang.Runnable>();
        threadsExecutor = new ThreadsExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, blockingQueue, threadFactory);
    }

    private static ThreadPool getInstance() {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null) {
                    instance = new ThreadPool();
                }
            }
        }
        return instance;
    }

    public static ThreadsExecutor getThreadsExecutor(){
        return getInstance().threadsExecutor;
    }

    public static void post(@NonNull Runnable runnable) {
        getInstance().threadsExecutor.execute(runnable);
    }

    void performStopAll() {
        mainLock.lock();
        try{
            // 清除blockingQueue中还未来得及执行的Runnable
            blockingQueue.clear();

            for (WeakReference<Runnable> weakReference : listRunnable) {
                if (weakReference == null) {
                    continue;
                }
                Runnable runnable = weakReference.get();
                if (runnable == null) {
                    continue;
                }

                runnable.stopRun();
            }

            listRunnable.clear();
        }finally{
            mainLock.unlock();
        }
    }

    public static void stopAll() {
        getInstance().performStopAll();
    }

    static void addRunnableRecord(@NonNull Runnable runnable) {
        if (runnable == null) {
            return;
        }
        getInstance().performAddRunnableRecord(runnable);
    }

    private boolean performAddRunnableRecord(@NonNull Runnable runnable) {
        if (runnable == null) {
            return false;
        }

        boolean result = false;

        mainLock.lock();
        try {
            boolean needAdd = true;
            for (WeakReference<Runnable> reference: listRunnable) {
                if (reference != null && reference.get() == runnable) {
                    needAdd = false;
                    break;
                }
            }

            if (needAdd) {
                WeakReference<Runnable> weakReference = new WeakReference<Runnable>(runnable);
                listRunnable.add(weakReference);
                result = true;
            }
        } finally {
            mainLock.unlock();
        }
        return result;
    }

    static void removeRunnableRecord(@NonNull Runnable runnable) {
        if (runnable == null) {
            return;
        }
        getInstance().performRemoveRunnableRecord(runnable);
    }

    private boolean performRemoveRunnableRecord(@NonNull Runnable runnable) {
        if (runnable == null) {
            return false;
        }

        boolean result = false;

        mainLock.lock();
        try {
            WeakReference<Runnable> refFind = null;
            for (WeakReference<Runnable> reference: listRunnable) {
                if (reference != null && reference.get() == runnable) {
                    refFind = reference;
                    break;
                }
            }

            if (refFind != null) {
                result = listRunnable.remove(refFind);
                runnable.stopRun();
            }
        } finally {
            mainLock.unlock();
        }

        return result;
    }

    public static void stop(@NonNull Runnable runnable) {
        if (runnable == null) {
            return;
        }
        getInstance().threadsExecutor.remove(runnable);
        runnable.performStopRun();
    }

    public static Handler getUIHandler() {
        if (UIHandler == null) {
            mainLock.lock();
            try {
                if (UIHandler == null) {
                    UIHandler = new Handler(Looper.getMainLooper());
                }
            } finally {
                mainLock.unlock();
            }
        }
        return UIHandler;
    }

    public static HandlerThread getLocalEventHandlerThread() {
        if (hThreadLocalEvent == null) {
            synchronized (ThreadPool.class) {
                if (hThreadLocalEvent == null) {
                    hThreadLocalEvent = new HandlerThread(Runnable.THREAD_NAME_PREFIX + "protocol");
                    hThreadLocalEvent.start();
                }
            }
        }

        return hThreadLocalEvent;
    }

    public static Handler getHandlerLocalEvent() {
        if (handlerLocalEvent == null) {
            mainLock.lock();
            try {
                if (handlerLocalEvent == null) {
                    hThreadLocalEvent = getLocalEventHandlerThread();
                    handlerLocalEvent = new Handler();
                }
            } finally {
                mainLock.unlock();
            }
        }
        return handlerLocalEvent;
    }
}