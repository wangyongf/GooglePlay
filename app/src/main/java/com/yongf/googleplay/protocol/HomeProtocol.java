/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: HomeProtocol						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/10       Create	
 */

package com.yongf.googleplay.protocol;

import com.google.gson.Gson;
import com.yongf.googleplay.base.BaseProtocol;
import com.yongf.googleplay.bean.HomeBean;

/**
 * 主页Home联网获取数据
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/10
 * @see
 * @since GooglePlay1.0
 */
public class HomeProtocol extends BaseProtocol<HomeBean> {

    private String interfaceKey = "home";

    @Override
    public String getInterfaceKey() {
        return this.interfaceKey;
    }

    @Override
    public HomeBean parseJson(String result) {
        Gson gson = new Gson();

        return gson.fromJson(result, HomeBean.class);
    }
}
