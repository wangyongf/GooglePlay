/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppDetailInfoHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       新增：Create
 *  1.1         Scott Wang     2016/4/16       新增：详情页的截图展示部分基本完成
 */

package com.yongf.googleplay.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.conf.Convention;
import com.yongf.googleplay.util.BitmapHelper;
import com.yongf.googleplay.util.UIUtils;
import com.yongf.googleplay.views.RatioLayout;

import java.util.List;

/**
 * 应用截图Holder
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class AppDetailPicHolder extends BaseHolder<AppInfoBean> {

    @ViewInject(R.id.app_detail_pic_iv_container)
    LinearLayout mContainerPic;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        List<String> picUrls = data.screen;

        for (int i = 0; i < picUrls.size(); i++) {
            //控件高度等于屏幕的1/2，也就是屏幕刚好放入2张截图
            int widthPixels = UIUtils.getResources().getDisplayMetrics().widthPixels;
            widthPixels = widthPixels - mContainerPic.getPaddingLeft() - mContainerPic.getPaddingRight();
            int childWidth = widthPixels / 2;

            String url = picUrls.get(i);
            ImageView ivPic = new ImageView(UIUtils.getContext());
            ivPic.setImageResource(R.drawable.ic_default);          //默认图片
            BitmapHelper.display(ivPic, Convention.URLs.IMAGE_BASE_URL + url);

            ViewGroup.LayoutParams ivParams = new ViewGroup.LayoutParams(childWidth,
                    UIUtils.dip2px(280));
            ivPic.setLayoutParams(ivParams);

            //已知控件的宽度，和图片的宽高比，去动态地计算控件的高度
            RatioLayout ratioLayout = new RatioLayout(UIUtils.getContext());
            ratioLayout.setRatio(150 / 250);            //图片的宽高比
            ratioLayout.setRelative(RatioLayout.FIXED_WIDTH);

            ratioLayout.addView(ivPic);

            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(childWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {        //不处理第一张图片
                containerParams.leftMargin = UIUtils.dip2px(5);
            }

            mContainerPic.addView(ratioLayout, containerParams);
        }
    }
}
