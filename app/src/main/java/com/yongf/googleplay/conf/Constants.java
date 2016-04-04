/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: Constants						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/4       增加了模拟耗时操作时长的变量
 */

package com.yongf.googleplay.conf;

import com.yongf.googleplay.utils.LogUtils;

/**
 * 常量
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/3
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
}
