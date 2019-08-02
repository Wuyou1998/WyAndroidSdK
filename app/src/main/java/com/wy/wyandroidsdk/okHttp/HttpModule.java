package com.wy.wyandroidsdk.okHttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* 名称: WyAndroidSdk.com.wy.wyandroidsdk.okHttp.HttpModule
 * 用户: _VIEW
 * 时间: 2019/8/2,14:22
 * 描述: 使用okHttp访问网络
 */
public class HttpModule {
    private static Handler handler = new Handler(Looper.getMainLooper());

    public interface HttpListener {
        void onSuccess(String callback);

        void onError();
    }

    public static void Get(final String url, final HttpListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final String data = response.body().toString();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(data);
                            }
                        });
                    } else
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError();
                            }
                        });

                } catch (IOException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError();
                        }
                    });
                }
            }
        }).start();

    }

    public static void Post(final String url, final HttpParams params, final HttpListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder requestBody = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : params.getUrlParams().entrySet()) {
                        requestBody.add(entry.getKey(), entry.getValue());
                    }
                    Request request = new Request.Builder().url(url).post(requestBody.build()).build();
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        final String data = response.body().toString();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(data);
                            }
                        });
                    } else
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError();
                            }
                        });
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError();
                        }
                    });
                }
            }
        });
    }
}
