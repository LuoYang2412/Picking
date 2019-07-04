package com.luoyang.picking.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.luoyang.picking.PickingApplication
import com.luoyang.picking.utils.AlertDialogUtil
import com.luoyang.picking.utils.ToastUtil
import timber.log.Timber

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        PickingApplication.application.addActivity(this)
        initUtil()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUtil() {
        ToastUtil.init(this)
        AlertDialogUtil.init(this)
    }

    override fun onDestroy() {
        PickingApplication.application.removeTopActivity()
        super.onDestroy()
    }

    protected fun showToast(msg: String) {
        Timber.d(msg)
        ToastUtil.show(msg)
    }
}
