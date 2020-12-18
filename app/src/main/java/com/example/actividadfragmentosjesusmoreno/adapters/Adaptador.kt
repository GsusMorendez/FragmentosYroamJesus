package com.example.tarea6spinnerrecyclerview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadfragmentosjesusmoreno.MainActivity
import com.example.actividadfragmentosjesusmoreno.R
import com.example.actividadfragmentosjesusmoreno.database.Alumnos


class Adaptador(var listaAlumnos: List<Alumnos>, var context: Context) :
    RecyclerView.Adapter<Adaptador.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutDeLosAlumnos =
            LayoutInflater.from(parent.context).inflate(R.layout.vista_alumnos, parent, false)
        val viewHolder = ViewHolder(layoutDeLosAlumnos)
        return viewHolder
    }


    class ViewHolder(layoutDeLosAlumnos: View) : RecyclerView.ViewHolder(layoutDeLosAlumnos) {

        var campoNombre = layoutDeLosAlumnos.findViewById<TextView>(R.id.textViewId) as TextView

        fun enlazar(miAlumno: Alumnos) {
            campoNombre.text = miAlumno.nombre

        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.enlazar(listaAlumnos[position])
        holder.itemView.setOnClickListener { click(listaAlumnos[position]) }

    }

    override fun getItemCount(): Int {
        return listaAlumnos.size
    }

    fun click(alumnos: Alumnos) {
        var miMain: MainActivity = context as MainActivity
        miMain.activarFragmentInfoYmatarAlResto(alumnos)

    }


}