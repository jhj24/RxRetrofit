package com.jhj.retrofitlibrary.interceptor

import android.content.Context
import android.util.Log
import com.jhj.retrofitlibrary.cache.CacheManager
import com.jhj.retrofitlibrary.cache.CacheType
import okhttp3.*
import java.io.IOException

/**
 * 字符串的缓存类
 * Created by xiaolei on 2017/12/9.
 */
internal class CacheInterceptor(
    context: Context,
    private val isCache: Boolean,
    cacheFileName: String,
    cacheSize: Long
) : Interceptor {

    private val cacheManager: CacheManager = CacheManager.getInstance(context, cacheFileName, cacheSize)


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val cacheControl = request.header("Cache-Control")

        //意思是要缓存,这里还支持WEB端协议的缓存头
        if (isCache || cacheControl != null && !cacheControl.isEmpty()) {
            val oldnow = System.currentTimeMillis()
            val url = request.url().url().toString()
            var responseStr: String? = null
            val reqBodyStr = getPostParams(request)
            try {
                val response = chain.proceed(request)
                // 只有在网络请求返回成功之后，才进行缓存处理，否则，404存进缓存，岂不笑话
                return if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responseStr = responseBody.string()
                        if (responseStr == null) {
                            responseStr = ""
                        }
                        //存缓存，以链接+参数进行MD5编码为KEY存
                        cacheManager.setCache(CacheManager.encryptMD5(url + reqBodyStr), responseStr)
                    }
                    getOnlineResponse(response, responseStr)
                } else {
                    chain.proceed(request)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // 发生异常了，我这里就开始去缓存，但是有可能没有缓存，那么久需要丢给下一轮处理了
                val response = getCacheResponse(request, oldnow)
                return response ?: chain.proceed(request)
            }

        } else {
            return chain.proceed(request)
        }
    }

    private fun getCacheResponse(request: Request, oldNow: Long): Response? {
        val url = request.url().url().toString()
        val params = getPostParams(request)
        //取缓存，以链接+参数进行MD5编码为KEY取
        val cacheStr = cacheManager.getCache(CacheManager.encryptMD5(url + params)) ?: return null
        val response = Response.Builder()
            .code(200)
            .body(ResponseBody.create(null, cacheStr))
            .request(request)
            .message(CacheType.CACHE_REQUEST)
            .protocol(Protocol.HTTP_1_1)
            .build()
        val useTime = System.currentTimeMillis() - oldNow
        Log.w("cacheInterceptor", useTime.toString())
        return response
    }

    private fun getOnlineResponse(response: Response, body: String?): Response {
        val responseBody = response.body()
        return Response.Builder()
            .code(response.code())
            .body(ResponseBody.create(responseBody?.contentType(), body ?: ""))
            .request(response.request())
            .message(response.message())
            .protocol(response.protocol())
            .build()
    }

    /**
     * 获取在Post方式下。向服务器发送的参数
     *
     * @param request Request
     * @return str
     */
    private fun getPostParams(request: Request): String {
        var reqBodyStr = ""
        val method = request.method()
        // 如果是Post，则尽可能解析每个参数
        if ("POST" == method) {
            val sb = StringBuilder()
            if (request.body() is FormBody) {
                val body = request.body()
                if (body is FormBody && body.size() > 0) {
                    for (i in 0 until body.size()) {
                        sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",")
                    }
                    sb.delete(sb.length - 1, sb.length)
                    reqBodyStr = sb.toString()
                    sb.delete(0, sb.length)
                }
            }
        }
        return reqBodyStr
    }

}
