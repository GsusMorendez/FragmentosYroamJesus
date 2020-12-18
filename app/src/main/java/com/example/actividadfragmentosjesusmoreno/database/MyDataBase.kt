package com.example.actividadfragmentosjesusmoreno.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alumnos::class, Profesores::class, Asignaturas::class, AsignaturaAlumnoCrossRef::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun alumnosDao():AlumnosDao
    abstract fun profesoresDao(): ProfesoresDao
    abstract fun asignaturasDao(): AsignaturasDao
    abstract fun asginaturasEnAlumnosdao(): AsignaturasEnAlumnosDao



    companion object {
        private const val DATABASE_NAME = "colegio"

        @Volatile
        private var INSTANCE: MyDataBase? = null

        fun getInstance(context: Context): MyDataBase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MyDataBase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }
    }
}