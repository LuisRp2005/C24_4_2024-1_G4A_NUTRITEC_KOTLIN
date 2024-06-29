package com.gutierrez.eddy.nutritec.models

import java.util.Date

data class AsignacionComida(
    val idAsignacionComida: Int,
    val usuario: Usuarios,
    val comida: Comidas,
    val fechaHoraRegistro: String
)
