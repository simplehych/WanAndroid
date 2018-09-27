package com.simple.wanandroid.base

/**
 * @author hych
 * @date 2018/9/27 16:13
 */
interface IPresenter<in V : IBaseView> {

    fun attachView(rootView: V)
    fun detachView()
}