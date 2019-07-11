package com.luoyang.picking.data.model
data class RouteAndCarAndUser(
    val carsDOList: List<CarsDO>,
    val routeDOList: List<RouteDO>,
    val userDOList: List<UserDO>
)

data class UserDO(
    val account: String,
    val address: String,
    val areaCity: Int,
    val areaCounty: Int,
    val areaProvince: Int,
    val backstage: Int,
    val createDate: Long,
    val createUserId: String,
    val id: String,
    val mobilePhone: String,
    val password: String,
    val realName: String,
    val remarks: String,
    val sex: Int,
    val state: Int,
    val telePhone: String
)

data class CarsDO(
    val createDate: String,
    val createUserId: String,
    val id: String,
    val image: String,
    val numPlate: String,
    val state: Int,
    val warehouseId: Int
)

data class RouteDO(
    val createDate: String,
    val createUserId: String,
    val id: String,
    val name: String,
    val remarks: String,
    val routeJson: String,
    val state: Int,
    val updateDate: String,
    val updateUserId: String,
    val warehouseId: Int
)