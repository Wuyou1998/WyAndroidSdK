package com.wy.wyandroidsdk.okHttp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* 名称: WyAndroidSdk.com.wy.wyandroidsdk.okHttp.HttpParams
 * 用户: _VIEW
 * 时间: 2019/8/2,14:32
 * 描述: post请求参数
 */
public class HttpParams {
    private ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();

    public HttpParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void put(String key, String value) {
        urlParams.put(key, value);
    }

    public ConcurrentHashMap<String, String> getUrlParams() {
        return urlParams;
    }
}
