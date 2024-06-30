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
import com.gutierrez.eddy.nutritec.adapter.ComidaAdapter
import com.gutierrez.eddy.nutritec.models.AsignacionComida
import com.gutierrez.eddy.nutritec.models.Comidas
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

class ComidaFragment : Fragment() {

    private lateinit var comidaAdapter: ComidaAdapter
    private lateinit var recyclerView: RecyclerView
    private var selectedComida: Comidas? = null
    private var usuarioLogueado: Usuarios? = null
    private var asignacionesExistentes: List<AsignacionComida> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comida, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewComidas)
        recyclerView.layoutManager = LinearLayoutManager(context)
        comidaAdapter = ComidaAdapter(
            onComidaClick = { comida ->
                selectedComida = comida
            },
            onAsignarClick = { comida ->
                asignarComida(comida)
            }
        )
        recyclerView.adapter = comidaAdapter

        fetchComidas()
        obtenerUsuarioLogueado()

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
                Log.e("ComidaFragment", "Error en la respuesta de getComidas: ${response.code()}")
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
                    if (usuario != null && usuario.idUsuario != null) {
                        usuarioLogueado = usuario
                        obtenerAsignacionesExistentes(usuario.idUsuario!!)
                    } else {
                        Log.e("ComidaFragment", "No se encontró al usuario con el correo: $correoUsuarioLogueado")
                    }
                } else {
                    Log.e("ComidaFragment", "Error en la respuesta de buscarUsuarioPorCorreo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuarios>, t: Throwable) {
                Log.e("ComidaFragment", "Error al ejecutar la llamada de buscarUsuarioPorCorreo: ${t.message}")
            }
        })
    }

    private fun obtenerCorreoUsuarioLogueado(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("USER_EMAIL", "") ?: ""
    }

    private fun obtenerAsignacionesExistentes(usuarioId: Int) {
        RetrofitInstance.asignacionComidaApi.listarAsignacionesUsuario(usuarioId).enqueue(object : Callback<List<AsignacionComida>> {
            override fun onResponse(call: Call<List<AsignacionComida>>, response: Response<List<AsignacionComida>>) {
                if (response.isSuccessful) {
                    asignacionesExistentes = response.body() ?: emptyList()
                } else {
                    Log.e("ComidaFragment", "Error en la respuesta de listarAsignacionesUsuario: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AsignacionComida>>, t: Throwable) {
                Log.e("ComidaFragment", "Error al ejecutar la llamada de listarAsignacionesUsuario: ${t.message}")
            }
        })
    }

    private fun asignarComida(comida: Comidas) {
        val usuario = usuarioLogueado
        if (usuario != null) {
            if (asignacionesExistentes.any { it.comida.idComida == comida.idComida }) {
                Toast.makeText(context, "Esta comida ya ha sido asignada.", Toast.LENGTH_SHORT).show()
                return
            }

            val currentDateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
            val asignacionComida = AsignacionComida(
                idAsignacionComida = 0,
                usuario = usuario,
                comida = comida,
                fechaHoraRegistro = currentDateTime
            )

            RetrofitInstance.asignacionComidaApi.asignarComida(asignacionComida).enqueue(object : Callback<AsignacionComida> {
                override fun onResponse(call: Call<AsignacionComida>, response: Response<AsignacionComida>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Comida asignada con éxito", Toast.LENGTH_SHORT).show()
                        asignacionesExistentes = asignacionesExistentes + response.body()!!
                    } else {
                        Log.e("ComidaFragment", "Error en la respuesta de asignarComida: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<AsignacionComida>, t: Throwable) {
                    Log.e("ComidaFragment", "Error al ejecutar la llamada de asignarComida: ${t.message}")
                }
            })
        } else {
            Toast.makeText(context, "No se ha podido obtener el usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
