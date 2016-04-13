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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.factory.ThreadPoolFactory;
import com.yongf.googleplay.holder.LoadMoreHolder;
import com.yongf.googleplay.utils.UIUtils;

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
public abstract class SuperBaseAdapter<ITEM_BEAN_TYPE> extends BaseAdapter implements AdapterView.OnItemClickListener {

    public static final int VIEWTYPE_LOADMORE = 0;
    public static final int VIEWTYPE_NORMAL = 1;

    public List<ITEM_BEAN_TYPE> mDataSource = new ArrayList<>();
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;

    public SuperBaseAdapter(AbsListView absListView, List<ITEM_BEAN_TYPE> dataSource) {
        super();

        absListView.setOnItemClickListener(this);
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
    public abstract BaseHolder<ITEM_BEAN_TYPE> getSpecialHolder(int position);

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

//        return VIEWTYPE_NORMAL;
        return getNormalViewType(position);
    }

    /**
     * 返回一个普通view的一个viewType
     *
     * @param position
     * @return
     * @call 子类可以复写该方法，添加更多的ViewType
     */
    public int getNormalViewType(int position) {
        return VIEWTYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<ITEM_BEAN_TYPE> holder;

        /**------------------- 初始化视图，决定根布局 -------------------**/

        if (convertView == null) {
            if (getItemViewType(position) == VIEWTYPE_LOADMORE) {       //是加载更多类型
                holder = (BaseHolder<ITEM_BEAN_TYPE>) getLoadMoreHolder();
            } else {
                holder = getSpecialHolder(position);
            }
        } else {
            holder = (BaseHolder<ITEM_BEAN_TYPE>) convertView.getTag();
        }

        /**------------------- 数据展示 -------------------**/
        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
            //加载更多类型
            if (hasMore()) {
                startLoadingMore();
            } else {
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.STATE_NONE);
            }
        } else {
            holder.setDataAndRefreshHolderView(mDataSource.get(position));
        }

        return holder.mHolderView;
    }

    /**
     * 决定是否有更多数据，默认是返回true，但是子类可以复写此方法
     * 如果子类返回的是false，就不会有加载更多的ListView Item
     *
     * @return
     * @call getView中滑到底的时候调用
     */
    private boolean hasMore() {
        return true;
    }

    /**
     * 滑到底之后，获取更多数据
     *
     * @call 滑到底的时候
     */
    private void startLoadingMore() {
        if (null == mLoadMoreTask) {
            //修改loadmore当前的视图为加载中
            int state = LoadMoreHolder.STATE_LOADING;
            mLoadMoreHolder.setDataAndRefreshHolderView(state);

            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolFactory.getNormalPool().execute(mLoadMoreTask);
        }
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

    /**
     * 可有可无的方法，如果子类有加载更多，复写即可
     * 真正开始加载更多数据的地方
     *
     * @return 数据集合
     * @call 滑倒底的时候被调用
     */
    public List<ITEM_BEAN_TYPE> onLoadMore() throws Exception {
        return null;
    }

    /**
     * 处理item的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (VIEWTYPE_LOADMORE == getItemViewType(position)) {
            //重新加载更多
            startLoadingMore();
        } else {
            onNormalItemClick(parent, view, position, id);
        }
    }

    /**
     * 点击普通条目（非加载更多条目）对应的事件处理
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     * @call 如果子类需要处理普通Item的点击事件，直接复写此方法
     */
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class LoadMoreTask implements Runnable {
        @Override
        public void run() {
            //真正开始请求网络，加载数据
            List<ITEM_BEAN_TYPE> moreData = null;

            /////// ------------------- 根据加载更多的数据，处理加载更多的结果 start ------------------- ///////
            //得到返回数据，处理结果
            int state;

            try {
                moreData = onLoadMore();

                if (moreData == null) {
                    //没有更多数据了
                    state = LoadMoreHolder.STATE_NONE;
                } else if (moreData.size() < Constants.PAGER_SIZE) {
                    //假如规定每页返回20条数据
                    //没有更多了
                    state = LoadMoreHolder.STATE_NONE;
                } else {
                    state = LoadMoreHolder.STATE_LOADING;
                }


            } catch (Exception e) {
                e.printStackTrace();
                state = LoadMoreHolder.STATE_RETRY;     //网络可能有问题，返回RETRY
            }

            /////// ------------------- 根据加载更多的数据，处理加载更多的结果 end ------------------- ///////

            final int finalState = state;
            final List<ITEM_BEAN_TYPE> finalMoreData = moreData;
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    //刷新LoadingMore视图
                    mLoadMoreHolder.setDataAndRefreshHolderView(finalState);

                    //刷新ListView视图，返回加载更多之后得到的数据mData.addAll()
                    if (finalMoreData != null) {
                        mDataSource.addAll(finalMoreData);
                        notifyDataSetChanged();
                    }
                }
            });

            mLoadMoreTask = null;
        }
    }
}
