package com.jhj.retrofitlibrary

import android.content.Context
import com.jhj.retrofitlibrary.model.Attribute
import com.jhj.retrofitlibrary.model.RetrofitManager
import io.reactivex.Observer


object RetrofitServiceManager {

    private val retrofitManager = RetrofitManager()


    fun init(mContext: Context): Attribute {
        return retrofitManager.init(mContext)
    }

    fun setCache(cache: Boolean): RetrofitServiceManager {
        retrofitManager.setCache(cache)
        return this
    }

    fun buildRetrofit(): RetrofitServiceManager {
        retrofitManager.buildRetrofit(null)
        return this
    }

    fun buildRetrofit(observer: Observer<*>?): RetrofitServiceManager {
        retrofitManager.buildRetrofit(observer)
        return this
    }

    fun <T> create(clazz: Class<T>): T {
        return retrofitManager.create(clazz)
    }
}