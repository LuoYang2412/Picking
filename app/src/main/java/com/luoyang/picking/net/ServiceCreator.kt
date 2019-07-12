package com.luoyang.picking.net

import com.javalong.retrofitmocker.createMocker
import com.luoyang.picking.BuildConfig
import com.luoyang.picking.PickingApplication
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

object ServiceCreator {

    private const val BASE_URL = "https://mobile.api.com/"

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor {
            val oldRequest = it.request()
            if (PickingApplication.application.userInfo != null) {
                if (oldRequest.method().equals("GET")) {
                    val url = oldRequest.url().newBuilder()
                        .addQueryParameter("token", PickingApplication.application.userInfo!!.token)
                        .build()
                    val newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(url).build()
                    return@Interceptor it.proceed(newRequest)
                } else if (oldRequest.method().equals("POST")) {
                    val bodyBuilder = FormBody.Builder()
                    val oldBody = oldRequest.body()
                    if (oldBody is FormBody) {
                        for (i in 0 until oldBody.size()) {
                            bodyBuilder.addEncoded(oldBody.encodedName(i), oldBody.encodedValue(i))
                        }
                    }
                    val formBody =
                        bodyBuilder
                            .addEncoded("token", PickingApplication.application.userInfo!!.token)
                            .build()
                    val newRequest = oldRequest.newBuilder().post(formBody)
                        .addHeader("Authorization", PickingApplication.application.userInfo!!.token)
                        .build()
                    return@Interceptor it.proceed(newRequest)
                }
            }

            it.proceed(oldRequest)
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