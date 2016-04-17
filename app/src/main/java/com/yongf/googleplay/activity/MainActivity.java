/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: MainActivity.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       新增：Create
 *  1.1         Scott Wang     2016/4/4       新增：在页面选中的时候开始加载数据
 *  1.2         Scott Wang     2016/4/10     新增：侧边栏DrawerLayout
 *  1.3         Scott Wang     2016/4/16     优化：封装BaseActivity
 */

package com.yongf.googleplay.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.astuetz.PagerSlidingTabStripExtends;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseActivity;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.factory.FragmentFactory;
import com.yongf.googleplay.holder.MenuHolder;
import com.yongf.googleplay.utils.LogUtils;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 主界面
 *
 * @author Scott Wang
 * @version 1.3, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class MainActivity extends BaseActivity {

    private PagerSlidingTabStripExtends mTabs;
    private ViewPager mViewPager;
    private String[] mMainTitles;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FrameLayout mMainMenu;

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        mMainTitles = UIUtils.getStringArray(R.array.main_titles);

        MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(adapter);

        //ViewPager和tabs的绑定
        mTabs.setViewPager(mViewPager);

        //设置ViewPager的动画
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        MenuHolder menuHolder = new MenuHolder();
        mMainMenu.addView(menuHolder.getHolderView());
        menuHolder.setDataAndRefreshHolderView(null);
    }

    /**
     * 初始化View
     */
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);

        mTabs = (PagerSlidingTabStripExtends) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        mMainMenu = (FrameLayout) findViewById(R.id.main_menu);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initEvent() {
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
     * 初始化ActionBar
     */
    @Override
    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);        //设置Logo
        actionBar.setIcon(R.mipmap.ic_launcher);            //设置Icon
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Google Play");

        //显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);

        initActionBarToggle();
    }

    private void initActionBarToggle() {
        mToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer_am,
                R.string.open,
                R.string.close
        );

        //同步状态
        mToggle.syncState();

        //设置mDrawerLayout拖动的监听
        mDrawerLayout.setDrawerListener(mToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //mToggle控制打开关闭drawerlayout
                mToggle.onOptionsItemSelected(item);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
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

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer
    {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @SuppressLint("NewApi")
        public void transformPage(View view, float position)
        {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            Log.e("TAG", view + " , " + position + "");

            if (position < -1)
            { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0)
                {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else
                {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else
            { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
