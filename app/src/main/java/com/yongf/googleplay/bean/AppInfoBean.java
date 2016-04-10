/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppInfoBean						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       Create	
 */

package com.yongf.googleplay.bean;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/5
 * @see
 * @since GooglePlay1.0
 */
public class AppInfoBean {

    public String des;                      //应用描述
    public String downloadUrl;         //下载地址
    public String iconUrl;                    //图标的地址
    public long id;                             //应用的ID
    public String name;                    //   应用的名称
    public String packageName;             //         应用的包名
    public long size;                     //应用的大小
    public float stars;                          //应用的评分
}
