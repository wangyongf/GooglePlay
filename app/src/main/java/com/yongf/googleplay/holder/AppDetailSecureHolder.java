/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppDetailInfoHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       Create	
 */

package com.yongf.googleplay.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.utils.BitmapHelper;
import com.yongf.googleplay.utils.UIUtils;

import java.util.List;

/**
 * 应用安全描述部分Holder
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class AppDetailSecureHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener {

    @ViewInject(R.id.app_detail_safe_des_container)
    LinearLayout mContainerDes;

    @ViewInject(R.id.app_detail_safe_pic_container)
    LinearLayout mContainerPic;

    @ViewInject(R.id.app_detail_safe_iv_arrow)
    ImageView mIvArrow;

    private boolean isOpen = true;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_secure, null);

        ViewUtils.inject(this, view);

        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        List<AppInfoBean.AppInfoSafeBean> safeBeans = data.safe;
        for (AppInfoBean.AppInfoSafeBean appInfoSafeBean : safeBeans) {
            ImageView ivIcon = new ImageView(UIUtils.getContext());

            BitmapHelper.display(ivIcon, Constants.URLs.IMAGE_BASE_URL + appInfoSafeBean.safeUrl);
            mContainerPic.addView(ivIcon);

            LinearLayout ll = new LinearLayout(UIUtils.getContext());
            //描述图标
            ImageView ivDes = new ImageView(UIUtils.getContext());
            BitmapHelper.display(ivDes, Constants.URLs.IMAGE_BASE_URL + appInfoSafeBean.safeDesUrl);
            //描述内容
            TextView tvDes = new TextView(UIUtils.getContext());
            tvDes.setText(appInfoSafeBean.safeDes);

            //加点间距
            int padding = UIUtils.dip2px(5);
            ll.setPadding(padding, padding, padding, padding);

            ll.addView(ivDes);
            ll.addView(tvDes);

            mContainerDes.addView(ll);
        }

        //默认折叠
        toggle(0);
    }

    @Override
    public void onClick(View v) {
        toggle(300);
    }

    /**
     * 切换
     */
    private void toggle(int duration) {
        if (isOpen) {
            //折叠

            /**
             * mContainerDes高度发生变化
             * 应有高度  => 0
             */

//            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mContainerDes.measure(0, 0);
            int measuredHeight = mContainerDes.getMeasuredHeight();
            int start = measuredHeight;     //动画开始高度
            int end = 0;            //动画结束高度

            startAnimation(start, end, duration);

        } else {
            //展开

            /**
             * mContainerDes高度发生变化
             * 0  => 应有高度
             */
            mContainerDes.measure(0, 0);
            int measuredHeight = mContainerDes.getMeasuredHeight();
            int end = measuredHeight;     //动画开始高度
            int start = 0;            //动画结束高度

            startAnimation(start, end, duration);

        }

        //箭头的旋转动画
        if (duration != 0) {
            if (isOpen) {
                ObjectAnimator rotation = ObjectAnimator.ofFloat(mIvArrow, "rotation", 180, 0);
                rotation.start();
            } else {
                ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180).start();
            }
        }
        isOpen = !isOpen;
    }

    private void startAnimation(int start, int end, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(duration);
        animator.start();           //默认duration是300ms
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();

                //修改高度 -- 通过LayoutParam
                ViewGroup.LayoutParams params = mContainerDes.getLayoutParams();
                params.height = height;
                mContainerDes.setLayoutParams(params);
            }
        });
    }
}
