/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create	
 */

package com.yongf.googleplay.fragment;

import android.view.View;
import android.widget.TextView;

import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.utils.UIUtils;

/**
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @description
 * @see
 * @since SmartBeiJing1.0
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    @Override
    public View initView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(this.getClass().getSimpleName());

        return tv;
    }
}
