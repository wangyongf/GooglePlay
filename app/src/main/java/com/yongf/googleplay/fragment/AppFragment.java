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
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.base.SuperBaseAdapter;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.factory.ListViewFactory;
import com.yongf.googleplay.holder.AppItemHolder;
import com.yongf.googleplay.protocol.AppProtocol;

import java.io.IOException;
import java.util.List;

/**
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @description
 * @see
 * @since GooglePlay1.0
 */
public class AppFragment extends BaseFragment {

    private List<AppInfoBean> mData;
    private AppProtocol mProtocol;

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
        listView.setAdapter(new AppAdapter(listView, mData));

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

    private class AppAdapter extends SuperBaseAdapter<AppInfoBean> {
        public AppAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public BaseHolder<AppInfoBean> getSpecialHolder() {
            return new AppItemHolder();
        }

        @Override
        public List<AppInfoBean> onLoadMore() throws Exception {
            //加载更多
            return mProtocol.loadData(mData.size());
        }
    }
}
