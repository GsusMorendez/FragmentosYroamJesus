package com.example.actividadfragmentosjesusmoreno.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadfragmentosjesusmoreno.R
import com.example.actividadfragmentosjesusmoreno.database.Alumnos
import com.example.actividadfragmentosjesusmoreno.database.DataRepository
import com.example.tarea6spinnerrecyclerview.adapters.Adaptador

private const val ARG_PARAM1 = "param1"

class FragmentAlumnos() : Fragment() {

    private var param1: String? = null
    lateinit var dataRepository: DataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_alumnos, container, false)

        var aux = v.findViewById<TextView>(R.id.textView2)


        dataRepository = DataRepository(context!!)
        var alumnosRelacion = dataRepository.getAllRelations()
        var alumnosConAsig = ArrayList<Alumnos>()

        for (relacion in alumnosRelacion) {
                for (asig in relacion.asignaturas) {
                if (asig.nombreAsignatura == param1) {
                    alumnosConAsig.add(relacion.alumnos)
                }
            }
        }

        //Generar el RecyclerView

        var recyclerView: RecyclerView = v.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        var adaptador = Adaptador(alumnosConAsig.toList(), context!!)
        recyclerView.adapter = adaptador

        return v
    }

    companion object {
        fun newInstance2(param1: String) =
            FragmentAlumnos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}