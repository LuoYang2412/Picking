package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luoyang.picking.PickingApplication
import com.luoyang.picking.net.PickingNetwork
import com.luoyang.picking.utils.AESUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel() {
    val inputSuccess = MutableLiveData<Boolean>()

    fun loginDataChanged(mobileNo: String, password: String) {
        inputSuccess.value = isMobileNo(mobileNo) && isPassword(password)
    }

    private fun isMobileNo(mobileNo: String): Boolean {
        return mobileNo.length == 11
    }

    private fun isPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                resultMsg.value = withContext(Dispatchers.IO) {
                    val password1 = AESUtils.encrypt(password)
                    val resource = PickingNetwork.getInstance().login(username, password1)
                    if (resource.success) {
                        PickingApplication.application.userInfo = resource.data
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
