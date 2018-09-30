package com.simple.wanandroid.ui.main

import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.AnimationUtils
import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseActivity
import com.simple.wanandroid.utils.ViewAnimUtils
import kotlinx.android.synthetic.main.activity_search.*

/**
 * @author hych
 * @date 2018/9/30 14:35
 */
class SearchActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_search
    }

    override fun initData() {
    }

    override fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation()
            setUpExitAnimation()
        } else {
            setUpView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpEnterAnimation() {
        val transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition

        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(p0: Transition) {
                p0.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionResume(p0: Transition) {
            }

            override fun onTransitionPause(p0: Transition) {
            }

            override fun onTransitionCancel(p0: Transition) {
            }

            override fun onTransitionStart(p0: Transition) {
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExitAnimation() {
        val fade = Fade()
        window.returnTransition = fade
        fade.duration = 300
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRevealShow() {
        ViewAnimUtils.animateRevealShow(this,
                searchLayout,
                searchTv.width / 2,
                R.color.background_floating_material_dark,
                object : ViewAnimUtils.OnRevealAnimationListener {
                    override fun onRevealHide() {

                    }

                    override fun onRevealShow() {
                        setUpView()
                    }

                })
    }

    private fun setUpView() {
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 300
        searchLayout.startAnimation(animation)
        searchLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimUtils.animateRevealHide(this,
                    searchTv,
                    searchTv.width / 2, R.color.background_floating_material_dark,
                    object : ViewAnimUtils.OnRevealAnimationListener {
                        override fun onRevealHide() {
                            defaultBackPressed()
                        }

                        override fun onRevealShow() {
                        }
                    })
        }
    }

    override fun start() {
    }

    private fun defaultBackPressed() {
        super.onBackPressed()
    }
}