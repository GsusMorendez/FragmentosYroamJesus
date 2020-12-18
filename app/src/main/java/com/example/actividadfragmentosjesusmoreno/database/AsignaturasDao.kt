package com.example.actividadfragmentosjesusmoreno.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface AsignaturasDao {

    @Insert
    fun insertAllAsig(vararg asignaturas: Asignaturas)

    @Insert
    fun insertAllAsig(asignaturas: List<Asignaturas>)

    @Query("SELECT * FROM Asignaturas")
    fun getAllAsignaturas(): List<Asignaturas>
}


