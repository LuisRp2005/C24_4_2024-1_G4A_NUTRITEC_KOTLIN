package com.gutierrez.eddy.nutritec.retrofit

import com.google.gson.GsonBuilder
import com.gutierrez.eddy.nutritec.api.EjerciciosApi
import com.gutierrez.eddy.nutritec.api.TipoimcApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.18.23:8080/"


    private val gson = GsonBuilder()
        .setLenient()

        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val tipoimcapi: TipoimcApi by lazy {
        retrofit.create(TipoimcApi::class.java)
    }

    val ejerciciosapi: EjerciciosApi by lazy {
        retrofit.create(EjerciciosApi::class.java)
    }
}
