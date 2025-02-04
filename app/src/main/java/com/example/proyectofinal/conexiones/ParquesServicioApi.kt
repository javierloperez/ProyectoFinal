package com.example.proyectofinal.conexiones

import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.modelos.Parques
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ParquesServicioApi {
    @GET("parques")
    suspend fun obtenerParques(): List<Parques>

    @GET("especies")
    suspend fun obtenerEspecies(): List<Especies>

    @GET("parques/{id}")
    suspend fun obtenerParque(
    @Path("id") id:Int
    ):Parques

    @GET("especies/{id}")
    suspend fun obtenerEspecie(
        @Path("id") id:Int,
    ):Especies

    @POST("parques")
    suspend fun insertarParque(
        @Body parque: Parques
    ): Parques

    @PUT("parques/{id}")
    suspend fun actualizarParque(
        @Path("id") id: Int,
        @Body parques: Parques
    ): Parques

    @DELETE("parques/{id}")
    suspend fun eliminarParque(
        @Path("id") id: Int
    ): Response<Unit>

    @POST("especies")
    suspend fun insertarEspecie(
        @Body especies: Especies
    ): Especies

    @PUT("especies/{id}")
    suspend fun actualizarEspecie(
        @Path("id") id: Int,
        @Body especies: Especies
    ): Especies

    @DELETE("especies/{id}")
    suspend fun eliminarEspecie(
        @Path("id") id: Int
    ): Response<Unit>

}