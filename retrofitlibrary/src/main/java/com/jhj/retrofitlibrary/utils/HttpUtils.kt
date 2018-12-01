package com.jhj.retrofitlibrary.utils

import com.jhj.retrofitlibrary.observer.base.BaseProgressObserver
import com.jhj.retrofitlibrary.requestbody.FileRequestBody
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


/**
 * Created by jhj on 2017-12-31 0031.
 */

object HttpUtils {

    /**
     * 传递过来的参数拼接成Url
     */
    fun appendUrl(url: String, params: HashMap<String, String>): String {
        if (params.isEmpty()) {
            return url
        }
        try {
            val sb = StringBuilder(url)
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0)
                sb.append("&")
            else
                sb.append("?")
            params.forEach { map ->
                //对参数进行 utf-8 编码,防止头信息传中文
                val urlValue = URLEncoder.encode(map.value, "UTF-8")
                sb.append(map.key).append("=").append(urlValue).append("&")

            }
            sb.deleteCharAt(sb.length - 1)
            return sb.toString()
        } catch (e: UnsupportedEncodingException) {

        }
        return url
    }


    /**
     * 生成类似表单的请求体
     */
    fun generateRequestBody(params: HttpParams, observer: BaseProgressObserver<*>): RequestBody {
        if (params.fileParams.isEmpty()) {
            //表单提交，没有文件
            val bodyBuilder = FormBody.Builder()
            for ((key, value) in params.stringParams) {
                bodyBuilder.add(key, value)
            }
            return bodyBuilder.build()
        } else {
            //表单提交，有文件
            val multipartBodybuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            //拼接键值对
            if (!params.stringParams.isEmpty()) {
                for ((key, value) in params.stringParams) {
                    multipartBodybuilder.addFormDataPart(key, value)
                }
            }
            //拼接文件
            for ((key, value) in params.fileParams) {
                if (value.isEmpty()) continue
                value.forEach { file ->
                    val mediaType = MediaType.parse(MimeTypeUtils.getFileType(file.name))
                    val fileBody = RequestBody.create(mediaType, file)
                    val a = FileRequestBody(fileBody, observer)
                    multipartBodybuilder.addFormDataPart(key, file.name, a)
                }
            }
            return multipartBodybuilder.build()
        }
    }
}
