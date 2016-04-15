/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: RatioLayout						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/11       Create	
 */

package com.yongf.googleplay.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.yongf.googleplay.R;

/**
 * 动态计算自身高（宽）度，以适应包裹的图片
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/11
 * @see
 * @since GooglePlay1.0
 */
public class RatioLayout extends FrameLayout {

    public static final int FIXED_WIDTH = 0;         //控件宽度固定，已知图片的宽高比，求控件的高度
    public static final int FIXED_HEIGHT = 1;        //控件高度固定，已知图片的宽高比，求控件的宽度

    private int mRelative = FIXED_WIDTH;
    //宽高比
    private float mRatio;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

        mRatio = typedArray.getFloat(R.styleable.RatioLayout_ratio, 0);

        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, FIXED_WIDTH);

        typedArray.recycle();
    }

    public void setRatio(int ratio) {
        mRatio = ratio;
    }

    public void setRelative(int relative) {
        mRelative = relative;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //宽度固定，已知图片的宽高比，动态改变控件的高度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        //高度固定，已知图片的宽高比，动态改变控件的宽度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && mRatio != 0 && mRelative == FIXED_WIDTH) {
            //宽度固定，已知图片的宽高比，动态改变控件的高度

            //得到自身的宽度
            int width = MeasureSpec.getSize(widthMeasureSpec);

            //得到孩子的宽度
            int childWidth = width - getPaddingLeft() - getPaddingRight();

            //控件宽度 / 控件高度 = mRatio

            //计算孩子的高度
            int childHeight = (int) (childWidth / mRatio + .5f);

            //计算自身的高度
            int height = childHeight + getPaddingTop() + getPaddingBottom();

            //主动测绘孩子，控制孩子的大小
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            //设置自己的测绘结果
            setMeasuredDimension(width, height);
        } else if (heightMode == MeasureSpec.EXACTLY && mRatio != 0 && mRelative == FIXED_HEIGHT) {
            //高度固定，已知图片的宽高比，动态改变控件的宽度

            //得到自身的高度
            int height = MeasureSpec.getSize(heightMeasureSpec);

            //得到孩子的高度
            int childHeight = height - getPaddingBottom() - getPaddingTop();

            //计算控件的宽度
            int childWidth = (int) (height * mRatio + .5f);

            //得到自身的宽度
            int width = childWidth + getPaddingLeft() + getPaddingRight();

            //主动测绘孩子，控制孩子的大小
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            //设置自己的测绘结果
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
