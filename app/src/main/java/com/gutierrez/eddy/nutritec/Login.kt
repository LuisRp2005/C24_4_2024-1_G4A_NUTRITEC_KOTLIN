package com.gutierrez.eddy.nutritec

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Obtener referencias a las vistas
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val btnRegistrar = findViewById<TextView>(R.id.btnRegistrar)

        // Configurar el OnClickListener para el TextView "Regístrate ahora"
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        // Configurar el OnClickListener para el botón "Iniciar Sesión"
        btnIngresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
