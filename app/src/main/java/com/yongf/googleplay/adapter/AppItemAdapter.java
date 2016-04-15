/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppItemAdapter						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       新增：Create
 */

package com.yongf.googleplay.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.yongf.googleplay.activity.DetailActivity;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.base.SuperBaseAdapter;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.holder.AppItemHolder;
import com.yongf.googleplay.utils.UIUtils;

import java.util.List;

/**
 * 基本的应用信息Item
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class AppItemAdapter extends SuperBaseAdapter<AppInfoBean> {

    public AppItemAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
        super(absListView, dataSource);
    }

    @Override
    public BaseHolder<AppInfoBean> getSpecialHolder(int position) {
        return new AppItemHolder();
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Jump2DetailActivity(mDataSource.get(position).packageName, mDataSource.get(position).name);
    }

    /**
     * 跳转到详情页
     *
     * @param packageName 应用包名
     * @param name        应用名称
     */
    private void Jump2DetailActivity(String packageName, String name) {
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        intent.putExtra("name", name);

        UIUtils.getContext().startActivity(intent);
    }
}
