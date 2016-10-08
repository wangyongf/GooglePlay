/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: SettingActivity.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     16-10-8        新增:Create
 */

package com.yongf.googleplay.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yongf.googleplay.R;

/**
 * 设置界面
 *
 * @author Scott Wang
 * @version 1.0, 16-10-8
 * @see
 * @since GooglePlay V1.0
 */
public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
