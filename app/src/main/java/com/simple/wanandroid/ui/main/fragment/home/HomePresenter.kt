package com.simple.wanandroid.ui.main.fragment.home

/**
 * @author hych
 * @date 2018/9/28 16:16
 */
class HomePresenter : HomeContract.Presenter {

    private val mHomeModel: HomeModel by lazy { HomeModel() }

    override fun requestHomeData(num: Int) {
    }

    override fun loadMoreData() {
    }

    override fun attachView(rootView: HomeContract.View) {
    }

    override fun detachView() {
    }
}