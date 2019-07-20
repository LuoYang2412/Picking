package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luoyang.picking.data.model.OrderDetail
import com.luoyang.picking.net.PickingNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderDetailViewModel : BaseViewModel() {
    val orderDetail = MutableLiveData<OrderDetail>()

    fun getOrderDetail(orderId: String) {
        viewModelScope.launch {
            try {
                resultMsg.value = withContext(Dispatchers.IO) {
                    val resource = PickingNetwork.getInstance().order_detail(orderId)
                    if (resource.success) {
                        orderDetail.postValue(resource.data)
                        return@withContext resource.success.toString()
                    } else {
                        return@withContext resource.message
                    }
                }
            } catch (t: Throwable) {
                resultMsg.value = t.message ?: "未知异常"
            }
        }
    }
}