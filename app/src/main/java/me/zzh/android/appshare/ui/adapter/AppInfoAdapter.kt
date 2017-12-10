package me.zzh.android.appshare.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.zzh.android.appshare.R
import me.zzh.android.appshare.ui.entity.AppInfo
import me.zzh.android.appshare.util.ViewHolder

class AppInfoAdapter(private val ctx: Context) : BaseAdapter() {
    var data: List<AppInfo>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_app_info, parent, false)
        }
        val tvName = ViewHolder.get<TextView>(view, R.id.tvName);
        val tvSize = ViewHolder.get<TextView>(view, R.id.tvSize);
        val tvLastUpdate = ViewHolder.get<TextView>(view, R.id.tvLastUpdate);

        with(getItem(position)) {
            tvName.text = name
            tvSize.text = size
            tvLastUpdate.text = lastUpdate
        }

        return view!!
    }

    override fun getItem(position: Int): AppInfo = data!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int {
        return data?.size ?: 0
    }
}