/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: FragmentFactory						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create	
 */

package com.yongf.googleplay.factory;

import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

import com.yongf.googleplay.fragment.AppFragment;
import com.yongf.googleplay.fragment.CategoryFragment;
import com.yongf.googleplay.fragment.GameFragment;
import com.yongf.googleplay.fragment.HomeFragment;
import com.yongf.googleplay.fragment.HotFragment;
import com.yongf.googleplay.fragment.RecommendFragment;
import com.yongf.googleplay.fragment.SubjectFragment;

/**
 * 获取Fragment的工厂
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since SmartBeiJing1.0
 */
public class FragmentFactory {

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_APP = 1;
    public static final int FRAGMENT_GAME = 2;
    public static final int FRAGMENT_SUBJECT = 3;
    public static final int FRAGMENT_RECOMMEND = 4;
    public static final int FRAGMENT_CATEGORY = 5;
    public static final int FRAGMENT_HOT = 6;

    private static SparseArrayCompat<Fragment> cacheFragment = new SparseArrayCompat<>();

    /**
     * 获取Fragment的工厂
     *
     * @param position
     * @return
     */
    public static Fragment getFragment(int position) {

        Fragment fragment = null;

        //如果缓存中存在，则直接从缓存中取出并返回
        Fragment tmpFragment = cacheFragment.get(position);
        if (tmpFragment != null) {
            fragment = tmpFragment;

            return fragment;
        }
//        if (cacheFragment.containsKey(position)) {
//            fragment = cacheFragment.get(position);
//
//            return fragment;
//        }

        switch (position) {
            case FRAGMENT_HOME:     //主页
                fragment = new HomeFragment();

                break;
            case FRAGMENT_APP:      //应用
                fragment = new AppFragment();

                break;
            case FRAGMENT_GAME:     //游戏
                fragment = new GameFragment();


                break;
            case FRAGMENT_SUBJECT:      //专题
                fragment = new SubjectFragment();

                break;
            case FRAGMENT_RECOMMEND:        //推荐
                fragment = new RecommendFragment();

                break;
            case FRAGMENT_CATEGORY:         //分类
                fragment = new CategoryFragment();

                break;
            case FRAGMENT_HOT:          //热门
                fragment = new HotFragment();

                break;
        }

        //保存对应的fragment
        cacheFragment.put(position, fragment);

        return fragment;
    }
}
