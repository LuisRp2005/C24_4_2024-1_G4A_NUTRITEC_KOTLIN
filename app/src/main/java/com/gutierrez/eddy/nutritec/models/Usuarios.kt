package com.gutierrez.eddy.nutritec.models

import java.util.Date

data class Usuarios(
    val idUsuario: Int,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val altura: Double,
    val peso: Double,
    val genero: String,
    val imc: Double,
    val rol: Int?,
    val contraseña: String,
    val fechaNacimiento: Date
)
