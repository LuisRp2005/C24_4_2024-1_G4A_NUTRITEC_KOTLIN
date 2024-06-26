package com.gutierrez.eddy.nutritec

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gutierrez.eddy.nutritec.models.TipoIMC
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModelPrueba : ViewModel() {
    private val tipoimcApi = RetrofitInstance.tipoimcapi
    val listaTipo = MutableLiveData<List<TipoIMC>>()

    fun listarTipos() {
        tipoimcApi.getTipoimc().enqueue(object : Callback<List<TipoIMC>> {
            override fun onResponse(call: Call<List<TipoIMC>>, response: Response<List<TipoIMC>>) {
                if (response.isSuccessful) {
                    listaTipo.postValue(response.body())
                    Log.d(TAG, "Listado de tipos IMC exitoso: ${response.body()}")
                } else {
                    Log.e(TAG, "Error en la respuesta: ${response.code()}")
                    // Manejar errores aquí si la respuesta no es exitosa
                }
            }

            override fun onFailure(call: Call<List<TipoIMC>>, t: Throwable) {
                Log.e(TAG, "Error de conexión: ${t.message}")
                // Manejar errores de conexión aquí
            }
        })
    }

    companion object {
        private const val TAG = "ListViewModelPrueba"
    }
}
