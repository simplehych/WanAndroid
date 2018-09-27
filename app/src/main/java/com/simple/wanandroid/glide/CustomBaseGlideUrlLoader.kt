package com.simple.wanandroid.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Pattern

/**
 * @author hych
 * @date 2018/9/27 14:54
 */
class CustomBaseGlideUrlLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>, modelCache: ModelCache<String, GlideUrl>)
    : BaseGlideUrlLoader<String>(concreteLoader, modelCache) {

    companion object {

        val urlCache = ModelCache<String, GlideUrl>(150)
        /**
         * Url的匹配规则
         */
        private val PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__")
    }

    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        val m = PATTERN.matcher(model)
        var bestBucket: Int
        if (m.find()) {
            val found = m.group(1)
                    .split("-".toRegex())
                    .dropLastWhile {
                        it.isEmpty()
                    }
                    .toTypedArray()

            for (bucketStr in found) {
                bestBucket = Integer.parseInt(bucketStr)
                if (bestBucket >= width) {
                    break
                }
            }
        }
        return model
    }

    override fun handles(model: String): Boolean {
        return true
    }
}

class CustomModelLoaderFactory : ModelLoaderFactory<String, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
        return CustomBaseGlideUrlLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java), CustomBaseGlideUrlLoader.urlCache)
    }

    override fun teardown() {
    }
}