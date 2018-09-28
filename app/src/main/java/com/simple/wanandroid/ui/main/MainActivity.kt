package com.simple.wanandroid.ui.main

import android.support.v4.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseActivity
import com.simple.wanandroid.ui.main.fragment.home.HomeFragment
import com.simple.wanandroid.ui.main.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author hych
 * @date 2018/9/27 16:16
 */
class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("首页", "我的")
    private val mIconUnSelectIds = intArrayOf(
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round)

    private var mHomeFragment: HomeFragment? = null
    private var mMineFragment: MineFragment? = null

    private var mCurIndex = 0

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
        val tabEntities = ArrayList<CustomTabEntity>()
        val mapTo = 0 until mTitles.size
        mapTo.mapTo(tabEntities) {
            TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])
        }
        commonTabLayout.setTabData(tabEntities)
        commonTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
        switchFragment(mCurIndex)
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideAllFragments(transaction)
        when (position) {
            0 -> {
                mHomeFragment?.let {
                    transaction.show(it)
                } ?: HomeFragment.getInstance(mTitles[position]).let {
                    mHomeFragment = it
                    transaction.add(R.id.containerFl, it, "home")
                }
            }
            1 -> {
                mMineFragment?.let {
                    transaction.show(it)
                } ?: MineFragment.getInstance(mTitles[position]).let {
                    mMineFragment = it
                    transaction.add(R.id.containerFl, it, "mine")
                }
            }
            else -> {
            }
        }

        mCurIndex = position
        commonTabLayout.currentTab = mCurIndex
        transaction.commitAllowingStateLoss()
    }

    private fun hideAllFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }

    override fun start() {
    }

    class TabEntity(var title: String,
                    private var selectedIcon: Int,
                    private var unSelectedIcon: Int)
        : CustomTabEntity {

        override fun getTabUnselectedIcon(): Int {
            return unSelectedIcon
        }

        override fun getTabSelectedIcon(): Int {
            return selectedIcon
        }

        override fun getTabTitle(): String {
            return title
        }
    }
}