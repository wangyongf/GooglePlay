/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       Create	
 */

package com.yongf.googleplay.base;

import android.view.View;

/**
 * 基类Holder
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/5
 * @see
 * @since GooglePlay1.0
 */
public abstract class BaseHolder<HOLDER_BEAN_TYPE> {

    public View mHolderView;
    //做holder需要持有孩子对象
    private HOLDER_BEAN_TYPE mData;

    public View getHolderView() {
        return mHolderView;
    }

    public BaseHolder() {
        //初始化根布局
        mHolderView = initHolderView();
        //绑定tag
        mHolderView.setTag(this);
    }

    /**
     * 初始化holderView/根视图
     *
     * @return
     * @call BaseHolder 初始化的时候调用
     */
    public abstract View initHolderView();

    /**
     * 设置数据，刷新视图
     *
     * @param data
     * @call 需要设置数据和刷新数据的时候调用
     */
    public void setDataAndRefreshHolderView(HOLDER_BEAN_TYPE data) {
        //保存数据
        mData = data;
        //刷新显示
        refreshHolderView(data);
    }

    /**
     * 刷新holder视图
     *
     * @param data
     * @call setDataAndRefershHolderView()调用的时候调用
     */
    public abstract void refreshHolderView(HOLDER_BEAN_TYPE data);
}
