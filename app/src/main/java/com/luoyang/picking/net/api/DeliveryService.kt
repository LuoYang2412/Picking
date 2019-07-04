package com.luoyang.picking.net.api

import com.google.gson.JsonObject
import com.luoyang.picking.net.Resource
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DeliveryService {
    /**
     * 获取货运单号
     * @param state 0未完成，1已完成
     */
    @FormUrlEncoded
    @POST("app_freightOrder_getAll")
    fun app_freightOrder_getAll(@Field("state") state: String, @Field("userId") userId: String): Call<Resource<JsonObject>>

    /**
     * 查询所有订单
     * @param freightOrderId 货运单号
     * @param pickUpId 提货点ID ""表示所有提货点
     */
    @FormUrlEncoded
    @POST("app_order_getAll")
    fun app_order_getAll(
        @Field("freightOrderId") freightOrderId: String, @Field("pickUpId") pickUpId: String,
        @Field("userId") userId: String
    ): Call<Resource<JsonObject>>

    /**
     * 获取路线
     */
    @FormUrlEncoded
    @POST("app_route_getRoute")
    fun app_route_getRoute(
        @Field("freightOrderId") freightOrderId: String,
        @Field("userId") userId: String
    ): Call<Resource<JsonObject>>

    /**
     * 修改订单状态
     * @param pickUpId ""表示装货完成状态，有值表示提货点下货完成
     */
    @FormUrlEncoded
    @POST("app_order_inDistribution")
    fun app_order_inDistribution(
        @Field("freightOrderId") freightOrderId: String,
        @Field("pickId") pickId: String,
        @Field("userId") userId: String
    ): Call<Resource<JsonObject>>
}