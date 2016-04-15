/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: DownloadManager						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/16       Create	
 */

package com.yongf.googleplay.manager;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.utils.FileUtils;
import com.yongf.googleplay.utils.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 下载管理器
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/16
 * @see
 * @since GooglePlay1.0
 */
public class DownloadManager {

    public static volatile DownloadManager instance;

    private DownloadManager() {

    }

    public static DownloadManager getInstance() {
        if (null == instance) {
            synchronized (DownloadManager.class) {
                if (null == instance) {
                    instance = new DownloadManager();
                }
            }
        }

        return instance;
    }

    /**
     * 下载
     *
     * @param data 下载的应用的数据
     */
    public void download(AppInfoBean data) {
        //发起网络请求

        try {
            //下载路径
            String savePath = FileUtils.getDownloadDir();

            String url = Constants.URLs.DOWNLOAD_BASE_URL;
            HttpUtils httpUtils = new HttpUtils();

            //相关参数
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("name", data.downloadUrl);
            params.addQueryStringParameter("range", 0 + "");

            ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);

            if (200 == responseStream.getStatusCode()) {
                InputStream in = null;
                FileOutputStream out = null;
                try {
                    in = responseStream.getBaseStream();
                    File saveFile = new File(savePath, data.packageName + ".apk");
                    out = new FileOutputStream(saveFile);

                    byte[] buffer = new byte[1024];
                    int len = -1;

                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } finally {
                    IOUtils.close(out, in);
                }
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
