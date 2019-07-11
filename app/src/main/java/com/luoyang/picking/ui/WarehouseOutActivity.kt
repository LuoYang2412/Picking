package com.luoyang.picking.ui

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.device.ScanManager
import android.device.scanner.configuration.PropertyID
import android.graphics.Canvas
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Vibrator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemSwipeListener
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

    private val warehouseViewModel by lazy { ViewModelProviders.of(this).get(WarehouseViewModel::class.java) }
    private val goodsAdapter = GoodsAdapter()

    val mScanManager = ScanManager()
    private var soundid: Int = 0
    private var soundpool: SoundPool? = null
    private var mVibrator: Vibrator? = null

    //扫码广播接收器
    private val scanBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            soundpool?.play(soundid, 1f, 1f, 0, 0, 1f)
            mVibrator?.vibrate(100)

            val barcode = intent?.getByteArrayExtra(ScanManager.DECODE_DATA_TAG)
            val barcodelen = intent?.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0)
            val stringExtra = String(barcode!!, 0, barcodelen!!)
            Timber.tag("============").d(stringExtra)

            warehouseViewModel.addGoods(stringExtra)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_out)

        mVibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        //列表
        goodsAdapter.isFirstOnly(false)
        goodsAdapter.setEmptyView(R.layout.item_warehouse_out_empty, recyclerView1)
        val itemDragAndSwipeCallback = ItemDragAndSwipeCallback(goodsAdapter)
        val itemTouchHelper = ItemTouchHelper(itemDragAndSwipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView1)
        goodsAdapter.enableSwipeItem()
        goodsAdapter.setOnItemSwipeListener(object : OnItemSwipeListener {
            override fun clearView(p0: RecyclerView.ViewHolder?, p1: Int) {
            }

            override fun onItemSwiped(p0: RecyclerView.ViewHolder?, p1: Int) {
                if (p1 == -1) {
                    warehouseViewModel.removeGoods(p0!!.layoutPosition)
                }
            }

            override fun onItemSwipeStart(p0: RecyclerView.ViewHolder?, p1: Int) {
            }

            override fun onItemSwipeMoving(
                p0: Canvas?,
                p1: RecyclerView.ViewHolder?,
                p2: Float,
                p3: Float,
                p4: Boolean
            ) {

            }

        })
        recyclerView1.adapter = goodsAdapter
        warehouseViewModel.pickingIdsList.observe(this, Observer {
            goodsAdapter.replaceData(it)
        })

        //下一步
        warehouseViewModel.nextButtonVisible.observe(this, Observer {
            radiusButton3.isVisible = it
        })
        radiusButton3.setOnClickListener {
            WarehouseOutNextActivity.goIn(this, warehouseViewModel.pickingIdsList.value!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val value = warehouseViewModel.pickingIdsList.value
            value?.clear()
            warehouseViewModel.setPickingIdsList(value!!)
        }
        super.onActivityResult(requestCode, resultCode, data)
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

