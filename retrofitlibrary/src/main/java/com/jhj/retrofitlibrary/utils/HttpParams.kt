package com.jhj.retrofitlibrary.utils

import java.io.File

/**
 * 请求参数
 * Created by jhj on 2017-12-30 0030.
 */
class HttpParams {
    /**
     * value 为字符串
     */
    var stringParams: LinkedHashMap<String, String> = linkedMapOf()
    /**
     * value 为文件
     */
    var fileParams: LinkedHashMap<String, List<File>> = linkedMapOf()


    fun put(key: String, value: String) {
        stringParams.put(key, value)
    }

    fun putAll(map: Map<String, String>) {
        stringParams.putAll(map)
    }

    fun put(key: String, file: File) {
        fileParams.put(key, listOf(file))
    }

    fun put(key: String, files: List<File>) {
        fileParams.put(key, files)
    }

    fun putAll(httpParams: HttpParams) {
        if (httpParams.stringParams.size > 0) {
            stringParams.putAll(httpParams.stringParams)
        }
        if (httpParams.fileParams.size > 0) {
            fileParams.putAll(httpParams.fileParams)
        }
    }


}