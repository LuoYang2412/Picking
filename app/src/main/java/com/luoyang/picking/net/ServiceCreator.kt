package com.luoyang.picking.net

import com.javalong.retrofitmocker.createMocker
import com.luoyang.picking.BuildConfig
import com.luoyang.picking.PickingApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

object ServiceCreator {

    private const val BASE_URL = BuildConfig.HOST

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor {
            val oldRequest = it.request()
            val newRequest = oldRequest.newBuilder()
                .addHeader("Authorization", PickingApplication.application.userInfo?.token ?: "")
                .build()
            it.proceed(newRequest)
        })
        .addInterceptor(HttpLoggingInterceptor {
            Timber.d(it)
        }.setLevel(HttpLoggingInterceptor.Level.BODY))

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())


    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T =
        retrofit.createMocker(serviceClass, BuildConfig.DEBUG)
}