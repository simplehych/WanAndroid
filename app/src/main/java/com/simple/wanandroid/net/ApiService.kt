package com.simple.wanandroid.net

import com.simple.wanandroid.ui.main.fragment.home.HomeBean
import com.simple.wanandroid.ui.main.fragment.home.Issue
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @author hych
 * @date 2018/9/27 09:16
 */
interface ApiService {

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * 根据 nextPageUrl请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related")
    fun getVideoRelatedData(@Query("id") id: Long): Observable<Issue>

}