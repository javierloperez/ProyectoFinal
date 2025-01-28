package com.example.proyectofinal.modelos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Especies(

    @SerialName(value= "id")
    val id:Int = 0,
    @SerialName(value= "nombre")
    val nombre:String = "",
    @SerialName(value= "descripcion")
    val descripcion:String = "",
    @SerialName(value= "tipo")
    val tipo:String = "",

)