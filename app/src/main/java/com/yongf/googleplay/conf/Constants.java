/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: Constants						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       新增：Create
 *  1.1         Scott Wang     2016/4/4       新增：增加了模拟耗时操作时长的变量
 *  1.2         Scott Wang     2016/4/5       新增：加入线程池相关常量（核心线程数、最大线程数、保持时间）
 *  1.3         Scott Wang     2016/4/10     新增：加入服务器相关地址
 *  1.4         Scott Wang     2016/4/16     新增：加入下载地址
 */

package com.yongf.googleplay.conf;

import com.yongf.googleplay.utils.LogUtils;

/**
 * 常量
 *
 * @author Scott Wang
 * @version 1.4, 2016/4/3
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
    /**
     * 分页的条数
     */
    public static final int PAGER_SIZE = 20;
    /**
     * 加载更多休眠时间，避免过快
     */
    public static final int LOADING_MORE_DURATION = 500;

    /**
     * JSON缓存过期时间-5分钟
     */
    public static final int EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 首页轮播图展示时长
     */
    public static final int HOME_CAROUSEL_DURATION = 4000;

    public static final class URLs {
        /**
         * Genymotion IP
         */
        public static final String IP1 = "http://10.0.3.2:8080/";

        /**
         * 小米3 IP
         */
        public static final String IP2 = "http://192.168.1.102:8080/";

        /**
         * 服务器地址(Genymotion)
         */
        public static final String BASE_URL = IP2 + "GooglePlayServer/";

        /**
         * 图片地址
         */
        public static final String IMAGE_BASE_URL = BASE_URL + "image?name=";

        /**
         * 下载应用地址
         */
        public static final String DOWNLOAD_BASE_URL = BASE_URL + "download";

    }
}
