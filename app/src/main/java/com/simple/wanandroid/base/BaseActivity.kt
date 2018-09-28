package com.simple.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author hych
 * @date 2018/9/27 15:44
 */
abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(layoutId())
        initData()
        initView()
        start()
    }


    abstract fun layoutId(): Int
    abstract fun initData()
    abstract fun initView()
    abstract fun start()
}