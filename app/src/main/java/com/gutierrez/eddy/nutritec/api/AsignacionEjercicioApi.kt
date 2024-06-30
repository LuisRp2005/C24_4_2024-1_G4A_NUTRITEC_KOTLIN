package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.AsignacionEjercicio
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

interface AsignacionEjercicioApi {
    @GET("/api/asignacion-ejercicios/usuario/{idUsuario}")
    fun listarAsignacionesUsuario(@Path("idUsuario") idUsuario: Int): Call<List<AsignacionEjercicio>>

    @GET("/api/asignacion-ejercicios/{id}")
    fun listarAsignacionPorId(@Path("id") id: Int): Call<AsignacionEjercicio>

    @POST("/api/asignacion-ejercicios")
    fun asignarEjercicio(@Body asignacionEjercicio: AsignacionEjercicio): Call<AsignacionEjercicio>

    @DELETE("/api/asignacion-ejercicios/{id}")
    fun eliminarAsignacion(@Path("id") id: Int): Call<Void>
}
