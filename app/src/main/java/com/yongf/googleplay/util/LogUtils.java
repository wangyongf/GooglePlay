/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: LogUtils.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 */

package com.yongf.googleplay.util;

import android.text.TextUtils;
import android.util.Log;

import com.yongf.googleplay.conf.Convention;

import java.util.List;

/**
 * 日志级别是LEVEL_ALL显示所有信息, 包括System.out.println信息
 * 日志级别是LEVEL_OFF关闭所有信息, 包括System.out.println信息
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class LogUtils {
    /**
     * 日志输出级别NONE
     */
    public static final int LEVEL_OFF = 0;
    /**
     * 日志输出级别NONE
     */
    public static final int LEVEL_ALL = 7;
    /**
     * 日志输出级别V
     */
    public static final int LEVEL_VERBOSE = 1;
    /**
     * 日志输出级别D
     */
    public static final int LEVEL_DEBUG = 2;
    /**
     * 日志输出级别I
     */
    public static final int LEVEL_INFO = 3;
    /**
     * 日志输出级别W
     */
    public static final int LEVEL_WARN = 4;
    /**
     * 日志输出级别E
     */
    public static final int LEVEL_ERROR = 5;
    /**
     * 日志输出级别S,自定义定义的一个级别
     */
    public static final int LEVEL_SYSTEM = 6;
    /**
     * 写文件的锁对象
     */
    private static final Object mLogLock = new Object();
    /**
     * 日志输出时的TAG
     */
    private static String mTag = "Google Play";

    /**
     * 是否允许输出log
     */
    private static int mDebuggable = Convention.DEBUG_LEVEL;

    /**
     * 用于记时的变量
     */
    private static long mTimestamp = 0;

    /**---------------日志输出,已固定TAG  begin---------------**/

    /**
     * 以级别为 d 的形式输出LOG
     *
     * @param msg
     */
    public static void v(String msg) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(mTag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     *
     * @param msg
     */
    public static void d(String msg) {
        if (mDebuggable >= LEVEL_DEBUG) {
            Log.d(mTag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     *
     * @param msg
     */
    public static void i(String msg) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(mTag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     *
     * @param msg
     */
    public static void w(String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(mTag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     *
     * @param tr
     */
    public static void w(Throwable tr) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(mTag, "", tr);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     *
     * @param msg
     * @param tr
     */
    public static void w(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_WARN && null != msg) {
            Log.w(mTag, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     *
     * @param msg
     */
    public static void e(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(mTag, msg);
        }
    }

    /**
     * 以级别为 s 的形式输出LOG,主要是为了System.out.println,稍微格式化了一下
     *
     * @param msg
     */
    public static void sf(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            System.out.println("----------" + msg + "----------");
        }
    }

    /**
     * 以级别为 s 的形式输出LOG,主要是为了System.out.println
     *
     * @param msg
     */
    public static void s(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            System.out.println(msg);
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     *
     * @param tr
     */
    public static void e(Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(mTag, "", tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     *
     * @param msg
     * @param tr
     */
    public static void e(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR && null != msg) {
            Log.e(mTag, msg, tr);
        }
    }

    /**---------------日志输出,已固定TAG  end---------------**/

    /**---------------日志输出,未固定TAG  begin---------------**/

    /**
     * 以级别为 d 的形式输出LOG
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(tag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (mDebuggable >= LEVEL_DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(tag, msg);
        }
    }

    /**---------------日志输出,未固定TAG  end---------------**/

    /**
     * 把Log存储到文件中
     *
     * @param log  需要存储的日志
     * @param path 存储路径
     */
    public static void log2File(String log, String path) {
        log2File(log, path, true);
    }

    public static void log2File(String log, String path, boolean append) {
        synchronized (mLogLock) {
            FileUtils.writeFile(log + "\r\n", path, append);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
     *
     * @param msg 需要输出的msg
     */
    public static void msgStartTime(String msg) {
        mTimestamp = System.currentTimeMillis();
        if (!TextUtils.isEmpty(msg)) {
            e("[Started：" + mTimestamp + "]" + msg);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg
     *
     * @param msg
     */
    public static void elapsed(String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mTimestamp;
        mTimestamp = currentTime;
        e("[Elapsed：" + elapsedTime + "]" + msg);
    }

    public static <T> void printList(List<T> list) {
        if (list == null || list.size() < 1) {
            return;
        }
        int size = list.size();
        i("---begin---");
        for (int i = 0; i < size; i++) {
            i(i + ":" + list.get(i).toString());
        }
        i("---end---");
    }

    public static <T> void printArray(T[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        int length = array.length;
        i("---begin---");
        for (int i = 0; i < length; i++) {
            i(i + ":" + array[i].toString());
        }
        i("---end---");
    }
}
