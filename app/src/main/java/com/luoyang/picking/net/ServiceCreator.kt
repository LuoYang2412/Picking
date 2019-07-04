package com.luoyang.picking.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

object ServiceCreator {

    //        private const val BASE_URL = "https://www.easy-mock.com/mock/5bf25d3c34392218c898a66b/QuickRun/"
//    private const val BASE_URL = "https://twms.qbftt.com/"//测试服
    private const val BASE_URL = "http://192.168.3.237:8081/"

    const val IMAGE_BASE_URL = "${BASE_URL}upload/"

    private val httpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor {
        Timber.d(it)
    }.setLevel(HttpLoggingInterceptor.Level.BODY))

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())


    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}