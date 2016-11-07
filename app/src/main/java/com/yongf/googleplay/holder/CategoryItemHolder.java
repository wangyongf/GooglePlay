/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: CategoryItemHolder						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/12       Create	
 */

package com.yongf.googleplay.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.bean.CategoryInfoBean;
import com.yongf.googleplay.conf.Convention;
import com.yongf.googleplay.util.BitmapHelper;
import com.yongf.googleplay.util.StringUtils;
import com.yongf.googleplay.util.UIUtils;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/12
 * @see
 * @since GooglePlay1.0
 */
public class CategoryItemHolder extends BaseHolder<CategoryInfoBean> {

    @ViewInject(R.id.item_category_item_1)
    LinearLayout mContainer_Item1;

    @ViewInject(R.id.item_category_item_2)
    LinearLayout mContainer_Item2;

    @ViewInject(R.id.item_category_item_3)
    LinearLayout mContainer_Item3;

    @ViewInject(R.id.item_category_icon_1)
    ImageView mIvIcon1;

    @ViewInject(R.id.item_category_icon_2)
    ImageView mIvIcon2;

    @ViewInject(R.id.item_category_icon_3)
    ImageView mIvIcon3;

    @ViewInject(R.id.item_category_name_1)
    TextView mTvName1;

    @ViewInject(R.id.item_category_name_2)
    TextView mTvName2;

    @ViewInject(R.id.item_category_name_3)
    TextView mTvName3;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_category_info, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void refreshHolderView(CategoryInfoBean data) {
        setItem(data.name1, data.url1, mTvName1, mIvIcon1);
        setItem(data.name2, data.url2, mTvName2, mIvIcon2);
        setItem(data.name3, data.url3, mTvName3, mIvIcon3);
    }

    private void setItem(final String name, String url, TextView tvName, ImageView ivIcon) {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(url)) {
            tvName.setText(name);
            ivIcon.setImageResource(R.drawable.ic_default);
            BitmapHelper.display(ivIcon, Convention.URLs.IMAGE_BASE_URL + url);
            ((ViewGroup) tvName.getParent()).setVisibility(View.VISIBLE);

            ((ViewGroup) tvName.getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ((ViewGroup) tvName.getParent()).setVisibility(View.INVISIBLE);
        }
    }
}
