package com.simple.wanandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 * @author hych
 * @date 2018/9/27 10:29
 */
class MyApplication : Application() {

    private var refWatcher: RefWatcher? = null

    companion object {
        private const val TAG = "MyApplication"
        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as MyApplication
            return myApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initConfig()
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    /**
     * 初始化配置
     */
    private fun initConfig() {

    }

    /**
     * 注册监听activity声明周期
     */
    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(p0: Activity?) {
            MLog.i(TAG, "${p0!!.componentName.className} onActivityPaused")
        }

        override fun onActivityResumed(p0: Activity?) {
            MLog.i(TAG, "${p0!!.componentName.className} onActivityResumed")
        }

        override fun onActivityStarted(p0: Activity?) {
            MLog.i(TAG, "${p0!!.componentName.className} onActivityStarted")
        }

        override fun onActivityDestroyed(p0: Activity?) {
            MLog.i(TAG, "${p0!!.componentName.className} onActivityDestroyed")
        }

        override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            MLog.i(TAG, "${p0!!.componentName.className} onActivitySaveInstanceState $p1")
        }

        override fun onActivityStopped(p0: Activity?) {
            MLog.i(TAG, "${p0!!.componentName.className} onActivityStopped")
        }

        override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
            MLog.i(TAG, "${p0!!.componentName.className} onActivityCreated $p1")
        }

    }
}