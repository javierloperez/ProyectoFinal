package com.example.proyectofinal.datos

import android.content.Context
import com.example.proyectofinal.conexiones.BaseDatosLocal
import com.example.proyectofinal.conexiones.ParquesServicioApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp {
    val jsonRepositorio: JsonRepositorio
    val baseLocalRepositorio: BaseLocalRepositorio
}

class ContenedorAppGeneral(private val context: Context) : ContenedorApp {
    private val baseUrl = "http://10.0.2.2:8080/api/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofit: ParquesServicioApi by lazy {
        retrofit.create(ParquesServicioApi::class.java)
    }

    override val jsonRepositorio: JsonRepositorio by lazy {
        ConexionParquesRepositorio(servicioRetrofit)
    }
    override val baseLocalRepositorio: BaseLocalRepositorio by lazy {
        ConexionBaseLocalRepositorio(BaseDatosLocal.obtenerBaseDatos(context).daoBaseDatos())
    }
}