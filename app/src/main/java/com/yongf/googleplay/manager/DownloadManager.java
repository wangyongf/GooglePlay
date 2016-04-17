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

import android.support.annotation.NonNull;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.factory.ThreadPoolFactory;
import com.yongf.googleplay.utils.CommonUtils;
import com.yongf.googleplay.utils.FileUtils;
import com.yongf.googleplay.utils.IOUtils;
import com.yongf.googleplay.utils.LogUtils;
import com.yongf.googleplay.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 下载管理器，需要时刻记录当前下载状态，暴露当前状态
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/16
 * @see
 * @since GooglePlay1.0
 */
public class DownloadManager {

    public static final int STATE_UNDOWNLOAD = 0;                   //未下载
    public static final int STATE_DOWNLOADING = 1;                  //下载中
    public static final int STATE_PAUSEDOWNLOAD = 2;                //暂停下载
    public static final int STATE_WAITINGDOWNLOAD = 3;          //等待下载
    public static final int STATE_DOWNLOADFAILED = 4;               //下载失败
    public static final int STATE_DOWNLOADED = 5;                       //下载完成
    public static final int STATE_INSTALLED = 6;                            //已安装


    public static volatile DownloadManager instance;
    //记录正在下载的downloadInfo
    public Map<String, DownloadInfo> mDownloadInfoMap = new HashMap<>();
    List<DownloadObserver> mDownloadObservers = new LinkedList<>();

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
     * 下载，用户点击了下载按钮
     *
     * @param info 下载的应用的数据（下载地址、状态、进度等）
     */
    public void download(DownloadInfo info) {
        mDownloadInfoMap.put(info.packageName, info);

        info.state = STATE_UNDOWNLOAD;
        notifyObservers(info);

        info.state = STATE_WAITINGDOWNLOAD;
        notifyObservers(info);

        //从线程池中获取线程，执行任务
        DownLoadTask task = new DownLoadTask(info);
        info.task = task;               //DownloadInfo的task（下载任务）
        ThreadPoolFactory.getDownloadPool().execute(task);
    }

    /**
     * 暴露下载状态，也就是需要提供DownloadInfo
     *
     * @param data
     * @call 外界需要知道最新的下载state
     */
    public DownloadInfo getDownloadInfo(AppInfoBean data) {

        /////// ------------------- 已经安装 ------------------- ///////

        if (CommonUtils.isInstalled(UIUtils.getContext(), data.packageName)) {
            return generateDownloadInfo(data, STATE_INSTALLED);
        }

        /////// ------------------- 下载完成 ------------------- ///////

        DownloadInfo info = generateDownloadInfo(data, STATE_UNDOWNLOAD);
        File saveApk = new File(info.savePath);         //这一步是为了获取savePath
        if (saveApk.exists() &&
                saveApk.length() == data.size) {     //如果存在下载目录里面，并且已下载完毕
            return generateDownloadInfo(data, STATE_DOWNLOADED);
        }

        /**
         * 下载中
         * 暂停下载
         * 等待下载
         * 下载失败
         */
        DownloadInfo downloadInfo = mDownloadInfoMap.get(data.packageName);
        if (downloadInfo != null) {
            return downloadInfo;
        }

        /////// ------------------- 未下载 ------------------- ///////

        return generateDownloadInfo(data, STATE_UNDOWNLOAD);
    }

    /**
     * 根据AppInfoBean生成一个DownloadInfo，进行一些常规的赋值
     *
     * @param data
     * @param state
     * @return
     */
    @NonNull
    private DownloadInfo generateDownloadInfo(AppInfoBean data, int state) {
        //下载路径
        String dir = FileUtils.getDownloadDir();
        File file = new File(dir, data.packageName + ".apk");
        //保存路径
        String savePath = file.getAbsolutePath();

        DownloadInfo info = new DownloadInfo();

        info.downloadUrl = data.downloadUrl;
        info.savePath = savePath;
        info.state = state;           //已安装
        info.packageName = data.packageName;
        info.max = data.size;
        info.currentProgress = 0;

        return info;
    }

    /**
     * 暂停下载
     *
     * @param info
     */
    public void pause(DownloadInfo info) {
        info.state = STATE_PAUSEDOWNLOAD;
        notifyObservers(info);
    }

    /**
     * 取消下载
     *
     * @param info
     */
    public void cancel(DownloadInfo info) {
        //改变状态
        info.state = STATE_UNDOWNLOAD;
        notifyObservers(info);

        Runnable task = info.task;
        //找到线程池，移除任务
        ThreadPoolFactory.getDownloadPool().remove(task);
    }

