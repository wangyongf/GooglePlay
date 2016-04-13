/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create	
 */

package com.yongf.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.protocol.HotProtocol;
import com.yongf.googleplay.utils.UIUtils;
import com.yongf.googleplay.views.FlowLayout;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * 热门页面
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class HotFragment extends BaseFragment {

    private List<String> mData;

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        //返回成功的视图
        ScrollView sv = new ScrollView(UIUtils.getContext());

        //创建流式布局
        FlowLayout fl = new FlowLayout(UIUtils.getContext());

        //往流式布局中添加ITEM
        for (final String data : mData) {
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(data);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(16);
            tv.setGravity(Gravity.CENTER);

            int padding = UIUtils.dip2px(5);
            tv.setPadding(padding, padding, padding, padding);

            /////// ------------------- normalDrawble start ------------------- ///////

//            tv.setBackgroundResource(R.drawable.shape_hot_fl_tv);
            GradientDrawable normalDrawble = new GradientDrawable();

            //得到随机颜色
            Random random = new Random();

            int alpha = 255;
            int red = random.nextInt(190) + 30;     //30-220
            int green = random.nextInt(190) + 30;          //30-220
            int blue = random.nextInt(190) + 30;               //30-220
            int argb = Color.argb(alpha, red, green, blue);

            //设置填充颜色
            normalDrawble.setColor(argb);
            //设置圆角半径
            normalDrawble.setCornerRadius(UIUtils.dip2px(6));

            /////// ------------------- normalDrawble end ------------------- ///////

            /////// ------------------- pressedDrawble begin ------------------- ///////

            GradientDrawable pressedDrawble = new GradientDrawable();
            pressedDrawble.setColor(Color.DKGRAY);
            pressedDrawble.setCornerRadius(UIUtils.dip2px(6));

            /////// ------------------- pressedDrawble end ------------------- ///////

            //设置一个状态图片
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawble);
            stateListDrawable.addState(new int[]{}, normalDrawble);

            tv.setBackgroundDrawable(stateListDrawable);

            tv.setClickable(true);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), data, Toast.LENGTH_SHORT).show();
                }
            });

            fl.addView(tv);
        }

        sv.addView(fl);

        return sv;
    }

    /**
     * 真正加载数据，执行耗时操作
     *
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        //执行耗时操作
        HotProtocol protocol = new HotProtocol();

        try {
            mData = protocol.loadData(0);

            return checkState(mData);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LoadingPager.LoadedResult.ERROR;
    }
}
