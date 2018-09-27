package com.simple.wanandroid.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author hych
 * @date 2018/9/27 14:33
 */
class IoToMainScheduler<T> : BaseScheduler<T>(Schedulers.io(),AndroidSchedulers.mainThread())