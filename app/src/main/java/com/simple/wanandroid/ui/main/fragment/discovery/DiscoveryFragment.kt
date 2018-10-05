package com.simple.wanandroid.ui.main.fragment.discovery

import android.support.v4.app.Fragment
import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseFragment
import com.simple.wanandroid.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * @author hych
 * @date 2018/10/5 16:06
 */
class DiscoveryFragment : BaseFragment() {

    private val tabList = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val discoveryFragment = DiscoveryFragment()
            discoveryFragment.mTitle = title
            return discoveryFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        tv_header_title.text = mTitle

        tabList.add("关注")
        tabList.add("分类")
//        fragments.add(FollowFragment.getInstance("关注"))
//        fragments.add(CategoryFragment.getInstance("分类"))

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager,fragments,tabList)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun lazyLoad() {
    }
}