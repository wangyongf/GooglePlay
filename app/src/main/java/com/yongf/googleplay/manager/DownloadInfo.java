/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: DownloadInfo						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/16       新增：Create	
 */

package com.yongf.googleplay.manager;

/**
 * 与下载管理相关的信息
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/16
 * @see
 * @since GooglePlay1.0
 */
public class DownloadInfo {

    public String savePath;
    public String downloadUrl;
    public int state = DownloadManager.STATE_UNDOWNLOAD;            //默认状态就是未下载
    public String packageName;              //应用的包名
    public long max;
    public long currentProgress;
    public Runnable task;
}
