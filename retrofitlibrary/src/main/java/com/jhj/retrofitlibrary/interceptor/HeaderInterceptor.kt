package com.jhj.retrofitlibrary.interceptor

import com.jhj.retrofitlibrary.model.HttpHeaders
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val headers: HttpHeaders) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
            .headers(appendHeaders())
            .method(original.method(), original.body())
            .build();

        return chain.proceed(request);
    }

    private fun appendHeaders(): Headers {
        val headerBuilder = Headers.Builder()
        if (headers.headersMap.isEmpty())
            return headerBuilder.build()
        for ((key, value) in headers.headersMap) {
            val sb = StringBuffer()
            //请求头部不支持
            value.forEach { char ->
                if (char <= '\u001f' || char >= '\u007f') {
                    sb.append(String.format("\\u%04x", char.toInt()))
                } else {
                    sb.append(char)
                }
            }

            headerBuilder.add(key, sb.toString())
        }
        return headerBuilder.build()
    }

}