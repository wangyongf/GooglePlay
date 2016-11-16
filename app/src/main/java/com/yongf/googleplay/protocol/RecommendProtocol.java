/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: RecommentProtocol						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/13       Create	
 */

package com.yongf.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yongf.googleplay.base.BaseProtocol;

import java.util.List;

/**
 * ${description}
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/13
 * @see
 * @since GooglePlay1.0
 */
public class RecommendProtocol extends BaseProtocol<List<String>> {

    private String key = "recommend";

    @Override
    public String getInterfaceKey() {
        return key;
    }

    @Override
    public List<String> parseJson(String result) {
        Gson gson = new Gson();

        return gson.fromJson(result, new TypeToken<List<String>>() {
        }.getType());
    }
}
