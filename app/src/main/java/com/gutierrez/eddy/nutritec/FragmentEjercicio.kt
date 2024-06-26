package com.gutierrez.eddy.nutritec

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gutierrez.eddy.nutritec.adapter.EjercicioAdapter
import com.gutierrez.eddy.nutritec.databinding.FragmentEjercicioBinding
import com.gutierrez.eddy.nutritec.models.Ejercicios
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentEjercicio : Fragment() {
    private var _binding: FragmentEjercicioBinding? = null
    private val binding get() = _binding!!
    private lateinit var ejercicioAdapter: EjercicioAdapter
    private val ejerciciosapi = RetrofitInstance.ejerciciosapi
    private val TAG = "FragmentEjercicio"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEjercicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        binding.recyclerejercicio.layoutManager = LinearLayoutManager(requireContext())

        // Inicializar el adaptador
        ejercicioAdapter = EjercicioAdapter()

        // Adjuntar el adaptador al RecyclerView
        binding.recyclerejercicio.adapter = ejercicioAdapter

        // Llamar al servicio web para obtener la lista de ejercicios
        ejerciciosapi.getEjercicio().enqueue(object : Callback<List<Ejercicios>> {
            override fun onResponse(call: Call<List<Ejercicios>>, response: Response<List<Ejercicios>>) {
                if (response.isSuccessful) {
                    val ejercicios = response.body()
                    if (ejercicios != null) {
                        // Actualizar el adaptador con los datos recibidos
                        ejercicioAdapter.submitList(ejercicios)
                    }
                    Log.d(TAG, "Listado de ejercicios exitoso: $ejercicios")
                } else {
                    Log.e(TAG, "Error en la respuesta: ${response.code()}")
                    // Manejar errores aquí si la respuesta no es exitosa
                }
            }

            override fun onFailure(call: Call<List<Ejercicios>>, t: Throwable) {
                Log.e(TAG, "Error de conexión: ${t.message}")
                // Manejar errores de conexión aquí
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
