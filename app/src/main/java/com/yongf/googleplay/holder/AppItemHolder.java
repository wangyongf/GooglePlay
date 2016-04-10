/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/5       Create	
 */

package com.yongf.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.utils.BitmapHelper;
import com.yongf.googleplay.utils.StringUtils;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 主页Holder
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/5
 * @see
 * @since GooglePlay1.0
 */
public class AppItemHolder extends BaseHolder<AppInfoBean> {

    @ViewInject(R.id.item_appinfo_iv_icon)
    ImageView mIvIcon;

    @ViewInject(R.id.item_appinfo_rb_stars)
    RatingBar mRbStar;

    @ViewInject(R.id.item_appinfo_tv_des)
    TextView mTvDes;

    @ViewInject(R.id.item_appinfo_tv_size)
    TextView mTvSize;

    @ViewInject(R.id.item_appinfo_tv_title)
    TextView mTvTitle;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);

        //注入
        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        mTvDes.setText(data.des);
        mTvSize.setText(StringUtils.formatFileSize(data.size));
        mTvTitle.setText(data.name);

        mIvIcon.setImageResource(R.drawable.ic_default);

//        BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
        String uri = Constants.URLs.IMAGE_BASE_URL + data.iconUrl;
        BitmapHelper.display(mIvIcon, uri);

        mRbStar.setRating(data.stars);
    }
}
