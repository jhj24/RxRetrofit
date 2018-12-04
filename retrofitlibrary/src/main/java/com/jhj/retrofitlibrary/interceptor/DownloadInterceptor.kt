package com.jhj.retrofitlibrary.interceptor

import com.jhj.retrofitlibrary.body.DownloadResponseBody
import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver
import okhttp3.Interceptor
import okhttp3.Response

class DownloadInterceptor(val observer: BaseDownloadObserver<*>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        val responseBody = response?.body()
        //封装responseBody，传入参数，获取数据进度回调
        val mBody = responseBody?.let {
            DownloadResponseBody(it, observer)
        }
        return response.newBuilder()
            .body(mBody)
            .build()
    }
}