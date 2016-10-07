/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: AppDetailInfoHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       新增：Create
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
import com.yongf.googleplay.conf.Convention;
import com.yongf.googleplay.utils.BitmapHelper;
import com.yongf.googleplay.utils.StringUtils;
import com.yongf.googleplay.utils.UIUtils;

/**
 * 应用相关信息Holder（版本、时间、下载量、评分等）
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class AppDetailInfoHolder extends BaseHolder<AppInfoBean> {

    @ViewInject(R.id.app_detail_info_iv_icon)
    ImageView mIvIcon;

    @ViewInject(R.id.app_detail_info_rb_star)
    RatingBar mRbStar;

    @ViewInject(R.id.app_detail_info_tv_downloadnum)
    TextView mTvDownLoadNum;

    @ViewInject(R.id.app_detail_info_tv_name)
    TextView mTvName;

    @ViewInject(R.id.app_detail_info_tv_time)
    TextView mTvTime;

    @ViewInject(R.id.app_detail_info_tv_version)
    TextView mTvVersion;

    @ViewInject(R.id.app_detail_info_tv_size)
    TextView mTvSize;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_info, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void refreshHolderView(AppInfoBean data) {
        String date = UIUtils.getString(R.string.detail_date, data.date);
        String downloadNum = UIUtils.getString(R.string.detail_downloadnum, data.downloadNum);
        String size = UIUtils.getString(R.string.detail_size, StringUtils.formatFileSize(data.size));
        String version = UIUtils.getString(R.string.detail_version, data.version);

        mTvDownLoadNum.setText(downloadNum);
        mTvName.setText(data.name);
        mTvTime.setText(date);
        mTvVersion.setText(version);
        mTvSize.setText(size);

        mIvIcon.setImageResource(R.drawable.ic_default);
        BitmapHelper.display(mIvIcon, Convention.URLs.IMAGE_BASE_URL + data.iconUrl);

        mRbStar.setRating(data.stars);
    }
}
