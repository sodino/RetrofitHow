package com.sodino.retrofit.retrofit;

import android.os.Build;

import com.sodino.retrofit.Constant;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class OkHttpInterceptor implements okhttp3.Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        long now = System.currentTimeMillis();

        Request newRequest = originalRequest.newBuilder()
                .header("sdkInt", Integer.toString(Build.VERSION.SDK_INT))
                .header("device", Build.DEVICE)
                .header("hardware", Build.HARDWARE)
//                .header("board", Build.BOARD)
                .header("brand", Build.BRAND)
//                .header("product", Build.PRODUCT)
                .header("display", Build.DISPLAY)
                .header("fingerprint", Build.FINGERPRINT)
                .header("clientReqTime", Long.toString(now))
                .header("user-agent", "android.niVoVin")
                .header("ticket", Constant.TICKET)
                .build();

        Response response = chain.proceed(newRequest);
        return response;
    }
}
