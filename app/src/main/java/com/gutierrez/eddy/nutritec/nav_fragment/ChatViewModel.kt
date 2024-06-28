package com.gutierrez.eddy.nutritec.nav_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.gutierrez.eddy.nutritec.models.ChatResponse

class ChatViewModel : ViewModel() {

    fun sendMessage(message: String, callback: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiService = RetrofitInstance.createApiService()
            val requestBody = mapOf("message" to message)
            val call = apiService.sendMessage(requestBody)
            Log.d("ChatViewModel", "Sending request: $requestBody to ${call.request().url}")
            call.enqueue(object : Callback<ChatResponse> {
                override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                    if (response.isSuccessful) {
                        Log.d("ChatViewModel", "Response: ${response.body()}")
                        callback(response.body()?.response)
                    } else {
                        Log.e("ChatViewModel", "Response error: ${response.errorBody()?.string()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    Log.e("ChatViewModel", "Request failed", t)
                    callback(null)
                }
            })
        }
    }
}
