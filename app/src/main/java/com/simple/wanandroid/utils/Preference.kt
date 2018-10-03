package com.simple.wanandroid.utils

import android.content.Context
import android.content.SharedPreferences
import com.simple.wanandroid.MyApplication
import java.io.*
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.reflect.KProperty

/**
 * @author hych
 * @date 2018/10/3 13:27
 */
class Preference<T>(val name: String, private val default: T) {

    companion object {
        private const val file_name = "wan_android_file"

        private val prefs: SharedPreferences by lazy {
            MyApplication.context.getSharedPreferences(file_name, Context.MODE_PRIVATE)
        }

        fun contains(key: String): Boolean {
            return prefs.contains(key)
        }

        fun getAll(): Map<String, *> {
            return prefs.all
        }

        fun clearPrefence(key: String) {
            prefs.edit().remove(key).apply()
        }

        fun clearAllPrefrence() {
            prefs.edit().clear().apply()
        }
    }

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getSharedPreferences(name, default)
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        putSharedPreferences(name, value)
    }

    private fun putSharedPreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Int -> putInt(name, value)
            is Long -> putLong(name, value)
            is Float -> putFloat(name, value)
            is String -> putString(name, value)
            is Boolean -> putBoolean(name, value)
            else -> {
                putString(name, serialize(value))
            }
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSharedPreferences(name: String, value: T): T = with(prefs) {
        val res: Any = when (default) {
            is Int -> getInt(name, default)
            is Long -> getLong(name, default)
            is Float -> getFloat(name, default)
            is String -> getString(name, default)
            is Boolean -> getBoolean(name, default)
            else -> {
                deSerialization(getString(name, serialize(default)))
            }
        }
        return res as T
    }

    /**
     * 序列化对象
     */
    @Throws(IOException::class)
    private fun <T> serialize(obj: T): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var setStr = byteArrayOutputStream.toString("ISO-8859-1")
        setStr = URLEncoder.encode(setStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return setStr
    }

    /**
     * 反序列化对象
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <T> deSerialization(str: String?): T {
        val redStr = URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        val obj = objectInputStream.readObject() as T
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }
}