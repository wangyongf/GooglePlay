/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppDetailInfoHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       Create	
 */

package com.yongf.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 应用截图Holder
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class AppDetailPicHolder extends BaseHolder<AppInfoBean> {


    @Override
    public View initHolderView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(this.getClass().getSimpleName());

        return tv;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {

    }
}
