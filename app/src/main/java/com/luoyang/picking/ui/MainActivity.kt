package com.luoyang.picking.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bin.david.form.utils.DensityUtils
import com.luoyang.picking.PickingApplication
import com.luoyang.picking.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        fun goIn(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //拣货
        radiusButton1.textView.apply {
            compoundDrawablePadding = DensityUtils.dp2px(this@MainActivity, 8F)
            setCompoundDrawables(
                null,
                getDrawable(R.drawable.ic_picking).apply {
                    setBounds(
                        0,
                        0,
                        DensityUtils.dp2px(this@MainActivity, 40F),
                        DensityUtils.dp2px(this@MainActivity, 40F)
                    )
                },
                null,
                null
            )
        }
        radiusButton1.setOnClickListener {
            PickingActivity.goIn(this)
        }

        //出库
        radiusButton2.textView.apply {
            compoundDrawablePadding = DensityUtils.dp2px(this@MainActivity, 8F)
            setCompoundDrawables(
                null,
                getDrawable(R.drawable.ic_warehouse_out).apply {
                    setBounds(
                        0,
                        0,
                        DensityUtils.dp2px(this@MainActivity, 40F),
                        DensityUtils.dp2px(this@MainActivity, 40F)
                    )
                },
                null,
                null
            )
        }
        radiusButton2.setOnClickListener {
            WarehouseOutActivity.goIn(this)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        PickingApplication.application.exit()
    }
}
