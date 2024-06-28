package com.gutierrez.eddy.nutritec

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gutierrez.eddy.nutritec.models.Usuarios
import com.gutierrez.eddy.nutritec.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registro : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var edtNombre: EditText
    private lateinit var edtApellido: EditText
    private lateinit var edtEdad: EditText
    private lateinit var edtGenero: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtContraseña: EditText
    private lateinit var edtAltura: EditText
    private lateinit var edtPeso: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        // Obtener referencia a las vistas
        edtNombre = findViewById(R.id.edtNombre)
        edtApellido = findViewById(R.id.edtApellido)
        edtAltura = findViewById(R.id.edtAltura)
        edtPeso = findViewById(R.id.edtPeso)
        edtEdad = findViewById(R.id.edtEdad)
        edtGenero = findViewById(R.id.edtGenero)
        edtCorreo = findViewById(R.id.edtCorreo)
        edtContraseña = findViewById(R.id.edtContraseña)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Obtener el correo del usuario desde SharedPreferences
        val email = sharedPreferences.getString("USER_EMAIL", "")
        edtCorreo.setText(email)
        edtCorreo.isEnabled = false // Deshabilitar el campo de correo

        // Configurar OnClickListener para el botón "Guardar"
        btnGuardar.setOnClickListener {
            guardarUsuario(email)
        }
    }

    private fun guardarUsuario(email: String?) {
        // Crear un objeto Usuario con los datos ingresados por el usuario
        val nombre = edtNombre.text.toString().trim()
        val apellido = edtApellido.text.toString().trim()
        val altura = edtAltura.text.toString().toDoubleOrNull()
        val peso = edtPeso.text.toString().toDoubleOrNull()
        val edad = edtEdad.text.toString().toIntOrNull()
        val genero = edtGenero.text.toString().trim()
        val contraseña = edtContraseña.text.toString().trim()

        if (nombre.isNotEmpty() && apellido.isNotEmpty() && altura != null && peso != null && edad != null && genero.isNotEmpty() && contraseña.isNotEmpty()) {
            // Calcular el IMC con 2 decimales
            val imc = String.format("%.2f", peso / (altura * altura)).toDouble()

            // Crear el objeto Usuario con el IMC calculado y rol 0
            val usuario = Usuarios(null, nombre, apellido, email!!, altura, peso, genero, imc, 0, contraseña, null)

            // Llamar a la API para guardar el usuario
            val call = RetrofitInstance.usuariosApi.guardarUsuario(usuario)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Usuario guardado correctamente, ir a la actividad principal
                        val intent = Intent(this@Registro, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Manejar errores de la API
                        Log.e("RegistroActivity", "Error en la llamada a la API: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Manejar fallos de conexión u otros errores
                    Log.e("RegistroActivity", "Error al ejecutar la llamada: ${t.message}")
                }
            })
        } else {
            // Mostrar un mensaje al usuario indicando que complete todos los campos
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
