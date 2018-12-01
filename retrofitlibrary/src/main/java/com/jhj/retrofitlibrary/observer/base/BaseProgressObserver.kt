package com.jhj.retrofitlibrary.observer.base

abstract class BaseProgressObserver<T> : BaseObserver<T>() {

    abstract fun onProgress(current: Long, total: Long)

}