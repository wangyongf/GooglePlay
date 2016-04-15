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
import com.yongf.googleplay.factory.ThreadPoolFactory;
import com.yongf.googleplay.manager.DownloadManager;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 应用详情页底部下载部分
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener {

    @ViewInject(R.id.app_detail_download_btn_share)
    Button mBtnShare;

    @ViewInject(R.id.app_detail_download_btn_favo)
    Button mBtnFavo;

    @ViewInject(R.id.app_detail_download_btn_download)
    Button mBtnDownload;

    private AppInfoBean mData;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);

        ViewUtils.inject(this, view);

        mBtnDownload.setOnClickListener(this);
        mBtnFavo.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        mData = data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_detail_download_btn_download:
                startDownload();

                break;
            case R.id.app_detail_download_btn_share:


                break;
            case R.id.app_detail_download_btn_favo:


                break;
        }
    }

    private void startDownload() {
        ThreadPoolFactory.getDownloadPool().execute(new DownLoadTask(mData));
    }

    /**
     * 开始下载应用
     */
    private class DownLoadTask implements Runnable {
        private AppInfoBean mData;

        public DownLoadTask(AppInfoBean data) {
            this.mData = data;
        }

        @Override
        public void run() {
            DownloadManager.getInstance().download(mData);
        }
    }
}
