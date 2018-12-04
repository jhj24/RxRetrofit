package com.jhj.rxretrofit

import android.app.Application
import com.jhj.retrofitlibrary.RetrofitServiceManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitServiceManager.init(this).setBaseUrl(UrlConstant.YED_URL)
    }
}