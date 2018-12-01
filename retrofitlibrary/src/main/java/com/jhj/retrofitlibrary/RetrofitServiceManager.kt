package com.jhj.retrofitlibrary

import okhttp3.OkHttpClient


object RetrofitServiceManager {

    private val retrofitManager = RetrofitManager()

    fun init(baseUrl: String): RetrofitManager {
        retrofitManager.init(baseUrl)
        return retrofitManager
    }

    fun updateBaseUrl(url: String): RetrofitServiceManager {
        retrofitManager.setBaseUrl(url)
        return this
    }

    fun updateClient(okHttpClient: OkHttpClient): RetrofitServiceManager {
        retrofitManager.setClient(okHttpClient)
        return this
    }


    fun <T> create(clazz: Class<T>): T {
        return retrofitManager.create(clazz)
    }


}