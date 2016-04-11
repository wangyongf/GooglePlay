/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: SubjectProtocol						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/11       Create	
 */

package com.yongf.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yongf.googleplay.base.BaseProtocol;
import com.yongf.googleplay.bean.SubjectInfoBean;

import java.util.List;

/**
 * 专题页面获取网络数据的协议
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/11
 * @see
 * @since GooglePlay1.0
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfoBean>> {

    private String key = "subject";

    @Override
    public String getInterfaceKey() {
        return key;
    }

    @Override
    public List<SubjectInfoBean> parseJson(String result) {
        Gson gson = new Gson();

        return gson.fromJson(result, new TypeToken<List<SubjectInfoBean>>() {
        }.getType());
    }
}
