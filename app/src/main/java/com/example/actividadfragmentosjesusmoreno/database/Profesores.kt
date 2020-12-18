package com.example.actividadfragmentosjesusmoreno.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profesores (
    @PrimaryKey val codigo: Int,
    @ColumnInfo(name = "nombre") val nombre: String?,
    @ColumnInfo(name = "apellido") val apellido: String?,
    @ColumnInfo(name = "asignaturas") val asignaturas: String?
)