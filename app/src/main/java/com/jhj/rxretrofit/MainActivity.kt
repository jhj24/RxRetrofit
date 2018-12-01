package com.jhj.rxretrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import com.jhj.retrofitlibrary.observer.DialogObserver
import com.jhj.retrofitlibrary.observer.ProgressObserver
import com.jhj.retrofitlibrary.observer.base.BaseObserver
import com.jhj.retrofitlibrary.utils.HttpParams
import com.jhj.retrofitlibrary.utils.HttpUtils
import com.jhj.rxretrofit.bean.ApplyTypeBean
import com.jhj.rxretrofit.bean.CompanyBean
import com.jhj.rxretrofit.bean.HttpResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.io.File

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
                .subscribe(object : BaseObserver<HttpResult<CompanyBean>>() {

                        override fun onNext(value: HttpResult<CompanyBean>) {
                            super.onNext(value)
                            toast(value.msg)

                        }


                    })

        }

        btn_type.setOnClickListener {
            RetrofitUtil.jqInstance()
                .getType(754)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DialogObserver<HttpResult<List<ApplyTypeBean>>>(this, "正在加载...") {

                    override fun onStart() {
                        super.onStart()
                        dialog.setDialogShowOnBackListener(object : OnDialogShowOnBackListener {
                            override fun cancel() {

                            }

                        })
                    }

                    override fun onNext(value: HttpResult<List<ApplyTypeBean>>) {
                        super.onNext(value)
                        textView.text = value.data[1].toString()

                    }
                })
        }

        btn_file.setOnClickListener {

            val a = "/storage/emulated/0/DCIM/Camera/IMG_20171004_160402.jpg"
            val httpParams = HttpParams()
            httpParams.put("user", "游客")
            httpParams.put("userGuid", "374de98405204c3fa32e75c30b384967")
            httpParams.put("file", File(a))

            val observer = object : ProgressObserver<HttpResult<String>>(this, "") {

                override fun onNext(value: HttpResult<String>) {
                    super.onNext(value)
                    toast(value.msg)
                }

                override fun onError(e: Throwable?) {
                    super.onError(e)
                }
            }

            val body = HttpUtils.generateRequestBody(httpParams, observer);

            RetrofitUtil.getInstance().uploadFile(UrlConstant.ADD_IMAGE, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

        }




    }
}
