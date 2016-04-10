/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseProtocol						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/10       Create	
 */

package com.yongf.googleplay.base;

import android.support.annotation.NonNull;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yongf.googleplay.conf.Constants;
import com.yongf.googleplay.utils.FileUtils;
import com.yongf.googleplay.utils.IOUtils;
import com.yongf.googleplay.utils.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 基本协议
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/10
 * @see
 * @since GooglePlay1.0
 */
public abstract class BaseProtocol<T> {

    public T loadData(int index) throws HttpException, IOException {

        /////// ------------------- 读取本地缓存 ------------------- ///////

        T localBean = getLocalData(index);
        if (localBean != null) {
            LogUtils.sf("读取本地文件" + getCacheFile(index).getAbsolutePath());
            return localBean;
        }

        /////// ------------------- 发送网络请求 ------------------- ///////

        String result = getNetworkData(index);

        /////// ------------------- json解析 ------------------- ///////

        //json解析
//        Gson gson = new Gson();
        T baseBean = parseJson(result);

        return baseBean;
    }

    private T getLocalData(int index) {
        File cachFile = getCacheFile(index);
        if (cachFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cachFile));

                //读取第一行
                String expirationTime = reader.readLine();

                if (System.currentTimeMillis() - Long.parseLong(expirationTime) < Constants.EXPIRE_TIME) {

                    //读取缓存内容
                    String jsonString = reader.readLine();

                    return parseJson(jsonString);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }

        return null;
    }

    @NonNull
    private File getCacheFile(int index) {
        String dir = FileUtils.getDir("json");
        String name = getInterfaceKey() + "." + index;
        return new File(dir, name);
    }

    private String getNetworkData(int index) throws HttpException, IOException {
        HttpUtils httpUtils = new HttpUtils();
        String url = Constants.URLs.BASE_URL + getInterfaceKey();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("index", index + "");

        ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);
        String jsonString = responseStream.readString();

        /////// ------------------- 保存网络数据到本地 ------------------- ///////

        File cacheFile = getCacheFile(index);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(cacheFile));
            writer.write(System.currentTimeMillis() + "\r\n");          //第一行写入插入时间
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }

        return jsonString;
    }

    public abstract String getInterfaceKey();

    public abstract T parseJson(String result);
}
