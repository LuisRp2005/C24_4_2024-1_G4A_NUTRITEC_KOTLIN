package com.gutierrez.eddy.nutritec.bottom_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.adapter.EjercicioAdapter
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EjercicioFragment : Fragment() {

    private lateinit var ejercicioAdapter: EjercicioAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ejercicio, container, false)

        recyclerView = view.findViewById(R.id.recyclerejercicio)
        recyclerView.layoutManager = LinearLayoutManager(context)
        ejercicioAdapter = EjercicioAdapter()
        recyclerView.adapter = ejercicioAdapter

        fetchEjercicios()

        return view
    }

    private fun fetchEjercicios() {
        lifecycleScope.launch {
            val response = withContext(Dispatchers.IO) {
                RetrofitInstance.ejerciciosapi.getEjercicio().execute()
            }

            if (response.isSuccessful && response.body() != null) {
                ejercicioAdapter.submitList(response.body()!!)
            } else {
                // Manejo de errores aquí
            }
        }
    }
}
