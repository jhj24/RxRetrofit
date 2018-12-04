package com.jhj.httplibrary.https

import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateFactory
import javax.net.ssl.*

object SafeSslSocketFactory {

    fun sslSocketFactory(): SSLParams {
        return getSslSocketFactoryBase(null, null, null)
    }

    /**
     * https单向认证
     * 可以额外配置信任服务端的证书策略，否则默认是按CA证书去验证的，若不是CA可信任的证书，则无法通过验证
     */
    fun getSslSocketFactory(trustManager: X509TrustManager): SSLParams {
        return getSslSocketFactoryBase(trustManager, null, null)
    }

    /**
     * https单向认证
     * 用含有服务端公钥的证书校验服务端证书
     */
    fun getSslSocketFactory(vararg certificates: InputStream): SSLParams {
        return getSslSocketFactoryBase(null, null, null, *certificates)
    }

    /**
     * https双向认证
     * bksFile 和 password -> 客户端使用bks证书校验服务端证书
     * certificates -> 用含有服务端公钥的证书校验服务端证书
     */
    fun getSslSocketFactory(bksFile: InputStream, password: String, vararg certificates: InputStream): SSLParams {
        return getSslSocketFactoryBase(null, bksFile, password, *certificates)
    }

    /**
     * https双向认证
     * bksFile 和 password -> 客户端使用bks证书校验服务端证书
     * X509TrustManager -> 如果需要自己校验，那么可以自己实现相关校验，如果不需要自己校验，那么传null即可
     */
    fun getSslSocketFactory(bksFile: InputStream, password: String, trustManager: X509TrustManager): SSLParams {
        return getSslSocketFactoryBase(trustManager, bksFile, password)
    }

    private fun getSslSocketFactoryBase(trustManager: X509TrustManager?, bksFile: InputStream?, password: String?, vararg certificates: InputStream): SSLParams {
        val sslParams = SSLParams()
        try {
            val keyManagers = prepareKeyManager(bksFile, password)
            val trustManagers = prepareTrustManager(*certificates)
            val manager: X509TrustManager?
            if (trustManager != null) {
                //优先使用用户自定义的TrustManager
                manager = trustManager
            } else if (trustManagers != null) {
                //然后使用默认的TrustManager
                manager = chooseTrustManager(trustManagers)
            } else {
                //否则使用不安全的TrustManager
                manager = UnSafeX509TrustManager()
            }
            // 创建TLS类型的SSLContext对象， that uses our TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            // 用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
            // 第一个参数是授权的密钥管理器，用来授权验证，比如授权自签名的证书验证。第二个是被授权的证书管理器，用来验证服务器端的证书
            sslContext.init(keyManagers, manager?.let { arrayOf<TrustManager>(it) }, null)
            // 通过sslContext获取SSLSocketFactory对象
            sslParams.sSLSocketFactory = sslContext.socketFactory
            sslParams.trustManager = manager
            return sslParams
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError(e)
        } catch (e: KeyManagementException) {
            throw AssertionError(e)
        }

    }

    private fun prepareKeyManager(bksFile: InputStream?, password: String?): Array<KeyManager>? {
        try {
            if (bksFile == null || password == null) return null
            val clientKeyStore = KeyStore.getInstance("BKS")
            clientKeyStore.load(bksFile, password.toCharArray())
            val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            kmf.init(clientKeyStore, password.toCharArray())
            return kmf.keyManagers
        } catch (e: Exception) {
            //e.printStackTrace();
        }

        return null
    }

    private fun prepareTrustManager(vararg certificates: InputStream): Array<TrustManager>? {
        if (certificates.isEmpty())
            return null
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            // 创建一个默认类型的KeyStore，存储我们信任的证书
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            for ((index, certStream) in certificates.withIndex()) {
                val certificateAlias = Integer.toString(index)
                // 证书工厂根据证书文件的流生成证书 cert
                val cert = certificateFactory.generateCertificate(certStream)
                // 将 cert 作为可信证书放入到keyStore中
                keyStore.setCertificateEntry(certificateAlias, cert)
                try {
                    certStream.close()
                } catch (e: IOException) {
                    //e.printStackTrace();
                }

            }
            //我们创建一个默认类型的TrustManagerFactory
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            //用我们之前的keyStore实例初始化TrustManagerFactory，这样tmf就会信任keyStore中的证书
            tmf.init(keyStore)
            //通过tmf获取TrustManager数组，TrustManager也会信任keyStore中的证书
            return tmf.trustManagers
        } catch (e: Exception) {
            //e.printStackTrace();
        }

        return null
    }

    private fun chooseTrustManager(trustManagers: Array<TrustManager>): X509TrustManager? {
        for (trustManager in trustManagers) {
            if (trustManager is X509TrustManager) {
                return trustManager
            }
        }
        return null
    }

    class SSLParams {
        var sSLSocketFactory: SSLSocketFactory? = null
        var trustManager: X509TrustManager? = null
    }

}
