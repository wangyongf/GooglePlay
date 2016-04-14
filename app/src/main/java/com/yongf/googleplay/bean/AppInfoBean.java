/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppInfoBean						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       Create
 *  1.1         Scott Wang     2016/4/15     更加全面的应用信息
 */

package com.yongf.googleplay.bean;

import java.util.List;

/**
 * 应用信息Bean
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/5
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

    /////// ------------------- app详情里面的一些字段 ------------------- ///////
    public String author;                           //应用作者
    public String date;                                 //应用更新时间
    public String downloadNum;              //下载量
    public String version;                          //版本号

    public List<AppInfoSafeBean> safe;              //安全部分
    public List<String> screen;                             //应用系列截图

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "des='" + des + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", size=" + size +
                ", stars=" + stars +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", version='" + version + '\'' +
                ", safe=" + safe +
                ", screen=" + screen +
                '}';
    }

    public class AppInfoSafeBean {
        public String safeDes;                      //安全描述
        public int safeDesColor;                    //安全描述部分的文字颜色
        public String safeDesUrl;                   //安全描述图标URL
        public String safeUrl;                          //安全图标URL
    }
}
