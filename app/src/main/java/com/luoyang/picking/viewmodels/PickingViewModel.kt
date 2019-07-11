package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luoyang.picking.data.model.PickingInfo
import com.luoyang.picking.net.PickingNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PickingViewModel : BaseViewModel() {
    val pickingInfo = MutableLiveData<PickingInfo>()

    fun getPinckingInfo(orderId: String) {
        viewModelScope.launch {
            try {
                resultMsg.value = withContext(Dispatchers.IO) {
                    val resource = PickingNetwork.getInstance().getPinckingInfo(orderId)
                    if (resource.success) {
                        if (resource.data!!.orderNo != null) {
                            pickingInfo.postValue(resource.data)
                            return@withContext resource.success.toString()
                        } else {
                            return@withContext "查找数据失败，无需重复扫描"
                        }
                    } else {
                        return@withContext resource.message
                    }
                }
            } catch (t: Throwable) {
                resultMsg.value = t.message ?: "未知异常"
            }
        }
    }

    fun printPincking(pickingId: String) {
        viewModelScope.launch {
            try {
                resultMsg.value = withContext(Dispatchers.IO) {
                    val resource = PickingNetwork.getInstance().pda_finishPicking(pickingId)
                    if (resource.success) {
                        return@withContext "拣货完成"
                    } else {
                        return@withContext resource.message
                    }
                }
            } catch (t: Throwable) {
                resultMsg.value = t.message
            }
        }
    }
}