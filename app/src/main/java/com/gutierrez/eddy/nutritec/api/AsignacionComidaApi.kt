package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.AsignacionComida
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AsignacionComidaApi {
    @GET("/api/asignacion-comidas")
    fun listarAsignacionesUsuario(@Query("usuarioId") usuarioId: Int): Call<List<AsignacionComida>>

    @GET("/api/asignacion-comidas/{id}")
    fun listarAsignacionPorId(@Path("id") id: Int): Call<AsignacionComida>
}
