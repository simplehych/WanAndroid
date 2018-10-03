package com.simple.wanandroid.net

import android.os.Build
import com.simple.wanandroid.MyApplication
import com.simple.wanandroid.utils.NetworkUtil
import com.simple.wanandroid.utils.Preference
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author hych
 * @date 2018/9/27 09:15
 */
object RetrofitManager {

    private var token: String by Preference("token", "")

    val service: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(UrlConstant.BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器，打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平，body，basic，headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //设置请求的缓存的大小跟位置
        val cacheFile = File(MyApplication.context.cacheDir, "cache")
        //50Mb缓存大小
        val cache = Cache(cacheFile, 1024 * 1024 * 50)
        return OkHttpClient.Builder()
                .addInterceptor(addQueryParameterInterceptor())
                .addInterceptor(addHeaderInterceptor())
//                .addInterceptor(addCacheInterceptor())
                .cache(cache)
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build()
    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                    .addQueryParameter("deviceModel", getMobileModel())
                    .build()
            val request = originalRequest.newBuilder()
                    .url(modifiedUrl)
                    .build()
            chain.proceed(request)
        }
    }

    /**
     * 设置请求头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder()
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
                    .build()
            chain.proceed(request)
        }
    }

    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                val maxAge = 0
                response.newBuilder()
                        .header("Cache-Control", "public,max-age=" + maxAge)
                        .removeHeader("Retrofit")
                        .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
        }
    }

    fun getMobileModel(): String {
        var model: String? = Build.MODEL
        model = model?.trim { it <= ' ' } ?: ""
        return model
    }
}