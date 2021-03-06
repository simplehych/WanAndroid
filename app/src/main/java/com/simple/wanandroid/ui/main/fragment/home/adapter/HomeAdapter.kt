package com.simple.wanandroid.ui.main.fragment.home.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.simple.wanandroid.R
import com.simple.wanandroid.durationFormat
import com.simple.wanandroid.glide.GlideApp
import com.simple.wanandroid.ui.main.fragment.home.Item
import com.simple.wanandroid.ui.main.fragment.vediodetail.VideoDetailActivity
import io.reactivex.Observable

/**
 * @author hych
 * @date 2018/9/30 11:05
 */
class HomeAdapter(var mContext: Context,
                  var mData: ArrayList<Item>)
    : RecyclerView.Adapter<ViewHolder>() {

    protected var mInflater: LayoutInflater? = null
    private var mTypeSupport: MultiType<Item>? = null
    var bannerItemSize = 0

    companion object {
        const val ITEM_TYPE_BANNER = 1
        const val ITEM_TYPE_TEXT_HEADER = 2
        const val ITEM_TYPE_CONTENT = 3
    }

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    constructor(context: Context, data: ArrayList<Item>, typeSupport: MultiType<Item>)
            : this(context, data) {
        mTypeSupport = typeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER ->
                ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_TEXT_HEADER ->
                ViewHolder(inflaterView(R.layout.item_home_header, parent))
            else ->
                ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }

    private fun inflaterView(layoutId: Int, parent: ViewGroup): View {
        val view = mInflater?.inflate(layoutId, parent, false)
        return view ?: View(mContext)

    }

    override fun getItemCount(): Int {
        return when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" -> ITEM_TYPE_TEXT_HEADER
            else -> ITEM_TYPE_CONTENT
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Item = mData[position]
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<Item> = mData.asSequence().take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()

                Observable.fromIterable(bannerItemData)
                        .subscribe { list ->
                            bannerFeedList.add(list.data?.cover?.feed ?: "")
                            bannerTitleList.add(list.data?.title ?: "")
                        }

                val bgaBanner = holder.getView<BGABanner>(R.id.banner)

                bgaBanner.run {
                    setAutoPlayAble(bannerFeedList.size > 1)
                    setData(bannerFeedList, bannerTitleList)
                    setAdapter { banner, _, imageUrl, position ->
                        GlideApp.with(mContext)
                                .load(imageUrl)
                                .transition(DrawableTransitionOptions().crossFade())
                                .into(banner.getItemImageView(position))
                    }
                    setDelegate { _, imageView, _, position ->
                        goToVideoPlayer(mContext as Activity, imageView, bannerItemData[position])
                    }
                }

            }
            ITEM_TYPE_TEXT_HEADER -> {
                holder.setText(R.id.tvHeader, mData[position + bannerItemSize - 1].data?.text ?: "")
            }
            else -> {
                setVideoItem(holder, mData[position + bannerItemSize - 1])
            }
        }
    }

    private fun setVideoItem(holder: ViewHolder, item: Item) {
        val itemData = item.data

        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        // 加载封页图
        GlideApp.with(mContext)
                .load(cover)
                .placeholder(R.drawable.placeholder_banner)
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_cover_feed))

        // 如果提供者信息为空，就显示默认
        if (avatar.isNullOrEmpty()) {
            GlideApp.with(mContext)
                    .load(defAvatar)
                    .placeholder(R.mipmap.default_avatar).circleCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))

        } else {
            GlideApp.with(mContext)
                    .load(avatar)
                    .placeholder(R.mipmap.default_avatar).circleCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))
        }
        holder.setText(R.id.tv_title, itemData?.title ?: "")

        //遍历标签
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.setText(R.id.tv_tag, tagText!!)

        holder.setText(R.id.tv_category, "#" + itemData?.category)

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), item)
        })


    }

    private fun goToVideoPlayer(activity: Activity, view: View, itemData: Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(VideoDetailActivity.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, pair)
            ActivityCompat.startActivity(activity, intent, options.toBundle())

        } else {
            activity.startActivity(intent)
        }
    }

    public fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    fun addItemData(itemList: ArrayList<Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    interface MultiType<T> {
        fun getLayoutId(item: T, position: Int): Int
    }
}


