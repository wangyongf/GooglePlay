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
import android.widget.AbsListView;
import android.widget.ListView;

import com.lidroid.xutils.exception.HttpException;
import com.yongf.googleplay.adapter.AppItemAdapter;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.factory.ListViewFactory;
import com.yongf.googleplay.holder.AppItemHolder;
import com.yongf.googleplay.manager.DownloadManager;
import com.yongf.googleplay.protocol.AppProtocol;

import java.io.IOException;
import java.util.List;

/**
 * 应用页面
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class AppFragment extends BaseFragment {

    private List<AppInfoBean> mData;
    private AppProtocol mProtocol;
    private AppAdapter mAdapter;

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        //返回成功的视图
        ListView listView = ListViewFactory.getListView();

        //设置适配器
        mAdapter = new AppAdapter(listView, mData);
        listView.setAdapter(mAdapter);

        return listView;
    }

    /**
     * 真正加载数据，执行耗时操作
     *
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        //执行耗时操作
//        SystemClock.sleep(Constants.SLEEP_DURATION);

        mProtocol = new AppProtocol();

        try {
            mData = mProtocol.loadData(0);

            return checkState(mData);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LoadingPager.LoadedResult.ERROR;
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

    private class AppAdapter extends AppItemAdapter {
        public AppAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public List<AppInfoBean> onLoadMore() throws Exception {
            //加载更多
            return mProtocol.loadData(mData.size());
        }
    }
}
