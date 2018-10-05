package com.simple.wanandroid.ui.main.fragment.vediodetail

import com.simple.wanandroid.base.IBaseView
import com.simple.wanandroid.base.IPresenter
import com.simple.wanandroid.ui.main.fragment.home.Item

/**
 * @author hych
 * @date 2018/10/4 16:16
 */
interface VideoDetailContract {

    interface View : IBaseView {

        fun setVideo(url: String)

        fun setVideoInfo(itemInfo: Item)

        fun setBackground(url: String)

        fun setRecentRelatedVideo(itemList: ArrayList<Item>)

        fun setErrorMsg(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        fun loadVideoInfo(itemInfo: Item)

        fun requestRelatedVideo(id: Long)

        fun saveWatchVideoHistory(itemInfo: Item)
    }
}