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
 *  1.4         Scott Wang     16-10-7         新增：将侧边栏换成MD风格，引入NavigationView
 *  1.5         Scott Wang     16-10-8         优化：将onNavigationItemSelected方法中的if换成switch
 */

package com.yongf.googleplay.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStripExtends;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseActivity;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.factory.FragmentFactory;
import com.yongf.googleplay.utils.LogUtils;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 主界面
 *
 * @author Scott Wang
 * @version 1.5, 16-10-7
 * @see
 * @since GooglePlay1.0
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PagerSlidingTabStripExtends mTabs;
    private ViewPager mViewPager;
    private String[] mMainTitles;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView mNvNav;



    /**
     * 初始化View
     */
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);

        mTabs = (PagerSlidingTabStripExtends) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mDrawer = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        mNvNav = (NavigationView) findViewById(R.id.nv_nav);

        initActionBar();
    }

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

        mNvNav.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 初始化ActionBar
     */
    public void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.open, R.string.close);

        //同步状态
        mToggle.syncState();

        //设置mDrawerLayout拖动的监听
        mDrawer.setDrawerListener(mToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:


                break;
            case R.id.nav_gallery:


                break;
            case R.id.nav_slideshow:


                break;
            case R.id.nav_manage:


                break;
            case R.id.nav_setting:          //设置界面
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

                break;
            case R.id.nav_send:


                break;
            case R.id.nav_recommend:


                break;
            default:
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);

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
}
