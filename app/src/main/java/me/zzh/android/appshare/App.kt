package me.zzh.android.appshare

import android.app.Application
import android.os.Build
import android.os.StrictMode


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // 通过修改虚拟机StrictMode设置绕过7.0的Uri检查
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            val builder = StrictMode.VmPolicy.Builder()
//            StrictMode.setVmPolicy(builder.build())
//            builder.detectFileUriExposure()
//        }
    }
}