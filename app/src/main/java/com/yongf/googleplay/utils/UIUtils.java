/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: UIUtils						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/4       增加3个方法：获取主线程ID, 获取主线程Handler, 安全执行Runnable任务
 *  1.2         Scott Wang     2016/4/15     得到strings.xml中带占位符的string
 */

package com.yongf.googleplay.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;

import com.yongf.googleplay.base.BaseApplication;

/**
 * 和UI相关的工具类
 *
 * @author Scott Wang
 * @version 1.2, 2016/4/3
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
     * 得到Assets资源
     *
     * @return
     */
    public static AssetManager getAssets() {
        return getContext().getAssets();
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
     * 得到String.xml中的字符串，带占位符
     *
     * @param resID
     * @param formatArgs 占位符
     * @return
     */
    public static String getString(int resID, Object... formatArgs) {
        return getContext().getResources().getString(resID, formatArgs);
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

    /**
     * 获取主线程的ID
     *
     * @return 主线程的ID
     */
    public static long getMainThreadID() {
        return BaseApplication.getMainThreadID();
    }

    /**
     * 获取主线程的Handler
     *
     * @return 主线程的Handler
     */
    public static Handler getMainThreadHandler() {
        return BaseApplication.getHandler();
    }

    /**
     * 安全执行任务
     *
     * @param task
     */
    public static void postTaskSafely(Runnable task) {
        int curThreadID = android.os.Process.myTid();

        if (curThreadID == getMainThreadID()) {
            //如果当前线程是主线程
            task.run();
        } else {
            //如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }

    }

    /**
     * 延迟执行任务
     *
     * @param task
     * @param delayMillis
     */
    public static void postTaskDelay(Runnable task, int delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    /**
     * 移除任务
     *
     * @param task
     */
    public static void removeTask(Runnable task) {
        getMainThreadHandler().removeCallbacks(task);
    }

    /**
     * dip转px
     *
     * @param context 上下文
     * @param dpValue dip值
     * @return 转换后的px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dip2px(float dpValue) {
        return dip2px(getContext(), dpValue);
    }

    /**
     * px转dip
     *
     * @param context 上下文
     * @param pxValue px值
     * @return 转换后的dip值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return px2dip(getContext(), pxValue);
    }
}
