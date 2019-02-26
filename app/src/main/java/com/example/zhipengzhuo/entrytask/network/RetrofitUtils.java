package com.example.zhipengzhuo.entrytask.network;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitUtils {
    public static volatile Retrofit sRetrofit;

    public static Retrofit getsRetrofit() {
        if (sRetrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (sRetrofit == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.12.74.169:7000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(RetrofitUtils.getOkHttpClient())
                            .build();
                    sRetrofit = retrofit;
                }
            }
        }

        return sRetrofit;
    }

    public static OkHttpClient getOkHttpClient() {
        // 日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        // 新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("Http请求参数：", message);
            }
        });
        loggingInterceptor.setLevel(level);
        // 定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }
}
