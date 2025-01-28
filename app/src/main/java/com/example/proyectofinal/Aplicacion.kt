package com.example.proyectofinal

import android.app.Application
import com.example.proyectofinal.datos.ContenedorApp
import com.example.proyectofinal.datos.ContenedorAppGeneral

class Aplicacion: Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = ContenedorAppGeneral(this)
    }
}