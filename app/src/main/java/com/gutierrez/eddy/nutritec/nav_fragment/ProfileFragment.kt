package com.gutierrez.eddy.nutritec.nav_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.databinding.FragmentProfileBinding
import com.gutierrez.eddy.nutritec.models.Usuarios
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var usuario: Usuarios

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        account?.let { googleAccount ->
            // Mostrar nombre y apellido
            binding.tvName.text = googleAccount.displayName
            binding.tvEmail.text = googleAccount.email
            binding.tvLastName.text = googleAccount.familyName

            // Cargar imagen de perfil
            Glide.with(this)
                .load(googleAccount.photoUrl)
                .into(binding.ivProfileImage)

            // Llamar a la API para obtener los datos del usuario registrado
            buscarUsuarioPorCorreo(googleAccount.email)
        }
    }

    private fun buscarUsuarioPorCorreo(email: String?) {
        // Llamar a la API para buscar el usuario por su correo electrónico
        val call = RetrofitInstance.usuariosApi.buscarUsuarioPorCorreo(email ?: "")
        call.enqueue(object : Callback<Usuarios> {
            override fun onResponse(call: Call<Usuarios>, response: Response<Usuarios>) {
                if (response.isSuccessful) {
                    response.body()?.let { usuario ->
                        // Mostrar los datos del usuario
                        this@ProfileFragment.usuario = usuario
                        mostrarDatosUsuario(usuario)
                    }
                } else {
                    Snackbar.make(requireView(), "Error al obtener datos del usuario", Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuarios>, t: Throwable) {
                Snackbar.make(requireView(), "Error de conexión", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDatosUsuario(usuario: Usuarios) {
        binding.tvName.text = usuario.nombre
        binding.tvLastName.text = usuario.apellido
        binding.tvEmail.text = usuario.correo
        binding.tvAltura.text = usuario.altura.toString()
        binding.tvPeso.text = usuario.peso.toString()
        binding.tvGenero.text = usuario.genero
        binding.tvImc.text = usuario.imc.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
