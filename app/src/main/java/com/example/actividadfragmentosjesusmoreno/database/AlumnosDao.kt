package com.example.actividadfragmentosjesusmoreno.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface AlumnosDao {
    @Insert
    fun insertAllAlumnos(vararg alumnos: Alumnos)

    @Query("SELECT * FROM Alumnos")
    fun getAllAlumns(): List<Alumnos>

}



