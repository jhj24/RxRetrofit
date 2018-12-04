package com.jhj.retrofitlibrary.model

import android.content.Context
import com.jhj.retrofitlibrary.interceptor.CacheInterceptor
import com.jhj.retrofitlibrary.interceptor.DownloadInterceptor
import com.jhj.retrofitlibrary.interceptor.UploadInterceptor
import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver
import com.jhj.retrofitlibrary.observer.base.BaseProgressObserver
import io.reactivex.Observer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 用于设置通用的请求头、请求体以及OkHttpClient
 *
 * Created by jhj on 2018-2-23 0023;
 */

class Attribute(private val mContext: Context) {

    private var baseUrl: String? = null
    private var headers: HttpHeaders? = null
    private var cache: Boolean = false
    private var cacheFileName: String = "cache"
    private var cacheSize: Long = 10 * 1024 * 1024
    private var client: OkHttpClient = OkHttpClient.Builder().build()

    fun getContext(): Context {
        return mContext
    }

    fun getBaseUrl(): String? {
        return baseUrl
    }

    fun setBaseUrl(baseUrl: String): Attribute {
        this.baseUrl = baseUrl
        return this
    }

    fun isCache(): Boolean {
        return cache;
    }

    fun setCache(cache: Boolean): Attribute {
        this.cache = cache
        return this
    }

    fun getCacheFileName(): String {
        return cacheFileName
    }

    fun setCacheFileName(cacheFileName: String): Attribute {
        this.cacheFileName = cacheFileName
        return this
    }

    fun getCacheSize(): Long {
        return cacheSize
    }

    fun setCacheSize(cacheSize: Long): Attribute {
        this.cacheSize = cacheSize
        return this
    }

    fun getHeaders(): HttpHeaders? {
        return headers
    }

    fun setHeaders(headers: HttpHeaders): Attribute {
        this.headers = headers
        return this
    }

    fun getClient(): OkHttpClient {
        return client
    }

    fun setClient(client: OkHttpClient): Attribute {
        this.client = client
        return this
    }
}
