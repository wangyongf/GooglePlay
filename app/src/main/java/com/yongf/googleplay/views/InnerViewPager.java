/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: InnerViewPager						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/11       Create	
 */

package com.yongf.googleplay.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决嵌套ViewPager的触摸拦截问题
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/11
 * @see
 * @since GooglePlay1.0
 */
public class InnerViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;
    private float mMoveX;
    private float mMoveY;

    public InnerViewPager(Context context) {
        super(context);
    }

    public InnerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 左右滑动，自己处理
     * 上下滑动，父容器处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = ev.getRawX();
                mMoveY = ev.getRawY();

                float dx = mMoveX - mDownX;
                float dy = mMoveY - mDownY;
                /////// ------------------- 如果左右移动的绝对值 > 上下移动的绝对值 ------------------- ///////
                if (Math.abs(dx) > Math.abs(dy)) {
                    //左右滚动，自己处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:


                break;
        }

        return super.onTouchEvent(ev);
    }
}
