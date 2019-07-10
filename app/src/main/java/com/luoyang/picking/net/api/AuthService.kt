package com.luoyang.picking.net.api

import com.google.gson.JsonObject
import com.javalong.retrofitmocker.annotation.MOCK
import com.luoyang.picking.data.model.UserInfo
import com.luoyang.picking.net.Resource
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 授权服务
 */
interface AuthService {

    //登录
    @MOCK("userInfo.json")
    @FormUrlEncoded
    @POST("loginPDA")
    fun login(
        @Field("account") account: String,
        @Field("password") password: String
    ): Call<Resource<UserInfo>>
}