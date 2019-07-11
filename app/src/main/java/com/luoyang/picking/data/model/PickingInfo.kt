package com.luoyang.picking.data.model

data class PickingInfo(
    val goodsList: List<GoodsInPicking>,
    val orderNo: String,
    val pickingDO: PickingDO,
    val shipNum: String,
    val warehouseName: String
)

data class GoodsInPicking(
    val goodsName: String,
    val inputQuantity: Int,
    val isChild: Int,
    val isSku: Int,
    val logisticsReq: String,
    val orderId: String,
    val outputQuantity: Int,
    val partName: String,
    val price: String,
    val quantity: Int,
    val skuWarehouseId: Int,
    val goodsSpecValue: String = "-"
)

data class PickingDO(
    val createDate: String,
    val id: String,
    val num: String,
    val pickUpId: Int,
    val state: Int,
    val warehouseId: Int
)