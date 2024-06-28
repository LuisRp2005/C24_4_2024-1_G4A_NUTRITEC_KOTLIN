package com.gutierrez.eddy.nutritec

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.gutierrez.eddy.nutritec.models.Usuarios
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val btnGoogleSignIn = findViewById<MaterialButton>(R.id.btnGoogleSignIn)
        btnGoogleSignIn.setOnClickListener {
            buttonGoogleSignIn()
        }
    }

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
    }

    private fun buttonGoogleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email

            // Guardar el correo en SharedPreferences
            val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("USER_EMAIL", email)
            editor.apply()

            // Verificar si el usuario ya está registrado
            verificarRegistroUsuario(email)
        } catch (e: ApiException) {
            Log.w("Login", "signInResult:failed code=" + e.statusCode)
            // Manejar el error apropiadamente
        }
    }

    private fun verificarRegistroUsuario(email: String?) {
        if (email.isNullOrEmpty()) return

        // Llamar a la API para verificar si el usuario ya está registrado
        val call = RetrofitInstance.usuariosApi.buscarUsuarioPorCorreo(email)
        call.enqueue(object : Callback<Usuarios?> {
            override fun onResponse(call: Call<Usuarios?>, response: Response<Usuarios?>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        // El usuario está registrado, ir al MainActivity
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // El usuario no está registrado, ir a RegistroActivity
                        val intent = Intent(this@Login, Registro::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // Manejar errores de la API
                    Log.e("Login", "Error en la llamada a la API: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuarios?>, t: Throwable) {
                // Manejar fallos de conexión u otros errores
                Log.e("Login", "Error al ejecutar la llamada: ${t.message}")
            }
        })
    }
}
