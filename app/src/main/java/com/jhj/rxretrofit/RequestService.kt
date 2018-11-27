package com.jhj.rxretrofit

import com.jhj.rxretrofit.bean.CompanyBean
import com.jhj.rxretrofit.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RequestService {

    @FormUrlEncoded
    @POST(UrlConstant.COMPANY_INFO)
    fun getInfo(@Field("comId") string: String): Observable<HttpResult<CompanyBean>>
}