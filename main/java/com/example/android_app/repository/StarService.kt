package com.example.android_app.repository

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface StarService {

    @GET("/api/starships")
    suspend fun getStarResponse(): Response<StarResponse>

    companion object{
        private const val BASE_URL = "https://swapi.dev/"

//        private val logger =
//            HttpLoggingInterceptor().apply {
//                this.level = HttpLoggingInterceptor.Level.BODY }
//
//        private val okHttp = OkHttpClient.Builder().apply {
//            this.addInterceptor(logger) }.build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory((GsonConverterFactory.create()))
                .build()
        }

        val starService: StarService by lazy {
            retrofit.create(StarService::class.java)
        }
    }
}