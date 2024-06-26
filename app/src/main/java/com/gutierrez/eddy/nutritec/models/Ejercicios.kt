package com.gutierrez.eddy.nutritec.models

data class Ejercicios(
    val id: Int,
    val nombre:String,
    val descripcion:String,
    val nivel:String,
    val tipoImc:TipoIMC,
    val images:String
)
