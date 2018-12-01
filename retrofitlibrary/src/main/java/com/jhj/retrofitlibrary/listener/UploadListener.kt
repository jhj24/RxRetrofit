package com.jhj.retrofitlibrary.listener

interface UploadListener {
    fun onProgress(current: Long, total: Long)
}