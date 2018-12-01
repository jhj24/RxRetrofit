package com.jhj.retrofitlibrary.observer

import android.app.Activity
import com.jhj.prompt.dialog.progress.LoadingFragment
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import com.jhj.retrofitlibrary.observer.base.BaseObserver


/**
 * 默认Dialog 样式
 *
 * 点击返回取消网络请求
 */
abstract class DialogObserver<T>(val activity: Activity, val msg: String) : BaseObserver<T>() {

    val dialog = LoadingFragment.Builder(activity)

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


    override fun onFinish() {
        super.onFinish()
        if (dialog.isShow()) {
            dialog.dismiss()
        }
    }

}