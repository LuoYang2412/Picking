package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData

class LoginViewModel : BaseViewModel() {
    val inputSuccess = MutableLiveData<Boolean>()
    val logined = false

    fun loginDataChanged(mobileNo: String, password: String) {
        inputSuccess.value = isMobileNo(mobileNo) && isPassword(password)
    }

    private fun isMobileNo(mobileNo: String): Boolean {
        return mobileNo.length == 11
    }

    private fun isPassword(password: String): Boolean {
        return password.length >= 6
    }
}
