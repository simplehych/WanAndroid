package com.simple.wanandroid.ui.main.fragment.vediodetail

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.simple.wanandroid.MyApplication
import com.simple.wanandroid.R
import com.simple.wanandroid.durationFormat
import com.simple.wanandroid.glide.GlideApp
import com.simple.wanandroid.glide.GlideRoundTransform
import com.simple.wanandroid.ui.main.fragment.home.Item
import com.simple.wanandroid.ui.main.fragment.home.adapter.ViewHolder

/**
 * @author hych
 * @date 2018/10/5 13:10
 */
class VideoDetailAdapter(var mContext: Context, var mData: ArrayList<Item>)
    : RecyclerView.Adapter<ViewHolder>() {

    private var mOnItemClickRelatedVideo: ((Item) -> Unit)? = null

    private var textTypeface: Typeface? = null

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(mContext).inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> R.layout.item_video_detail_info
            mData[position].type == "textCard" -> R.layout.item_video_text_card
            mData[position].type == "videoSmallCard" -> R.layout.item_video_small_card
            else -> throw IllegalAccessException("APi 解析出错，出现其他类型")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mData[position]
        when {
            position == 0 -> setVideoDetailInfo(data, holder)

            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)
                //设置方正兰亭细黑简体
                holder.getView<TextView>(R.id.tv_text_card).typeface = textTypeface

            }
            data.type == "videoSmallCard" -> {
                with(holder) {
                    setText(R.id.tv_title, data.data?.title!!)
                    setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
                    setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                        override fun loadImage(iv: ImageView, path: String) {
                            GlideApp.with(mContext)
                                    .load(path)
                                    .optionalTransform(GlideRoundTransform())
                                    .placeholder(R.drawable.placeholder_banner)
                                    .into(iv)
                        }
                    })
                }
                // 判断onItemClickRelatedVideo 并使用
                holder.itemView.setOnClickListener { mOnItemClickRelatedVideo?.invoke(data) }

            }
            else -> throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    private fun setVideoDetailInfo(data: Item, holder: ViewHolder) {
        data.data?.title?.let { holder.setText(R.id.tv_title, it) }
        //视频简介
        data.data?.description?.let { holder.setText(R.id.expandable_text, it) }
        //标签
        holder.setText(R.id.tv_tag, "#${data.data?.category} / ${durationFormat(data.data?.duration)}")
        //喜欢
        holder.setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
        //分享
        holder.setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
        //评论
        holder.setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())

        if (data.data?.author != null) {
            with(holder) {
                setText(R.id.tv_author_name, data.data.author.name)
                setText(R.id.tv_author_desc, data.data.author.description)
                setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(data.data.author.icon) {
                    override fun loadImage(iv: ImageView, path: String) {
                        //加载头像
                        GlideApp.with(mContext)
                                .load(path)
                                .placeholder(R.mipmap.default_avatar)
                                .circleCrop()
                                .into(iv)
                    }
                })
            }
        } else {
            holder.setViewVisibility(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(MyApplication.context, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(MyApplication.context, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(MyApplication.context, "评论", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setOnItemDetailClick(itemClick: (item: Item) -> Unit) {
        mOnItemClickRelatedVideo = itemClick
    }

    fun addData(item: Item) {
        mData.clear()
        notifyDataSetChanged()
        mData.add(item)
        notifyItemInserted(0)

    }

    fun addData(item: ArrayList<Item>) {
        mData.addAll(item)
        notifyItemRangeInserted(1, item.size)

    }
}