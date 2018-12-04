package com.jhj.retrofitlibrary.observer

import android.app.Activity
import com.jhj.prompt.dialog.progress.PercentFragment
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver
import java.io.File

open class DownloadObserver<T>(val activity: Activity, val msg: String, downloadFile: File) :
    BaseDownloadObserver<T>(downloadFile) {

    var dialog = PercentFragment.Builder(activity)

    override fun onStart() {
        super.onStart()
        dialog
            .setText(msg)
            .setDialogShowOnBackListener(object : OnDialogShowOnBackListener {
                override fun cancel() {
                    if (disposable?.isDisposed == false) {
                        disposable?.dispose()
                    }
                }
            })
            .show()
    }

    override fun onProgress(current: Long, total: Long) {
        activity.runOnUiThread {
            if (dialog.getMaxProgress() != current.toInt()) {
                val progress = (current * 100.0 / total).toInt()
                dialog.setScaleDisplay().setProgress(progress)
            } else {
                dialog.dismiss()
            }
        }
    }


    override fun onFinish() {
        super.onFinish()
        if (dialog.isShow()) {
            dialog.dismiss()
        }
    }

}