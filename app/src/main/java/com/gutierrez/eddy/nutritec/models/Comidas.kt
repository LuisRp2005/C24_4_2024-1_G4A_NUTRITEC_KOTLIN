package com.gutierrez.eddy.nutritec.models

data class Comidas (
    val idComida: Int,
    val nombreComida:String,
    val calorias: Int,
    val categoriaComida:CategoriaComida,
    val images:String
)