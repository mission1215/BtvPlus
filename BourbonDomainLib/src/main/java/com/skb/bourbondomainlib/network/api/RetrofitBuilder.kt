package com.skb.bourbondomainlib.network.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Type
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object RetrofitBuilder {
    private const val TAG = "RetrofitBuilder"
    private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"
    private const val CONNECTION_TIMEOUT = 7000L
    private const val READ_TIMEOUT_7SECS = 7000L

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()
    }

    private fun getGson(): Gson {
        return GsonBuilder()
            .create()
    }

    fun <T> createService(
        service: Class<T>,
        baseUrl: String?,
        trustedHosts: List<String?>? = null,
        readTimeout: Long = READ_TIMEOUT_7SECS,
    ): T {
        val retrofit = createRetrofit(baseUrl, trustedHosts, readTimeout)
        return retrofit.create(service)
    }

    private fun createRetrofit(
        baseUrl: String?,
        trustedHosts: List<String?>? = null,
        readTimeout: Long,
    ): Retrofit {
//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//            level =
//                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
//        }
        val httpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(ApiLoggingInterceptor())
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(false)

        if (trustedHosts != null) {
            try {
                httpClientBuilder.hostnameVerifier { hostname: String?, _: SSLSession? ->
                    Timber.tag(TAG).d("verify() hostname: $hostname")
                    Timber.tag(TAG).d("verify() trusted hosts: $trustedHosts")
                    trustedHosts.contains(hostname)
                }

                // NEW sslSocketFactory setup code for new version of okhttp.
                val trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                        .apply {
                            init(null as KeyStore?)
                        }
                val trustManagers = trustManagerFactory.trustManagers
                check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                    "Unexpected default trust managers:" + trustManagers.contentToString()
                }
                val trustManager = trustManagers[0] as X509TrustManager
                val sslContext = SSLContext.getInstance("TLS").apply {
                    init(null, arrayOf<TrustManager>(trustManager), null)
                }
                httpClientBuilder.sslSocketFactory(sslContext.socketFactory, trustManager)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val builder = Retrofit.Builder()
        if (!baseUrl.isNullOrEmpty()) {
            builder.baseUrl(baseUrl)
        }
        return builder.client(httpClientBuilder.build())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    class EmptyResponse

    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type?,
            annotations: Array<Annotation>?,
            retrofit: Retrofit?,
        ): Converter<ResponseBody, *> {
            val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
            return Converter<ResponseBody, Any> {
                if (it.contentLength() == 0L) return@Converter EmptyResponse()
                delegate.convert(it)
            }
        }
    }
}