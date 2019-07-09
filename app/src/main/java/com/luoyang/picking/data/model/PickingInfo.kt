package com.luoyang.picking.data.model

class PickingInfo(
    val time: String,
    val storage: String,
    val orderId: String,
    val logisticsId: String,
    val pickingGoods: ArrayList<PickingGoods>
)