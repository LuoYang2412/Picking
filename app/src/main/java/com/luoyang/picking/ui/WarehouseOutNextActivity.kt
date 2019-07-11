package com.luoyang.picking.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luoyang.picking.R
import com.luoyang.picking.ui.adapters.WareHouseOutCarsSpinnerAdapter
import com.luoyang.picking.ui.adapters.WareHouseOutDriverSpinnerAdapter
import com.luoyang.picking.ui.adapters.WareHouseOutRougeSpinnerAdapter
import com.luoyang.picking.viewmodels.WarehouseOutNextViewModel
import kotlinx.android.synthetic.main.activity_warehouse_out_next.*

class WarehouseOutNextActivity : BaseActivity() {

    companion object {
        fun goIn(activity: Activity, pickingIds: ArrayList<String>) {
            val intent = Intent(activity, WarehouseOutNextActivity::class.java)
            intent.putExtra("pickingIds", pickingIds)
            activity.startActivityForResult(intent, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_out_next)

        val pickingId = intent.getSerializableExtra("pickingIds") as ArrayList<*> as ArrayList<String>

        val warehouseOutNextViewModel = ViewModelProviders.of(this).get(WarehouseOutNextViewModel::class.java)

        //司机车辆路线
        val driverSpinnerAdapter = WareHouseOutDriverSpinnerAdapter()
        spinner.adapter = driverSpinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                warehouseOutNextViewModel.driverId = driverSpinnerAdapter.data[position].id
            }

        }
        val carsSpinnerAdapter = WareHouseOutCarsSpinnerAdapter()
        spinner1.adapter = carsSpinnerAdapter
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                warehouseOutNextViewModel.carId = carsSpinnerAdapter.data[position].id
            }

        }
        val rougeSpinnerAdapter = WareHouseOutRougeSpinnerAdapter()
        spinner2.adapter = rougeSpinnerAdapter
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                warehouseOutNextViewModel.routeId = rougeSpinnerAdapter.data[position].id
            }

        }
        warehouseOutNextViewModel.getRouteAndCarAndUser()
        warehouseOutNextViewModel.routeAndCarAndUser.observe(this, Observer {
            driverSpinnerAdapter.data.apply {
                clear()
                addAll(it.userDOList)
            }
            driverSpinnerAdapter.notifyDataSetChanged()
            carsSpinnerAdapter.data.apply {
                clear()
                addAll(it.carsDOList)
            }
            carsSpinnerAdapter.notifyDataSetChanged()
            rougeSpinnerAdapter.data.apply {
                clear()
                addAll(it.routeDOList)
            }
            rougeSpinnerAdapter.notifyDataSetChanged()
        })

        //出库
        radiusButton5.setOnClickListener {
            warehouseOutNextViewModel.warehouseOut(pickingId)
        }

        warehouseOutNextViewModel.resultMsg.observe(this, Observer {
            when {
                it == "出库成功" -> {
                    showToast(it)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                else -> showToast(it)
            }
        })

    }
}
