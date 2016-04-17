/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppDetailInfoHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       新增：Create
 *  1.1         Scott Wang     2016/4/16       新增：详情页底部界面完成，加入下载逻辑
 */

package com.yongf.googleplay.holder;

import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.manager.DownloadInfo;
import com.yongf.googleplay.manager.DownloadManager;
import com.yongf.googleplay.utils.CommonUtils;
import com.yongf.googleplay.utils.UIUtils;
import com.yongf.googleplay.views.ProgressButton;

import java.io.File;

/**
 * 应用详情页底部下载部分
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener, DownloadManager.DownloadObserver {

    @ViewInject(R.id.app_detail_download_btn_share)
    Button mBtnShare;

    @ViewInject(R.id.app_detail_download_btn_favo)
    Button mBtnFavo;

    @ViewInject(R.id.app_detail_download_btn_download)
    ProgressButton mProgressButton;

    private AppInfoBean mData;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);

        ViewUtils.inject(this, view);

        mProgressButton.setOnClickListener(this);
        mBtnFavo.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        mData = data;

        /////// ------------------- 根据不同的状态给用户不同的提示 ------------------- ///////

        DownloadInfo info = DownloadManager.getInstance().getDownloadInfo(data);
        refreshProgressBtnUI(info);
    }

    /**
     * 刷新ProgressButton的UI
     *
     * @param info
     */
    private void refreshProgressBtnUI(DownloadInfo info) {
        mProgressButton.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);

        switch (info.state) {
            case DownloadManager.STATE_UNDOWNLOAD:          //未下载
                mProgressButton.setText("下载");

                break;
            case DownloadManager.STATE_DOWNLOADING:         //下载中
                mProgressButton.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                mProgressButton.setProgressEnable(true);
                mProgressButton.setMax(info.max);
                mProgressButton.setProgress(info.currentProgress);
                int progress = (int) (info.currentProgress * 100.f / info.max + .5f);
                mProgressButton.setText(progress + "%");

                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD:           //暂停下载
                mProgressButton.setText("继续下载");

                break;
            case DownloadManager.STATE_WAITINGDOWNLOAD:         //等待下载
                mProgressButton.setText("等待中...");

                break;
            case DownloadManager.STATE_DOWNLOADFAILED:              //下载失败
                mProgressButton.setText("重试");

                break;
            case DownloadManager.STATE_DOWNLOADED:                  //已下载
                mProgressButton.setProgressEnable(false);
                mProgressButton.setText("安装");

                break;
            case DownloadManager.STATE_INSTALLED:               //已安装
                mProgressButton.setText("打开");

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_detail_download_btn_download:
                DownloadInfo info = DownloadManager.getInstance().getDownloadInfo(mData);
                switch (info.state) {
                    /**
                     状态(记录)  	|  给用户的提示(ui展现)
                     ---------------- |----------------------
                     未下载			    |下载
                     下载中			    |显示进度条
                     暂停下载			|继续下载
                     等待下载			|等待中...
                     下载失败			|重试
                     下载完成 		|安装
                     已安装 			|打开
                     */
                    case DownloadManager.STATE_UNDOWNLOAD:          //未下载
//                        mProgressButton.setText("下载");
                        startDownload(info);

                        break;
                    case DownloadManager.STATE_DOWNLOADING:         //下载中
//                        mProgressButton.setText("显示进度条");
                        pauseDownload(info);

                        break;
                    case DownloadManager.STATE_PAUSEDOWNLOAD:           //暂停下载
//                        mProgressButton.setText("继续下载");
                        startDownload(info);

                        break;
                    case DownloadManager.STATE_WAITINGDOWNLOAD:         //等待下载
//                        mProgressButton.setText("等待中...");
                        cancelDownload(info);

                        break;
                    case DownloadManager.STATE_DOWNLOADFAILED:              //下载失败
//                        mProgressButton.setText("重试");
                        startDownload(info);

                        break;
                    case DownloadManager.STATE_DOWNLOADED:                  //已下载
//                        mProgressButton.setText("安装");
                        installAPK(info);

                        break;
                    case DownloadManager.STATE_INSTALLED:               //已安装
//                        mProgressButton.setText("打开");
                        openAPK(info);

                        break;
                }

                break;
//            case R.id.app_detail_download_btn_share:
//
//
//                break;
//            case R.id.app_detail_download_btn_favo:
//
//
//                break;
        }
    }

    /**
     * 打开应用
     *
     * @param info
     */
    private void openAPK(DownloadInfo info) {
        CommonUtils.openApp(UIUtils.getContext(), info.packageName);
    }

    /**
     * 安装应用
     *
     * @param info
     */
    private void installAPK(DownloadInfo info) {
        File apkFile = new File(info.savePath);
        CommonUtils.installApp(UIUtils.getContext(), apkFile);
    }

    /**
     * 取消下载
     *
     * @param info
     */
    private void cancelDownload(DownloadInfo info) {
        DownloadManager.getInstance().cancel(info);
    }

    /**
     * 暂停下载
     *
     * @param info
     */
    private void pauseDownload(DownloadInfo info) {
        DownloadManager.getInstance().pause(info);
    }

    /**
     * 开始下载
     *
     * @param info
     */
    private void startDownload(DownloadInfo info) {
        /////// ------------------- 根据不同的状态执行不同的操作 ------------------- ///////

        DownloadManager.getInstance().download(info);
    }

    /////// ------------------- 收到数据改变，更新UI ------------------- ///////

    @Override
    public void onDownloadInfoChange(final DownloadInfo info) {
        //过滤DownloadInfo
        if (!info.packageName.equals(mData.packageName)) {
            return;
        }

        UIUtils.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                refreshProgressBtnUI(info);
            }
        });
    }

    public void addObserverAndRefresh() {
        DownloadManager.getInstance().addObserver(this);
        //手动刷新
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mData);
        DownloadManager.getInstance().notifyObservers(downloadInfo);        //方式1
//        refreshProgressBtnUI(downloadInfo);               //方式2
    }
}
