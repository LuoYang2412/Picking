package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val resultMsg = MutableLiveData<String>()
}