package me.zzh.android.appshare.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import me.zzh.android.appshare.R
import me.zzh.android.appshare.ui.adapter.AppInfoAdapter
import me.zzh.android.appshare.ui.view.ToolbarView

class MainActivity : AppCompatActivity() {
    private lateinit var mToolbar: ToolbarView
    private lateinit var mAppInfoAdapter: AppInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialView()
    }

    private fun initialView() {
        mToolbar = ToolbarView.init(this)
        mToolbar.setRhsBtn("分享", View.OnClickListener { v ->

        })
        mAppInfoAdapter = AppInfoAdapter(this)
    }
}