/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseApplication						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/4        增加了Handler定义，相关方法
 */

package com.yongf.googleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * 定义一个全局的盒子，里面放置的对象，属性，方法都是可以全局调用的
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";
    private static Context mContext;
    private static Thread mMainThread;
    private static long mMainThreadID;
    private static Looper mMainLooper;
    private static Handler mHandler;

    public static String getTAG() {
        return TAG;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadID() {
        return mMainThreadID;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * 程序的入口
     */
    @Override
    public void onCreate() {
        //初始化一些，常用的属性，然后放到盒子中
        //上下文
        mContext = getApplicationContext();

        //主线程
        mMainThread = Thread.currentThread();

        //主线程ID
        mMainThreadID = android.os.Process.myTid();

        //主线程Looper对象
        mMainLooper = getMainLooper();

        //定义一个handler
        mHandler = new Handler();

        super.onCreate();
    }
}
