package com.fanqiao.secondkill.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class HttpUtil {
    public static String get(String url) {
        String body = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            log.info("create httppost:" + url);
            HttpGet get = new HttpGet(url);
            get.addHeader("Accept-Charset","utf-8");
            HttpResponse response = sendRequest(httpClient, get);
            body = parseResponse(response);
        } catch (IOException e) {
            log.error("send post request failed: {}", e.getMessage());
        }

        return body;
    }


    private static HttpResponse sendRequest(CloseableHttpClient httpclient, HttpUriRequest httpost)
            throws ClientProtocolException, IOException {
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        return response;
    }

    private static String parseResponse(HttpResponse response) {
        log.info("get response from http server..");
        HttpEntity entity = response.getEntity();

        log.info("response status: " + response.getStatusLine());
        Charset charset = ContentType.getOrDefault(entity).getCharset();
        if (charset != null) {
            log.info(charset.name());
        }

        String body = null;
        try {
            body = EntityUtils.toString(entity, "utf-8");
            log.info("body " + body);
        } catch (IOException e) {
            log.warn("{}: cannot parse the entity", e.getMessage());
        }

        return body;
    }
}
