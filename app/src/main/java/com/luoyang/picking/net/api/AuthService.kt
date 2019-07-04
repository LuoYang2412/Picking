package com.luoyang.picking.net.api

import com.google.gson.JsonObject
import com.luoyang.picking.net.Resource
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 授权服务
 */
interface AuthService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("account") account: String,
        @Field("password") password: String,
        @Field("uuid") uuid: String = "0"
    ): Call<Resource<JsonObject>>

    /**
     * 修改手机号
     * @code 短信验证码
     */
    @FormUrlEncoded
    @POST("app_changeMobilePhone")
    fun app_changeMobilePhone(
        @Field("oldPhone") oldPhone: String,
        @Field("newPhone") newPhone: String,
        @Field("userId") userId: String,
        @Field("code") code: String
    ): Call<Resource<JsonObject>>

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("app_changePassword")
    fun app_changePassword(
        @Field("newPassword") newPassword: String,
        @Field("oldPassword") oldPassword: String,
        @Field("userId") userId: String
    ): Call<Resource<JsonObject>>

    /**
     * 发送短信验证码
     * @param type 0用户注册，1登录确认，2登录异常,3身份验证,4修改密码,5信息变更
     */
    @FormUrlEncoded
    @POST("send_message")
    fun send_message(
        @Field("mobile") mobile: String,
        @Field("type") type: Int
    ): Call<Resource<JsonObject>>
}