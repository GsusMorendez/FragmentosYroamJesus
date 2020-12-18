package com.example.actividadfragmentosjesusmoreno.database

import android.content.Context
import android.os.AsyncTask

class DataRepository(context: Context) {
    private val alumnosDao: AlumnosDao? = MyDataBase.getInstance(context)?.alumnosDao()
    private val profesoresDao: ProfesoresDao? = MyDataBase.getInstance(context)?.profesoresDao()
    private val asignaturasDao: AsignaturasDao? = MyDataBase.getInstance(context)?.asignaturasDao()
    private val asignaturasEnAlumnosDao: AsignaturasEnAlumnosDao? =
        MyDataBase.getInstance(context)?.asginaturasEnAlumnosdao()


    //las funciones correspondientes a las querys que hemos hecho en las clases DAO

    //parte de alumnos

    fun insertAllAlumnos(alumnos: Alumnos): Int {
        if (alumnosDao != null) {
            return InsertAsyncTask1(alumnosDao).execute(alumnos).get()
        }
        return -1
    }

    private class InsertAsyncTask1(private val alumnosDao: AlumnosDao) :
        AsyncTask<Alumnos, Void, Int>() {
        override fun doInBackground(vararg misAlumnos: Alumnos?): Int? {
            try {
                for (alumnos in misAlumnos) {
                    if (alumnos != null) alumnosDao.insertAllAlumnos(alumnos)
                }
                return 0
            } catch (exception: Exception) {
                return -1
            }
        }
    }

    fun getAllAlumns(): List<Alumnos> {
        return getAllAux(alumnosDao!!).execute().get()
    }

    private class getAllAux(private val alumnosDao: AlumnosDao) :
        AsyncTask<Alumnos, Void, List<Alumnos>>() {
        override fun doInBackground(vararg customers: Alumnos?): List<Alumnos> {
            return alumnosDao.getAllAlumns()
        }
    }

    //parte profesores

    fun getAllProfesor(): List<Profesores> {
        return getAllAuxProf(profesoresDao!!).execute().get()
    }

    private class getAllAuxProf(private val profesoresDao: ProfesoresDao) :
        AsyncTask<Profesores, Void, List<Profesores>>() {
        override fun doInBackground(vararg profesores: Profesores?): List<Profesores> {
            return profesoresDao.getAllProfesor()
        }
    }

    fun insertAllProfesores(profesores: Profesores): Int {
        if (alumnosDao != null) {
            return InsertAsyncTask2(profesoresDao!!).execute(profesores).get()
        }
        return -1
    }

    private class InsertAsyncTask2(private val profesoresDao: ProfesoresDao) :
        AsyncTask<Profesores, Void, Int>() {
        override fun doInBackground(vararg misProfesores: Profesores?): Int? {
            try {
                for (profesores in misProfesores) {
                    if (profesores != null) profesoresDao.insertAllProfesores(profesores)
                }
                return 0
            } catch (exception: Exception) {
                return -1
            }
        }
    }


    //parte Asignaturas

    fun getAllAsignaturas(): List<Asignaturas> {
        return getAllAuxAsignaturas(asignaturasDao!!).execute().get()
    }

    private class getAllAuxAsignaturas(private val asignaturasDao: AsignaturasDao) :
        AsyncTask<Asignaturas, Void, List<Asignaturas>>() {
        override fun doInBackground(vararg asignaturas: Asignaturas?): List<Asignaturas> {
            return asignaturasDao.getAllAsignaturas()
        }
    }

    fun insertAllAsignaturas(asignaturas: Asignaturas): Int {
        if (alumnosDao != null) {
            return InsertAsyncTask3(asignaturasDao!!).execute(asignaturas).get()
        }
        return -1
    }

    private class InsertAsyncTask3(private val asignaturasDao: AsignaturasDao) :
        AsyncTask<Asignaturas, Void, Int>() {
        override fun doInBackground(vararg misAsignaturas: Asignaturas?): Int? {
            try {
                for (asignaturas in misAsignaturas) {
                    if (asignaturas != null) asignaturasDao.insertAllAsig(asignaturas)
                }
                return 0
            } catch (exception: Exception) {
                return -1
            }
        }
    }

    //Parte de la relacion alumno/asignatura

    fun getAllRelations(): List<AsignaturasEnAlumnos> {
        return getAuxRelation(asignaturasEnAlumnosDao!!).execute().get()
    }

    private class getAuxRelation(private val asignaturasEnAlumnosDao: AsignaturasEnAlumnosDao) :
        AsyncTask<Void, Void, List<AsignaturasEnAlumnos>>() {
        override fun doInBackground(vararg p0: Void?): List<AsignaturasEnAlumnos> {
            return asignaturasEnAlumnosDao.getAllRelations()
        }
    }

    fun insertRelation(asignaturasEnAlumnos: AsignaturasEnAlumnos): Int {
        if (alumnosDao != null && asignaturasDao != null && asignaturasEnAlumnosDao != null) {
            return insertRelationAux(alumnosDao, asignaturasDao, asignaturasEnAlumnosDao).execute(
                asignaturasEnAlumnos
            ).get()
        }
        return -1
    }

    private class insertRelationAux(
        private val alumnosDao: AlumnosDao,
        private val asignaturasDao: AsignaturasDao,
        private val asignaturasEnAlumnosDao: AsignaturasEnAlumnosDao
    ) : AsyncTask<AsignaturasEnAlumnos, Void, Int>() {
        override fun doInBackground(vararg asignaturasEnAlumnos: AsignaturasEnAlumnos?): Int? {
            try {

                for (asignInAlum in asignaturasEnAlumnos) {

                    if (asignInAlum != null) {
//                        alumnosDao.insertAllAlumnos(asignInAlum.alumnos)
//                        asignaturasDao.insertAllAsig(asignInAlum.asignaturas)
                        for (asig in asignInAlum.asignaturas) {
                            asignaturasEnAlumnosDao.insertRelation(
                                AsignaturaAlumnoCrossRef(
                                    asignInAlum.alumnos.codigo,
                                    asig.nombreAsignatura
                                )
                            )
                        }
                    }

                }
                return 0
            } catch (exception: Exception) {
                return -1

            }

        }
    }


}