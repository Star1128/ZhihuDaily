package com.example.zhihudaily.utils;

import okhttp3.*;

/**
 * NOTE:
 *
 * @author wxc 2021/8/16
 * @version 1.0.0
 */
public class HttpUtil {

    public static void sendRequestWithOkHttp(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);//在enqueue方法里已开好子线程，最终的请求结果会回调到callback中
    }

}
