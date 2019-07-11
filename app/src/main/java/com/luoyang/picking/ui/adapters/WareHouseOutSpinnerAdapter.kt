package com.luoyang.picking.ui.adapters

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.bin.david.form.utils.DensityUtils
import com.luoyang.picking.data.model.CarsDO
import com.luoyang.picking.data.model.RouteDO
import com.luoyang.picking.data.model.UserDO

class WareHouseOutCarsSpinnerAdapter : BaseAdapter() {
    val data = ArrayList<CarsDO>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: WareHouseOutSpinnerAdapterViewHolder
        if (convertView == null) {
            viewHolder = WareHouseOutSpinnerAdapterViewHolder(parent!!.context)
            view = viewHolder.textView
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as WareHouseOutSpinnerAdapterViewHolder
        }
        viewHolder.textView.text = data[position].numPlate
        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}

class WareHouseOutDriverSpinnerAdapter : BaseAdapter() {
    val data = ArrayList<UserDO>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: WareHouseOutSpinnerAdapterViewHolder
        if (convertView == null) {
            viewHolder = WareHouseOutSpinnerAdapterViewHolder(parent!!.context)
            view = viewHolder.textView
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as WareHouseOutSpinnerAdapterViewHolder
        }
        viewHolder.textView.text = data[position].realName
        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}

class WareHouseOutRougeSpinnerAdapter : BaseAdapter() {
    val data = ArrayList<RouteDO>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: WareHouseOutSpinnerAdapterViewHolder
        if (convertView == null) {
            viewHolder = WareHouseOutSpinnerAdapterViewHolder(parent!!.context)
            view = viewHolder.textView
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as WareHouseOutSpinnerAdapterViewHolder
        }
        viewHolder.textView.text = data[position].name
        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}

class WareHouseOutSpinnerAdapterViewHolder(val context: Context) {
    val textView = TextView(context)

    init {
        val layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 40F))
        textView.layoutParams = layoutParams
        textView.gravity = Gravity.CENTER
    }
}