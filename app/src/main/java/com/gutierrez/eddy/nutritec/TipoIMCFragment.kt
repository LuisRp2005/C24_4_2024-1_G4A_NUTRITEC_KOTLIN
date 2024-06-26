package com.gutierrez.eddy.nutritec

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gutierrez.eddy.nutritec.adapter.TipoimcAdapter
import com.gutierrez.eddy.nutritec.databinding.FragmentTipoIMCBinding
import com.gutierrez.eddy.nutritec.models.TipoIMC
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TipoIMCFragment : Fragment() {
    private var _binding: FragmentTipoIMCBinding? = null
    private val tipoimcApi = RetrofitInstance.tipoimcapi
    private lateinit var itemTipoimcAdapter: TipoimcAdapter
    private val tipoIMC = mutableListOf<TipoIMC>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTipoIMCBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        itemTipoimcAdapter = TipoimcAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = itemTipoimcAdapter

        obtenerServicios()
    }

    private fun obtenerServicios() {
        tipoimcApi.getTipoimc().enqueue(object : Callback<List<TipoIMC>> {
            override fun onResponse(call: Call<List<TipoIMC>>, response: Response<List<TipoIMC>>) {
                if (response.isSuccessful) {
                    tipoIMC.clear()
                    tipoIMC.addAll(response.body() ?: emptyList())
                    itemTipoimcAdapter.notifyDataSetChanged()
                } else {
                    // Manejar errores aquí si la respuesta no es exitosa
                }
            }

            override fun onFailure(call: Call<List<TipoIMC>>, t: Throwable) {
                // Manejar errores de conexión aquí
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
