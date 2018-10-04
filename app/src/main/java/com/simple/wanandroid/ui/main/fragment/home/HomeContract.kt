package com.simple.wanandroid.ui.main.fragment.home

import com.simple.wanandroid.base.IBaseView
import com.simple.wanandroid.base.IPresenter

/**
 * @author hych
 * @date 2018/9/28 15:58
 */
interface HomeContract {

    interface View : IBaseView {
        fun setHomeData(homeBean: HomeBean)
        fun setMoreData(itemList: ArrayList<Item>)
        fun showError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestHomeData(num: Int)
        fun loadMoreData()
    }

}