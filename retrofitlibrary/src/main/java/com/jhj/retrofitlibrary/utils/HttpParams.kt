package com.jhj.retrofitlibrary.utils

import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object HttpParams {

    /**
     * value 为字符串
     */
    private var stringParams: LinkedHashMap<String, String> = linkedMapOf()

    /**
     * value 为文件
     */
    private var fileParams: LinkedHashMap<String, List<File>> = linkedMapOf()


    fun put(key: String, value: String): HttpParams {
        stringParams[key] = value
        return this
    }

    fun putAll(map: Map<String, String>): HttpParams {
        stringParams.putAll(map)
        return this
    }

    fun put(key: String, file: File): HttpParams {
        fileParams[key] = listOf(file)
        return this
    }

    fun put(key: String, files: List<File>): HttpParams {
        fileParams[key] = files
        return this
    }

    fun putAll(httpParams: HttpParams): HttpParams {
        if (httpParams.stringParams.size > 0) {
            stringParams.putAll(httpParams.stringParams)
        }
        if (httpParams.fileParams.size > 0) {
            fileParams.putAll(httpParams.fileParams)
        }
        return this
    }

    fun build(): RequestBody {
        if (fileParams.isEmpty()) {
            //表单提交，没有文件
            val bodyBuilder = FormBody.Builder()
            for ((key, value) in stringParams) {
                bodyBuilder.add(key, value)
            }
            return bodyBuilder.build()
        } else {
            //表单提交，有文件
            val multipartBodybuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            //拼接键值对
            if (!stringParams.isEmpty()) {
                for ((key, value) in stringParams) {
                    multipartBodybuilder.addFormDataPart(key, value)
                }
            }
            //拼接文件
            for ((key, value) in fileParams) {
                if (value.isEmpty()) continue
                value.forEach { file ->
                    val mediaType = MediaType.parse(MimeTypeUtils.getFileType(file.name))
                    val fileBody = RequestBody.create(mediaType, file)
                    multipartBodybuilder.addFormDataPart(key, file.name, fileBody)
                }
            }
            return multipartBodybuilder.build()
        }
    }
}