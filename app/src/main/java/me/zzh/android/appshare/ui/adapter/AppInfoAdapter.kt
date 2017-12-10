package me.zzh.android.appshare.ui.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import collections.forEach
import me.zzh.android.appshare.R
import me.zzh.android.appshare.ui.entity.AppInfo
import me.zzh.android.appshare.util.ViewHolder
import java.io.File
import java.text.DecimalFormat

class AppInfoAdapter(private val ctx: Context) : BaseAdapter() {
    var data: List<AppInfo>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private val checkedItemIndex = SparseBooleanArray()
    private val mDecimalFormat = DecimalFormat("0.00")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_app_info, parent, false) as View
            val cbSelect = ViewHolder.get<CheckBox>(view, R.id.cbSelect);
            cbSelect.setOnCheckedChangeListener { v, isChecked ->
                val index = v.getTag(R.id.index) as Int
                checkedItemIndex.put(index, isChecked)
                if (onItemCheckedChangeListener != null) {
                    onItemCheckedChangeListener!!.onItemCheckedChange(index, data!![index])
                }
            }
            view.setOnClickListener {
                cbSelect.performClick()
            }
        }
        val cbSelect = ViewHolder.get<CheckBox>(view, R.id.cbSelect);
        val ivIcon = ViewHolder.get<ImageView>(view, R.id.ivIcon);
        val tvName = ViewHolder.get<TextView>(view, R.id.tvName);
        val tvSize = ViewHolder.get<TextView>(view, R.id.tvSize);
        val tvLastUpdate = ViewHolder.get<TextView>(view, R.id.tvLastUpdate);

        cbSelect.setTag(R.id.index, position)
        cbSelect.isChecked = checkedItemIndex.get(position)
        with(getItem(position)) {
            ivIcon.setImageDrawable(icon)
            tvName.text = name
            tvSize.text = String.format("%s MB", mDecimalFormat.format(File(apkSource).length() / 1024f / 1024f)) // 应用大小
            tvLastUpdate.text = lastUpdate
        }
        return view
    }

    override fun getItem(position: Int): AppInfo = data!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int {
        return data?.size ?: 0
    }

    fun getCheckedItemCount(): Int {
        var count = 0
        for (i in 0..checkedItemIndex.size()) {
            if (checkedItemIndex.get(i)) {
                count++
            }
        }
        return count
    }

    fun getCheckedItem(): List<AppInfo> {
        val result = ArrayList<AppInfo>()
        checkedItemIndex.forEach { i, b ->
            if (b) {
                result.add(data!![i])
            }
        }
        return result
    }

    interface OnItemCheckedChangeListener {
        fun onItemCheckedChange(index: Int, item: AppInfo)
    }

    var onItemCheckedChangeListener: OnItemCheckedChangeListener? = null
}