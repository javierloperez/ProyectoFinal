package com.example.proyectofinal.conexiones

import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.modelos.Parques
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ParquesServicioApi {
    @GET("especies/{id}")
    suspend fun obtenerParques(): List<Parques>

    @GET("parques/{id}")
    suspend fun obtenerEspecies(): List<Especies>

    @POST("parques")
    suspend fun insertarParque(
        @Body parques: Parques
    ): Parques

    @PUT("parques/{id}")
    suspend fun actualizarParque(
        @Path("id") id: Int,
        @Body parques: Parques
    ): Parques

    @DELETE("parques/{id}")
    suspend fun eliminarParque(
        @Path("id") id: Int
    ): Parques


}