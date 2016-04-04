/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: Constants						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/4       增加了模拟耗时操作时长的变量
 *  1.2         Scott Wang     2016/4/5       加入线程池相关常量（核心线程数、最大线程数、保持时间）
 */

package com.yongf.googleplay.conf;

import com.yongf.googleplay.utils.LogUtils;

/**
 * 常量
 *
 * @author Scott Wang
 * @version 1.2, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class Constants {

    /**
     * 关闭日志的显示
     */
    public static final int DEBUG_LEVEL = LogUtils.LEVEL_ALL;

    /**
     * 项目开发过程中模拟耗时操作的时长
     */
    public static final int SLEEP_DURATION = 1000;

    /**
     * 普通线程池核心线程数量
     */
    public static final int NORMAL_POOL_CORE_POOL_SIZE = 5;

    /**
     * 普通线程池最大线程数量
     */
    public static final int NORMAL_POOL_MAX_POOL_SIZE = 5;

    /**
     * 普通线程池线程保持时间
     */
    public static final int NORMAL_POOL_KEEP_ALIVE_TIME = 3000;

    /**
     * 下载线程池核心线程数量
     */
    public static final int DOWNLOAD_POOL_CORE_POOL_SIZE = 3;

    /**
     * 下载线程池最大线程数量
     */
    public static final int DOWNLOAD_POOL_MAX_POOL_SIZE = 3;

    /**
     * 下载线程池线程保持时间
     */
    public static final int DOWNLOAD_POOL_KEEP_ALIVE_TIME = 3000;
}