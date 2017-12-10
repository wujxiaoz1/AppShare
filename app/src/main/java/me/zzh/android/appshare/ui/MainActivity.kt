package me.zzh.android.appshare.ui

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import me.zzh.android.appshare.R
import me.zzh.android.appshare.ui.adapter.AppInfoAdapter
import me.zzh.android.appshare.ui.entity.AppInfo
import me.zzh.android.appshare.ui.view.ToolbarView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var mToolbar: ToolbarView
    private lateinit var mAppInfoAdapter: AppInfoAdapter
    private val mDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialView()
        loadAppList()
    }

    private fun initialView() {
        mToolbar = ToolbarView.init(this)
        mToolbar.setRhsBtn("分享", View.OnClickListener { v ->
            val list = mAppInfoAdapter.getCheckedItem()
            if (list.isEmpty()) {
                return@OnClickListener
            }
            list.forEach { info ->
                shareApp(info)
            }
        })
        mAppInfoAdapter = AppInfoAdapter(this)
        mAppInfoAdapter.onItemCheckedChangeListener = object : AppInfoAdapter.OnItemCheckedChangeListener {
            override fun onItemCheckedChange(index: Int, item: AppInfo) {
                val checkedCount = mAppInfoAdapter.getCheckedItemCount()
                mToolbar.setTitle(if (checkedCount == 0) "本机应用" else String.format("已选%d个", checkedCount))
            }
        }
        lvData.adapter = mAppInfoAdapter
    }

    // 加载App类别
    private fun loadAppList() {
        Observable.fromIterable(packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES))
                .filter { it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0 } // 过期系统App
                .map {
                    val icon = it.applicationInfo.loadIcon(packageManager) // 应用图标
                    val name = it.applicationInfo.loadLabel(packageManager).toString() // 应用名字
                    val lastUpdate = mDateFormat.format(it.lastUpdateTime) // 最后更新
                    AppInfo(it.packageName, icon, name, lastUpdate, it.applicationInfo.sourceDir)
                }.toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { appInfoList -> mAppInfoAdapter.data = appInfoList }
    }

    // 分享App
    private fun shareApp(appInfo: AppInfo) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_TEXT, "${appInfo.pkgName}.apk")
        val apkUri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(this, "$packageName.provider", File(appInfo.apkSource))
        } else {
            apkUri = Uri.fromFile(File(appInfo.apkSource))
        }
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        intent.putExtra(Intent.EXTRA_STREAM, apkUri)
        Log.w("ShareApp", apkUri!!.toString())
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }
}