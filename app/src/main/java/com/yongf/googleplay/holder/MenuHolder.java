/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: MenuHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/17       新增：Create	
 */

package com.yongf.googleplay.holder;

import android.view.View;

import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.util.UIUtils;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/17
 * @see
 * @since GooglePlay1.0
 */
public class MenuHolder extends BaseHolder<Object> {

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);

        return view;
    }

    @Override
    public void refreshHolderView(Object data) {

    }
}
