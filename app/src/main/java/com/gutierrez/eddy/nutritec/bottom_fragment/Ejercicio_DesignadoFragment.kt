package com.gutierrez.eddy.nutritec.bottom_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.adapter.AsignacionEjercicioAdapter
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import com.gutierrez.eddy.nutritec.models.AsignacionEjercicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ejercicio_DesignadoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AsignacionEjercicioAdapter
    private lateinit var asignaciones: List<AsignacionEjercicio>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ejercicio_designado, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewEjercicios)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RetrofitInstance.asignacionEjercicioApi.listarTodasLasAsignaciones().enqueue(object: Callback<List<AsignacionEjercicio>> {
            override fun onResponse(call: Call<List<AsignacionEjercicio>>, response: Response<List<AsignacionEjercicio>>) {
                if (response.isSuccessful) {
                    asignaciones = response.body() ?: emptyList()
                    adapter = AsignacionEjercicioAdapter(asignaciones)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<AsignacionEjercicio>>, t: Throwable) {
                // Manejar el error
            }
        })
    }
}

