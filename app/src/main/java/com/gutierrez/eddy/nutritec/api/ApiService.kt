package com.gutierrez.eddy.nutritec.api

import com.gutierrez.eddy.nutritec.models.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/chat/send")
    fun sendMessage(@Body requestBody: Map<String, String>): Call<ChatResponse>
}
