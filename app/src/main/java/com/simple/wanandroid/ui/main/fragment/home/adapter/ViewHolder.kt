package com.simple.wanandroid.ui.main.fragment.home.adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView

@Suppress("UNCHECKED_CAST")
/**
 * @author hych
 * @date 2018/10/3 15:03
 */
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mItemView: SparseArray<View> ?= null

    init {
        mItemView = SparseArray()
    }
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mItemView?.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mItemView?.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, text: String): ViewHolder {
        val view = getView<TextView>(viewId)
        view.text = text
        return this
    }

    fun setImagePath(viewId: Int, imageLoader: HolderImageLoader): ViewHolder {
        val view = getView<ImageView>(viewId)
        imageLoader.loadImage(view, imageLoader.path)
        return this
    }

    fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    fun setOnItemClickListener(listener: View.OnClickListener): ViewHolder {
        itemView.setOnClickListener(listener)
        return this
    }

    abstract class HolderImageLoader(val path: String) {
        abstract fun loadImage(iv: ImageView, path: String)
    }
}