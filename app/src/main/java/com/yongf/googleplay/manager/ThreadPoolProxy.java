/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: ThreadPoolProxy						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/4       Create
 *  1.1         Scott Wang     2016/4/5       初始化线程池，双重检查锁机制
 */

package com.yongf.googleplay.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程，执行任务，提交任务
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/4
 * @see
 * @since GooglePlay1.0
 */
public class ThreadPoolProxy {

    private static final String TAG = "ThreadPoolProxy";

    private int mCorePoolSize;
    private int mMaximumPoolSize;
    private long mKeepAliveTime;
    private volatile ThreadPoolExecutor mExecutor;       //只需创建一次

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        mKeepAliveTime = keepAliveTime;

        //初始化线程池
        initThreadPoolExecutor();
    }

    /**
     * 初始化线程池，双重检查加锁double check lock
     */
    private ThreadPoolExecutor initThreadPoolExecutor() {
        if (mExecutor == null) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null) {
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    //丢弃任务并抛出RejectedExecutionException
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

                    mExecutor = new ThreadPoolExecutor(
                            mCorePoolSize,               //核心线程数
                            mMaximumPoolSize,            //最大线程数
                            mKeepAliveTime,              //保持时间
                            unit,                       //保持时间对应的单位
                            workQueue,              //缓存队列、阻塞队列
                            threadFactory,          //线程工厂
                            handler                     //异常捕获器
                    );
                }
            }
        }

        return mExecutor;
    }

    /**
     * 执行任务
     *
     * @param task
     */
    public void execute(Runnable task) {
        mExecutor.execute(task);
    }

    /**
     * 提交任务
     *
     * @param task
     * @return
     */
    public Future<?> submit(Runnable task) {
        return mExecutor.submit(task);
    }

    /**
     * 移除任务
     *
     * @param task
     */
    public void remove(Runnable task) {
        mExecutor.remove(task);
    }
}
