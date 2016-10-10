/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: DetailActivity.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       新增：Create
 *  1.1         Scott Wang     2016/4/15       新增：应用详情页跳转，数据加载，数据获取协议封装，信息部分
 *  1.2         Scott Wang     2016/4/15       新增：详情页安全部分数据展示、折叠属性动画
 *  1.3         Scott Wang     2016/4/16       优化：部分封装，详情页的返回应用列表，标题栏显示应用名称
 *  1.4         Scott Wang     16-10-7           优化：重构代码，将初始化view的代码放到initView方法中
 *  1.5         Scott Wang     16-10-7           优化：不再在代码中设置ActionBar，重构页面布局
 *  1.6         Scott Wang     16-10-10         优化：移除mToolbar
 */

package com.yongf.googleplay.activity;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseActivity;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.conf.Convention;
import com.yongf.googleplay.holder.AppDetailBottomHolder;
import com.yongf.googleplay.holder.AppDetailDesHolder;
import com.yongf.googleplay.holder.AppDetailInfoHolder;
import com.yongf.googleplay.holder.AppDetailPicHolder;
import com.yongf.googleplay.holder.AppDetailSecureHolder;
import com.yongf.googleplay.manager.DownloadManager;
import com.yongf.googleplay.protocol.DetailProtocol;
import com.yongf.googleplay.utils.LogUtils;
import com.yongf.googleplay.utils.UIUtils;

import java.io.IOException;

/**
 * 应用详情页
 *
 * @author Scott Wang
 * @version 1.6, 16-10-7
 * @see
 * @since GooglePlay1.0
 */
public class DetailActivity extends BaseActivity {


    private static final String TAG = "DetailActivity";

    @ViewInject(R.id.app_detail_bottom)
    FrameLayout mContainerBottom;

    @ViewInject(R.id.app_detail_des)
    FrameLayout mContainerDes;

    @ViewInject(R.id.app_detail_info)
    FrameLayout mContainerInfo;

    @ViewInject(R.id.app_detail_pic)
    FrameLayout mContainerPic;

    @ViewInject(R.id.app_detail_secure)
    FrameLayout mContainerSecure;

    private String mPackageName;
    private LoadingPager mLoadingPager;
    private AppInfoBean mData;
    private String mName;
    private AppDetailBottomHolder mAppDetailBottomHolder;

    /**
     * 页面初始化
     */
    @Override
    public void init() {
        mPackageName = getIntent().getStringExtra(Convention.APP_PACKAGE_NAME);
        mName = getIntent().getStringExtra(Convention.APP_NAME);
    }

    @Override
    public void initView() {
        mLoadingPager = new LoadingPager(UIUtils.getContext()) {
            @Override
            public View initSuccessView() {
                return onInitView();
            }

            @Override
            public LoadedResult initData() {
                return onInitData();
            }
        };
        setContentView(mLoadingPager);

        initActionBar();
    }

    @Override
    public void initData() {
        //触发加载数据
        mLoadingPager.loadData();
    }

    /**
     * 界面可见的时候重新添加观察者
     */
    @Override
    protected void onResume() {
        if (mAppDetailBottomHolder != null) {
            //开启监听的时候，手动获取最新状态
            mAppDetailBottomHolder.addObserverAndRefresh();
        }

        super.onResume();
    }

    /**
     * 初始化视图
     */
    private View onInitView() {
        View view = View.inflate(DetailActivity.this, R.layout.activity_detail, null);

        ViewUtils.inject(this, view);

        /////// ------------------- 填充内容 ------------------- ///////

        //1. 信息部分
        AppDetailInfoHolder appDetailInfoHolder = new AppDetailInfoHolder();
        mContainerInfo.addView(appDetailInfoHolder.getHolderView());
        appDetailInfoHolder.setDataAndRefreshHolderView(mData);

        //2. 安全部分
        AppDetailSecureHolder appDetailSecureHolder = new AppDetailSecureHolder();
        mContainerSecure.addView(appDetailSecureHolder.getHolderView());
        appDetailSecureHolder.setDataAndRefreshHolderView(mData);

        //3. 截图部分
        AppDetailPicHolder appDetailPicHolder = new AppDetailPicHolder();
        mContainerPic.addView(appDetailPicHolder.getHolderView());
        appDetailPicHolder.setDataAndRefreshHolderView(mData);

        //4. 描述部分
        AppDetailDesHolder appDetailDesHolder = new AppDetailDesHolder();
        mContainerDes.addView(appDetailDesHolder.getHolderView());
        appDetailDesHolder.setDataAndRefreshHolderView(mData);

        //5. 下载部分
        mAppDetailBottomHolder = new AppDetailBottomHolder();
        mContainerBottom.addView(mAppDetailBottomHolder.getHolderView());
        mAppDetailBottomHolder.setDataAndRefreshHolderView(mData);

        DownloadManager.getInstance().addObserver(mAppDetailBottomHolder);

        return view;
    }

    /**
     * 初始化数据
     */
    private LoadingPager.LoadedResult onInitData() {
        //发起网络请求
        DetailProtocol protocol = new DetailProtocol(mPackageName);

        try {
            mData = protocol.loadData(0);

            if (mData != null) {
                return LoadingPager.LoadedResult.SUCCESS;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LoadingPager.LoadedResult.ERROR;
    }

    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(mName);
        String temp;
        if (actionBar == null) {
            temp = "false";
        } else {
            temp = "true";
        }
        LogUtils.i(TAG, "initActionBar: " + temp);

        //显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 界面不可见的时候移除观察者
     */
    @Override
    protected void onPause() {
        if (mAppDetailBottomHolder != null) {
            DownloadManager.getInstance().deleteObserver(mAppDetailBottomHolder);
        }

        super.onPause();
    }
}
