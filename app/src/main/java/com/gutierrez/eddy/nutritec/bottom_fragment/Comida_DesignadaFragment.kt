package com.gutierrez.eddy.nutritec.bottom_fragment

import AsignacionComidaAdapter
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import com.gutierrez.eddy.nutritec.models.AsignacionComida
import com.gutierrez.eddy.nutritec.models.Usuarios
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Comida_DesignadaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AsignacionComidaAdapter
    private lateinit var asignaciones: List<AsignacionComida>
    private var idUsuarioLogueado: Int = 0 // Aquí se almacenará el ID del usuario logueado

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

        // Obtener el correo electrónico del usuario logueado desde SharedPreferences
        val correoUsuarioLogueado = obtenerCorreoUsuarioLogueado()

        // Llamar a la API para buscar al usuario por correo electrónico
        val usuariosApi = RetrofitInstance.usuariosApi
        usuariosApi.buscarUsuarioPorCorreo(correoUsuarioLogueado).enqueue(object : Callback<Usuarios> {
            override fun onResponse(call: Call<Usuarios>, response: Response<Usuarios>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        idUsuarioLogueado = usuario.idUsuario ?: 0

                        // Una vez obtenido el ID del usuario logueado, llamar a la API para obtener las asignaciones de comida
                        obtenerAsignacionesDeComida(idUsuarioLogueado)
                    } else {
                        // Manejar caso donde el usuario no se encuentra
                        Log.e("Comida_DesignadaFragment", "No se encontró al usuario con el correo: $correoUsuarioLogueado")
                    }
                } else {
                    // Manejar respuesta no exitosa
                    Log.e("Comida_DesignadaFragment", "Error en la respuesta de buscarUsuarioPorCorreo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuarios>, t: Throwable) {
                // Manejar el error
                Log.e("Comida_DesignadaFragment", "Error al ejecutar la llamada de buscarUsuarioPorCorreo: ${t.message}")
            }
        })
    }

    // Método para obtener el correo electrónico del usuario logueado desde SharedPreferences
    private fun obtenerCorreoUsuarioLogueado(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", MODE_PRIVATE)
        return sharedPreferences.getString("USER_EMAIL", "") ?: ""
    }

    // Método para obtener las asignaciones de comida del usuario logueado
    private fun obtenerAsignacionesDeComida(idUsuario: Int) {
        RetrofitInstance.asignacionComidaApi.listarAsignacionesUsuario(idUsuario).enqueue(object : Callback<List<AsignacionComida>> {
            override fun onResponse(call: Call<List<AsignacionComida>>, response: Response<List<AsignacionComida>>) {
                if (response.isSuccessful) {
                    asignaciones = response.body() ?: emptyList()
                    adapter = AsignacionComidaAdapter(asignaciones)
                    recyclerView.adapter = adapter
                } else {
                    // Manejar respuesta no exitosa
                    Log.e("Comida_DesignadaFragment", "Error en la respuesta de listarAsignacionesUsuario: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AsignacionComida>>, t: Throwable) {
                // Manejar el error
                Log.e("Comida_DesignadaFragment", "Error al ejecutar la llamada de listarAsignacionesUsuario: ${t.message}")
            }
        })
    }
}

