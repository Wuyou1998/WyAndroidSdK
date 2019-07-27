package com.wy.wyandroidsdk.okHttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/* 名称: WyAndroidSdk.com.wy.wyandroidsdk.okHttp.request.CommonRequest
 * 用户: _VIEW
 * 时间: 2019/7/27,9:15
 * 描述: 生成request对象
 */
public class CommonRequest {
    /**
     * @param url
     * @param params
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = builder.build();
        return new Request.Builder().url(url).post(formBody).build();
    }

    /**
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder stringBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(stringBuilder.substring(0, stringBuilder.length() - 1)).get().build();
    }
}
