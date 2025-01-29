package com.example.proyectofinal.datos

import com.example.proyectofinal.conexiones.ParquesServicioApi
import com.example.proyectofinal.dao.DaoBaseDatos
import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.modelos.Favoritos
import com.example.proyectofinal.modelos.Parques


interface JsonRepositorio {
    suspend fun obtenerParques(): List<Parques>
    suspend fun obtenerParque(id:Int):Parques
    suspend fun insertarParque(parques: Parques):Parques
    suspend fun eliminarParque(id: Int):Parques
    suspend fun actualizarParque(id: Int,parques: Parques):Parques


    suspend fun obtenerEspecies(): List<Especies>
    suspend fun obtenerEspecie(id: Int): Especies
    suspend fun insertarEspecie(especies: Especies):Especies
    suspend fun eliminarEspecie(id: Int):Especies
    suspend fun actualizarEspecie(id: Int,especies: Especies):Especies

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

    override suspend fun obtenerParque(id:Int): Parques =
        servicioApi.obtenerParque(id)

    override suspend fun insertarParque(parques: Parques): Parques =
        servicioApi.insertarParque(parques)

    override suspend fun eliminarParque(id: Int): Parques =
        servicioApi.eliminarParque(id)

    override suspend fun actualizarParque(id: Int, parques: Parques): Parques =
        servicioApi.actualizarParque(id,parques)

    override suspend fun obtenerEspecies(): List<Especies> =
        servicioApi.obtenerEspecies()

    override suspend fun obtenerEspecie(id:Int): Especies =
        servicioApi.obtenerEspecie(id)

    override suspend fun insertarEspecie(especies: Especies): Especies =
        servicioApi.insertarEspecie(especies)

    override suspend fun eliminarEspecie(id: Int): Especies =
        servicioApi.eliminarEspecie(id)

    override suspend fun actualizarEspecie(id: Int, especies: Especies): Especies =
        servicioApi.actualizarEspecie(id,especies)

}

