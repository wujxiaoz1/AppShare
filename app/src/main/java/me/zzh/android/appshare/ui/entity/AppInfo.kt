package me.zzh.android.appshare.ui.entity

import android.graphics.drawable.Drawable
import java.io.File

// 应用信息
data class AppInfo(
        val pkgName: String, // 包名
        val icon: Drawable, // 图标
        val name: String, // 名称
        val lastUpdate: String, // 最后更新
        val apkSource: String // 资源文件
)