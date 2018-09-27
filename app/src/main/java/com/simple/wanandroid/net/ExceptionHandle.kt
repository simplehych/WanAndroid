package com.simple.wanandroid.net

import com.google.gson.JsonParseException
import com.simple.wanandroid.MLog
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * @author hych
 * @date 2018/9/27 13:58
 */
object ExceptionHandle {

    private const val TAG = "ExceptionHandle"
    var errorCode = ErrorStatus.UNKNOWN_ERROR
    var errorMsg = "请求失败，请稍后重试"

    fun handleException(e: Throwable): String {
        e.printStackTrace()

        fun setErrorInfo(code: Int, msg: String) {
            errorCode = code
            errorMsg = msg
        }

        when (e) {
            is SocketTimeoutException ->
                setErrorInfo(ErrorStatus.NETWORK_ERROR, "网络连接异常")
            is ConnectException ->
                setErrorInfo(ErrorStatus.NETWORK_ERROR, "网络连接异常")
            is JsonParseException ->
                setErrorInfo(ErrorStatus.SERVER_ERROR, "数据解析异常")
            is JSONException ->
                setErrorInfo(ErrorStatus.SERVER_ERROR, "数据解析异常")
            is ParseException ->
                setErrorInfo(ErrorStatus.SERVER_ERROR, "数据解析异常")
            is UnknownHostException ->
                setErrorInfo(ErrorStatus.NETWORK_ERROR, "网络连接异常")
            is IllegalArgumentException ->
                setErrorInfo(ErrorStatus.SERVER_ERROR, "参数错误")
            else ->
                setErrorInfo(ErrorStatus.UNKNOWN_ERROR, "未知错误")
        }

        MLog.e(TAG, "$errorMsg  ${e.message}")
        return errorMsg

    }
}