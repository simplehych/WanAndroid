package com.simple.wanandroid.ui.main

import android.app.Activity
import android.content.Intent
import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * @author hych
 * @date 2018/9/28 14:51
 */
class SplashActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
    }

    override fun initView() {

        Observable.timer(2, TimeUnit.SECONDS).subscribe(object : Observer<Long> {
            override fun onComplete() {
                val intent = Intent(mContext as Activity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Long) {
            }

            override fun onError(e: Throwable) {
            }

        })

    }

    override fun start() {
    }
}