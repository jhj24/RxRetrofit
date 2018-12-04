package com.jhj.rxretrofit.net

import com.jhj.retrofitlibrary.RetrofitServiceManager
import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver
import com.jhj.retrofitlibrary.observer.base.BaseProgressObserver
import com.jhj.rxretrofit.bean.HttpResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import okhttp3.ResponseBody

object RetrofitUtil {

    fun getInstance(): RequestService {
        return RetrofitServiceManager.setCache(true).buildRetrofit().create(RequestService::class.java)
    }

    fun getInstanceNoCache(): RequestService {
        return RetrofitServiceManager.buildRetrofit().create(RequestService::class.java)
    }


    fun uploadFile(url: String, requestBody: RequestBody, observer: BaseProgressObserver<HttpResult<String>>) {
        getInstance()
            .uploadFile(url, requestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


    fun download(baseUrl: String, url: String, observer: BaseDownloadObserver<ResponseBody>) {
        val a = RetrofitServiceManager.buildRetrofit(observer).create(RequestService::class.java)
        a.download(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

}