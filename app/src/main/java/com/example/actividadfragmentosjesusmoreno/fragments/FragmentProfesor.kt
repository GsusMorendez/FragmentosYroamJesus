package com.example.actividadfragmentosjesusmoreno.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.actividadfragmentosjesusmoreno.R
import com.example.actividadfragmentosjesusmoreno.database.DataRepository

private const val ARG_PARAM1 = "param1"

class FragmentProfesor : Fragment() {

    private var param1: String? = null

    lateinit var dataRepository: DataRepository
    lateinit var campoNombre: TextView
    lateinit var campoAppellidos: TextView


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
        var v = inflater.inflate(R.layout.fragment_profesor, container, false)
        campoNombre = v.findViewById(R.id.textViewNombreProf)
        campoAppellidos = v.findViewById(R.id.textViewApellidoProf)

        dataRepository = DataRepository(context!!)
        var profs = dataRepository.getAllProfesor()


        for (miProf in profs) {
            if (miProf.asignaturas == param1) {
                campoNombre.text = ""
                campoAppellidos.text = ""
                campoNombre.text = miProf.nombre
                campoAppellidos.text = miProf.apellido
            }
        }

        //campoNombre.text =  param1

        return v
    }

    companion object {

        fun newInstance(param1: String) =
            FragmentProfesor().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }




}