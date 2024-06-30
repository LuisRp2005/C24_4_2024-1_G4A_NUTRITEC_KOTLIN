package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.AsignacionComida
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

interface AsignacionComidaApi {
    @GET("/api/asignacion-comidas/usuario/{idUsuario}")
    fun listarAsignacionesUsuario(@Path("idUsuario") idUsuario: Int): Call<List<AsignacionComida>>

    @GET("/api/asignacion-comidas/{id}")
    fun listarAsignacionPorId(@Path("id") id: Int): Call<AsignacionComida>

    @POST("/api/asignacion-comidas")
    fun asignarComida(@Body asignacionComida: AsignacionComida): Call<AsignacionComida>

    @DELETE("/api/asignacion-comidas/{id}")
    fun eliminarAsignacion(@Path("id") id: Int): Call<Void>
}
