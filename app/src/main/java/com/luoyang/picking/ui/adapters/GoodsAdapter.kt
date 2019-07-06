package com.luoyang.picking.ui.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.luoyang.picking.R
import com.luoyang.picking.data.model.Goods

class GoodsAdapter : BaseQuickAdapter<Goods, BaseViewHolder>(R.layout.item_goods) {
    override fun convert(helper: BaseViewHolder?, item: Goods?) {
        helper?.setText(R.id.textView2, item?.goods_id)
        helper?.setChecked(R.id.checkBox1, item!!.check)
    }

}