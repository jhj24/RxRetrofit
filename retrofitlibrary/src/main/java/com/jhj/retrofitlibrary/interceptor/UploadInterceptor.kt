package com.jhj.retrofitlibrary.interceptor

import com.jhj.retrofitlibrary.body.UploadRequestBody
import com.jhj.retrofitlibrary.observer.base.BaseProgressObserver
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class UploadInterceptor(private val mObserver: BaseProgressObserver<*>) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //封装request对象
        val request = wrapRequest(chain.request())
        return chain.proceed(request!!)
    }


    private fun wrapRequest(request: Request?): Request? {
        if (request?.body() == null) {
            return request
        }
        val builder = request.newBuilder()

        //封装requestBody，传入参数，获取数据进度回调
        request.body()?.let {
            builder.method(request.method(), UploadRequestBody(it, mObserver))
        }
        return builder.build()
    }
}
