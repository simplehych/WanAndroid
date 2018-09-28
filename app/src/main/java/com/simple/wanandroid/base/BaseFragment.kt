package com.simple.wanandroid.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author hych
 * @date 2018/9/27 16:21
 */
abstract class BaseFragment : Fragment() {

    private var hasLoadData = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        lazyLoadDataIfPrepared()
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }


    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun lazyLoad()
}