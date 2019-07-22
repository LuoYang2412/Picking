package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luoyang.picking.data.model.RouteAndCarAndUser
import com.luoyang.picking.net.PickingNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectRouteViewModel : BaseViewModel() {
    val routeAndCarAndUser = MutableLiveData<RouteAndCarAndUser>()
    var carId: String? = null
    var driverId: String? = null
    var routeId: String? = null

    fun getRouteAndCarAndUser() {
        viewModelScope.launch {
            try {
                resultMsg.value = withContext(Dispatchers.IO) {
                    val resource = PickingNetwork.getInstance().getRouteAndCarAndUser()
                    if (resource.success) {
                        routeAndCarAndUser.postValue(resource.data)
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

    fun checkRoute(): Boolean {
        if (driverId == null || routeId == null) {
            resultMsg.value = "请至少选择司机和路线"
            return false
        }
        return true
    }
}