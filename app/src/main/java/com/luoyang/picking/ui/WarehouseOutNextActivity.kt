package com.luoyang.picking.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.luoyang.picking.R

class WarehouseOutNextActivity : BaseActivity() {

    companion object {
        fun goIn(context: Context, pickingId: String) {
            val intent = Intent(context, WarehouseOutNextActivity::class.java)
            intent.putExtra("pickingId", pickingId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_out_next)

        val pickingId = intent.getStringExtra("pickingId")

    }
}
