package com.gutierrez.eddy.nutritec.bottom_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.adapter.EjercicioAdapter
import com.gutierrez.eddy.nutritec.models.AsignacionEjercicio
import com.gutierrez.eddy.nutritec.models.Ejercicios
import com.gutierrez.eddy.nutritec.models.Usuarios
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EjercicioFragment : Fragment() {

    private lateinit var ejercicioAdapter: EjercicioAdapter
    private lateinit var recyclerView: RecyclerView
    private var selectedEjercicio: Ejercicios? = null
    private var usuarioLogueado: Usuarios? = null
    private var asignacionesExistentes: List<AsignacionEjercicio> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ejercicio, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewEjercicios)
        recyclerView.layoutManager = LinearLayoutManager(context)
        ejercicioAdapter = EjercicioAdapter(
            onEjercicioClick = { ejercicio ->
                selectedEjercicio = ejercicio
            },
            onAsignarClick = { ejercicio ->
                asignarEjercicio(ejercicio)
            }
        )
        recyclerView.adapter = ejercicioAdapter

        fetchEjercicios()
        obtenerUsuarioLogueado()

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
                Log.e("EjercicioFragment", "Error en la respuesta de getEjercicio: ${response.code()}")
            }
        }
    }

    private fun obtenerUsuarioLogueado() {
        val correoUsuarioLogueado = obtenerCorreoUsuarioLogueado()
        val usuariosApi = RetrofitInstance.usuariosApi
        usuariosApi.buscarUsuarioPorCorreo(correoUsuarioLogueado).enqueue(object : Callback<Usuarios> {
            override fun onResponse(call: Call<Usuarios>, response: Response<Usuarios>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        usuarioLogueado = usuario
                        obtenerAsignacionesExistentes(usuario.idUsuario!!)
                    } else {
                        Log.e("EjercicioFragment", "No se encontró al usuario con el correo: $correoUsuarioLogueado")
                    }
                } else {
                    Log.e("EjercicioFragment", "Error en la respuesta de buscarUsuarioPorCorreo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuarios>, t: Throwable) {
                Log.e("EjercicioFragment", "Error al ejecutar la llamada de buscarUsuarioPorCorreo: ${t.message}")
            }
        })
    }

    private fun obtenerCorreoUsuarioLogueado(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("USER_EMAIL", "") ?: ""
    }

    private fun obtenerAsignacionesExistentes(usuarioId: Int) {
        RetrofitInstance.asignacionEjercicioApi.listarAsignacionesUsuario(usuarioId).enqueue(object : Callback<List<AsignacionEjercicio>> {
            override fun onResponse(call: Call<List<AsignacionEjercicio>>, response: Response<List<AsignacionEjercicio>>) {
                if (response.isSuccessful) {
                    asignacionesExistentes = response.body() ?: emptyList()
                } else {
                    Log.e("EjercicioFragment", "Error en la respuesta de listarAsignacionesUsuario: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AsignacionEjercicio>>, t: Throwable) {
                Log.e("EjercicioFragment", "Error al ejecutar la llamada de listarAsignacionesUsuario: ${t.message}")
            }
        })
    }

    private fun asignarEjercicio(ejercicio: Ejercicios) {
        val usuario = usuarioLogueado
        if (usuario != null) {
            if (asignacionesExistentes.any { it.ejercicio.id == ejercicio.id }) {
                Toast.makeText(context, "Este ejercicio ya ha sido asignado.", Toast.LENGTH_SHORT).show()
                return
            }

            val currentDateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
            val asignacionEjercicio = AsignacionEjercicio(
                idAsignacionEjercicio = 0,
                usuario = usuario,
                ejercicio = ejercicio,
                fechaHoraAsignacion = currentDateTime
            )

            RetrofitInstance.asignacionEjercicioApi.asignarEjercicio(asignacionEjercicio).enqueue(object : Callback<AsignacionEjercicio> {
                override fun onResponse(call: Call<AsignacionEjercicio>, response: Response<AsignacionEjercicio>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Ejercicio asignado con éxito", Toast.LENGTH_SHORT).show()
                        asignacionesExistentes = asignacionesExistentes + response.body()!!
                    } else {
                        Log.e("EjercicioFragment", "Error en la respuesta de asignarEjercicio: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<AsignacionEjercicio>, t: Throwable) {
                    Log.e("EjercicioFragment", "Error al ejecutar la llamada de asignarEjercicio: ${t.message}")
                }
            })
        } else {
            Toast.makeText(context, "No se ha podido obtener el usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
