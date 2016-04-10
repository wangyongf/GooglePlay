/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: SuperBaseAdapter						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       Create
 *  1.1         Scott Wang     2016/4/10     抽象特殊ViewHolder，加入加载更多列表项
 */

package com.yongf.googleplay.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yongf.googleplay.holder.LoadMoreHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类Adapter
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/5
 * @see
 * @since GooglePlay1.0
 */
public abstract class SuperBaseAdapter<ITEM_BEAN_TYPE> extends BaseAdapter {

    public static final int VIEWTYPE_LOADMORE = 0;
    public static final int VIEWTYPE_NORMAL = 1;

    public List<ITEM_BEAN_TYPE> mDataSource = new ArrayList<>();
    private LoadMoreHolder mLoadMoreHolder;

    public SuperBaseAdapter(List<ITEM_BEAN_TYPE> dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public int getCount() {
        if (mDataSource != null) {
            return mDataSource.size() + 1;
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

    /**
     * 返回具体的BaseHolder的子类
     *
     * @return
     * @call getView方法中如果没有convertView的时候被创建
     */
    public abstract BaseHolder<ITEM_BEAN_TYPE> getSpecialHolder();

    /////// ------------------- ListView里面可以显示多种ViewType ------------------- ///////

    //
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;        //2
    }

    /**
     * Integers must be in the range 0 to getViewTypeCount - 1
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //如果滑到底部，那么对应的ViewType是加载更多
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;
        }

        return VIEWTYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<ITEM_BEAN_TYPE> holder;

        /**------------------- 初始化视图，决定根布局 -------------------**/

        if (convertView == null) {
            if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
                holder = (BaseHolder<ITEM_BEAN_TYPE>) getLoadMoreHolder();
            } else {
                holder = getSpecialHolder();
            }
        } else {
            holder = (BaseHolder<ITEM_BEAN_TYPE>) convertView.getTag();
        }

        /**------------------- 数据展示 -------------------**/
        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
            //加载更多类型
            mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.STATE_LOADING);
        } else {
            holder.setDataAndRefreshHolderView(mDataSource.get(position));
        }

        return holder.mHolderView;
    }

    /**
     * 返回加载更多的视图
     *
     * @return
     */
    private LoadMoreHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }

        return mLoadMoreHolder;
    }

}
