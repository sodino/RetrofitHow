package com.sodino.retrofit.protocol;

import android.util.Log;

import com.sodino.retrofit.Constant;
import com.sodino.retrofit.bean.RespNodeDepartment;
import com.sodino.retrofit.bean.UserBean;
import com.sodino.retrofit.retrofit.RetrofitUtil;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/1/25.
 */

public class AccountProtocol {
    public static final String ACT_GET_ACCOUNT_INFO = "account/getLoginInfo";
    private Retrofit retrofit = RetrofitUtil.getComonRetrofit(Constant.DEV_URL_PRE_ACCOUNT);
    private AccountApi accountApi = retrofit.create(AccountApi.class);

    public void reqGetAccountInfo() {
        Call<UserBean> call = accountApi.reqGetAccountInfo();

        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                Log.d("Test", "onResponse code=" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    UserBean userBean = response.body();
                    Log.d("Test", "nickname=" + userBean.nick_name);
                }else{

                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.d("Test", "onFailure reqGetAccountInfo()");
            }
        });
    }

    public void reqGetDepartmentList(final int idRootDepartment, final int reqLevel) {
        Call<RespNodeDepartment> call = accountApi.reqGetDepartmentList(idRootDepartment,
                Short.MAX_VALUE); // 客户端不支持分页的UI操作，所以需要拉取全部的目录结构，所以传了个 Short.MAX);
        call.enqueue(new Callback<RespNodeDepartment>() {
            @Override
            public void onResponse(Call<RespNodeDepartment> call, Response<RespNodeDepartment> response) {
                Log.d("Test", "onResponse code=" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    RespNodeDepartment respNodeDep = response.body();
                    Log.d("Test", "respNodeDep.departments.size=" + respNodeDep.departments.size());
                }else{

                }
            }

            @Override
            public void onFailure(Call<RespNodeDepartment> call, Throwable t) {
                Log.d("Test", "onFailure reqGetDepartmentList()");
            }
        });
    }

    public void reqDelMessage(final String ids){
        Call<Object> call = accountApi.reqDelMessage(ids);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("Test", "onResponse code=" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
//                    RespNodeDepartment respNodeDep = response.body();
                    Log.d("Test", "reqDelMessage oook");
                } else {

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("Test", "onFailure reqDelMessage()");
            }
        });
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.d("Test", "onResponse code=" + response.code());
//                if (response.code() == HttpURLConnection.HTTP_OK) {
////                    RespNodeDepartment respNodeDep = response.body();
//                    Log.d("Test", "reqDelMessage oook");
//                }else{
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Log.d("Test", "onFailure reqDelMessage()");
//            }
//        });
    }
}
