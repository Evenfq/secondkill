package com.fanqiao.secondkill.util;


//import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Copyright (C), 2018-2021, 深圳小财迷信息科技有限公司
 * FileName: HttpClientUtils
 * Author:   xiaojian
 * Date:     2019/1/16 16:24
 * Description:
 *
 * @since 1.0.0
 */
@Slf4j
public class HttpClientUtils {

    public static String doPost(String url, JSONObject json){
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            StringEntity entity = new StringEntity(json.toString(), "utf-8");
            //entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json"); // 发送json数据需要设置contentType
            post.setEntity(entity);
            response = httpClient.execute(post);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                // 返回json格式
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("通讯异常，异常信息:[{}]", e);
        }
        finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}