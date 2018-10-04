package com.simple.wanandroid.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author hych
 * @date 2018/9/27 11:28
 */
object NetworkUtil {

    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return !(null == info || !info.isAvailable)
    }

    fun isWifi(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo!=null&&activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }

}