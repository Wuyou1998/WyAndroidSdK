package com.wy.wyandroidsdk.okHttp;

import com.wy.wyandroidsdk.okHttp.https.HttpsUtils;
import com.wy.wyandroidsdk.okHttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/* 名称: WyAndroidSdk.com.wy.wyandroidsdk.okHttp.CommonOkHttpClient
 * 用户: _VIEW
 * 时间: 2019/7/27,9:14
 * 描述: 请求的发送，参数配置，https支持
 */
public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient httpClient;

    //配置参数
    static {
        //创建client构建者
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //填充超时参数
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //允许重定向
        builder.followRedirects(true);
        //https支持
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        builder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        httpClient = builder.build();
    }

    public static Call sendRequest(Request request, CommonJsonCallback  callback) {
        Call call = httpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
