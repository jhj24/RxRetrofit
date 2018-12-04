package com.jhj.httplibrary.https

import android.annotation.SuppressLint
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * 存在安全漏洞，信任所有域名
 *
 * 实际应用中具体规则可以根据实际需求
 */
class UnSafeHostnameVerifier : HostnameVerifier {
    @SuppressLint("BadHostnameVerifier")
    override fun verify(hostname: String?, session: SSLSession?): Boolean {

        return true
    }
}