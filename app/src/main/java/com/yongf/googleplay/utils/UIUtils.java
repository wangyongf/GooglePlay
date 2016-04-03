/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: UIUtils						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create	
 */

package com.yongf.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;

import com.yongf.googleplay.base.BaseApplication;

/**
 * 和UI相关的工具类
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class UIUtils {

    private static final String TAG = "UIUtils";

    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**
     * 得到Resource对象
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串
     *
     * @param resID
     * @return
     */
    public static String getString(int resID) {
        return getContext().getResources().getString(resID);
    }

    /**
     * 得到String.xml中的字符串数组
     *
     * @param resID
     * @return
     */
    public static String[] getStringArray(int resID) {
        return getResources().getStringArray(resID);
    }

    /**
     * 得到colors.xml中的颜色
     *
     * @param colorID
     * @return
     */
    public static int getColor(int colorID) {
        return getResources().getColor(colorID);
    }

    /**
     * 获取应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }
}
