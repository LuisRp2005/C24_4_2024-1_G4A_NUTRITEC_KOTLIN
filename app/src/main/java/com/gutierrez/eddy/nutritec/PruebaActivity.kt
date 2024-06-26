package com.gutierrez.eddy.nutritec

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gutierrez.eddy.nutritec.adapter.TipoimcAdapter
import com.gutierrez.eddy.nutritec.databinding.ActivityPruebaBinding
import com.gutierrez.eddy.nutritec.models.TipoIMC
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PruebaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPruebaBinding
    private lateinit var adapter: TipoimcAdapter
    private val tipoimcApi = RetrofitInstance.tipoimcapi
    private val TAG = "PruebaActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPruebaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TipoimcAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        // Realizar la llamada a la API directamente en la actividad
        tipoimcApi.getTipoimc().enqueue(object : Callback<List<TipoIMC>> {
            override fun onResponse(call: Call<List<TipoIMC>>, response: Response<List<TipoIMC>>) {
                if (response.isSuccessful) {
                    val tipos = response.body()
                    if (tipos != null) {
                        adapter.submitList(tipos)
                    }
                    Log.d(TAG, "Listado de tipos IMC exitoso: $tipos")
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
}
