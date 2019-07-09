package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luoyang.picking.data.model.PickingClassify
import com.luoyang.picking.data.model.PickingInfo
import com.luoyang.picking.net.PickingNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PickingViewModel : BaseViewModel() {
    val pickingClassifys = MutableLiveData<ArrayList<PickingClassify>>()
    val pickingInfo = MutableLiveData<PickingInfo>()

    fun getPickingClassify() {
        viewModelScope.launch {
            try {
                resultMsg.value = withContext(Dispatchers.IO) {
                    val resource = PickingNetwork.getInstance().getPickingClassify()
                    if (resource.success) {
                        pickingClassifys.postValue(resource.data)
                        return@withContext resource.success.toString()
                    } else {
                        return@withContext resource.message
                    }
                }
            } catch (t: Throwable) {
                resultMsg.value = t.message
            }
        }
    }

    fun getPinckingInfo() {
        viewModelScope.launch {
            try {
                resultMsg.value = withContext(Dispatchers.IO) {
                    val resource = PickingNetwork.getInstance().getPinckingInfo()
                    if (resource.success) {
                        pickingInfo.postValue(resource.data)
                        return@withContext resource.success.toString()
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