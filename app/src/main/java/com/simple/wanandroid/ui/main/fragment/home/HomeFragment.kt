package com.simple.wanandroid.ui.main.fragment.home

import android.view.View
import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author hych
 * @date 2018/9/27 16:22
 */
class HomeFragment : BaseFragment() {

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {

    }

    override fun lazyLoad() {
    }

    fun setClick() {
        homeTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
            }
        })

        homeTv.setOnClickListener({ view: View? ->
        })

        homeTv.setOnClickListener({ view ->
        })

        homeTv.setOnClickListener({
        })

        homeTv.setOnClickListener() {
        }

        homeTv.setOnClickListener { view ->
        }

    }
}