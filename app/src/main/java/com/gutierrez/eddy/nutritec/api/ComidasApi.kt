package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.Comidas
import retrofit2.Call
import retrofit2.http.GET

interface ComidasApi {
    @GET("/api/v1/comida")
    fun getComidas():Call<List<Comidas>>
}