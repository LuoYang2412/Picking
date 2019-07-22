package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.luoyang.picking.net.PickingNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WarehouseOutViewModel : BaseViewModel() {
    val pickingIdsList = MutableLiveData<ArrayList<String>>()
    val nextButtonVisible = MutableLiveData<Boolean>()
    private val pickingIds = ArrayList<String>()
    var carId: String? = null
    lateinit var driverId: String
    lateinit var routeId: String

    fun clearList() {
        pickingIdsList.value = pickingIds.apply { clear() }
        checkNextButtonVisible()
    }

    fun addGoods(pickingId: String) {
        if (checkPcikingIdNoAdd(pickingId)) {
            pickingIds.add(pickingId)
            this.pickingIdsList.value = pickingIds
            checkNextButtonVisible()
        }
    }

    private fun checkPcikingIdNoAdd(pickingId: String): Boolean {
        var noAdd = true
        pickingIds.map {
            if (it == pickingId) {
                noAdd = false
            }
        }
        return noAdd
    }

    fun removeGoods(index: Int) {
        pickingIds.removeAt(index)
        checkNextButtonVisible()
    }

    private fun checkNextButtonVisible() {
        nextButtonVisible.value = pickingIds.size != 0
    }

    fun order_output_verification(orderId: String) {
        if (checkPcikingIdNoAdd(orderId)) {
            viewModelScope.launch {
                try {
                    resultMsg.value = withContext(Dispatchers.IO) {
                        val resource = PickingNetwork.getInstance().order_output_verification(orderId, routeId)
                        if (resource.success) {
                            return@withContext "订单验证成功::$orderId"
                        } else {
                            return@withContext "${resource.message}"
                        }
                    }
                } catch (t: Throwable) {
                    resultMsg.value = t.message
                }
            }
        }
    }

    fun warehouseOut() {
        viewModelScope.launch {
            try {
                resultMsg.value = withContext(Dispatchers.IO) {
                    val resource =
                        PickingNetwork.getInstance()
                            .warehouseOut(carId ?: "", driverId, Gson().toJson(pickingIds), routeId)
                    if (resource.success) {
                        return@withContext "出库成功"
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