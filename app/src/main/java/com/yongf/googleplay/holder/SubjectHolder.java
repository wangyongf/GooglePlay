/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: SubjectHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/11       Create	
 */

package com.yongf.googleplay.holder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.SubjectInfoBean;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.utils.BitmapHelper;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 专题页面Holder
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/11
 * @see
 * @since GooglePlay1.0
 */
public class SubjectHolder extends BaseHolder<SubjectInfoBean> {

    @ViewInject(R.id.item_subject_iv_icon)
    ImageView mIvIcon;

    @ViewInject(R.id.item_subject_tv_title)
    TextView mTvTitle;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void refreshHolderView(SubjectInfoBean data) {
        Typeface typeface = Typeface.createFromAsset(UIUtils.getAssets(), "font/qitijianti.ttf");
        mTvTitle.setTypeface(typeface);
        mTvTitle.setText(data.des);

        mIvIcon.setImageResource(R.drawable.ic_default);

        BitmapHelper.display(mIvIcon, Constants.URLs.IMAGE_BASE_URL + data.url);
    }
}
