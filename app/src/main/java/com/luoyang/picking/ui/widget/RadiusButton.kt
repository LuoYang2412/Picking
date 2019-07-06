package com.luoyang.picking.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.luoyang.picking.R

class RadiusButton(context: Context, attrs: AttributeSet, defStyLeAttr: Int) :
    CardView(context, attrs, defStyLeAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    var textView: TextView = TextView(context)
        private set

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RadiusButton)
        textView.text = attributes.getString(R.styleable.RadiusButton_text) ?: "Radius Button"
        textView.setTextColor(
            attributes.getColor(
                R.styleable.RadiusButton_textColor, Color.parseColor("#54000000")
            )
        )
        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            attributes.getDimension(R.styleable.RadiusButton_textSize, 14F)
        )
        isEnabled = attributes.getBoolean(R.styleable.RadiusButton_enabled, true)
        attributes.recycle()
        textView.gravity = Gravity.CENTER

        val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        layoutParams.leftMargin = pix2dip(8)
        layoutParams.rightMargin = pix2dip(8)
        layoutParams.topMargin = pix2dip(16)
        layoutParams.bottomMargin = pix2dip(16)
        addView(textView, layoutParams)
    }

    private fun pix2dip(pxValue: Int): Int {
        val scale = context.resources.getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }
}