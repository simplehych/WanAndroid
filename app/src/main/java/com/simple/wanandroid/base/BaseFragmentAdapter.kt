package com.simple.wanandroid.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author hych
 * @date 2018/10/5 16:13
 */
class BaseFragmentAdapter : FragmentPagerAdapter {

    private var mFragmentList: List<Fragment>? = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fragmentManager: FragmentManager,
                fragmentList: List<Fragment>,
                titles: List<String>) : super(fragmentManager) {
        setFragments(fragmentManager, fragmentList, titles)
    }

    private fun setFragments(fragmentManager: FragmentManager, fragmentList: List<Fragment>, titles: List<String>) {
        mTitles = titles
        fragmentList?.let {
            val beginTransaction = fragmentManager.beginTransaction()
            fragmentList.forEach {
                beginTransaction.remove(it)
            }
            beginTransaction.commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        mFragmentList = fragmentList
        notifyDataSetChanged()
    }

    override fun getItem(p0: Int): Fragment {
        return mFragmentList?.get(p0) ?: Fragment()
    }

    override fun getCount(): Int {
        return mFragmentList?.size ?: 0
    }
}