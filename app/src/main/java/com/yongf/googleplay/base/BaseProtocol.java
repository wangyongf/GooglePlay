/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014 		
 * 文件名: BaseProtocol						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/10       Create
 *  1.1         Scott Wang     2016/4/15       进一步优化，允许URL自定义传参，解决缓存文件混乱
 *  1.2         Scott Wang     16-10-6           修复：将缓存json响应字符串的换行符用BufferedWriter.newLine()生成
 */

package com.yongf.googleplay.base;

import android.support.annotation.NonNull;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yongf.googleplay.conf.Convention;
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
import java.util.Map;

/**
 * 基本协议
 *
 * @author Scott Wang
 * @version 1.2, 2016/4/10
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

                if (System.currentTimeMillis() - Long.parseLong(expirationTime) < Convention.EXPIRE_TIME) {

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

        //如果是详情页    interfaceKey + "." + 包名

        Map<String, String> extraParams = getExtraParams();
        String name = null;
        if (extraParams == null) {
            name = getInterfaceKey() + "." + index;
        } else {
            //详情页
            for (Map.Entry<String, String> info : extraParams.entrySet()) {
                String key = info.getKey();             //"packageName"
                String value = info.getValue();         //值 - 包名

                name = getInterfaceKey() + "." + value;         //interfaceKey + "." + packageName
            }
        }

        return new File(dir, name);
    }

    /**
     * 返回额外参数
     *
     * @return
     * @call 默认在基类里面返回是空，但是如果子类需要返回额外的参数，复写该方法
     */
    public Map<String, String> getExtraParams() {


        return null;
    }

    private String getNetworkData(int index) throws HttpException, IOException {
        HttpUtils httpUtils = new HttpUtils();
        String url = Convention.URLs.BASE_URL + getInterfaceKey();
        RequestParams params = new RequestParams();

        Map<String, String> extraParams = getExtraParams();

        if (extraParams == null) {
            params.addQueryStringParameter("index", index + "");
        } else {
            //如果有额外的参数
            for (Map.Entry<String, String> info : extraParams.entrySet()) {
                String key = info.getKey();         //参数的key
                String value = info.getValue();         //参数的value

                params.addQueryStringParameter(key, value);
            }
        }

        ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);
        String jsonString = responseStream.readString();

        /////// ------------------- 保存网络数据到本地 ------------------- ///////

        File cacheFile = getCacheFile(index);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(cacheFile));
            writer.write(System.currentTimeMillis() + "");          //第一行写入插入时间，注意，这里的换行符
            writer.newLine();
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
