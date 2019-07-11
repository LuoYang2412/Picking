package com.luoyang.picking.ui.adapters

import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.luoyang.picking.R

class GoodsAdapter : BaseItemDraggableAdapter<String, BaseViewHolder>(R.layout.item_goods, ArrayList<String>()) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.textView2, item?:"")
    }
}