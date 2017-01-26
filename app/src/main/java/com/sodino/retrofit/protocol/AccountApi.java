package com.sodino.retrofit.protocol;

import com.sodino.retrofit.bean.RespNodeDepartment;
import com.sodino.retrofit.bean.UserBean;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/1/25.
 */

public interface AccountApi {
    public static final String ACT_GET_ACCOUNT_INFO = "account/getLoginInfo";
    public static final String ACT_GET_DEPARTMENT_LIST = "department/{idRootDepartment}/subdivision";
    public static final String ACT_DEL_MESSAGE = "message/{ids}";

    @GET(ACT_GET_ACCOUNT_INFO)
    Call<UserBean> reqGetAccountInfo();


    @GET(ACT_GET_DEPARTMENT_LIST)
    Call<RespNodeDepartment> reqGetDepartmentList(@Path("idRootDepartment") long idRootDepartment, @Query("page_size") long pageSize);

    @DELETE(ACT_DEL_MESSAGE)
    Call<Object> reqDelMessage(@Path("ids") String ids);
}
