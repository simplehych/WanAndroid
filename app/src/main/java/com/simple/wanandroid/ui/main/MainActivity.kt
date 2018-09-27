package com.simple.wanandroid.ui.main

import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseActivity

/**
 * @author hych
 * @date 2018/9/27 16:16
 */
class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")
    private val mIconUnSelectIds = intArrayOf(
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round)

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}