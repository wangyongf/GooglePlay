/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: GameProtocol						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/11       Create	
 */

package com.yongf.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yongf.googleplay.base.BaseProtocol;
import com.yongf.googleplay.bean.AppInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/11
 * @see
 * @since GooglePlay1.0
 */
public class GameProtocol extends BaseProtocol<List<AppInfoBean>> {


    @Override
    public String getInterfaceKey() {
        return "game";
    }

    @Override
    public List<AppInfoBean> parseJson(String result) {
        Gson gson = new Gson();

        /////// ------------------- 泛型解析 ------------------- ///////

//        return gson.fromJson(result, new TypeToken<List<AppInfoBean>>() {
//        }.getType());

        /////// ------------------- 结点解析 ------------------- ///////

        List<AppInfoBean> appInfoBeans = new ArrayList<>();

        //1. 获得json解析器
        JsonParser parser = new JsonParser();

        JsonElement rootJsonElement = parser.parse(result);

        //JsonElement ==> JsonArray
        JsonArray rootJsonArray = rootJsonElement.getAsJsonArray();

        //遍历JsonArray
        for (JsonElement itemJsonElement : rootJsonArray) {
            //JsonElement ==> JsonObject
            JsonObject itemJsonObject = itemJsonElement.getAsJsonObject();

//            JsonPrimitive desPrimitive = itemJsonObject.getAsJsonPrimitive("des");
//
//            //这样，就获取了des这个属性
//            String des = desPrimitive.getAsString();

            String des = itemJsonObject.get("des").getAsString();
            String downloadUrl = itemJsonObject.get("downloadUrl").getAsString();
            String iconUrl = itemJsonObject.get("iconUrl").getAsString();
            long id = itemJsonObject.get("id").getAsLong();
            String name = itemJsonObject.get("name").getAsString();
            String packageName = itemJsonObject.get("packageName").getAsString();
            long size = itemJsonObject.get("size").getAsLong();
            float stars = itemJsonObject.get("stars").getAsFloat();

            AppInfoBean infoBean = new AppInfoBean();
            infoBean.des = des;
            infoBean.downloadUrl = downloadUrl;
            infoBean.iconUrl = iconUrl;
            infoBean.id = id;
            infoBean.name = name;
            infoBean.packageName = packageName;
            infoBean.size = size;
            infoBean.stars = stars;

            appInfoBeans.add(infoBean);
        }

        //返回结果
        return appInfoBeans;
    }
}
