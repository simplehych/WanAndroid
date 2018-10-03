package com.simple.wanandroid.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * @author hych
 * @date 2018/9/30 16:21
 */
object ViewAnimUtils {

    interface OnRevealAnimationListener {
        fun onRevealHide()
        fun onRevealShow()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateRevealShow(context: Context,
                          view: View,
                          startRadius: Int,
                          color: Int,
                          listener: OnRevealAnimationListener) {

        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2

        val finalRadius = Math.hypot(view.width.toDouble(), view.height.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius.toFloat(), finalRadius)
        anim.duration = 300
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                view.setBackgroundColor(color)
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                view.visibility = View.VISIBLE
                listener.onRevealShow()
            }
        })
        anim.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateRevealHide(context: Context,
                          view: View,
                          finalRadius: Int,
                          color: Int,
                          listener: OnRevealAnimationListener) {


        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2
        val initialRadius = view.width
        // 与入场动画的区别就是圆圈起始和终止的半径相反
        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius.toFloat(), finalRadius.toFloat())
        anim.duration = 300
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                view.setBackgroundColor(color)
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                listener.onRevealHide()
                view.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }
}