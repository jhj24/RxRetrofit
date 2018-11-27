package com.jhj.retrofitlibrary


object RetrofitServiceManager {

    private val retrofitManager = RetrofitManager()

    fun init(baseUrl: String): RetrofitManager {
        retrofitManager.init(baseUrl)
        return retrofitManager
    }


    fun <T> create(clazz: Class<T>): T {
        return retrofitManager.create(clazz)
    }


}