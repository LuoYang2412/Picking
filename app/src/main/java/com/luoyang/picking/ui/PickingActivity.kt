package com.luoyang.picking.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewConfiguration
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.luoyang.picking.R
import com.luoyang.picking.data.model.PickingClassify
import com.luoyang.picking.data.model.PickingGoods
import com.luoyang.picking.utils.ViewDp2Px
import com.luoyang.picking.viewmodels.PickingViewModel
import kotlinx.android.synthetic.main.activity_picking.*


/**拣货*/
class PickingActivity : BaseActivity() {

    companion object {
        fun goIn(context: Context) {
            val intent = Intent(context, PickingActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var menuData = ArrayList<PickingClassify>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picking)

        //筛选菜单
        getOverflowMenu()
        val pickingViewModel = ViewModelProviders.of(this).get(PickingViewModel::class.java)
        pickingViewModel.getPickingClassify()
        pickingViewModel.pickingClassifys.observe(this, Observer {
            menuData = it
        })

        //表格
        pickingViewModel.getPinckingGoods()
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels / 5
        val column = Column<String>("货架区", "shelf_area")
        column.width = width
        val column1 = Column<String>("商品", "goods")
        column1.width = width
        val column2 = Column<String>("规格", "specification")
        column2.width = width
        val column3 = Column<Int>("数量", "count")
        column3.width = width
        val tableData = TableData<PickingGoods>("商品明细", ArrayList<PickingGoods>(), column, column1, column2, column3)
        smartTable.tableData = tableData
        smartTable.config.apply {
            isShowXSequence = false
            isShowYSequence = false
            isShowTableTitle = false
            columnTitleHorizontalPadding = 0
            columnTitleVerticalPadding = 8
            horizontalPadding = 2
            verticalPadding = 0
            minTableWidth = metrics.widthPixels - ViewDp2Px.dip2px(this@PickingActivity, 16F)
        }
        pickingViewModel.pickingGoods.observe(this, Observer {
            smartTable.tableData.t = it
            smartTable.notifyDataChanged()
        })

        //跳过
        radiusButton.setOnClickListener {
            //TODO 获取下一条数据
        }

        //确定打印
        radiusButton4.setOnClickListener {
            //TODO 打印
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        val menuItem = menu?.add("拣货分类")
        menuItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuData.map {
            menu?.add(Menu.NONE, it.id, Menu.NONE, it.neme)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 0) {
            openOptionsMenu()
        } else if (item?.itemId != android.R.id.home) {
            showToast("分类：_${item?.itemId}")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openOptionsMenu() {
        val toolbar = window.decorView.findViewById<View>(R.id.action_bar)
        if (toolbar is Toolbar) {
            //默认actionBar时，显示溢出菜单
            toolbar.showOverflowMenu()
        } else {
            super.openOptionsMenu()
        }
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
