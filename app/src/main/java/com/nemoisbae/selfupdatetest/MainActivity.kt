package com.nemoisbae.selfupdatetest

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.listener.OnDownloadListenerAdapter
import com.azhon.appupdate.manager.DownloadManager

/**
 * https://github.com/azhon/AppUpdate
 */

class MainActivity : AppCompatActivity() {
    private val versionString: String = "2.0"
    private val apkName: String = "selfUpdate.apk"
    private val host: String = "http://raspberrybouteiller.ddns.net/$apkName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.versionTextView).text = "Version: $versionString"

        val downloadListenerAdapter = object : OnDownloadListenerAdapter() {
            /**
             * 下载中
             *
             * @param max      总进度
             * @param progress 当前进度
             */
            override fun downloading(max: Int, progress: Int) {
                val curr = (progress / max.toDouble() * 100.0).toInt()
                findViewById<ProgressBar>(R.id.downLoadProgressBar).max = 100
                findViewById<ProgressBar>(R.id.downLoadProgressBar).progress = curr
            }
        }


        val config: UpdateConfiguration = UpdateConfiguration().apply {
            this.setEnableLog(true)
            this.setOnDownloadListener(downloadListenerAdapter)
        }

        if ("2.0" != versionString) {
            val downloadManager: DownloadManager = DownloadManager.getInstance(this)
            downloadManager.apkName = apkName
            downloadManager.apkUrl = host
            downloadManager.smallIcon = R.mipmap.ic_launcher
            downloadManager.configuration = config
            downloadManager.download()
        }
    }
}