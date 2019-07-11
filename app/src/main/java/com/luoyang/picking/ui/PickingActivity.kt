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
import android.os.SystemClock
import android.os.Vibrator
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.bin.david.form.utils.DensityUtils
import com.luoyang.picking.R
import com.luoyang.picking.data.model.PickingGoods
import com.luoyang.picking.viewmodels.PickingViewModel
import kotlinx.android.synthetic.main.activity_picking.*
import timber.log.Timber


/**拣货*/
class PickingActivity : BaseActivity() {

    companion object {
        fun goIn(context: Context) {
            val intent = Intent(context, PickingActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val pickingViewModel by lazy { ViewModelProviders.of(this).get(PickingViewModel::class.java) }

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
            pickingViewModel.getPinckingInfo(stringExtra)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picking)

        mVibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        //表格
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels / 5
        val column = Column<String>("货架区", "partName")
        column.width = width
        val column1 = Column<String>("商品", "goodsName")
        column1.width = width
        val column2 = Column<String>("规格", "goodsSpecValue")
        column2.width = width / 2
        val column3 = Column<Int>("数量", "outputQuantity")
        column3.width = width / 2
        val tableData = TableData<PickingGoods>("商品明细", ArrayList<PickingGoods>(), column, column1, column2, column3)
        smartTable.tableData = tableData
        smartTable.config.apply {
            isShowXSequence = false
            isShowYSequence = false
            isShowTableTitle = false
            columnTitleHorizontalPadding = 0
            columnTitleVerticalPadding = 8
            horizontalPadding = 2
            verticalPadding = 1
            minTableWidth = metrics.widthPixels - DensityUtils.dp2px(this@PickingActivity, 16F)
        }
        pickingViewModel.pickingInfo.observe(this, Observer {
            include.isGone = true
            radiusButton4.isGone = false

            textView4.text = it.pickingDO.createDate
            textView6.text = it.warehouseName
            textView8.text = it.orderNo
            textView10.text = it.shipNum

            smartTable.tableData.t = it.goodsList
            smartTable.notifyDataChanged()
        })

        //下一步
        radiusButton4.setOnClickListener {
            pickingViewModel.printPincking(pickingViewModel.pickingInfo.value?.pickingDO!!.id)
        }

        pickingViewModel.resultMsg.observe(this, Observer {
            when {
                it == "拣货完成" -> {
                    include.isGone = false
                    radiusButton4.isGone = true
                    showToast(it)
                }
                else -> showToast(it)
            }
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val downTime = SystemClock.uptimeMillis()
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {
                keyDpadUpAction(downTime)
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                keyDpadDownAction(downTime)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 向下按键，模拟表格下滑
     */
    private fun keyDpadDownAction(downTime: Long) {
        val motionEventDown = MotionEvent.obtain(
            downTime,
            downTime,
            MotionEvent.ACTION_DOWN,
            160F,
            180F,
            0
        )
        val motionEventMove = MotionEvent.obtain(
            downTime,
            downTime + 100,
            MotionEvent.ACTION_MOVE,
            160F,
            140F,
            0
        )
        val motionEventUp = MotionEvent.obtain(
            downTime,
            downTime + 200,
            MotionEvent.ACTION_UP,
            50F,
            100F,
            0
        )
        smartTable.onTouchEvent(motionEventDown)
        smartTable.onTouchEvent(motionEventMove)
        smartTable.onTouchEvent(motionEventUp)
    }

    /**
     * 向上按键，模拟表格上滑
     */
    private fun keyDpadUpAction(downTime: Long) {
        val motionEventDown = MotionEvent.obtain(
            downTime,
            downTime,
            MotionEvent.ACTION_DOWN,
            160F,
            100F,
            0
        )
        val motionEventMove = MotionEvent.obtain(
            downTime,
            downTime + 100,
            MotionEvent.ACTION_MOVE,
            160F,
            140F,
            0
        )
        val motionEventUp = MotionEvent.obtain(
            downTime,
            downTime + 200,
            MotionEvent.ACTION_UP,
            50F,
            180F,
            0
        )
        smartTable.onTouchEvent(motionEventDown)
        smartTable.onTouchEvent(motionEventMove)
        smartTable.onTouchEvent(motionEventUp)
    }

    /**
     *使按下菜单按钮时，菜单显示在溢出菜单位置
     */
    private fun getOverflowMenu() {
        try {
            val config = ViewConfiguration.get(this)
            val menuKeyField = ViewConfiguration::class.java.getDeclaredField("sHasPermanentMenuKey")
            if (menuKeyField != null) {
                menuKeyField.isAccessible = true
                menuKeyField.setBoolean(config, false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
