package com.jhj.rxretrofit

import com.jhj.retrofitlibrary.RetrofitServiceManager

object RetrofitUtil {

    fun getInstance(): RequestService {
        return RetrofitServiceManager.create(RequestService::class.java)
    }


}