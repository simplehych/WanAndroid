package com.simple.wanandroid.ui.main.fragment.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseFragment
import com.simple.wanandroid.ui.main.SearchActivity
import com.simple.wanandroid.ui.main.fragment.home.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author hych
 * @date 2018/9/27 16:22
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    private var mTitle: String? = null
    private val num: Int = 1
    private var mHomeAdapter: HomeAdapter? = null
    private var loadingMore = false
    private var isRefresh = false
    private var mMaterialHeader: MaterialHeader? = null

    companion object {
        fun getInstance(title: String): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            homeFragment.arguments = bundle
            homeFragment.mTitle = title
            return homeFragment
        }
    }

    private val mPresenter by lazy {
        HomePresenter()
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private val simpleDateFormat by lazy {
//        SimpleDateFormat("- MMM. dd, 'Brunch' -", java.util.Locale.ENGLISH)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        mPresenter.attachView(this)
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader
        mMaterialHeader?.setShowBezierWave(true)
        mRefreshLayout.setPrimaryColorsId(R.color.primary_material_dark)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager?.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    println("/  firstVisibleItem: $firstVisibleItem  /  childCount: $childCount   /   itemCount: $itemCount  ")
                    if (firstVisibleItem + childCount > itemCount!!) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    toolbar.setBackgroundColor(resources.getColor(android.R.color.transparent))
                    iv_search.setImageResource(R.mipmap.ic_launcher)
                    tv_header_title.text = ""
                } else {
                    toolbar.setBackgroundColor(resources.getColor(android.R.color.transparent))
                    iv_search.setImageResource(R.mipmap.ic_launcher)
                    tv_header_title.text = ""
                }
            }
        })

        iv_search.setOnClickListener { openSearchActivity() }
    }

    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = activity?.let {
                ActivityOptionsCompat.makeSceneTransitionAnimation(it, iv_search, iv_search.transitionName)
            }
            startActivity(Intent(activity, SearchActivity::class.java), options?.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    fun setClick() {
        tv_header_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
            }
        })

        tv_header_title.setOnClickListener({ view: View? ->
        })

        tv_header_title.setOnClickListener({ view ->
        })

        tv_header_title.setOnClickListener({
        })

        tv_header_title.setOnClickListener() {
        }

        tv_header_title.setOnClickListener { view ->
        }

    }

    override fun setHomeData(homeBean: HomeBean) {
        // Adapter
        mHomeAdapter = activity?.let {
            HomeAdapter(it, homeBean.issueList[0].itemList)
        }
        //设置 banner 大小
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)

        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {
    }

    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
        }
    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }
}