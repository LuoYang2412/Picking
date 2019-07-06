package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import com.luoyang.picking.data.model.PickingClassify
import com.luoyang.picking.data.model.PickingGoods

class PickingViewModel : BaseViewModel() {
    val pickingClassifys = MutableLiveData<ArrayList<PickingClassify>>()
    val pickingGoods = MutableLiveData<ArrayList<PickingGoods>>()

    fun getPickingClassify() {
        val arrayList = ArrayList<PickingClassify>()
        for (i in 1..5) {
            arrayList.add(PickingClassify(i, "menu{$i}_${(Math.random() * 10)}"))
        }
        pickingClassifys.postValue(arrayList)
    }

    fun getPinckingGoods() {
        val arrayList = ArrayList<PickingGoods>()
        for (i in 1..50) {
            arrayList.add(PickingGoods("$i 货架区货架区货架区", "商品$i", "Kg${Math.random() * 100}", i * 3))
        }
        pickingGoods.postValue(arrayList)
    }
}