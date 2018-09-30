package com.simple.wanandroid.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.RuntimeException

/**
 * @author hych
 * @date 2018/9/30 09:12
 */
open class BasePresenter<T : IBaseView> : IPresenter<T> {

    var mRootView: T? = null
        private set

    private val isViewAttached: Boolean
        get() = mRootView != null

    private var compositeDisposable = CompositeDisposable()

    override fun attachView(rootView: T) {
        mRootView = rootView
    }

    override fun detachView() {
        mRootView = null

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor()
        : RuntimeException("Please call IPresenter.attachView(IBaseView) before requesting data to the IPresenter")

}