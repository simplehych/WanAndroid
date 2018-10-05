package com.simple.wanandroid.ui.main.fragment.vediodetail

import android.app.Activity
import com.simple.wanandroid.MyApplication
import com.simple.wanandroid.base.BasePresenter
import com.simple.wanandroid.dataFormat
import com.simple.wanandroid.net.ExceptionHandle
import com.simple.wanandroid.ui.main.fragment.home.Item
import com.simple.wanandroid.utils.NetworkUtil
import com.simple.wanandroid.utils.ToastUtils

/**
 * @author hych
 * @date 2018/10/4 16:23
 */
class VideoDetailPresenter : BasePresenter<VideoDetailContract.View>(), VideoDetailContract.Presenter {

    private val videoDetailModel: VideoDetailModel by lazy {
        VideoDetailModel()
    }

    override fun loadVideoInfo(itemInfo: Item) {
        val activity = (mRootView as Activity)
        checkViewAttached()
        val playInfo = itemInfo.data?.playInfo
        val wifi = NetworkUtil.isWifi(activity)
        playInfo?.run {
            if (size > 1) {
                for (i in playInfo) {
                    if (wifi) {
                        i.type = "high"
                    } else {
                        i.type = "normal"
                        ToastUtils.showShort(activity,
                                "本次消耗${dataFormat(i.urlList[0].size)}流量")
                    }
                    mRootView?.setVideo(i.url)
                    break
                }
            } else {
                mRootView?.setVideo(itemInfo.data.playUrl)
            }
        }
        //设置背景
        val displayMetrics = activity.resources.displayMetrics
        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels
        val backgroundUrl = itemInfo.data?.cover?.blurred + "/thumbnail/${widthPixels}x$heightPixels"
        mRootView?.setBackground(backgroundUrl)
        mRootView?.setVideoInfo(itemInfo)
    }

    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = videoDetailModel.requestRelatedData(id)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        setRecentRelatedVideo(issue.itemList)

                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }

                })
        addSubscription(disposable)
    }

    override fun saveWatchVideoHistory(itemInfo: Item) {

    }
}