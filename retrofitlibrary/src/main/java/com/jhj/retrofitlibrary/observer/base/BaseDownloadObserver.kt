package com.jhj.retrofitlibrary.observer.base

import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

abstract class BaseDownloadObserver(var file: File) : BaseObserver<ResponseBody>() {

    //下载进度监听
    abstract fun onProgress(current: Long, total: Long)

    override fun onNext(value: ResponseBody) {
        super.onNext(value)
        val byteStream = value.byteStream()
        val os: FileOutputStream
        try {
            os = FileOutputStream(file)
        } catch (e: Exception) {
            onError(e)
            return
        }
        var bytesRead: Int
        val buffer = ByteArray(1024)
        var process = 0L

        try {
            do {
                bytesRead = byteStream?.read(buffer) ?: -1
                if (bytesRead == -1) break
                process += bytesRead
                os.write(buffer, 0, bytesRead)
            } while (true)
        } catch (e: Exception) {
            if (file.exists()) {
                file.delete()
            }
            onError(e)
            return
        }
    }
}