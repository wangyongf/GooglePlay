/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: DetailProtocol						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/14       Create	
 */

package com.yongf.googleplay.protocol;

import com.google.gson.Gson;
import com.yongf.googleplay.base.BaseProtocol;
import com.yongf.googleplay.bean.AppInfoBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用详情页数据协议
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/14
 * @see
 * @since GooglePlay1.0
 */
public class DetailProtocol extends BaseProtocol<AppInfoBean> {

    private String key = "detail";
    private String mPackageName;

    public DetailProtocol(String packageName) {
        super();

        this.mPackageName = packageName;
    }

    @Override
    public String getInterfaceKey() {
        return key;
    }

    @Override
    public AppInfoBean parseJson(String result) {
        Gson gson = new Gson();

        return gson.fromJson(result, AppInfoBean.class);
    }

    @Override
    public Map<String, String> getExtraParams() {
        Map<String, String> extraParams = new HashMap<>();
        extraParams.put("packageName", mPackageName);

        return extraParams;
    }
}
