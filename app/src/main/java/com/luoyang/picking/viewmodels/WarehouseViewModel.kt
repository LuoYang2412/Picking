package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData

class WarehouseViewModel : BaseViewModel() {
    val pickingIdsList = MutableLiveData<ArrayList<String>>()
    val nextButtonVisible = MutableLiveData<Boolean>()
    private val pickingIds = ArrayList<String>()

    fun setPickingIdsList(value: ArrayList<String>) {
        pickingIdsList.value = value
        checkNextButtonVisible()
    }

    fun addGoods(pickingId: String) {
        pickingIds.map {
            if (it == pickingId) {
                return
            }
        }
        pickingIds.add(pickingId)
        this.pickingIdsList.value = pickingIds
        checkNextButtonVisible()
    }

    fun removeGoods(index: Int) {
        pickingIds.removeAt(index)
        checkNextButtonVisible()
    }

    private fun checkNextButtonVisible() {
        nextButtonVisible.value = pickingIds.size != 0
    }
}