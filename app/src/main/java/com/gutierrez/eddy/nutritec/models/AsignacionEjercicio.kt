package com.gutierrez.eddy.nutritec.models


data class AsignacionEjercicio(
    val idAsignacionEjercicio: Int,
    val usuario: Usuarios,
    val ejercicio: Ejercicios,
    val fechaHoraAsignacion: String
)

