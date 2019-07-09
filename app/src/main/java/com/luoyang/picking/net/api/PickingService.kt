package com.luoyang.picking.net.api

import com.google.gson.JsonObject
import com.javalong.retrofitmocker.annotation.MOCK
import com.luoyang.picking.data.model.PickingClassify
import com.luoyang.picking.data.model.PickingInfo
import com.luoyang.picking.net.Resource
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PickingService {
    //获取拣货分类
    @MOCK("pickingClassify.json")
    @FormUrlEncoded
    @POST("getPickingClassify")
    fun getPickingClassify(): Call<Resource<ArrayList<PickingClassify>>>

    //获取拣货单
    @MOCK("pickingInfo.json")
    @FormUrlEncoded
    @POST("getPinckingInfo")
    fun getPinckingInfo(
    ): Call<Resource<PickingInfo>>

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