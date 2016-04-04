/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: LoadingPager						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/4       Create
 *  1.1         Scott Wang     2016/4/4       LoadingPager数据加载，刷新视图，相关优化
 */

package com.yongf.googleplay.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.yongf.googleplay.R;
import com.yongf.googleplay.utils.LogUtils;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 加载页面的基类
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/4
 * @see
 * @since GooglePlay1.0
 */
public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_NONE = -1;                 //默认状态
    public static final int STATE_LOADING = 0;              //正在请求网络
    public static final int STATE_EMPTY = 1;                //空状态
    public static final int STATE_ERROR = 2;                //错误状态
    public static final int STATE_SUCCESS = 3;              //成功状态

    private static final String TAG = "LoadingPager";

    /**
     * 当前状态
     */
    public int mCurState = STATE_NONE;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public LoadingPager(Context context) {
        super(context);

        //初始化公共页面
        initView();

        //初始化事件
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新加载数据
                loadData();
            }
        });
    }

    /**
     * 初始化常规视图
     *
     * @call LoadingPager初始化的时候被调用
     */
    private void initView() {
        //加载页面
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);

        //错误页面
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);

        //空页面
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        refreshUI();
    }

    /**
     * 根据当前页面类型，显示不同的View
     *
     * @call 1. LoadingPager初始化的时候
     * @call 2. 真正加载数据执行完成的时候
     */
    private void refreshUI() {
        //显示loading视图
        mLoadingView.setVisibility((mCurState == STATE_LOADING) || (mCurState == STATE_NONE) ? VISIBLE : GONE);

        //显示error视图
        mErrorView.setVisibility((mCurState == STATE_ERROR) ? VISIBLE : GONE);

        //显示empty视图
        mEmptyView.setVisibility((mCurState == STATE_EMPTY) ? VISIBLE : GONE);

        if (mSuccessView == null && mCurState == STATE_SUCCESS) {
            //创建成功视图
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }

        if (mSuccessView != null) {
            mSuccessView.setVisibility((mCurState == STATE_SUCCESS) ? VISIBLE : GONE);
        }
    }

    /**
     * 返回创建的成功视图
     *
     * @return
     * @call 真正加载数据完成之后，并且数据加载成功，必须告知具体的成功视图
     */
    public abstract View initSuccessView();

    /**
     * 触发数据加载
     *
     * @call 暴露给外界调用
     */
    public void loadData() {
        //如果加载成功，那就无需重复加载
        if (mCurState != STATE_SUCCESS && mCurState != STATE_LOADING) {
            LogUtils.sf("开始加载数据");

            //保证每次执行的时候一定是加载中视图，而不是上一次的mCurState
            int state = STATE_LOADING;
            mCurState = state;
            refreshUI();

            //异步加载数据
            new Thread(new LoadDataTask()).start();
        }
    }

    /**
     * 必须实现，但是不知道具体实现，定义成为抽象方法，让子类具体实现
     * 真正加载数据
     *
     * @return
     * @call loadData()方法被调用的时候
     */
    public abstract LoadedResult initData();

    public enum LoadedResult {
        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);

        int state;

        private LoadedResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            //真正地加载网络数据
            LoadedResult tmpState = initData();

            //处理加载结果
            mCurState = tmpState.getState();

            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    //刷新UI
                    refreshUI();
                }
            });
        }
    }
}
