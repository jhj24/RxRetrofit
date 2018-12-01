package com.jhj.retrofitlibrary.interceptor

/*
class UploadInterceptor(private val listener: UploadListener) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (null == request.body()) {
            return chain.proceed(request)
        }

        val build = request
            .newBuilder()
            .method(request.method(), FileRequestBody(request.body(), listener))
            .build()
        return chain.proceed(build)

    }
}*/
