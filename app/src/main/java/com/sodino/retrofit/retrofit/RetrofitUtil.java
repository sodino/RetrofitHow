package com.sodino.retrofit.retrofit;

import com.sodino.retrofit.thread.ThreadPool;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new OkHttpInterceptor()).build();

    public static Retrofit getComonRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(ThreadPool.getThreadsExecutor())
                .client(okHttpClient)
                .build();

        return retrofit;
    }
}
