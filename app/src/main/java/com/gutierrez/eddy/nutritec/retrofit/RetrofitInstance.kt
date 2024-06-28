package com.gutierrez.eddy.nutritec.retrofit

import UsuariosApi
import com.google.gson.GsonBuilder
import com.gutierrez.eddy.nutritec.api.ApiService
import com.gutierrez.eddy.nutritec.api.CategoriaComidaApi
import com.gutierrez.eddy.nutritec.api.ComidasApi
import com.gutierrez.eddy.nutritec.api.EjerciciosApi
import com.gutierrez.eddy.nutritec.api.TipoimcApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.18.10:8080/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val tipoimcapi: TipoimcApi by lazy {
        retrofit.create(TipoimcApi::class.java)
    }

    val ejerciciosapi: EjerciciosApi by lazy {
        retrofit.create(EjerciciosApi::class.java)
    }

    val comidasApi: ComidasApi by lazy {
        retrofit.create(ComidasApi::class.java)
    }

    val categoriaComidaApi: CategoriaComidaApi by lazy {
        retrofit.create(CategoriaComidaApi::class.java)
    }

    val usuariosApi: UsuariosApi by lazy {
        retrofit.create(UsuariosApi::class.java)
    }
    fun createApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}