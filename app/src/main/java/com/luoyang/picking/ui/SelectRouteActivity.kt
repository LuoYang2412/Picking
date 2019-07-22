package com.luoyang.picking.ui

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
import com.luoyang.picking.viewmodels.SelectRouteViewModel
import kotlinx.android.synthetic.main.activity_warehouse_out_next.*

//选择路线
class SelectRouteActivity : BaseActivity() {

    companion object {
        fun goIn(context: Context) {
            val intent = Intent(context, SelectRouteActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_out_next)

        val selectRouteViewModel = ViewModelProviders.of(this).get(SelectRouteViewModel::class.java)

        //司机车辆路线
        val driverSpinnerAdapter = WareHouseOutDriverSpinnerAdapter()
        spinner.adapter = driverSpinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectRouteViewModel.driverId = driverSpinnerAdapter.data[position].id
            }

        }
        val carsSpinnerAdapter = WareHouseOutCarsSpinnerAdapter()
        spinner1.adapter = carsSpinnerAdapter
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectRouteViewModel.carId = carsSpinnerAdapter.data[position].id
            }

        }
        val rougeSpinnerAdapter = WareHouseOutRougeSpinnerAdapter()
        spinner2.adapter = rougeSpinnerAdapter
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectRouteViewModel.routeId = rougeSpinnerAdapter.data[position].id
            }

        }
        selectRouteViewModel.getRouteAndCarAndUser()
        selectRouteViewModel.routeAndCarAndUser.observe(this, Observer {
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

        //下一步
        radiusButton5.setOnClickListener {
            if (selectRouteViewModel.checkRoute()) {
                WarehouseOutActivity.goIn(
                    this,
                    selectRouteViewModel.carId,
                    selectRouteViewModel.driverId!!,
                    selectRouteViewModel.routeId!!
                )
            }
        }

        selectRouteViewModel.resultMsg.observe(this, Observer {
            when {
                else -> showToast(it)
            }
        })

    }
}
