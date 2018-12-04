package com.jhj.rxretrofit.net

import com.jhj.retrofitlibrary.HttpRetrofit
import com.jhj.retrofitlibrary.observer.base.BaseDownloadObserver
import com.jhj.retrofitlibrary.observer.base.BaseProgressObserver
import com.jhj.rxretrofit.bean.HttpResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import okhttp3.ResponseBody

object RetrofitUtil {

    fun getInstance(): RequestService {
        return HttpRetrofit.setCache(true).buildRetrofit().create(RequestService::class.java)
    }

    fun getInstanceNoCache(): RequestService {
        return HttpRetrofit.buildRetrofit().create(RequestService::class.java)
    }


    fun uploadFile(url: String, requestBody: RequestBody, observer: BaseProgressObserver<HttpResult<String>>) {
        HttpRetrofit.buildRetrofit(observer).create(RequestService::class.java)
            .uploadFile(url, requestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


    fun download(url: String, observer: BaseDownloadObserver<ResponseBody>) {
        HttpRetrofit.buildRetrofit(observer).create(RequestService::class.java)
            .download(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

}