package me.zzh.android.appshare.ui.view

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import me.zzh.android.appshare.R

// Toolbar辅助工具
class ToolbarView private constructor() {
    private lateinit var mTitle: TextView
    private lateinit var mRhsBtn: TextView

    constructor(activity: AppCompatActivity) : this() {
        mTitle = activity.findViewById<TextView>(R.id.vToolbarTitle)
        mRhsBtn = activity.findViewById<TextView>(R.id.vToolbarRhsBtn)
        activity.setSupportActionBar(activity.findViewById<Toolbar>(R.id.vToolbar))
        setTitle(activity.title)
    }

    constructor(view: View) : this() {
        mTitle = view.findViewById<TextView>(R.id.vToolbarTitle)
        mRhsBtn = view.findViewById<TextView>(R.id.vToolbarRhsBtn)
    }

    // 设置标题
    fun setTitle(title: CharSequence) {
        mTitle.text = title
    }

    // 设置右菜单
    fun setRhsBtn(title: CharSequence, onClickListener: View.OnClickListener) {
        mRhsBtn.text = title
        mRhsBtn.setOnClickListener(onClickListener)
    }

    companion object {
        @JvmStatic
        fun init(activity: AppCompatActivity): ToolbarView {
            return ToolbarView(activity)
        }

        @JvmStatic
        fun init(view: View): ToolbarView {
            return ToolbarView(view)
        }
    }
}