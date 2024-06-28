package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.Usuarios
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UsuariosApi {

    @GET("usuario/{correo}")
    fun buscarUsuarioPorCorreo(@Path("correo") correo: String): Call<Usuarios>

    // Agrega más métodos según las APIs que necesites consumir
}
