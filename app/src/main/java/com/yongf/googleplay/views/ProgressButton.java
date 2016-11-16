/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: ProgressButton						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/15       Create	
 */

package com.yongf.googleplay.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/15
 * @see
 * @since GooglePlay1.0
 */
public class ProgressButton extends Button {

    private boolean mProgressEnable;            //是否启用进度条的功能
    private long mMax = 100;            //进度条的最大值
    private long mProgress;             //当前进度
    private Drawable mProgressDrawable;         //进度条的前景图片

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgressEnable(boolean progressEnable) {
        mProgressEnable = progressEnable;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public void setMax(long max) {
        mMax = max;
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(long progress) {
        mProgress = progress;

        //刷新进度
        invalidate();
    }

    /**
     * 设置进度条的背景
     *
     * @param progressDrawable
     */
    public void setProgressDrawable(Drawable progressDrawable) {
        mProgressDrawable = progressDrawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mProgressEnable) {
            Drawable drawable = new ColorDrawable(Color.parseColor("#3E50B4"));
            int left = 0;
            int top = 0;
            int right = (int) ((mProgress * 1.0f) / mMax * getMeasuredWidth() + .5f);
            int bottom = getBottom();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }

        super.onDraw(canvas);       //绘制文本、背景
    }
}
