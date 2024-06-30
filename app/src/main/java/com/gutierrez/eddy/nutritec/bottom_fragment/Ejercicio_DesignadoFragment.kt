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
import com.gutierrez.eddy.nutritec.adapter.AsignacionEjercicioAdapter
import com.gutierrez.eddy.nutritec.models.AsignacionEjercicio
import com.gutierrez.eddy.nutritec.models.Usuarios
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ejercicio_DesignadoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AsignacionEjercicioAdapter
    private lateinit var asignaciones: List<AsignacionEjercicio>
    private var idUsuarioLogueado: Int = 0

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

        val correoUsuarioLogueado = obtenerCorreoUsuarioLogueado()

        val usuariosApi = RetrofitInstance.usuariosApi
        usuariosApi.buscarUsuarioPorCorreo(correoUsuarioLogueado).enqueue(object : Callback<Usuarios> {
            override fun onResponse(call: Call<Usuarios>, response: Response<Usuarios>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        idUsuarioLogueado = usuario.idUsuario ?: 0
                        obtenerAsignacionesDeEjercicio(idUsuarioLogueado)
                    } else {
                        Log.e("Ejercicio_DesignadoFragment", "No se encontró al usuario con el correo: $correoUsuarioLogueado")
                    }
                } else {
                    Log.e("Ejercicio_DesignadoFragment", "Error en la respuesta de buscarUsuarioPorCorreo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuarios>, t: Throwable) {
                Log.e("Ejercicio_DesignadoFragment", "Error al ejecutar la llamada de buscarUsuarioPorCorreo: ${t.message}")
            }
        })
    }

    private fun obtenerCorreoUsuarioLogueado(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("USER_EMAIL", "") ?: ""
    }

    private fun obtenerAsignacionesDeEjercicio(idUsuario: Int) {
        RetrofitInstance.asignacionEjercicioApi.listarAsignacionesUsuario(idUsuario).enqueue(object : Callback<List<AsignacionEjercicio>> {
            override fun onResponse(call: Call<List<AsignacionEjercicio>>, response: Response<List<AsignacionEjercicio>>) {
                if (response.isSuccessful) {
                    asignaciones = response.body() ?: emptyList()
                    adapter = AsignacionEjercicioAdapter(asignaciones) { asignacion ->
                        eliminarAsignacion(asignacion)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Log.e("Ejercicio_DesignadoFragment", "Error en la respuesta de listarAsignacionesUsuario: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AsignacionEjercicio>>, t: Throwable) {
                Log.e("Ejercicio_DesignadoFragment", "Error al ejecutar la llamada de listarAsignacionesUsuario: ${t.message}")
            }
        })
    }

    private fun eliminarAsignacion(asignacion: AsignacionEjercicio) {
        RetrofitInstance.asignacionEjercicioApi.eliminarAsignacion(asignacion.idAsignacionEjercicio).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Asignación de ejercicio eliminada con éxito", Toast.LENGTH_SHORT).show()
                    asignaciones = asignaciones.filter { it.idAsignacionEjercicio != asignacion.idAsignacionEjercicio }
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("Ejercicio_DesignadoFragment", "Error en la respuesta de eliminarAsignacion: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Ejercicio_DesignadoFragment", "Error al ejecutar la llamada de eliminarAsignacion: ${t.message}")
            }
        })
    }
}
