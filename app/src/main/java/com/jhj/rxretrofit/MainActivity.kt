package com.jhj.rxretrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import com.jhj.retrofitlibrary.observer.DialogObserver
import com.jhj.retrofitlibrary.observer.DownloadObserver
import com.jhj.retrofitlibrary.observer.ProgressObserver
import com.jhj.retrofitlibrary.observer.base.BaseObserver
import com.jhj.retrofitlibrary.utils.HttpParams
import com.jhj.rxretrofit.bean.ApplyTypeBean
import com.jhj.rxretrofit.bean.HttpResult
import com.jhj.rxretrofit.net.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_company.setOnClickListener {
            RetrofitUtil.getInstance()
                .getInfo("754")
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<HttpResult<List<ApplyTypeBean>>>() {

                    override fun onNext(value: HttpResult<List<ApplyTypeBean>>) {
                            super.onNext(value)
                            toast(value.msg)

                        }


                    })

        }

        btn_type.setOnClickListener {
            RetrofitUtil.getInstanceNoCache()
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
            val d = HttpParams
                .put("user", "游客")
                .put("userGuid", "374de98405204c3fa32e75c30b384967")
                .put("file", File(a))
                .build()

            RetrofitUtil.uploadFile(UrlConstant.ADD_IMAGE, d, object : ProgressObserver<HttpResult<String>>(this, "") {

                override fun onNext(value: HttpResult<String>) {
                    super.onNext(value)
                    toast(value.msg)
                }

                override fun onError(e: Throwable?) {
                    super.onError(e)
                }
            })


        }

        btn_post.setOnClickListener {
            val path = FileUtils.getSDPath("file" + File.separator)
            val file = File(path, FileUtils.getNameFromUrl(UrlConstant.downUrl))
            val requestBody = HttpParams.put("comId", "46").build()
            RetrofitUtil.download(
                UrlConstant.YED_URL,
                UrlConstant.downUrl,
                object : DownloadObserver<ResponseBody>(this, "", file) {

                    override fun onNext(value: ResponseBody) {
                        super.onNext(value)


                    }
                })
        }



    }
}
