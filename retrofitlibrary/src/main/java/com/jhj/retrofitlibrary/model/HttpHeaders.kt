package com.jhj.retrofitlibrary.model

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.webkit.WebSettings
import java.util.*

/**
 * 请求头部
 * Created by jhj on 2017-12-30 0030.
 */
class HttpHeaders {
    private var acceptLanguage: String? = null

    var headersMap: LinkedHashMap<String, String> = linkedMapOf()

    fun put(key: String, value: String) {
        headersMap.put(key, value)
    }

    fun putAll(map: Map<String, String>) {
        for ((key, value) in map) {
            headersMap.put(key, value)
        }
    }

    fun putAll(headers: HttpHeaders) {
        headersMap.putAll(headers.headersMap)
    }


    /**
     * Accept-Language: zh-CN,zh;q=0.8
     */
    fun getAcceptLanguage(): String? {
        if (TextUtils.isEmpty(acceptLanguage)) {
            val locale = Locale.getDefault()
            val language = locale.language
            val country = locale.country
            val acceptLanguageBuilder = StringBuilder(language)
            if (!TextUtils.isEmpty(country)) acceptLanguageBuilder.append('-').append(country).append(',').append(language).append(";q=0.8")
            acceptLanguage = acceptLanguageBuilder.toString()
            return acceptLanguage
        }
        return acceptLanguage
    }

    /**
     * User-Agent: Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; Redmi Note 3 Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36
     */
    fun getUserAgent(context: Context): String {
        val userAgent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                WebSettings.getDefaultUserAgent(context)
            } catch (e: Exception) {
                System.getProperty("http.agent")
            }

        } else {
            //API低于17获取User-Agent的方法
            System.getProperty("http.agent")
        }
        val sb = StringBuffer()
        //请求头部不支持
        userAgent.forEach { char ->
            if (char <= '\u001f' || char >= '\u007f') {
                sb.append(String.format("\\u%04x", char.toInt()))
            } else {
                sb.append(char)
            }
        }
        return sb.toString()
    }


}