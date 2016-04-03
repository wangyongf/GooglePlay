/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create	
 */

package com.yongf.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 所有Fragment的基类
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since SmartBeiJing1.0
 */
public abstract class BaseFragment extends Fragment {

    /**------------------- 共有的属性 -------------------**/
    /**------------------- 共有的方法 -------------------**/

    private static final String TAG = "BaseFragment";
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initData();
        initEvent();

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化View，并且是必须实现，但是不知道具体实现，抽象方法
     *
     * @return
     */
    public abstract View initView();

    public void init() {

    }

    public void initEvent() {

    }

    public void initData() {

    }
}
