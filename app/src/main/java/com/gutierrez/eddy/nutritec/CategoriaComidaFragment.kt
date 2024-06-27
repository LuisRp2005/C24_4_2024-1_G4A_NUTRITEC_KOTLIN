package com.gutierrez.eddy.nutritec

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gutierrez.eddy.nutritec.adapter.CategoriaAdapter
import com.gutierrez.eddy.nutritec.databinding.FragmentCategoriaComidaBinding
import com.gutierrez.eddy.nutritec.models.CategoriaComida
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaComidaFragment : Fragment() {
    private var _binding: FragmentCategoriaComidaBinding? = null
    private val categoriaComidaApi = RetrofitInstance.categoriaComidaApi
    private lateinit var categoriaAdapter: CategoriaAdapter
    private val categorias = mutableListOf<CategoriaComida>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriaComidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriaAdapter = CategoriaAdapter()
        binding.recyclerCategorias.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCategorias.adapter = categoriaAdapter

        obtenerCategorias()
    }

    private fun obtenerCategorias() {
        categoriaComidaApi.getCategoriaComida().enqueue(object : Callback<List<CategoriaComida>> {
            override fun onResponse(call: Call<List<CategoriaComida>>, response: Response<List<CategoriaComida>>) {
                if (response.isSuccessful) {
                    categorias.clear()
                    categorias.addAll(response.body() ?: emptyList())
                    categoriaAdapter.submitList(categorias)
                } else {
                    // Manejar errores aquí si la respuesta no es exitosa
                }
            }

            override fun onFailure(call: Call<List<CategoriaComida>>, t: Throwable) {
                // Manejar errores de conexión aquí
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
