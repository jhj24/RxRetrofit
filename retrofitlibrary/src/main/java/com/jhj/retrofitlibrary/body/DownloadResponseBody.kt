package com.jhj.retrofitlibrary.body

import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio

class DownloadResponseBody<T>(private val responseBody: ResponseBody, val downloadObserver: BaseDownloadObserver<T>) :
    ResponseBody() {

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun source(): BufferedSource {
        val mBufferedSource = Okio.buffer(source(responseBody.source()));
        return mBufferedSource;
    }

    private fun source(bufferedSource: BufferedSource): ForwardingSource {

        return object : ForwardingSource(bufferedSource) {
            private var totalBytesRead = 0L
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                val count = if (bytesRead != -1L) {
                    bytesRead
                } else {
                    0L
                }
                totalBytesRead += count
                downloadObserver.onProgress(totalBytesRead, contentLength())
                return bytesRead
            }
        }
    }
}