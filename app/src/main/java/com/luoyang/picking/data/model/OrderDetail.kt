package com.luoyang.picking.data.model

data class OrderDetail(
    val logisticsReq: String,
    val orderGoodsDOS: List<OrderGoodsDOS>,
    val orderNo: String,
    val pickUpPoint: String,
    val remarks: String,
    val shipNum: String,
    val shipState: Int,
    val state: Int
)

data class OrderGoodsDOS(
    val goodsName: String,
    val inputQuantity: Int,
    val isChild: Int,
    val isSku: Int,
    val logisticsReq: String,
    val orderId: String,
    val outputQuantity: Int,
    val price: String,
    val quantity: Int,
    val skuWarehouseId: Int,
    val goodsSpecValue: String
)