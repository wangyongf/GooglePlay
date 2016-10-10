/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: SettingActivity.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     16-10-8        新增:Create
 *  1.1         Scott Wang     16-10-10      新增：更新SettingActivity的布局，增加工具栏返回功能
 */

package com.yongf.googleplay.activity;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseActivity;

/**
 * 设置界面
 *
 * @author Scott Wang
 * @version 1.1, 16-10-8
 * @see
 * @since GooglePlay V1.0
 */
public class SettingActivity extends BaseActivity {


    private static final String TAG = "SettingActivity";

    @Override
    public void initView() {
        setContentView(R.layout.activity_setting);

        //findView


        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("设置中心");

        //显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
