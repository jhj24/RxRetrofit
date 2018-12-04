package com.jhj.retrofitlibrary.model

import android.content.Context
import com.jhj.retrofitlibrary.interceptor.CacheInterceptor
import com.jhj.retrofitlibrary.interceptor.DownloadInterceptor
import com.jhj.retrofitlibrary.interceptor.HeaderInterceptor
import com.jhj.retrofitlibrary.interceptor.UploadInterceptor
import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver
import com.jhj.retrofitlibrary.observer.base.BaseProgressObserver
import io.reactivex.Observer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private var retrofit: Retrofit? = null
    private var attribute: Attribute? = null
    private var isCache = false

    fun init(mContext: Context): Attribute {
        attribute = Attribute(mContext)
        isCache = attribute?.isCache() == true
        return attribute as Attribute

    }

    fun setCache(cache: Boolean) {
        isCache = cache
    }

    fun buildRetrofit(observer: Observer<*>?) {
        if (attribute == null) {
            throw NullPointerException()
        }
        attribute?.let {
            if (it.getBaseUrl() == null) {
                throw NullPointerException()
            }

            val interceptor = when {
                observer is BaseDownloadObserver<*> -> DownloadInterceptor(observer)
                observer is BaseProgressObserver<*> -> UploadInterceptor(observer)
                isCache -> CacheInterceptor(
                    it.getContext(),
                    it.isCache(),
                    it.getCacheFileName(),
                    it.getCacheSize()
                )
                else -> null
            }
            isCache = it.isCache()

            val client = if (interceptor == null) {
                it.getClient()
            } else {
                it.getClient().newBuilder().addNetworkInterceptor(interceptor).build()
            }

            if (it.getHeaders() != null) {
                it.getClient().newBuilder().addNetworkInterceptor(HeaderInterceptor(it.getHeaders()!!)).build()
            }

            retrofit = Retrofit.Builder()
                .baseUrl(attribute?.getBaseUrl()!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

    }


    fun <T> create(clazz: Class<T>): T {
        if (retrofit == null) {
            throw NullPointerException()
        }
        return retrofit!!.create(clazz)
    }


}