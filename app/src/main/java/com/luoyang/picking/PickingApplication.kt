package com.luoyang.picking

import android.app.Activity
import android.app.Application
import android.os.Process
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.luoyang.picking.data.model.UserInfo
import timber.log.Timber


class PickingApplication : Application() {
    private val activitys = ArrayList<Activity>()
    private val preference by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    var userInfo: UserInfo? = null
        set(value) {
            field = value
            preference.edit().putString("userInfo", Gson().toJson(value)).apply()
        }
        get() {
            if (field == null) {
                val string = preference.getString("userInfo", null)
                if (string != null) {
                    return Gson().fromJson(string, UserInfo::class.java)
                } else {
                    return null
                }
            } else {
                return field
            }
        }

    companion object {
        lateinit var application: PickingApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        initTools()
    }

    fun addActivity(activity: Activity) {
        activitys.add(activity)
    }

    fun removeTopActivity() {
        activitys.removeAt(activitys.size - 1)
    }

    fun exit() {
        activitys.map {
            it.finish()
        }
        activitys.clear()
        preference.edit().clear().apply()
        Process.killProcess(Process.myPid())
    }

    private fun initTools() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}