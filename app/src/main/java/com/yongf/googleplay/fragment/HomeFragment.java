/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/5       模拟首页数据(ListView)
 *  1.2         Scott Wang     2016/4/10     正式从服务器获取数据
 *  1.3         Scott Wang     16-10-16       优化：当MainActivity活动停止时，移除轮播图定时事件
 */

package com.yongf.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.lidroid.xutils.exception.HttpException;
import com.yongf.googleplay.adapter.AppItemAdapter;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.bean.HomeBean;
import com.yongf.googleplay.conf.Convention;
import com.yongf.googleplay.factory.ListViewFactory;
import com.yongf.googleplay.holder.AppItemHolder;
import com.yongf.googleplay.holder.PictureHolder;
import com.yongf.googleplay.manager.DownloadManager;
import com.yongf.googleplay.protocol.HomeProtocol;

import java.io.IOException;
import java.util.List;

/**
 * 应用主页面
 *
 * @author Scott Wang
 * @version 1.3, 2016/4/4
 * @see
 * @since GooglePlay1.0
 */
public class HomeFragment extends BaseFragment {

    private List<AppInfoBean> mData;            //listView的数据源
    private List<String> mPictures;          //轮播图图片的URLs集合
    private HomeProtocol mProtocol;
    private HomeAdapter mAdapter;
    private PictureHolder mPictureHolder;

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        //返回成功的视图
        ListView listView = ListViewFactory.getListView();

        //创建一个PictureHolder  轮播图
        mPictureHolder = new PictureHolder();

        //触发加载数据
        mPictureHolder.setDataAndRefreshHolderView(mPictures);

        View headerView = mPictureHolder.getHolderView();
        listView.addHeaderView(headerView);

        //设置adapter
        mAdapter = new HomeAdapter(listView, mData);
        listView.setAdapter(mAdapter);

        return listView;
    }

    @Override
    public void onStop() {
        super.onStop();

        mPictureHolder.mAutoScrollTask.stop();
    }

    /**
     * 真正加载数据，执行耗时操作
     *
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initData() {

        /////// ------------------- 协议简单封装之后 ------------------- ///////

        mProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mProtocol.loadData(0);

            LoadingPager.LoadedResult state = checkState(homeBean);
            if (state != LoadingPager.LoadedResult.SUCCESS) {       //如果不成功，直接返回 homeBean ok
                return state;
            }

            state = checkState(homeBean.list);
            if (state != LoadingPager.LoadedResult.SUCCESS) {       //如果不成功，就直接返回 homeBean.list ok
                return state;
            }

            mData = homeBean.list;
            mPictures = homeBean.picture;

            return LoadingPager.LoadedResult.SUCCESS;
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LoadingPager.LoadedResult.ERROR;
    }

    private List<AppInfoBean> loadMore(int index) throws HttpException, IOException {

        /////// ------------------- 协议简单封装之后 ------------------- ///////

        HomeBean homeBean = mProtocol.loadData(index);

        if (null == homeBean) {
            return null;
        } else if (null == homeBean.list || 0 == homeBean.list.size()) {
            return null;
        }

        return homeBean.list;
    }

    @Override
    public void onResume() {
        //重新添加监听
        if (mAdapter != null) {
            List<AppItemHolder> appItemHolders = mAdapter.getAppItemHolders();
            for (AppItemHolder holder : appItemHolders) {
                DownloadManager.getInstance().addObserver(holder);
            }
            //手动刷新
            mAdapter.notifyDataSetChanged();
        }

        super.onResume();
    }

    @Override
    public void onPause() {
        //移除监听
        if (mAdapter != null) {
            List<AppItemHolder> appItemHolders = mAdapter.getAppItemHolders();
            for (AppItemHolder holder : appItemHolders) {
                DownloadManager.getInstance().deleteObserver(holder);
            }
        }

        super.onPause();
    }

    private class HomeAdapter extends AppItemAdapter {

        public HomeAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public List<AppInfoBean> onLoadMore() throws Exception {
            //休眠一下
            SystemClock.sleep(Convention.LOADING_MORE_DURATION);

            return loadMore(mData.size());
        }
    }
}
