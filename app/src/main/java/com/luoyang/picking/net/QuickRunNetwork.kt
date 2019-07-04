package com.luoyang.picking.net

import com.luoyang.picking.net.api.AuthService
import com.luoyang.picking.net.api.DeliveryService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class QuickRunNetwork {

    private val authService = ServiceCreator.create(AuthService::class.java)

    private val deliveryService = ServiceCreator.create(DeliveryService::class.java)

    /**
     * 登录
     */
    suspend fun login(mobileNo: String, password: String) = authService.login(mobileNo, password).await()

    /**
     * 获取货运单号
     * @param state 0未完成，1已完成
     */
    suspend fun app_freightOrder_getAll(state: String, userId: String) =
        deliveryService.app_freightOrder_getAll(state, userId).await()

    /**
     * 查询所有订单
     * @param freightOrderId 货运单号
     * @param pickUpId 提货点ID ""表示所有提货点
     */
    suspend fun app_order_getAll(freightOrderId: String, pickUpId: String, userId: String) =
        deliveryService.app_order_getAll(freightOrderId, pickUpId, userId).await()

    suspend fun app_route_getRoute(freightOrderId: String, userId: String) =
        deliveryService.app_route_getRoute(freightOrderId, userId).await()

    /**
     * 修改订单状态
     * @param pickUpId ""表示装货完成状态，有值表示提货点下货完成
     */
    suspend fun app_order_inDistribution(freightOrderId: String, pickUpId: String, userId: String) =
        deliveryService.app_order_inDistribution(freightOrderId, pickUpId, userId).await()

    suspend fun app_changeMobilePhone(oldPhone: String, newPhone: String, userId: String, code: String) =
        authService.app_changeMobilePhone(oldPhone, newPhone, userId, code).await()

    suspend fun app_changePassword(newPassword: String, oldPassword: String, userId: String) =
        authService.app_changePassword(newPassword, oldPassword, userId).await()

    suspend fun send_message(mobile: String, type: Int) = authService.send_message(mobile, type).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {

        private var network: QuickRunNetwork? = null

        fun getInstance(): QuickRunNetwork {
            if (network == null) {
                synchronized(QuickRunNetwork::class.java) {
                    if (network == null) {
                        network = QuickRunNetwork()
                    }
                }
            }
            return network!!
        }

    }

}