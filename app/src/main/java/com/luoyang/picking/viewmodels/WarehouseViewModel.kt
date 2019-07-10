package com.luoyang.picking.viewmodels

import androidx.lifecycle.MutableLiveData
import com.luoyang.picking.data.model.Goods

class WarehouseViewModel : BaseViewModel() {
    val goods = MutableLiveData<ArrayList<Goods>>()
    val nextButtonVisible = MutableLiveData<Boolean>()
    private val goodsList = ArrayList<Goods>()

    fun addGoods(goods: Goods) {
        goodsList.map {
            if (it.goods_id == goods.goods_id) {
                return
            }
        }
        goodsList.add(goods)
        this.goods.value = goodsList
        checkNextButtonVisible()
    }

    fun removeGoods(index: Int) {
        goodsList.removeAt(index)
        checkNextButtonVisible()
    }

    private fun checkNextButtonVisible() {
        nextButtonVisible.value = goodsList.size != 0
    }
}