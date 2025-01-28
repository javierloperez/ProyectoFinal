package com.example.proyectofinal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.proyectofinal.modelos.Favoritos

@Dao
interface DaoBaseDatos{
    @Query("select * from Favoritos where id = :id")
    suspend fun obtenerFavorito(id:Int): Favoritos

    @Query("select * from Favoritos order by id DESC")
    suspend fun obtenerFavoritos(): List<Favoritos>

    @Insert
    suspend fun insertarFavorito(favoritos: Favoritos)

    @Update
    suspend fun actualizarFavorito(favoritos: Favoritos)

    @Delete
    suspend fun eliminarFavorito(favoritos: Favoritos)
}