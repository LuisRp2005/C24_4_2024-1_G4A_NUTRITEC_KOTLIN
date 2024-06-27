package com.gutierrez.eddy.nutritec

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.gutierrez.eddy.nutritec.R

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val editTextFecha = findViewById<EditText>(R.id.editTextFecha)
        editTextFecha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.length == 2 && !it.contains("/")) {
                        it.insert(2, "/")
                    } else if (it.length == 5 && !it.substring(3).contains("/")) {
                        it.insert(5, "/")
                    } else {
                        // Aquí puedes manejar cualquier otra lógica si es necesaria
                    }
                }
            }
        })

        // Validación de longitud total
        editTextFecha.filters = arrayOf(android.text.InputFilter.LengthFilter(10))
    }
}

