package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.AsignacionEjercicio
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AsignacionEjercicioApi {
    @GET("/api/asignacion-ejercicios")
    fun listarTodasLasAsignaciones(): Call<List<AsignacionEjercicio>>

    @GET("/api/asignacion-ejercicios/{id}")
    fun listarAsignacionPorId(@Path("id") id: Int): Call<AsignacionEjercicio>
}
