package com.simple.wanandroid.ui.main.fragment.home

import com.simple.wanandroid.net.RetrofitManager
import com.simple.wanandroid.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author hych
 * @date 2018/9/28 16:18
 */
class HomeModel {

    fun requestHomeData(num: Int): Observable<HomeBean> {
        return RetrofitManager.service
                .getFirstHomeData(num)
                .compose(SchedulerUtils.ioToMain())
    }

    fun loadMoreData(url: String): Observable<HomeBean> {
        return RetrofitManager.service
                .getMoreHomeData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}