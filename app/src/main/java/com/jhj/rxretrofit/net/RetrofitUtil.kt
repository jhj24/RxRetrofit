package com.jhj.rxretrofit.net

import com.jhj.retrofitlibrary.RetrofitServiceManager
import com.jhj.retrofitlibrary.observer.base.BaseProgressObserver
import com.jhj.retrofitlibrary.requestbody.FileRequestBody
import com.jhj.rxretrofit.UrlConstant
import com.jhj.rxretrofit.bean.HttpResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

object RetrofitUtil {

    fun getInstance(): RequestService {
        return RetrofitServiceManager.create(RequestService::class.java)
    }

    fun jqInstance(): RequestService {
        return RetrofitServiceManager.init(UrlConstant.YED_URL).create(RequestService::class.java)
    }


    fun uploadFile(url: String, requestBody: RequestBody, observer: BaseProgressObserver<HttpResult<String>>) {
        getInstance()
            .uploadFile(url, FileRequestBody(requestBody, observer))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    fun download(url: String) {
        getInstance()
            .download(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}