package com.luoyang.picking.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import com.luoyang.picking.R

class RadiusButton(context: Context, attrs: AttributeSet, defStyLeAttr: Int) :
    CardView(context, attrs, defStyLeAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    var textView: TextView = TextView(context)
        private set

    @ColorInt
    var mCardBackgroundColor = resources.getColor(android.R.color.white)
    @ColorInt
    var mTextColor = resources.getColor(R.color.mtrl_chip_text_color)
    var mCardElevation = 0F

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RadiusButton)
        if (attributes != null) {
            textView.text = attributes.getString(R.styleable.RadiusButton_text) ?: "Radius Button"
            mTextColor = attributes.getColor(R.styleable.RadiusButton_textColor, textView.currentTextColor)
            textView.setTextColor(mTextColor)
            isEnabled = attributes.getBoolean(R.styleable.RadiusButton_enabled, true)
        }
        attributes.recycle()

        val attributes1 = context.obtainStyledAttributes(attrs, R.styleable.CardView)
        if (attributes1 != null) {
            mCardBackgroundColor = attributes1.getColor(
                R.styleable.CardView_cardBackgroundColor,
                resources.getColor(android.R.color.white)
            )
            mCardElevation = attributes1.getDimension(R.styleable.CardView_cardElevation, 0F)
        }
        attributes1.recycle()

        val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        layoutParams.leftMargin = 16
        layoutParams.rightMargin = 16
        layoutParams.topMargin = 40
        layoutParams.bottomMargin = 40
        addView(textView, layoutParams)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (isEnabled) {
            setCardBackgroundColor(mCardBackgroundColor)
            textView.setTextColor(mTextColor)
            cardElevation = mCardElevation
        } else {
            setCardBackgroundColor(Color.parseColor("#FFD6D7D7"))
            textView.setTextColor(Color.parseColor("#FFA0A0A0"))
            cardElevation = 0F
        }
    }

}