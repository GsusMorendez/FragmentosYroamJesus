package com.example.actividadfragmentosjesusmoreno.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfesoresDao {
    @Insert
    fun insertAllProfesores(vararg profesores: Profesores)

    @Query("SELECT * FROM Profesores")
    fun getAllProfesor(): List<Profesores>
}