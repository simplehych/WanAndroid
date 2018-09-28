package com.simple.wanandroid.utils

import android.content.Context
import android.widget.Toast
import kotlin.properties.Delegates

/**
 * @author hych
 * @date 2018/9/28 10:39
 */
object ToastUtils {
    private var mToast: Toast? = null
    private lateinit var m: Toast
    private val a: Int by Delegates.notNull<Int>()

    fun showShort(context: Context, message: String) {
        mToast?.let {
            it.setText(message)
        } ?: Toast.makeText(context, message, Toast.LENGTH_SHORT).let {
            mToast = it
        }
        mToast?.show()
    }

    fun <T1, T2> ifNotNull(value1: T1?,
                           value2: T2?,
                           bothNotNull: (T1, T2) -> Unit) {

        if (value1 != null && value2 != null) {
            bothNotNull(value1, value2)
        }
    }
}