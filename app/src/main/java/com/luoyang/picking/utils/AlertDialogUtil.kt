package com.luoyang.picking.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object AlertDialogUtil {
    private var builder: AlertDialog.Builder? = null
    fun init(context: Context) {
        builder = AlertDialog.Builder(context)
    }

    fun show(
        message: String,
        positiveButtonText: String,
        outCacele: Boolean,
        listener: DialogInterface.OnClickListener
    ) {
        if (builder != null) {
            builder!!
                .setMessage(message)
                .setPositiveButton(positiveButtonText, listener)
                .create()
                .apply {
                    setCanceledOnTouchOutside(outCacele)
                    setCancelable(outCacele)
                }
                .show()
        }
    }
}