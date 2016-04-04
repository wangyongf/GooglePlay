/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: ThreadPoolFactory						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       Create	
 */

package com.yongf.googleplay.factory;

import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.manager.ThreadPoolProxy;

/**
 * 线程池工厂类，线程安全的
 * 生产线程池
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/5
 * @see
 * @since GooglePlay1.0
 */
public class ThreadPoolFactory {

    private static final String TAG = "ThreadPoolFactory";

    private static volatile ThreadPoolProxy mNormalPool;
    private static volatile ThreadPoolProxy mDownloadPool;

    /**
     * 得到一个普通线程池
     *
     * @return 普通线程池
     */
    public static ThreadPoolProxy getNormalPool() {
        if (mNormalPool == null) {
            synchronized (ThreadPoolFactory.class) {
                if (mNormalPool == null) {
                    mNormalPool = new ThreadPoolProxy(
                            Constants.NORMAL_POOL_CORE_POOL_SIZE,
                            Constants.NORMAL_POOL_MAX_POOL_SIZE,
                            Constants.NORMAL_POOL_KEEP_ALIVE_TIME);
                }
            }
        }

        return mNormalPool;
    }

    /**
     * 得到下载的线程池
     *
     * @return 下载线程池
     */
    public static ThreadPoolProxy getDownloadPool() {
        if (mDownloadPool == null) {
            synchronized (ThreadPoolFactory.class) {
                if (mDownloadPool == null) {
                    mDownloadPool = new ThreadPoolProxy(
                            Constants.DOWNLOAD_POOL_CORE_POOL_SIZE,
                            Constants.DOWNLOAD_POOL_MAX_POOL_SIZE,
                            Constants.DOWNLOAD_POOL_KEEP_ALIVE_TIME);
                }
            }
        }

        return mDownloadPool;
    }
}
