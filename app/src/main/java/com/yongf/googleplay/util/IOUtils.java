/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: IOUtils.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       新增：Create
 *  1.1         Scott Wang     2016/4/16     新增：重载close方法，可同时关闭多个流
 */

package com.yongf.googleplay.util;

import java.io.Closeable;
import java.io.IOException;


/**
 * 流相关工具类
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class IOUtils {

    /**
     * 关闭流
     *
     * @param io
     * @return
     */
    public static boolean close(Closeable io) {
        if (null != io) {
            try {
                io.close();
            } catch (IOException e) {
                LogUtils.e(e);

                return false;
            }
        }
        return true;
    }

    /**
     * 同时关闭多个流
     *
     * @param ios 多个流
     * @return
     */
    public static boolean close(Closeable... ios) {
        for (Closeable io : ios) {
            if (!close(io)) {
                return false;
            }
        }

        return true;
    }
}
