/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppDetailInfoHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       新增：Create
 *  1.1         Scott Wang     2016/4/16       新增：详情页的应用描述基本完成
 */

package com.yongf.googleplay.holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 应用描述Holder
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class AppDetailDesHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener {

    @ViewInject(R.id.app_detail_des_tv_author)
    TextView mTvAuthor;

    @ViewInject(R.id.app_detail_des_iv_arrow)
    ImageView mIvArrow;

    @ViewInject(R.id.app_detail_des_tv_des)
    TextView mTvDes;

    private boolean isOpen = true;
    private int mTvDesMeasuredHeight;
    private AppInfoBean mData;
    private long mDuration;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_des, null);

        ViewUtils.inject(this, view);

        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        mData = data;

        mTvAuthor.setText(data.author);
        mTvDes.setText(data.des);

        mTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTvDesMeasuredHeight = mTvDes.getMeasuredHeight();

                //默认折叠
                toggle(0);

                //如果不移除，高度变化的时候，Height又会随之改变
                mTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        toggle(300);
    }

    private void toggle(int duration) {
        mDuration = duration;
        if (isOpen) {       //折叠
            int start = mTvDesMeasuredHeight;
            int end = getShortHeight(7, mData);

            startDesAnimation(duration, start, end);
        } else {            //展开
            int start = mTvDesMeasuredHeight;
            int end = getShortHeight(7, mData);

            startDesAnimation(duration, end, start);
        }

        //箭头的动画
        if (duration > 0) {
            if (isOpen) {
                startArrowAnimation(duration, 180, 0);
            } else {
                startArrowAnimation(duration, 0, 180);
            }
        }

        isOpen = !isOpen;
    }

    /**
     * 箭头部分动画
     *
     * @param duration
     */
    private void startArrowAnimation(int duration, int start, int end) {
        ObjectAnimator.ofFloat(mIvArrow, "rotation", start, end).setDuration(duration).start();
    }

    /**
     * 应用描述部分的动画
     *
     * @param duration
     * @param start
     * @param end
     */
    private void startDesAnimation(int duration, int start, int end) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mTvDes, "height", start, end).setDuration(duration);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (0 == mDuration) {
                    return;
                }

                ViewParent parent = mTvDes.getParent();
                while (true) {
                    parent = parent.getParent();
                    if (parent == null) {       //已经没有父亲
                        break;
                    }
                    if (parent instanceof ScrollView) {     //已经找到ScrollView
                        ((ScrollView) parent).fullScroll(View.FOCUS_DOWN);
                        break;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 指定行高
     *
     * @param index 指定textview的内容所占行数
     * @param data
     * @return
     */
    private int getShortHeight(int index, AppInfoBean data) {
        TextView tempTextView = new TextView(UIUtils.getContext());
        tempTextView.setLines(index);
        tempTextView.setText(data.des);

        tempTextView.measure(0, 0);

        int measuredHeight = tempTextView.getMeasuredHeight();

        return measuredHeight;
    }
}
