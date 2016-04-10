/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/3       Create
 *  1.1         Scott Wang     2016/4/5       模拟首页数据(ListView)
 *  1.2         Scott Wang     2016/4/10     正式从服务器获取数据
 */

package com.yongf.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.base.SuperBaseAdapter;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.bean.HomeBean;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.holder.HomeHolder;
import com.yongf.googleplay.utils.UIUtils;

import java.io.IOException;
import java.util.List;

/**
 * 主界面Fragment
 *
 * @author Scott Wang
 * @version 1.2, 2016/4/4
 * @see
 * @since GooglePlay1.0
 */
public class HomeFragment extends BaseFragment {

    private List<AppInfoBean> mData;            //listView的数据源
    private List<String> pictures;          //轮播图

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
        try {
            //发送网络请求
            HttpUtils httpUtils = new HttpUtils();
            String url = Constants.URLs.BASE_URL + "home";
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("index", "0");

            ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);
            String result = responseStream.readString();

            //json解析
            Gson gson = new Gson();
            HomeBean homeBean = gson.fromJson(result, HomeBean.class);

            LoadingPager.LoadedResult state = checkState(homeBean);
            if (state != LoadingPager.LoadedResult.SUCCESS) {       //如果不成功，直接返回 homeBean ok
                return state;
            }

            state = checkState(homeBean.list);
            if (state != LoadingPager.LoadedResult.SUCCESS) {       //如果不成功，就直接返回 homeBean.list ok
                return state;
            }

            mData = homeBean.list;
            pictures = homeBean.picture;

            return LoadingPager.LoadedResult.SUCCESS;
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LoadingPager.LoadedResult.ERROR;
    }

    private class HomeAdapter extends SuperBaseAdapter<AppInfoBean> {
        public HomeAdapter(List<AppInfoBean> dataSource) {
            super(dataSource);
        }

        @Override
        public BaseHolder<AppInfoBean> getSpecialHolder() {
            return new HomeHolder();
        }
    }
}
