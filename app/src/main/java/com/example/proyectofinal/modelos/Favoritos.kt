package com.example.proyectofinal.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favoritos")
data class Favoritos(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreEspecie:String = "",
    val apodo:String = ""
    )