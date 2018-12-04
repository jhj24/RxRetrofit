package com.jhj.rxretrofit.net

import com.jhj.rxretrofit.bean.ApplyTypeBean
import com.jhj.rxretrofit.bean.HttpResult
import com.jhj.rxretrofit.common.UrlConstant
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


interface RequestService {

    //=====文件上传、下载=====

    @POST
    fun uploadFile(@Url url: String, @Body body: RequestBody): Observable<HttpResult<String>>


    @GET
    fun download(@Url url: String): Observable<ResponseBody>

    //======其他请求=====

    @FormUrlEncoded
    @POST(UrlConstant.type)
    fun getInfo(@Field("memberId") string: String): Observable<HttpResult<List<ApplyTypeBean>>>


    @FormUrlEncoded
    @POST(UrlConstant.type)
    fun getType(@Field("memberId") memberId: Int): Observable<HttpResult<List<ApplyTypeBean>>>

}