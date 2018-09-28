package com.simple.wanandroid.ui.main.fragment

import android.view.View
import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseFragment
import com.simple.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @author hych
 * @date 2018/9/27 16:22
 */
class MineFragment : BaseFragment(), View.OnClickListener {


    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {
        avatarIv.setOnClickListener(this)
        nicknameTv.setOnClickListener(this)
        descriptionTv.setOnClickListener(this)
        messageTv.setOnClickListener(this)
        collectionTv.setOnClickListener(this)
        settingTv.setOnClickListener(this)
        feedbackTv.setOnClickListener(this)
        aboutTv.setOnClickListener(this)
    }

    override fun lazyLoad() {
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when (id) {
            R.id.avatarIv -> {
                showShort("avatarIv")
            }
            R.id.nicknameTv -> {
                showShort("nicknameTv")
            }
            R.id.descriptionTv -> {
                showShort("descriptionTv")
            }
            R.id.messageTv -> {
                showShort("messageTv")
            }
            R.id.collectionTv -> {
                showShort("collectionTv")
            }
            R.id.settingTv -> {
                showShort("settingTv")
            }
            R.id.feedbackTv -> {
                showShort("feedbackTv")
            }
            R.id.aboutTv -> {
                showShort("aboutTv")
            }
            else -> {
            }
        }
    }

    private fun showShort(message: String) {
        context?.let { ToastUtils.showShort(it, message) }
    }

}