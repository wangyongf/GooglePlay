/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: DetailActivity.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       Create
 *  1.1         Scott Wang     2016/4/15       应用详情页跳转，数据加载，数据获取协议封装，信息部分
 *  1.2         Scott Wang     2016/4/15       详情页安全部分数据展示、折叠属性动画
 */

package com.yongf.googleplay.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.holder.AppDetailBottomHolder;
import com.yongf.googleplay.holder.AppDetailDesHolder;
import com.yongf.googleplay.holder.AppDetailInfoHolder;
import com.yongf.googleplay.holder.AppDetailPicHolder;
import com.yongf.googleplay.holder.AppDetailSecureHolder;
import com.yongf.googleplay.protocol.DetailProtocol;
import com.yongf.googleplay.utils.UIUtils;

import java.io.IOException;

/**
 * 应用详情页
 *
 * @author Scott Wang
 * @version 1.2, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class DetailActivity extends ActionBarActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化
        init();
    }

    /**
     * 页面初始化
     */
    private void init() {
        mPackageName = getIntent().getStringExtra("packageName");

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

        //触发加载数据
        mLoadingPager.loadData();

        setContentView(mLoadingPager);

        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);        //设置Logo
        actionBar.setIcon(R.mipmap.ic_launcher);            //设置Icon
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Google Play");

        //显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    /**
     * 初始化视图
     */
    private View onInitView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.activity_detail, null);

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
        AppDetailBottomHolder appDetailBottomHolder = new AppDetailBottomHolder();
        mContainerBottom.addView(appDetailBottomHolder.getHolderView());
        appDetailBottomHolder.setDataAndRefreshHolderView(mData);

        return view;
    }

    /**
     * 初始化事件
     */
    private void initEvent() {

    }
}
