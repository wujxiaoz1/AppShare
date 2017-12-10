package me.zzh.android.appshare.ui.entity

import android.graphics.drawable.Drawable

// 应用信息
data class AppInfo(
        val icon: Drawable, // 图标
        val name: String, // 名称
        val size: String, // 大小
        val lastUpdate: String // 最后更新
)