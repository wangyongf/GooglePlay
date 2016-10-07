/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: PictureHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/11       Create	
 */

package com.yongf.googleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.conf.Convention;
import com.yongf.googleplay.utils.BitmapHelper;
import com.yongf.googleplay.utils.UIUtils;

import java.util.List;

/**
 * 首页轮播图的容器
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/11
 * @see
 * @since GooglePlay1.0
 */
public class PictureHolder extends BaseHolder<List<String>> {

    @ViewInject(R.id.item_home_picture_pager)
    ViewPager mViewPager;

    @ViewInject(R.id.item_home_picture_container_indicator)
    LinearLayout mContainerIndicator;

    private List<String> mData;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_home_carousel, null);

        //注入
        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void refreshHolderView(List<String> data) {
        mData = data;
        mViewPager.setAdapter(new PictureAdapter());

        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        //添加小点
        for (int i = 0; i < data.size(); i++) {
            View indicatorView = new View(UIUtils.getContext());
            indicatorView.setBackgroundResource(R.drawable.indicator_normal);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(5), UIUtils.dip2px(5));
            params.leftMargin = UIUtils.dip2px(5);          //左边距
            params.bottomMargin = UIUtils.dip2px(8);        //下边距

            mContainerIndicator.addView(indicatorView, params);

            //默认选中效果
            if (i == 0) {
                indicatorView.setBackgroundResource(R.drawable.indicator_selected);
            }
        }

        //监听滑动事件
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % mData.size();

                for (int i = 0; i < mData.size(); i++) {
                    View indicatorView = mContainerIndicator.getChildAt(i);
                    //还原背景
                    indicatorView.setBackgroundResource(R.drawable.indicator_normal);

                    if (i == position) {
                        indicatorView.setBackgroundResource(R.drawable.indicator_selected);
                    } else {
                        indicatorView.setBackgroundResource(R.drawable.indicator_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置curItem为 count / 2
        int index = Integer.MAX_VALUE / 2;
        int diff = index % mData.size();
        mViewPager.setCurrentItem(index - diff);

        //自动轮播
        final AutoScrollTask autoScrollTask = new AutoScrollTask();
        autoScrollTask.start();

        //用户触摸的时候移除自动轮播的任务
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        autoScrollTask.stop();

                        break;
                    case MotionEvent.ACTION_MOVE:


                        break;
                    case MotionEvent.ACTION_UP:
                        autoScrollTask.start();

                        break;
                }

                return false;
            }
        });
    }

    private class AutoScrollTask implements Runnable {

        /**
         * 开始自动轮播
         */
        public void start() {
            UIUtils.postTaskDelay(this, Convention.HOME_CAROUSEL_DURATION);
        }

        /**
         * 停止自动轮播
         */
        public void stop() {
            UIUtils.removeTask(this);
        }

        @Override
        public void run() {
            int item = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(item++);

            //再次开始
            start();
        }
    }

    private class PictureAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (mData != null) {
                return Integer.MAX_VALUE;
            }

            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mData.size();

            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(R.drawable.ic_default);

            BitmapHelper.display(iv, Convention.URLs.IMAGE_BASE_URL + mData.get(position));

            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
