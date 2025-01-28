package com.example.proyectofinal.datos

import com.example.proyectofinal.conexiones.ParquesServicioApi
import com.example.proyectofinal.dao.DaoBaseDatos
import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.modelos.Favoritos
import com.example.proyectofinal.modelos.Parques


interface JsonRepositorio {
    suspend fun obtenerParques(): List<Parques>
    suspend fun obtenerEspecies(): List<Especies>
}

interface BaseLocalRepositorio {
    suspend fun obtenerFavoritos(): List<Favoritos>
    suspend fun obtenerFavorito(id:Int): Favoritos
    suspend fun insertarFavorito(favoritos: Favoritos)
    suspend fun actualizarFavorito(favoritos: Favoritos)
    suspend fun eliminarFavorito(favoritos: Favoritos)
}

class ConexionBaseLocalRepositorio(
    private val daoBaseDatos: DaoBaseDatos,
) : BaseLocalRepositorio {
    override suspend fun obtenerFavorito(id: Int): Favoritos =
        daoBaseDatos.obtenerFavorito(id)

    override suspend fun obtenerFavoritos(): List<Favoritos> =
        daoBaseDatos.obtenerFavoritos()

    override suspend fun insertarFavorito(favoritos: Favoritos) =
        daoBaseDatos.insertarFavorito(favoritos)

    override suspend fun actualizarFavorito(favoritos: Favoritos) =
        daoBaseDatos.actualizarFavorito(favoritos)

    override suspend fun eliminarFavorito(favoritos: Favoritos) =
        daoBaseDatos.eliminarFavorito(favoritos)
}

class ConexionParquesRepositorio(
    private val servicioApi: ParquesServicioApi
) : JsonRepositorio {
    override suspend fun obtenerParques(): List<Parques> =
        servicioApi.obtenerParques()

    override suspend fun obtenerEspecies(): List<Especies> =
        servicioApi.obtenerEspecies()
}

