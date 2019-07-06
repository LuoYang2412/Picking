package com.luoyang.picking.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.device.ScanManager
import android.device.scanner.configuration.PropertyID
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luoyang.picking.R
import com.luoyang.picking.ui.adapters.GoodsAdapter
import com.luoyang.picking.viewmodels.WarehouseViewModel
import kotlinx.android.synthetic.main.activity_warehouse_out.*
import timber.log.Timber

/**出库*/
class WarehouseOutActivity : BaseActivity() {

    companion object {
        fun goIn(context: Context) {
            val intent = Intent(context, WarehouseOutActivity::class.java)
            context.startActivity(intent)
        }
    }

    val goodsAdapter = GoodsAdapter()

    val mScanManager = ScanManager()
    private var soundid: Int = 0
    private var soundpool: SoundPool? = null
    private var mVibrator: Vibrator? = null


    private val scanBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            soundpool?.play(soundid, 1f, 1f, 0, 0, 1f)
            mVibrator?.vibrate(100)

            val barcode = intent?.getByteArrayExtra(ScanManager.DECODE_DATA_TAG)
            val barcodelen = intent?.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0)
            val stringExtra = String(barcode!!, 0, barcodelen!!)
            Timber.tag("============").d(stringExtra)
            var checks: Boolean? = null
            for (i in 0 until goodsAdapter.data.size) {
                val it = goodsAdapter.data[i]
                if (it.goods_id == stringExtra) {
                    it.check = true
                    (recyclerView1.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(i, 0)
                    goodsAdapter.notifyItemChanged(i)
                }
                if (checks == null) {
                    checks = it.check
                }
                checks = checks as Boolean && it.check
            }
            radiusButton3.isVisible = checks!!
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_out)

        mVibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val warehouseViewModel = ViewModelProviders.of(this).get(WarehouseViewModel::class.java)

        swipeRefreshLayout1.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
        swipeRefreshLayout1.setOnRefreshListener {
            warehouseViewModel.getData()
            radiusButton3.isVisible = false
            Handler().postDelayed({ swipeRefreshLayout1.isRefreshing = false }, 500)
        }

        goodsAdapter.isFirstOnly(false)
        goodsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT)
        recyclerView1.adapter = goodsAdapter

        radiusButton3.setOnClickListener {
            showToast("出库")
        }

        warehouseViewModel.getData()
        warehouseViewModel.data.observe(this, Observer {
            goodsAdapter.replaceData(it)
        })
    }

    override fun onResume() {
        super.onResume()
        initScan()

        val filter = IntentFilter()
        val idbuf = intArrayOf(PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG)
        val value_buf = mScanManager.getParameterString(idbuf)
        if (value_buf != null && value_buf[0] != null && value_buf[0] != "") {
            filter.addAction(value_buf[0])
        } else {
            filter.addAction(ScanManager.ACTION_DECODE)
        }
        registerReceiver(scanBroadcastReceiver, filter)
    }

    private fun initScan() {
        mScanManager.openScanner()

        mScanManager.switchOutputMode(0)
        soundpool = SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100) // MODE_RINGTONE
        soundid = soundpool!!.load("/etc/Scan_new.ogg", 1)
    }

    override fun onPause() {
        super.onPause()
        mScanManager.stopDecode()
        unregisterReceiver(scanBroadcastReceiver)
    }
}

