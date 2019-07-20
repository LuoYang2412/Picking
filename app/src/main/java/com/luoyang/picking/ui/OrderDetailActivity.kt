package com.luoyang.picking.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.device.ScanManager
import android.device.scanner.configuration.PropertyID
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Vibrator
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.bin.david.form.utils.DensityUtils
import com.luoyang.picking.R
import com.luoyang.picking.data.model.OrderGoodsDOS
import com.luoyang.picking.viewmodels.OrderDetailViewModel
import kotlinx.android.synthetic.main.activity_order_detail.*
import timber.log.Timber

class OrderDetailActivity : BaseActivity() {

    companion object {
        fun goIn(context: Context) {
            val intent = Intent(context, OrderDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    val orderDetailViewModel by lazy { ViewModelProviders.of(this).get(OrderDetailViewModel::class.java) }

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
            orderDetailViewModel.getOrderDetail(stringExtra)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val goodsNameColumn = Column<String>("商品名称", "goodsName")
        val outputQuantityColumn = Column<Int>("数量", "outputQuantity")
        val goodsSpecValueColumn = Column<String>("规格", "goodsSpecValue")
        val tableData = TableData<OrderGoodsDOS>(
            "订单详情",
            ArrayList<OrderGoodsDOS>(),
            goodsNameColumn,
            outputQuantityColumn,
            goodsSpecValueColumn
        )
        smartTable1.tableData = tableData
        smartTable1.config.apply {
            isShowXSequence = false
            isShowYSequence = false
            columnTitleHorizontalPadding = 0
            columnTitleVerticalPadding = 8
            horizontalPadding = 2
            verticalPadding = 1
            minTableWidth = resources.displayMetrics.widthPixels - DensityUtils.dp2px(this@OrderDetailActivity, 16F)
        }

        orderDetailViewModel.orderDetail.observe(this, Observer {
            include1.isGone = true
            textView17.text = it.orderNo
            textView19.text = when (it.state) {
                0 -> "待处理"
                1 -> "已处理"
                else -> "-"
            }
            textView21.text = it.shipNum
            textView23.text = when (it.shipState) {
                -2 -> "暂无"
                0 -> "待拣货"
                1 -> "已拣货"
                3 -> "待出库"
                4 -> "代配送"
                5 -> "配送中"
                6 -> "已完成"
                else -> "-"
            }
            textView25.text = it.pickUpPoint
            textView29.text = when (it.logisticsReq) {
                "1" -> "常温"
                "2" -> "冷藏"
                "3" -> "保鲜"
                else -> "-"
            }
            textView27.text = it.remarks ?: "-"
            smartTable1.tableData.t = it.orderGoodsDOS
            smartTable1.notifyDataChanged()
        })

        orderDetailViewModel.resultMsg.observe(this, Observer {
            showToast(it)
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
