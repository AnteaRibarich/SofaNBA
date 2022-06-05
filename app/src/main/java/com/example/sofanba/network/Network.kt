package com.example.sofanba.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {

    private val service: BallDontLieService
    private val baseUrl = "https://www.balldontlie.io/api/v1/"

    private val serviceSofa: SofaService
    private val baseUrlSofa = "https://academy-2022.dev.sofascore.com/api/v1/academy/"

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)
        val retrofit =
            Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).build()
        service = retrofit.create(BallDontLieService::class.java)
        val retrofitSofa =
            Retrofit.Builder().baseUrl(baseUrlSofa).addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).build()
        serviceSofa = retrofitSofa.create(SofaService::class.java)
    }

    fun getService(): BallDontLieService = service
    fun getServiceSofa(): SofaService = serviceSofa
}
