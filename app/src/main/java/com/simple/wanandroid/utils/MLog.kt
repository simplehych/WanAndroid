package com.simple.wanandroid.utils

import android.util.Log

/**
 * @author hych
 * @date 2018/9/27 11:01
 */
object MLog {

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }
}