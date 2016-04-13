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
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.protocol.RecommendProtocol;
import com.yongf.googleplay.utils.UIUtils;
import com.yongf.googleplay.views.flyout.ShakeListener;
import com.yongf.googleplay.views.flyout.StellarMap;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * 推荐页面
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/3
 * @see
 * @since GooglePlay1.0
 */
public class RecommendFragment extends BaseFragment {

    private List<String> mData;
    private RecommendAdapter mAdapter;
    private ShakeListener mShakeListener;

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());

        mAdapter = new RecommendAdapter();

        //设置适配器
        stellarMap.setAdapter(mAdapter);

        //设置第一页的时候显示
        stellarMap.setGroup(0, true);
        //设置把屏幕拆分成多少个格子
        stellarMap.setRegularity(15, 20);       //300个格子

        //加入摇一摇
        mShakeListener = new ShakeListener(UIUtils.getContext());

        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                int groupIndex = stellarMap.getCurrentGroup();
                if (groupIndex == mAdapter.getGroupCount() - 1) {
                    groupIndex = 0;
                } else {
                    groupIndex++;
                }
                stellarMap.setGroup(groupIndex, true);
            }
        });

        return stellarMap;
    }

    @Override
    public void onResume() {
        if (mShakeListener != null) {
            mShakeListener.resume();
        }

        super.onResume();
    }

    @Override
    public void onPause() {
        if (mShakeListener != null) {
            mShakeListener.pause();
        }

        super.onPause();
    }

    /**
     * 真正加载数据，执行耗时操作
     *
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        RecommendProtocol protocol = new RecommendProtocol();

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

    private class RecommendAdapter implements StellarMap.Adapter {

        private static final int PAGER_SIZE = 15;           //每页显示多少条数据

        @Override
        public int getGroupCount() {        //有多少组
            int groupCount = mData.size() / PAGER_SIZE;
            if (mData.size() % PAGER_SIZE > 0) {
                groupCount++;
            }

            return groupCount;
        }

        @Override
        public int getCount(int group) {        //每组有多少条数据
            if (group == getGroupCount() - 1) {
                //是否有余数
                if (mData.size() % PAGER_SIZE > 0) {        //有余数
                    return mData.size() % PAGER_SIZE;
                }
            }

            return PAGER_SIZE;
        }

        @Override
        public View getView(int group, int position, View convertView) {        //返回具体的view
            TextView tv = new TextView(UIUtils.getContext());

            //group 代表第几组 position 代表第几组中的第几个位置

            int index = group * PAGER_SIZE + position;

            tv.setText(mData.get(index));

            Random random = new Random();

            //随机大小
            tv.setTextSize(random.nextInt(6) + 15);         //15 - 21

            //随机颜色
            int alpha = 255;
            int red = random.nextInt(180) + 30;         //30 - 210
            int green = random.nextInt(180) + 30;
            int blue = random.nextInt(180) + 30;
            int argb = Color.argb(alpha, red, green, blue);
            tv.setTextColor(argb);

            //设置紧密程度
            int padding = UIUtils.dip2px(5);
            tv.setPadding(padding, padding, padding, padding);

            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
