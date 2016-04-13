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
import com.yongf.googleplay.bean.CategoryInfoBean;
import com.yongf.googleplay.factory.ListViewFactory;
import com.yongf.googleplay.holder.CategoryItemHolder;
import com.yongf.googleplay.holder.CategoryTitleHolder;
import com.yongf.googleplay.protocol.CategoryProtocol;

import java.io.IOException;
import java.util.List;

/**
 * 分类页面
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class CategoryFragment extends BaseFragment {

    private List<CategoryInfoBean> mData;

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.getListView();
        listView.setAdapter(new CategoryAdapter(listView, mData));

        return listView;
    }

    /**
     * 真正加载数据，执行耗时操作
     *
     * @return 数据加载结果
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        //执行耗时操作
        CategoryProtocol protocol = new CategoryProtocol();

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

    private class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean> {
        public CategoryAdapter(AbsListView absListView, List<CategoryInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public BaseHolder<CategoryInfoBean> getSpecialHolder(int position) {
            CategoryInfoBean categoryInfoBean = mData.get(position);
            //如果是title ==> titleHolder
            if (categoryInfoBean.isTitle) {
                return new CategoryTitleHolder();
            } else {
                return new CategoryItemHolder();
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }

        @Override
        public int getNormalViewType(int position) {        //此时有3种ViewType 0 1 2
            CategoryInfoBean categoryInfoBean = mData.get(position);
            //如果是title ==> titleHolder
            if (categoryInfoBean.isTitle) {
                return super.getNormalViewType(position) + 1;
            } else {
                return super.getNormalViewType(position);
            }
        }
    }
}
