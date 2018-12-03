package com.jhj.retrofitlibrary

import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver
import com.jhj.retrofitlibrary.requestbody.DownloadProgress
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private var retrofit: Retrofit? = null
    private var okHttpClient = OkHttpClient.Builder().build()


    fun init(baseUrl: String, observer: BaseDownloadObserver?) {

        retrofit = if (observer == null) {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        } else {
            val interceptor = Interceptor { chain ->
                val response = chain.proceed(chain.request())
                val responseBody = response?.body()
                val mBody = responseBody?.let {
                    DownloadProgress(it, observer)
                }
                return@Interceptor response.newBuilder()
                    .body(mBody)
                    .build()
            }

            val okHttpClient = OkHttpClient
                .Builder()
                .addNetworkInterceptor(interceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }

    fun setClient(client: OkHttpClient): RetrofitManager {
        this.okHttpClient = client
        retrofit?.newBuilder()?.client(okHttpClient)?.build()
        return this
    }

    fun setBaseUrl(url: String): RetrofitManager {
        retrofit?.newBuilder()?.baseUrl(url)?.build()
        return this
    }


    fun <T> create(clazz: Class<T>): T {
        if (retrofit == null) {
            throw NullPointerException()
        }
        return retrofit!!.create(clazz)
    }
}