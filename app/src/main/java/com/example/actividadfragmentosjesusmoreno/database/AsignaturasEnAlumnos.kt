package com.example.actividadfragmentosjesusmoreno.database

import androidx.room.*

@Entity
class AsignaturasEnAlumnos(

    @Embedded
    var alumnos: Alumnos,

    @Relation(
        parentColumn = "codigo",
        entity = Asignaturas::class,
        entityColumn = "nombreAsignatura",
        associateBy = Junction(
            value = AsignaturaAlumnoCrossRef::class,
            parentColumn = "codigo",
            entityColumn = "nombreAsignatura"
        )
    )

    var asignaturas: List<Asignaturas>
)

@Entity(primaryKeys = ["codigo", "nombreAsignatura"])
data class AsignaturaAlumnoCrossRef(
    val codigo: Int,
    val nombreAsignatura: String

    )










