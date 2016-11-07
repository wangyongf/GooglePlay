/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: LoadMoreHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       Create	
 */

package com.yongf.googleplay.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.util.UIUtils;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/5
 * @see
 * @since GooglePlay1.0
 */
public class LoadMoreHolder extends BaseHolder<Integer> {

    public static final int STATE_LOADING = 0;
    public static final int STATE_RETRY = 1;
    public static final int STATE_NONE = 2;

    @ViewInject(R.id.item_loadmore_container_loading)
    LinearLayout mContainerLoading;

    @ViewInject(R.id.item_loadmore_container_retry)
    LinearLayout mContainerRetry;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void refreshHolderView(Integer state) {
        mContainerLoading.setVisibility(View.GONE);
        mContainerRetry.setVisibility(View.GONE);

        switch (state) {
            case STATE_LOADING:     //加载更多中
                mContainerLoading.setVisibility(View.VISIBLE);

                break;
            case STATE_RETRY:       //加载更多失败
                mContainerRetry.setVisibility(View.VISIBLE);

                break;
            case STATE_NONE:
                break;
        }
    }
}
