package com.jhj.retrofitlibrary.observer.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T> : Observer<T> {

    var disposable: Disposable? = null

    override fun onComplete() {
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        onFinish()
    }


    override fun onSubscribe(d: Disposable?) {
        onStart()
        disposable = d;
    }

    open fun onStart() {}
    open fun onFinish() {}

    override fun onNext(value: T) {

    }

    override fun onError(e: Throwable?) {
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        onFinish()
    }

}