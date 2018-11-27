package com.jhj.rxretrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.retrofitlibrary.DefaultObserver
import com.jhj.rxretrofit.bean.CompanyBean
import com.jhj.rxretrofit.bean.HttpResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_company.setOnClickListener {
            RetrofitUtil.getInstance()
                    .getInfo("46")
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : DefaultObserver<HttpResult<CompanyBean>>() {

                        override fun onNext(value: HttpResult<CompanyBean>) {
                            super.onNext(value)
                            toast(value.msg)

                        }


                    })

        }


    }
}
