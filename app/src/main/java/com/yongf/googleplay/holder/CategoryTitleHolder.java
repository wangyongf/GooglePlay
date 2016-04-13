/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: CategoryTitleHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/12       Create	
 */

package com.yongf.googleplay.holder;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.CategoryInfoBean;
import com.yongf.googleplay.utils.UIUtils;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/12
 * @see
 * @since GooglePlay1.0
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfoBean> {

    private TextView mTvTitle;

    @Override
    public View initHolderView() {
        mTvTitle = new TextView(UIUtils.getContext());

        //自定义字体
        Typeface typeface = Typeface.createFromAsset(UIUtils.getAssets(), "font/qitijianti.ttf");
        mTvTitle.setTypeface(typeface);

        int paddingLeft = UIUtils.dip2px(40);
        int padding = UIUtils.dip2px(5);
        mTvTitle.setPadding(paddingLeft, padding, padding, padding);

        mTvTitle.setTextSize(35);
        mTvTitle.setTextColor(Color.RED);
        mTvTitle.setBackgroundColor(Color.WHITE);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mTvTitle.setLayoutParams(params);

        return mTvTitle;
    }

    @Override
    public void refreshHolderView(CategoryInfoBean data) {
        mTvTitle.setText(data.title);
    }
}
