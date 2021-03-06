/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/4       加入LoadingPager视图显示，数据加载，优化，封装
 *  1.2         Scott Wang     2016/4/10     从网络加载数据的判断，检查数据是否为空，返回加载结果
 */

package com.yongf.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yongf.googleplay.util.LogUtils;
import com.yongf.googleplay.util.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * 所有Fragment的基类
 *
 * @author Scott Wang
 * @version 1.2, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public abstract class BaseFragment extends Fragment {

    /**------------------- 共有的属性 -------------------**/
    /**
     * ------------------- 共有的方法 -------------------
     **/

    private LoadingPager mLoadingPager;

    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }

                @Override
                public LoadedResult initData() {
                    return BaseFragment.this.initData();
                }
            };

            LogUtils.sf("创建mLoadingPager");
        }/* else if (mLoadingPager.getParent() != null) {
            ((ViewGroup) mLoadingPager.getParent()).removeView(mLoadingPager);
        }*/

        return mLoadingPager;
    }

    /**
     * 返回创建的成功视图
     *
     * @return
     * @call 真正加载数据完成之后，并且数据加载成功，必须告知具体的成功视图，但是不知道具体实现
     */
    public abstract View initSuccessView();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initData();
        initEvent();

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化View，并且是必须实现，但是不知道具体实现，抽象方法
     *
     * @return
     */
    public View initView() {
        return null;
    }

    public void init() {

    }

    /**
     * 初始化事件
     */
    public void initEvent() {

    }

    /**
     * 真正加载数据，但是BaseFragment不知道具体实现，必须实现，
     * 定义成为抽象方法，让子类具体实现，它是LoadingPager同名方法
     *
     * @return
     */
    public abstract LoadingPager.LoadedResult initData();

    /**
     * @param obj 网络数据json化之后的对象
     * @return
     */
    public LoadingPager.LoadedResult checkState(Object obj) {
        if (obj == null) {
            return LoadingPager.LoadedResult.EMPTY;
        }

        //list
        if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }

        //map
        if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }

        return LoadingPager.LoadedResult.SUCCESS;
    }
}
