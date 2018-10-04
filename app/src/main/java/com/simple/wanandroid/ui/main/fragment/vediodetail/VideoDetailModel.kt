package com.simple.wanandroid.ui.main.fragment.vediodetail

import com.simple.wanandroid.net.RetrofitManager
import com.simple.wanandroid.rx.scheduler.SchedulerUtils
import com.simple.wanandroid.ui.main.fragment.home.Issue
import io.reactivex.Observable

/**
 * @author hych
 * @date 2018/10/4 16:25
 */
class VideoDetailModel {

    fun requestRelatedData(id: Long): Observable<Issue> {
        return RetrofitManager.service
                .getVideoRelatedData(id)
                .compose(SchedulerUtils.ioToMain())
    }
}