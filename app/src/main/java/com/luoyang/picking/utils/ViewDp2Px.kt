package com.luoyang.picking.utils

import android.content.Context

/**
 * View dp px 转换工具类
 */
class ViewDp2Px {
    companion object {
        /**
         * dp 转 px
         *
         * @param context [Context]
         * @param dpValue `dpValue`
         * @return `pxValue`
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.getDisplayMetrics().density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * px 转 dp
         *
         * @param context [Context]
         * @param pxValue `pxValue`
         * @return `dpValue`
         */
        fun pix2dip(context: Context, pxValue: Int): Int {
            val scale = context.resources.getDisplayMetrics().density
            return (pxValue / scale + 0.5f).toInt()
        }
    }
}