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
import com.gutierrez.eddy.nutritec.adapter.ComidaAdapter
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComidaFragment : Fragment() {

    private lateinit var comidaAdapter: ComidaAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_comida, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewComidas)
        recyclerView.layoutManager = LinearLayoutManager(context)
        comidaAdapter = ComidaAdapter()
        recyclerView.adapter = comidaAdapter

        fetchComidas()

        return view
    }

    private fun fetchComidas() {
        lifecycleScope.launch {
            val response = withContext(Dispatchers.IO) {
                RetrofitInstance.comidasApi.getComidas().execute()
            }

            if (response.isSuccessful && response.body() != null) {
                comidaAdapter.submitList(response.body()!!)
            } else {
                // Manejo de errores aquí
            }
        }
    }
}
