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
import com.yongf.googleplay.bean.SubjectInfoBean;
import com.yongf.googleplay.holder.SubjectHolder;
import com.yongf.googleplay.protocol.SubjectProtocol;
import com.yongf.googleplay.utils.UIUtils;

import java.io.IOException;
import java.util.List;

/**
 * 专题页面
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class SubjectFragment extends BaseFragment {

    private List<SubjectInfoBean> mData;

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());

        listView.setAdapter(new SubjectAdapter(listView, mData));

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
        SubjectProtocol protocol = new SubjectProtocol();

        try {
            mData = protocol.loadData(0);

            return checkState(mData);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LoadingPager.LoadedResult.ERROR;
    }

    private class SubjectAdapter extends SuperBaseAdapter<SubjectInfoBean> {

        public SubjectAdapter(AbsListView absListView, List<SubjectInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public BaseHolder<SubjectInfoBean> getSpecialHolder(int position) {
            return new SubjectHolder();
        }
    }
}