    /////// ------------------- 自定义观察者设计模式 start ------------------- ///////

    /**
     * 添加观察者
     */
    public void addObserver(DownloadObserver observer) {
        if (null == observer) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!mDownloadObservers.contains(observer)) {
                mDownloadObservers.add(observer);
            }
        }
    }

    /**
     * 删除观察者
     */
    public synchronized void deleteObserver(DownloadObserver observer) {
        mDownloadObservers.remove(observer);
    }

    /**
     * 通知观察者数据改变
     */
    public void notifyObservers(DownloadInfo info) {
        for (DownloadObserver observer : mDownloadObservers) {
            observer.onDownloadInfoChange(info);
        }
    }

    public interface DownloadObserver {
        void onDownloadInfoChange(DownloadInfo info);
    }

    /////// ------------------- 自定义观察者设计模式 end ------------------- ///////

    /**
     * 开始下载应用
     */
    private class DownLoadTask implements Runnable {
        private DownloadInfo mInfo;

        public DownLoadTask(DownloadInfo info) {
            super();

            mInfo = info;
        }

        @Override
        public void run() {
            //发起网络请求

            try {
                mInfo.state = STATE_DOWNLOADING;
                notifyObservers(mInfo);

                long initRange = 0;
                File saveAPK = new File(mInfo.savePath);
                if (saveAPK.exists()) {
                    initRange = saveAPK.length();       //未下载完的APK已有的长度
                }
                mInfo.currentProgress = initRange;          //处理初始进度

                //下载地址
                String url = Constants.URLs.DOWNLOAD_BASE_URL;
                HttpUtils httpUtils = new HttpUtils();

                //相关参数
                RequestParams params = new RequestParams();
                params.addQueryStringParameter("name", mInfo.downloadUrl);
                params.addQueryStringParameter("range", initRange + "");        //已有长度

                ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);

                if (mInfo.packageName.equals("com.itheima.www")) {
                    LogUtils.sf(mInfo.max + "");
                }

                if (200 == responseStream.getStatusCode()) {
                    InputStream in = null;
                    FileOutputStream out = null;
                    boolean isPause = false;
                    try {
                        in = responseStream.getBaseStream();
                        File saveFile = new File(mInfo.savePath);
                        out = new FileOutputStream(saveFile, true);             //处理文件写入

                        byte[] buffer = new byte[1024];
                        int len = -1;

                        while ((len = in.read(buffer)) != -1) {
                            if (mInfo.state == STATE_PAUSEDOWNLOAD) {
                                isPause = true;
                                break;
                            }

                            out.write(buffer, 0, len);
                            mInfo.currentProgress += len;

                            mInfo.state = STATE_DOWNLOADING;
                            notifyObservers(mInfo);
                        }

//                        if (mInfo.currentProgress == mInfo.max) {       //下载完成了
//                            mInfo.state = STATE_DOWNLOADED;
//                            notifyObservers(mInfo);
//                        } else {                //暂停了
//                            mInfo.state = STATE_PAUSEDOWNLOAD;
//                            notifyObservers(mInfo);
//                        }
                        if (mInfo.packageName.equals("com.itheima.www")) {
                            LogUtils.sf(mInfo.currentProgress + " " + isPause);
                        }

                        if (isPause) {// 用户暂停了下载走到这里来了
                            /*############### 当前状态: 暂停 ###############*/
                            mInfo.state = STATE_PAUSEDOWNLOAD;
                            notifyObservers(mInfo);
                            /*#######################################*/
                        } else {// 下载完成走到这里来
							/*############### 当前状态: 下载完成 ###############*/
                            mInfo.state = STATE_DOWNLOADED;
                            notifyObservers(mInfo);
							/*#######################################*/
                        }
                    } finally {
                        IOUtils.close(out, in);
                    }
                } else {
                    mInfo.state = STATE_DOWNLOADFAILED;
                    notifyObservers(mInfo);
                }
            } catch (HttpException e) {
                e.printStackTrace();

                mInfo.state = STATE_DOWNLOADFAILED;
                notifyObservers(mInfo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

                mInfo.state = STATE_DOWNLOADFAILED;
                notifyObservers(mInfo);
            } catch (IOException e) {
                e.printStackTrace();

                mInfo.state = STATE_DOWNLOADFAILED;
                notifyObservers(mInfo);
            }
        }
    }
}
