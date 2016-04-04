/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/5       模拟首页数据(ListView)
 */

package com.yongf.googleplay.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yongf.googleplay.R;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.base.SuperBaseAdapter;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面Fragment
 *
 * @author Scott Wang
 * @version 1.1, 2016/4/4
 * @see
 * @since GooglePlay1.0
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private List<String> mData;

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        //返回成功的视图
        ListView listView = new ListView(UIUtils.getContext());

        //简单的设置
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFastScrollEnabled(true);

        //设置adapter
        listView.setAdapter(new HomeAdapter(mData));

        return listView;
    }

    /**
     * 真正加载数据，执行耗时操作
     *
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(i + "");
        }

        //执行耗时操作
        SystemClock.sleep(Constants.SLEEP_DURATION);

        return LoadingPager.LoadedResult.SUCCESS;
    }

    private class HomeAdapter extends SuperBaseAdapter<String> {
        public HomeAdapter(List<String> dataSource) {
            super(dataSource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                //初始化holder
                holder = new ViewHolder();
                //初始化根布局
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_tmp, null);
                //绑定Tag
                convertView.setTag(holder);
                //初始化子对象
                holder.mTvTmp1 = (TextView) convertView.findViewById(R.id.tmp_tv_1);
                holder.mTvTmp2 = (TextView) convertView.findViewById(R.id.tmp_tv_2);
            } else {
                //直接从convertView中取得holder
                holder = (ViewHolder) convertView.getTag();
            }

            //得到数据
            String data = mData.get(position);
            //刷新显示
            holder.mTvTmp1.setText("我是1：" + data);
            holder.mTvTmp2.setText("我是2：" + data);

            //返回itemView
            return convertView;
        }

        class ViewHolder {
            TextView mTvTmp1;
            TextView mTvTmp2;
        }
    }
}
