package com.simple.wanandroid.ui.main.fragment.vediodetail

import android.content.res.Configuration
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.widget.ImageView
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.simple.wanandroid.R
import com.simple.wanandroid.base.BaseActivity
import com.simple.wanandroid.glide.GlideApp
import com.simple.wanandroid.sdkExceedLollipop
import com.simple.wanandroid.ui.main.fragment.home.Item
import com.simple.wanandroid.utils.MLog
import kotlinx.android.synthetic.main.activity_video_detail.*

/**
 * @author hych
 * @date 2018/10/4 15:21
 */
class VideoDetailActivity : BaseActivity(), VideoDetailContract.View {

    private var isTransition = false
    private lateinit var itemData: Item
    private var itemList = ArrayList<Item>()
    private var transition: Transition? = null

    private var orientationUtils: OrientationUtils? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    private val mAdapter by lazy {
        VideoDetailAdapter(this, itemList)
    }

    private val mPresenter by lazy { VideoDetailPresenter() }

    companion object {
        const val TAG = "VideoDetailActivity"
        const val BUNDLE_VIDEO_DATA = "video_data"
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"
    }

    override fun layoutId(): Int = R.layout.activity_video_detail

    override fun initData() {
        itemData = intent.getSerializableExtra(BUNDLE_VIDEO_DATA) as Item
        isTransition = intent.getBooleanExtra(TRANSITION, false)

        mPresenter.saveWatchVideoHistory(itemData)
    }

    override fun initView() {
        mPresenter.attachView(this)
        initTransition()
        initVideoViewConfig()

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        mAdapter.setOnItemDetailClick { mPresenter.loadVideoInfo(it) }
    }

    override fun start() {
    }


    fun loadVideoInfo() {
        mPresenter.loadVideoInfo(itemData)
    }

    override fun setVideo(url: String) {
        MLog.i(TAG, "setVideo url: $url")
        mVideoView.setUp(url, false, "")
        mVideoView.startPlayLogic()
    }

    override fun setVideoInfo(itemInfo: Item) {
        itemData = itemInfo
        mAdapter.addData(itemInfo)
        // 请求相关的最新等视频
        mPresenter.requestRelatedVideo(itemInfo.data?.id?:0)
    }

    override fun setBackground(url: String) {
        GlideApp.with(this)
                .load(url)
                .centerCrop()
                .format(DecodeFormat.PREFER_RGB_565)
                .transition(DrawableTransitionOptions().crossFade())
                .into(mVideoBackground)
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<Item>) {
        mAdapter.addData(itemList)
        this.itemList = itemList
    }

    override fun setErrorMsg(errorMsg: String) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    private fun initVideoViewConfig() {
        orientationUtils = OrientationUtils(this, mVideoView)
        mVideoView.isRotateViewAuto = false
        mVideoView.setIsTouchWiget(true)

        val videoThumb = ImageView(this)
        videoThumb.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideApp.with(this)
                .load(itemData.data?.cover?.feed)
                .centerCrop()
                .into(videoThumb)
        mVideoView.thumbImageView = videoThumb

        mVideoView.setStandardVideoAllCallBack(object : VideoListener {

            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.backToProtVideo()
            }
        })

        mVideoView.backButton.setOnClickListener { onBackPressed() }

        mVideoView.fullscreenButton.setOnClickListener {
            orientationUtils?.resolveByClick()
            mVideoView.startWindowFullscreen(this, true, true)
        }

        //锁屏事件
        mVideoView.setLockClickListener { _, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils?.isEnable = !lock
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    private fun initTransition() {
        if (isTransition && sdkExceedLollipop()) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            loadVideoInfo()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

            override fun onTransitionEnd(p0: Transition?) {
                loadVideoInfo()
                transition?.removeListener(this)
            }

        })
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) return

        //释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()

        if (isTransition && sdkExceedLollipop()) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else {
            mVideoView
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
        mPresenter.detachView()
    }
}