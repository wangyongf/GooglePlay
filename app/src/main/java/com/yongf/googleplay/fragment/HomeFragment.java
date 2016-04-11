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

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.yongf.googleplay.base.BaseFragment;
import com.yongf.googleplay.base.BaseHolder;
import com.yongf.googleplay.base.LoadingPager;
import com.yongf.googleplay.base.SuperBaseAdapter;
import com.yongf.googleplay.bean.AppInfoBean;
import com.yongf.googleplay.bean.HomeBean;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.factory.ListViewFactory;
import com.yongf.googleplay.holder.AppItemHolder;
import com.yongf.googleplay.protocol.HomeProtocol;
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
    private HomeProtocol mProtocol;

    /**
     * 返回成功的视图
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        //返回成功的视图
        ListView listView = ListViewFactory.getListView();

//        //创建一个PictureHolder  轮播图
//        PictureHolder pictureHolder = new PictureHolder();
//        View headerView = pictureHolder.getHolderView();
//        listView.addHeaderView(headerView);

        //设置adapter
        listView.setAdapter(new HomeAdapter(listView, mData));

        return listView;
    }

    /**
     * 真正加载数据，执行耗时操作
     *
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initData() {
//        try {
//            //发送网络请求
//            HttpUtils httpUtils = new HttpUtils();
//            String url = Constants.URLs.BASE_URL + "home";
//            RequestParams params = new RequestParams();
//            params.addQueryStringParameter("index", "0");
//
//            ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);
//            String result = responseStream.readString();
//
//            //json解析
//            Gson gson = new Gson();
//            HomeBean homeBean = gson.fromJson(result, HomeBean.class);

        /////// ------------------- 协议简单封装之后 ------------------- ///////

        mProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mProtocol.loadData(0);

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

    private List<AppInfoBean> loadMore(int index) throws HttpException, IOException {
//        //联网加载更多数据
//        //发送网络请求
//        HttpUtils httpUtils = new HttpUtils();
//        String url = Constants.URLs.BASE_URL + "home";
//        RequestParams params = new RequestParams();
//        params.addQueryStringParameter("index", index + "");
//
//        ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);
//        String result = responseStream.readString();
//
//        //json解析
//        Gson gson = new Gson();
//        HomeBean homeBean = gson.fromJson(result, HomeBean.class);

        /////// ------------------- 协议简单封装之后 ------------------- ///////

        HomeBean homeBean = mProtocol.loadData(index);

        if (null == homeBean) {
            return null;
        } else if (null == homeBean.list || 0 == homeBean.list.size()) {
            return null;
        }

        return homeBean.list;
    }

    private class HomeAdapter extends SuperBaseAdapter<AppInfoBean> {

        public HomeAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public BaseHolder<AppInfoBean> getSpecialHolder() {
            return new AppItemHolder();
        }

        @Override
        public List<AppInfoBean> onLoadMore() throws Exception {
            //休眠一下
            SystemClock.sleep(Constants.LOADING_MORE_DURATION);

            return loadMore(mData.size());
        }

        @Override
        public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(UIUtils.getContext(), mData.get(position).packageName, Toast.LENGTH_SHORT).show();
        }
    }
}
