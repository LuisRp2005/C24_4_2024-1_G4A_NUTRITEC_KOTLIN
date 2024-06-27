package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.CategoriaComida
import retrofit2.Call
import retrofit2.http.GET

interface CategoriaComidaApi {
    @GET("/api/categorias-comida")
    fun getCategoriaComida(): Call<List<CategoriaComida>>
}