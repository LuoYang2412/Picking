package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import com.luoyang.picking.data.model.Goods

class WarehouseViewModel : BaseViewModel() {
    val data = MutableLiveData<ArrayList<Goods>>()

    fun getData() {
        val arrayList = ArrayList<Goods>()
        arrayList.add(Goods("6959310410617"))
        arrayList.add(Goods("http://weixin.qq.com/r/RUzXz97EsIPPrZqs9xlX"))
        arrayList.add(Goods("http://m.tb.cn/ZjgGcw"))
        arrayList.add(Goods("6959310402094"))

        data.value = arrayList
    }
}