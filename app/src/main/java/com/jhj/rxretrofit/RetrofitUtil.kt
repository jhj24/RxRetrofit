package com.jhj.rxretrofit

import com.jhj.retrofitlibrary.RetrofitServiceManager

object RetrofitUtil {

    fun getInstance(): RequestService {
        return RetrofitServiceManager.create(RequestService::class.java)
    }

    fun jqInstance(): RequestService {
        return RetrofitServiceManager.init(UrlConstant.YED_URL).create(RequestService::class.java)
    }


}