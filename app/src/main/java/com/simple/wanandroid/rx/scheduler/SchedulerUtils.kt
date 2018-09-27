package com.simple.wanandroid.rx.scheduler

/**
 * @author hych
 * @date 2018/9/27 14:33
 */
object SchedulerUtils {

    fun <T> ioToMain(): IoToMainScheduler<T> {
        return IoToMainScheduler()
    }
}