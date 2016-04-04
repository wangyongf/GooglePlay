/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: MainActivity.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/4       在页面选中的时候开始加载数据
 */

package com.yongf.googleplay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStripExtends;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.factory.FragmentFactory;
import com.yongf.googleplay.utils.LogUtils;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 主界面
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class MainActivity extends ActionBarActivity {

    private PagerSlidingTabStripExtends mTabs;
    private ViewPager mViewPager;
    private String[] mMainTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化View
        initView();

        //初始化数据
        initData();

        //初始化事件
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //完成数据加载
                BaseFragment fragment = FragmentFactory.getFragment(position);
                if (fragment != null) {
                    fragment.getLoadingPager().loadData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMainTitles = UIUtils.getStringArray(R.array.main_titles);

//        MainPagerAdapter adapter = new MainPagerAdapter();

//        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());

        MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(adapter);

        //ViewPager和tabs的绑定
        mTabs.setViewPager(mViewPager);
    }

    /**
     * 初始化View
     */
    private void initView() {
        setContentView(R.layout.activity_main);

        mTabs = (PagerSlidingTabStripExtends) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);

        //初始化ActionBar
        initActionBar();
    }

    /**
     * 初始化ActionBar
     */
    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);        //设置Logo
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Google Play");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    class MainPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mMainTitles != null) {
                return mMainTitles.length;
            }

            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(mMainTitles[position]);

            container.addView(tv);

            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * 必须要重写此方法
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }
    }

    class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //创建Fragment
            Fragment fragment = FragmentFactory.getFragment(position);

            LogUtils.sf("初始化" + mMainTitles[position]);
//            Log.i("GooglePlay", "初始化" + mMainTitles[position]);

            return fragment;
        }

        @Override
        public int getCount() {
            if (mMainTitles != null) {
                return mMainTitles.length;
            }

            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }
    }

    class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public MainFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentFactory.getFragment(position);

            LogUtils.sf("初始化" + mMainTitles[position]);

            return fragment;
        }

        @Override
        public int getCount() {
            if (mMainTitles != null) {
                return mMainTitles.length;
            }

            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }
    }
}
