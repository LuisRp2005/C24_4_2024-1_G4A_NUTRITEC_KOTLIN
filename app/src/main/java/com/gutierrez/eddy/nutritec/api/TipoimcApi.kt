package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.TipoIMC
import retrofit2.Call
import retrofit2.http.GET

interface TipoimcApi {
    @GET("api/tipos-imc")
    fun getTipoimc():Call < List<TipoIMC>>
}
