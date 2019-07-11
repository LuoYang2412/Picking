package com.luoyang.picking.net

import com.luoyang.picking.net.api.AuthService
import com.luoyang.picking.net.api.PickingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PickingNetwork {

    private val authService = ServiceCreator.create(AuthService::class.java)

    private val pickingService = ServiceCreator.create(PickingService::class.java)

    suspend fun login(mobileNo: String, password: String) = authService.login(mobileNo, password).await()

    suspend fun getPinckingInfo(orderId : String) = pickingService.getPinckingInfo(orderId ).await()

    suspend fun pda_finishPicking(pickingId: String) = pickingService.pda_finishPicking(pickingId).await()

    suspend fun warehouseOut(carsId: String, handover: String, list: String, routeId: String) =
        pickingService.order_output(carsId, handover, list, routeId).await()

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

        private var network: PickingNetwork? = null

        fun getInstance(): PickingNetwork {
            if (network == null) {
                synchronized(PickingNetwork::class.java) {
                    if (network == null) {
                        network = PickingNetwork()
                    }
                }
            }
            return network!!
        }

    }

}