package com.jhj.retrofitlibrary

import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver


object RetrofitServiceManager {

    private val retrofitManager = RetrofitManager()

    fun init(baseUrl: String): RetrofitManager {
        init(baseUrl, null)
        return retrofitManager
    }

    fun init(baseUrl: String, observer: BaseDownloadObserver?): RetrofitManager {
        retrofitManager.init(baseUrl, observer)
        return retrofitManager
    }

    /*fun updateBaseUrl(url: String): RetrofitServiceManager {
        retrofitManager.setBaseUrl(url)
        return this
    }

    fun updateClient(okHttpClient: OkHttpClient): RetrofitServiceManager {
        retrofitManager.setClient(okHttpClient)
        return this
    }*/


    fun <T> create(clazz: Class<T>): T {
        return retrofitManager.create(clazz)
    }




}