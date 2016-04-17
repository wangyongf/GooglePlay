/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       新增：Create
 */

package com.yongf.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.manager.DownloadInfo;
import com.yongf.googleplay.manager.DownloadManager;
import com.yongf.googleplay.utils.BitmapHelper;
import com.yongf.googleplay.utils.CommonUtils;
import com.yongf.googleplay.utils.StringUtils;
import com.yongf.googleplay.utils.UIUtils;
import com.yongf.googleplay.views.CircleProgressView;

import java.io.File;

/**
 * 应用列表展示Holder
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/5
 * @see
 * @since GooglePlay1.0
 */
public class AppItemHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener, DownloadManager.DownloadObserver {

    @ViewInject(R.id.item_appinfo_iv_icon)
    ImageView mIvIcon;

    @ViewInject(R.id.item_appinfo_rb_stars)
    RatingBar mRbStar;

    @ViewInject(R.id.item_appinfo_tv_des)
    TextView mTvDes;

    @ViewInject(R.id.item_appinfo_tv_size)
    TextView mTvSize;

    @ViewInject(R.id.item_appinfo_tv_title)
    TextView mTvTitle;

    @ViewInject(R.id.item_appinfo_cpv)
    CircleProgressView mCircleProgressView;

    private AppInfoBean mData;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);

        //注入
        ViewUtils.inject(this, view);

        mCircleProgressView.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        //清除复用convertView之后的progress效果
        mCircleProgressView.setProgress(0);

        mData = data;

        mTvDes.setText(data.des);
        mTvSize.setText(StringUtils.formatFileSize(data.size));
        mTvTitle.setText(data.name);

        mIvIcon.setImageResource(R.drawable.ic_default);

//        BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
        String uri = Constants.URLs.IMAGE_BASE_URL + data.iconUrl;
        BitmapHelper.display(mIvIcon, uri);

        mRbStar.setRating(data.stars);

        /////// ------------------- 根据不同的状态给用户不同的提示 ------------------- ///////

        DownloadInfo info = DownloadManager.getInstance().getDownloadInfo(data);
        refreshCircleProgressViewUI(info);
    }

    /**
     * 刷新ProgressButton的UI
     *
     * @param info
     */
    private void refreshCircleProgressViewUI(DownloadInfo info) {
        switch (info.state) {
            case DownloadManager.STATE_UNDOWNLOAD:          //未下载
                mCircleProgressView.setText("下载");
                mCircleProgressView.setIcon(R.drawable.ic_download);

                break;
            case DownloadManager.STATE_DOWNLOADING:         //下载中
//                mCircleProgressView.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                mCircleProgressView.setProgressEnable(true);
                mCircleProgressView.setMax(info.max);
                mCircleProgressView.setProgress(info.currentProgress);
                int progress = (int) (info.currentProgress * 100.f / info.max + .5f);
                mCircleProgressView.setText(progress + "%");
                mCircleProgressView.setIcon(R.drawable.ic_pause);

                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD:           //暂停下载
                mCircleProgressView.setText("继续下载");
                mCircleProgressView.setIcon(R.drawable.ic_resume);

                break;
            case DownloadManager.STATE_WAITINGDOWNLOAD:         //等待下载
                mCircleProgressView.setText("等待中...");
                mCircleProgressView.setIcon(R.drawable.ic_pause);

                break;
            case DownloadManager.STATE_DOWNLOADFAILED:              //下载失败
                mCircleProgressView.setText("重试");
                mCircleProgressView.setIcon(R.drawable.ic_redownload);

                break;
            case DownloadManager.STATE_DOWNLOADED:                  //已下载
                mCircleProgressView.setProgressEnable(false);
                mCircleProgressView.setText("安装");
                mCircleProgressView.setIcon(R.drawable.ic_install);

                break;
            case DownloadManager.STATE_INSTALLED:               //已安装
                mCircleProgressView.setText("打开");
                mCircleProgressView.setIcon(R.drawable.ic_install);

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_appinfo_cpv:
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
                        startDownload(info);

                        break;
                    case DownloadManager.STATE_DOWNLOADING:         //下载中
                        pauseDownload(info);

                        break;
                    case DownloadManager.STATE_PAUSEDOWNLOAD:           //暂停下载
                        startDownload(info);

                        break;
                    case DownloadManager.STATE_WAITINGDOWNLOAD:         //等待下载
                        cancelDownload(info);

                        break;
                    case DownloadManager.STATE_DOWNLOADFAILED:              //下载失败
                        startDownload(info);

                        break;
                    case DownloadManager.STATE_DOWNLOADED:                  //已下载
                        installAPK(info);

                        break;
                    case DownloadManager.STATE_INSTALLED:               //已安装
                        openAPK(info);

                        break;
                }

                break;
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
                refreshCircleProgressViewUI(info);
            }
        });
    }
}
