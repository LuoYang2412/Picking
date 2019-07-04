package com.luoyang.picking.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luoyang.picking.R
import com.luoyang.picking.ui.adapters.GoodsAdapter
import com.luoyang.picking.viewmodels.WarehouseViewModel
import kotlinx.android.synthetic.main.activity_warehouse_out.*

/**出库*/
class WarehouseOut : BaseActivity() {
    companion object {
        fun goIn(context: Context) {
            val intent = Intent(context, WarehouseOut::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_out)

        val warehouseViewModel = ViewModelProviders.of(this).get(WarehouseViewModel::class.java)

        swipeRefreshLayout1.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
        swipeRefreshLayout1.setOnRefreshListener {
            warehouseViewModel.getData()
            Handler().postDelayed({ swipeRefreshLayout1.isRefreshing = false }, 500)
        }

        val goodsAdapter = GoodsAdapter()
        goodsAdapter.isFirstOnly(false)
        goodsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT)
        recyclerView1.adapter = goodsAdapter

        floatingActionButton.setOnClickListener {
            showToast("扫描")
        }

        warehouseViewModel.getData()
        warehouseViewModel.data.observe(this, Observer {
            goodsAdapter.replaceData(it)
        })
    }
}

