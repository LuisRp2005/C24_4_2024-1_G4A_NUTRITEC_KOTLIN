package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.Ejercicios
import retrofit2.Call
import retrofit2.http.GET

interface EjerciciosApi {
    @GET ("api/v1/ejercicio")
    fun getEjercicio (): Call< List<Ejercicios>>
}