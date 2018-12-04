package com.jhj.rxretrofit

import android.app.Application
import com.jhj.retrofitlibrary.HttpRetrofit
import com.jhj.rxretrofit.common.UrlConstant

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        HttpRetrofit.init(this).setBaseUrl(UrlConstant.YED_URL)
    }
}