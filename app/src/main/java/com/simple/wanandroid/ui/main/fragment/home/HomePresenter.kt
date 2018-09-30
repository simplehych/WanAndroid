package com.simple.wanandroid.ui.main.fragment.home

import com.simple.wanandroid.base.BasePresenter
import com.simple.wanandroid.net.ExceptionHandle

/**
 * @author hych
 * @date 2018/9/28 16:16
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private var bannerHomeBean: HomeBean? = null
    private var nextPageUrl: String? = null
    private val homeModel: HomeModel by lazy { HomeModel() }

    override fun requestHomeData(num: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.requestHomeData(num)
                .flatMap { homeBean: HomeBean ->
                    val bannerItemList = homeBean.issueList[0].itemList
                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach {
                        bannerItemList.remove(it)
                    }
                    bannerHomeBean = homeBean
                    homeModel.loadMoreData(homeBean.nextPageUrl)
                }.subscribe({ homeBean ->
                    mRootView?.run {
                        dismissLoading()
                        nextPageUrl = homeBean.nextPageUrl
                        val newBannerItemList = homeBean.issueList[0].itemList

                        bannerHomeBean?.issueList?.apply {
                            this[0].itemList.addAll(newBannerItemList)
                        }
                        setHomeData(bannerHomeBean)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    override fun loadMoreData() {
    }
}