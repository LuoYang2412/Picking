package com.luoyang.picking.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var mToast: Toast? = null

    @SuppressLint("ShowToast")
    fun init(context: Context) {
        if (mToast == null) {
            mToast = Toast.makeText(context.applicationContext, "", Toast.LENGTH_SHORT)
        }
    }

    fun show(msg: String) {
        if (mToast != null) {
            mToast!!.setText(msg)
            mToast!!.show()
        }
    }
}