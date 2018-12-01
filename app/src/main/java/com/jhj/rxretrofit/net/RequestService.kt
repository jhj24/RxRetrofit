package com.jhj.rxretrofit.net

import com.jhj.rxretrofit.UrlConstant
import com.jhj.rxretrofit.bean.ApplyTypeBean
import com.jhj.rxretrofit.bean.CompanyBean
import com.jhj.rxretrofit.bean.HttpResult
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*
import java.io.File


interface RequestService {

    @FormUrlEncoded
    @POST(UrlConstant.COMPANY_INFO)
    fun getInfo(@Field("comId") string: String): Observable<HttpResult<CompanyBean>>


    @FormUrlEncoded
    @POST(UrlConstant.type)
    fun getType(@Field("memberId") memberId: Int): Observable<HttpResult<List<ApplyTypeBean>>>

    @POST
    fun uploadFile(@Url url: String, @Body body: RequestBody): Observable<HttpResult<String>>

    @POST
    fun <T> post(@Url url: String, @Body body: RequestBody): Observable<T>

    @GET
    fun download(@Url url: String): Observable<HttpResult<File>>
}