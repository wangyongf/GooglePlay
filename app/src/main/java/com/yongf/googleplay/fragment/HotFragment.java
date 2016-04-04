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

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.utils.UIUtils;

import java.util.Random;

/**
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @description
 * @see
 * @since GooglePlay1.0
 */
public class HotFragment extends BaseFragment {

    private static final String TAG = "HotFragment";

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(this.getClass().getSimpleName());

        return tv;
    }

    /**
     * 真正加载数据，执行耗时操作
     *
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        //执行耗时操作
        SystemClock.sleep(Constants.SLEEP_DURATION);

        //随机返回3种状态中的一种
        LoadingPager.LoadedResult[] array = {LoadingPager.LoadedResult.EMPTY,
                LoadingPager.LoadedResult.ERROR, LoadingPager.LoadedResult.SUCCESS};
        Random random = new Random();
        int index = random.nextInt(array.length);

        return array[index];
    }
}
