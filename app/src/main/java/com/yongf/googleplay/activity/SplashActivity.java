/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: SplashActivity.java						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     16-11-7        新增:Create	
 */

package com.yongf.googleplay.activity;

import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseActivity;

/**
 * 应用初始化界面
 *
 * @author Scott Wang
 * @version 1.0, 16-11-7
 * @see
 * @since GooglePlay V0.1
 */
public class SplashActivity extends BaseActivity {

    private ImageView mIvSplash;

    /**
     * Splash界面的动画效果集
     */
    private AnimationSet mAs;

    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);

        mIvSplash = (ImageView) findViewById(R.id.iv_splash);

        //开始动画
        startAnimation();
    }

    @Override
    public void initEvent() {
        super.initEvent();

        //1. 监听动画播完的事件，只是一处用的事件，采用匿名类对象；多处用到，声明成成员变量
        mAs.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //监听动画播完
                Intent guide = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(guide);

                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 开始播放动画：旋转，缩放，渐变
     */
    private void startAnimation() {
        //false 代表动画集中每一种动画都采用各自的动画插入器
        mAs = new AnimationSet(false);

        //旋转动画，锚点
        RotateAnimation ra = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f        //设置图片的锚点为图片的中心点
        );
        //设置动画的播放时间
        ra.setDuration(2000);
        ra.setFillAfter(true);      //动画播放完之后，停留在当前状态

        //添加到动画集
        mAs.addAnimation(ra);

        //渐变动画
        AlphaAnimation aa = new AlphaAnimation(0, 1);       //由完全透明到不透明
        aa.setDuration(2000);
        aa.setFillAfter(true);

        //添加到动画集
        mAs.addAnimation(aa);

        //缩放动画
        ScaleAnimation sa = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        //设置动画播放时间
        sa.setDuration(2000);
        sa.setFillAfter(true);

        //添加到动画集
        mAs.addAnimation(sa);

        //播放动画
        mIvSplash.startAnimation(mAs);
    }
}
