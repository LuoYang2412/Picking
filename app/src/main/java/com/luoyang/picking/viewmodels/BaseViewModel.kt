package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected val resultMsg = MutableLiveData<String>()
}