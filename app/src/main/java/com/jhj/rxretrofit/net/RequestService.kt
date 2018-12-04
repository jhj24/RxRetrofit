package com.jhj.rxretrofit.net

import com.jhj.rxretrofit.UrlConstant
import com.jhj.rxretrofit.bean.ApplyTypeBean
import com.jhj.rxretrofit.bean.CompanyBean
import com.jhj.rxretrofit.bean.HttpResult
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


interface RequestService {

    @FormUrlEncoded
    @POST(UrlConstant.type)
    fun getInfo(@Field("memberId") string: String): Observable<HttpResult<List<ApplyTypeBean>>>


    @FormUrlEncoded
    @POST(UrlConstant.type)
    fun getType(@Field("memberId") memberId: Int): Observable<HttpResult<List<ApplyTypeBean>>>

    @POST
    fun uploadFile(@Url url: String, @Body body: RequestBody): Observable<HttpResult<String>>


    @GET
    fun download(@Url url: String): Observable<ResponseBody>
}