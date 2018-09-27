package com.simple.wanandroid.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * @author hych
 * @date 2018/9/27 14:46
 */
class CustomAppGlideModule : AppGlideModule() {

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool,ArrayPool,MemoryCache等等)
     */
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //重新设置内存限制
        builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024))
    }

    /**
     * 清单解析的开启
     * 这里不开启，避免添加相同的modules俩次
     */
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    /**
     * 为App注册一个自定义的String类型的BaseGlideUrlLoader
     */
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(String::class.java,InputStream::class.java,CustomModelLoaderFactory())
    }
}