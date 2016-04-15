/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/15       新增：Create
 */

package com.yongf.googleplay.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.yongf.googleplay.activity.MainActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * 基类Activity
 * 共同属性、共同方法
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/15
 * @see
 * @since GooglePlay1.0
 */
public abstract class BaseActivity extends ActionBarActivity {

    private List<ActionBarActivity> mActivityList = new LinkedList<>();
    private long mPreTime;
    private Activity mCurActivity;

    public Activity getCurActivity() {
        return mCurActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initView();
        initData();
        initEvent();
        initActionBar();

        mActivityList.add(this);
    }

    @Override
    protected void onDestroy() {
        mActivityList.remove(this);

        super.onDestroy();
    }

    /**
     * 初始化
     */
    public void init() {

    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 初始化视图（界面）
     */
    public abstract void initView();

    /**
     * 初始化事件
     */
    public void initEvent() {

    }

    /**
     * 初始化ActionBar
     */
    public void initActionBar() {

    }

    @Override
    protected void onResume() {
        mCurActivity = this;

        super.onResume();
    }

    /**
     * 完全退出
     */
    public void exit() {
        for (ActionBarActivity activity : mActivityList) {
            activity.finish();
        }
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (this instanceof MainActivity) {
            if (System.currentTimeMillis() - mPreTime > 2000) {     //两次点击间隔大于2s
                Toast.makeText(getApplicationContext(), "再次点击退出", Toast.LENGTH_SHORT).show();

                mPreTime = System.currentTimeMillis();

                return;
            }
        }

        super.onBackPressed();          //finish();
    }
}
