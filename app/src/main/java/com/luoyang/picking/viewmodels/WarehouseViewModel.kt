package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import com.luoyang.picking.data.model.Goods

class WarehouseViewModel : BaseViewModel() {
    val data = MutableLiveData<ArrayList<Goods>>()

    fun getData() {
        val arrayList = ArrayList<Goods>()
        for (i in 1..10) {
            arrayList.add(Goods((Math.random() * 26 + 97).toString()))
        }
        data.value = arrayList
    }
}