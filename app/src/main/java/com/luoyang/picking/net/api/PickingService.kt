package com.luoyang.picking.net.api

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
    @POST("pda_getRouteAndType")
    fun getPickingClassify(): Call<Resource<ArrayList<PickingClassify>>>

    //获取拣货单
    @MOCK("pickingInfo.json")
    @FormUrlEncoded
    @POST("pda_getPicking")
    fun getPinckingInfo(
        @Field("flag") flag: String,
        @Field("routeId") routeId: String
    ): Call<Resource<PickingInfo>>

    //拣货完成打印标签
    @MOCK("")
    @FormUrlEncoded
    @POST("pda_finishPicking")
    fun pda_finishPicking(
        @Field("pickingId") pickingId: String
    ): Call<Resource<*>>

    //出库
    @MOCK("")
    @FormUrlEncoded
    @POST("order_output")
    fun order_output(
        @Field("carsId") carsId: String,
        @Field("handover") handover: String,//驾驶员ID
        @Field("list") list: String,//物流单ID
        @Field("routeId") routeId: String
    ): Call<Resource<*>>
}