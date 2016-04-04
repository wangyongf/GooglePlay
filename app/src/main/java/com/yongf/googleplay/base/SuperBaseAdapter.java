/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: SuperBaseAdapter						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       Create	
 */

package com.yongf.googleplay.base;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类Adapter
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/5
 * @see
 * @since GooglePlay1.0
 */
public abstract class SuperBaseAdapter<ITEM_BEAN_TYPE> extends BaseAdapter {

    public List<ITEM_BEAN_TYPE> mDataSource = new ArrayList<>();

    public SuperBaseAdapter(List<ITEM_BEAN_TYPE> dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public int getCount() {
        if (mDataSource != null) {
            return mDataSource.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDataSource != null) {
            return mDataSource.get(position);
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
