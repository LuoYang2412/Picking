package com.luoyang.picking.net.api

import com.javalong.retrofitmocker.annotation.MOCK
import com.luoyang.picking.data.model.PickingInfo
import com.luoyang.picking.data.model.RouteAndCarAndUser
import com.luoyang.picking.net.Resource
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface PickingService {

    //获取拣货单
    @MOCK("pickingInfo.json")
    @FormUrlEncoded
    @POST("pda_getPicking")
    fun getPinckingInfo(
        @Field("orderId") orderId: String
    ): Call<Resource<PickingInfo>>

    //拣货完成打印标签
    @MOCK("resource.json")
    @FormUrlEncoded
    @POST("pda_finishPicking")
    fun pda_finishPicking(
        @Field("pickingId") pickingId: String
    ): Call<Resource<Objects>>

    //获取路线车辆司机
    @MOCK("routeAndCarAndUser.json")
    @POST("getRouteAndCarAndUser")
    fun getRouteAndCarAndUser(): Call<Resource<RouteAndCarAndUser>>

    //出库
    @MOCK("resource.json")
    @FormUrlEncoded
    @POST("order_output")
    fun order_output(
        @Field("carsId") carsId: String,
        @Field("handover") handover: String,//驾驶员ID
        @Field("lists") list: String,//物流单ID
        @Field("routeId") routeId: String
    ): Call<Resource<Objects>>
}