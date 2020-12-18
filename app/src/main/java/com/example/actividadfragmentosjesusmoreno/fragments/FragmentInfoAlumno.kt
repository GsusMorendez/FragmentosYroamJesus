package com.example.actividadfragmentosjesusmoreno.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.actividadfragmentosjesusmoreno.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"


class FragmentInfoAlumno() : Fragment() {
    private var param1: Int? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: ArrayList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getStringArrayList(ARG_PARAM4)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v = inflater.inflate(R.layout.fragment_info_alumno, container, false)

        var campoCodigo = v.findViewById<TextView>(R.id.textViewId)
        var campoNombre = v.findViewById<TextView>(R.id.textViewNombre)
        var campoApellido = v.findViewById<TextView>(R.id.textViewApellido)
        var campoAsignaturas = v.findViewById<TextView>(R.id.textViewAsignaturas)

        var asignaturasDeCadaAlumno = ""
        for (asig in param4!!) {
            asignaturasDeCadaAlumno += asig + " "
        }

        if (param1 != 0){
            campoCodigo.text = "código: " + param1.toString()
            campoNombre.text = " nombre: " + param2
            campoApellido.text = "Apellido: " + param3
            campoAsignaturas.text = "Asignaturas: " + asignaturasDeCadaAlumno
        }


        return v
    }

    companion object {
        fun newInstance3(
            param1: Int,
            param2: String,
            param3: String,
            param4: ArrayList<String>,
        ) =
            FragmentInfoAlumno().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putStringArrayList(ARG_PARAM4, param4)
                }
            }
    }
}