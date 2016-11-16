/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: CircleProgressView						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/17       新增：Create	
 */

package com.yongf.googleplay.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongf.googleplay.R;
import com.yongf.googleplay.util.UIUtils;

/**
 * 自定义圆形进度条
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/17
 * @see
 * @since GooglePlay1.0
 */
public class CircleProgressView extends LinearLayout {

    private ImageView mIcon;
    private TextView mText;

    private boolean mProgressEnable = true;
    private long mMax = 100;
    private long mProgress = 0;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.circle_progress_view, this);

        mIcon = (ImageView) view.findViewById(R.id.cpv_iv_icon);
        mText = (TextView) view.findViewById(R.id.cpv_tv_des);
    }

    /**
     * 是否显示进度
     *
     * @param progressEnable
     */
    public void setProgressEnable(boolean progressEnable) {
        mProgressEnable = progressEnable;
    }

    /**
     * 设置最大值
     *
     * @param max
     */
    public void setMax(long max) {
        mMax = max;
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(long progress) {
        mProgress = progress;

        invalidate();           //执行onDraw()方法
    }

    /**
     * 设置图标
     *
     * @param resId
     */
    public void setIcon(int resId) {
        mIcon.setImageResource(resId);
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        mText.setText(text);
    }

    /////// ------------------- onDraw, onMeasure, onLayout ------------------- ///////

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);         //绘制具体的内容（图片和文字）

        if (mProgressEnable) {
            RectF oval = new RectF(mIcon.getLeft(), mIcon.getTop(), mIcon.getRight(), mIcon.getBottom());
            float startAngle = -90;

            float sweepAngle = mProgress * 360.f / mMax;

            boolean useCenter = false;          //是否保留两边的半径
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(UIUtils.dip2px(3));
            paint.setAntiAlias(true);           //消除锯齿
            canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
        }
        
    }
}
