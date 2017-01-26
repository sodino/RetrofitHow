package com.sodino.retrofit.thread;

import android.os.Process;

/**
 * 线程优先级：
 *
 * UI > PROTOCOL > DATA_READ_WRITE > NET_TRANSFER
 * Created by Administrator on 2015/12/11.
 */
public class ThreadPriority {
    // ---------  NET priority start---------
    /**网络的上传、下载任务。*/
    public static final int NET_TRANSFER = Process.THREAD_PRIORITY_BACKGROUND;
    /**
     * 网络下载任务
     * @deprecated Use {@link #NET_TRANSFER}.
     * */
    @Deprecated
    public static final int NET_DOWNLOAD = NET_TRANSFER;
    /**
     * @deprecated Use {@link #NET_TRANSFER}.
     * */
    @Deprecated
    public static final int NET_UPLOAD = NET_TRANSFER;
    // ---------  NET priority end---------

    // ---------  DATA priority start---------
    // ---------  以下类型的优先级是一致的 ---------
    /**本地数据的编码与解码、读与写，如数据库、SharedPreferences、图片编解码。*/
    public static final int DATA_READ_WRITE = Process.THREAD_PRIORITY_DEFAULT + Process.THREAD_PRIORITY_LESS_FAVORABLE * 5;
    /**
     * 数据库的读写。
     * @deprecated Use {@link #DATA_READ_WRITE}.
     * */
    @Deprecated
    public static final int DATABASE_READ_WRITE = DATA_READ_WRITE;
    /**
     * @deprecated Use {@link #DATA_READ_WRITE}.
     * */
    @Deprecated
    public static final int SHARED_PREFERENCES_READ_WRITE = DATA_READ_WRITE;
    /**
     * @deprecated Use {@link #DATA_READ_WRITE}.
     * */
    @Deprecated
    public static final int IMAGE_ENCODE_DECODE = DATA_READ_WRITE;
    // ---------  DATA priority end---------

    /**
     * 网络协议请求。只比UI线程小一级。
     * */
    public static final int PROTOCOL = Process.THREAD_PRIORITY_DEFAULT + Process.THREAD_PRIORITY_LESS_FAVORABLE * 2;

    /**和系统默认的Ui线程优先级保持一致。Process.threadPriority = THREAD_PRIORITY_DEFAULT = 0.*/
    public static final int UI = Process.THREAD_PRIORITY_DEFAULT;
}
