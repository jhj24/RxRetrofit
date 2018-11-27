package com.jhj.retrofitlibrary

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private var retrofit: Retrofit? = null
    private var okHttpClient = OkHttpClient.Builder().build()


    fun init(baseUrl: String) {
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun setClient(client: OkHttpClient): RetrofitManager {
        this.okHttpClient = client
        retrofit?.newBuilder()?.client(okHttpClient)?.build()
        return this
    }


    fun <T> create(clazz: Class<T>): T {
        if (retrofit == null) {
            throw NullPointerException()
        }
        return retrofit!!.create(clazz)
    }

}