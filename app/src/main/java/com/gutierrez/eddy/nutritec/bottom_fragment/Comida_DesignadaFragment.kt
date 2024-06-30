package com.gutierrez.eddy.nutritec.bottom_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.adapter.AsignacionComidaAdapter
import com.gutierrez.eddy.nutritec.models.AsignacionComida
import com.gutierrez.eddy.nutritec.models.Usuarios
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Comida_DesignadaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AsignacionComidaAdapter
    private lateinit var asignaciones: List<AsignacionComida>
    private var idUsuarioLogueado: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comida_designada, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewComidas)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val correoUsuarioLogueado = obtenerCorreoUsuarioLogueado()

        val usuariosApi = RetrofitInstance.usuariosApi
        usuariosApi.buscarUsuarioPorCorreo(correoUsuarioLogueado).enqueue(object : Callback<Usuarios> {
            override fun onResponse(call: Call<Usuarios>, response: Response<Usuarios>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        idUsuarioLogueado = usuario.idUsuario ?: 0
                        obtenerAsignacionesDeComida(idUsuarioLogueado)
                    } else {
                        Log.e("Comida_DesignadaFragment", "No se encontró al usuario con el correo: $correoUsuarioLogueado")
                    }
                } else {
                    Log.e("Comida_DesignadaFragment", "Error en la respuesta de buscarUsuarioPorCorreo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuarios>, t: Throwable) {
                Log.e("Comida_DesignadaFragment", "Error al ejecutar la llamada de buscarUsuarioPorCorreo: ${t.message}")
            }
        })
    }

    private fun obtenerCorreoUsuarioLogueado(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("USER_EMAIL", "") ?: ""
    }

    private fun obtenerAsignacionesDeComida(idUsuario: Int) {
        RetrofitInstance.asignacionComidaApi.listarAsignacionesUsuario(idUsuario).enqueue(object : Callback<List<AsignacionComida>> {
            override fun onResponse(call: Call<List<AsignacionComida>>, response: Response<List<AsignacionComida>>) {
                if (response.isSuccessful) {
                    asignaciones = response.body() ?: emptyList()
                    adapter = AsignacionComidaAdapter(asignaciones) { asignacion ->
                        eliminarAsignacion(asignacion)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Log.e("Comida_DesignadaFragment", "Error en la respuesta de listarAsignacionesUsuario: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AsignacionComida>>, t: Throwable) {
                Log.e("Comida_DesignadaFragment", "Error al ejecutar la llamada de listarAsignacionesUsuario: ${t.message}")
            }
        })
    }

    private fun eliminarAsignacion(asignacion: AsignacionComida) {
        RetrofitInstance.asignacionComidaApi.eliminarAsignacion(asignacion.idAsignacionComida).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Asignación de comida eliminada con éxito", Toast.LENGTH_SHORT).show()
                    asignaciones = asignaciones.filter { it.idAsignacionComida != asignacion.idAsignacionComida }
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("Comida_DesignadaFragment", "Error en la respuesta de eliminarAsignacion: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Comida_DesignadaFragment", "Error al ejecutar la llamada de eliminarAsignacion: ${t.message}")
            }
        })
    }
}
