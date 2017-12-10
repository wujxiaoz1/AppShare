package me.zzh.android.appshare.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

class AppUtil private constructor() {
    companion object {
        @JvmStatic
        fun getPackageInfoList(ctx: Context): List<PackageInfo> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return ctx.packageManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES)
            } else {
                return ctx.packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
            }
        }

        @JvmStatic
        fun getApplicationName(ctx: Context, pm: PackageManager) {

        }
    }
}