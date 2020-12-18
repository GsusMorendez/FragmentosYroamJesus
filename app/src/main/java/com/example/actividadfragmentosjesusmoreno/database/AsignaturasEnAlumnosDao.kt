package com.example.actividadfragmentosjesusmoreno.database

import androidx.room.*

@Dao
interface AsignaturasEnAlumnosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRelation(join: AsignaturaAlumnoCrossRef)

    @Transaction
    @Query("SELECT * FROM Alumnos")
    fun getAllRelations(): List<AsignaturasEnAlumnos>
}

