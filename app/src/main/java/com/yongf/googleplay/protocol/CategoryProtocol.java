/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: CategoryProtocol						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/12       Create	
 */

package com.yongf.googleplay.protocol;

import com.yongf.googleplay.base.BaseProtocol;
import com.yongf.googleplay.bean.CategoryInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/12
 * @see
 * @since GooglePlay1.0
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>> {

    private String key = "category";

    @Override
    public String getInterfaceKey() {
        return key;
    }

    @Override
    public List<CategoryInfoBean> parseJson(String result) {
        /////// ------------------- Android SDK自带的JSON解析 ------------------- ///////
        List<CategoryInfoBean> categoryInfoBeanList = new ArrayList<>();

        try {
            JSONArray rootJsonArray = new JSONArray(result);

            //遍历jsonArray
            for (int i = 0; i < rootJsonArray.length(); i++) {
                JSONObject itemJsonObject = rootJsonArray.getJSONObject(i);

                String title = itemJsonObject.getString("title");
                CategoryInfoBean titleBean = new CategoryInfoBean();
                titleBean.title = title;
                titleBean.isTitle = true;

                categoryInfoBeanList.add(titleBean);

                JSONArray infos = itemJsonObject.getJSONArray("infos");
                //遍历infos
                for (int j = 0; j < infos.length(); j++) {
                    JSONObject infosJSONObject = infos.getJSONObject(j);
                    String name1 = infosJSONObject.getString("name1");
                    String name2 = infosJSONObject.getString("name2");
                    String name3 = infosJSONObject.getString("name3");
                    String url1 = infosJSONObject.getString("url1");
                    String url2 = infosJSONObject.getString("url2");
                    String url3 = infosJSONObject.getString("url3");

                    CategoryInfoBean infoBean = new CategoryInfoBean();
                    infoBean.name1 = name1;
                    infoBean.name2 = name2;
                    infoBean.name3 = name3;
                    infoBean.url1 = url1;
                    infoBean.url2 = url2;
                    infoBean.url3 = url3;
                    infoBean.isTitle = false;

                    categoryInfoBeanList.add(infoBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categoryInfoBeanList;
    }
}
