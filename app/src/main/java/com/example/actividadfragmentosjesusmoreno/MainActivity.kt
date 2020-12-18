package com.example.actividadfragmentosjesusmoreno

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.actividadfragmentosjesusmoreno.database.*
import com.example.actividadfragmentosjesusmoreno.fragments.FragmentAlumnos
import com.example.actividadfragmentosjesusmoreno.fragments.FragmentAlumnos.Companion.newInstance2
import com.example.actividadfragmentosjesusmoreno.fragments.FragmentInfoAlumno
import com.example.actividadfragmentosjesusmoreno.fragments.FragmentInfoAlumno.Companion.newInstance3
import com.example.actividadfragmentosjesusmoreno.fragments.FragmentProfesor
import com.example.actividadfragmentosjesusmoreno.fragments.FragmentProfesor.Companion.newInstance
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var dataRepository: DataRepository

    var alumSeleccionado = ArrayList<String>()

    var estaDestruido = false
    var eleccionAsignatura: String? = null

    var fragmentProfesor: FrameLayout? = null
    var fragmentInfoAlumno: FrameLayout? = null
    var fragmentAlumnos: FrameLayout? = null

    var profFrag: FragmentProfesor? = null
    var aluFrag: FragmentAlumnos? = null
    var infoaluFrag: FragmentInfoAlumno? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //para recuperar variables tras giro de pantalla:
        if (savedInstanceState != null) {
            alumSeleccionado =
                savedInstanceState!!.getStringArrayList("datosAlum") as ArrayList<String>;
            eleccionAsignatura = savedInstanceState!!.getString("eleccion")
        }

        dataRepository = DataRepository(this)
        fragmentProfesor = findViewById(R.id.frameLayoutProf)
        fragmentAlumnos = findViewById(R.id.frameLayoutAlum)
        fragmentInfoAlumno = findViewById(R.id.frameLayoutAlumINFO)

        leerJson()

        //Generar el Spinner dinámico con las asignaturas existentes en nuestra BBDD:

        var misAsignaturas: List<Asignaturas> = dataRepository.getAllAsignaturas()
        var arrayAsignaturas = ArrayList<String>()
        for (asig in misAsignaturas) {
            arrayAsignaturas.add(asig.nombreAsignatura)
        }
        var opciones: Array<String> = arrayAsignaturas.toTypedArray()
        var spinner = findViewById<Spinner>(R.id.spinner)
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        spinner.adapter = adapter


        //Para que la primera vez ya aparezca cargada la info con la primera asignatura cualquiera que sea:
        if (eleccionAsignatura == null) {
            spinner.setSelection(0, true)
            eleccionAsignatura = spinner.selectedItem.toString()
        }

        eleccionAsignatura?.let { establecerFragments(it) }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                eleccionAsignatura = item.toString()
                establecerFragments(item.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun establecerFragments(elecionSpinner: String) {

        var arrAsig = ArrayList<String>()
        if (alumSeleccionado.size > 0) {
            for (i in 3 until alumSeleccionado.size) {
                arrAsig.add(alumSeleccionado[i])
            }
            infoaluFrag = newInstance3(
                alumSeleccionado[0].toInt(),
                alumSeleccionado[1],
                alumSeleccionado[2],
                arrAsig
            )
        } else {
            infoaluFrag = newInstance3(0, "", "", arrAsig)
        }
        profFrag = newInstance(elecionSpinner)
        aluFrag = newInstance2(elecionSpinner)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (fragmentInfoAlumno != null) {
            fragmentTransaction.replace(R.id.frameLayoutAlumINFO, infoaluFrag!!)
        }
        fragmentTransaction.replace(R.id.frameLayoutProf, profFrag!!)
        fragmentTransaction.replace(R.id.frameLayoutAlum, aluFrag!!)
        fragmentTransaction.commit()
    }


    fun leerFicheroDatos(): String {
        var bufferedReaderRecurso =
            BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datos)))
        var jsonEnString = ""
        var linea: String? = null;
        while ({ linea = bufferedReaderRecurso.readLine(); linea }() != null) {
            jsonEnString += linea
        }
        bufferedReaderRecurso.close()
        return jsonEnString
    }


    fun leerJson() {
        var jsonEnString: String = leerFicheroDatos()

        var misFrofesores = ArrayList<Profesores>()
        var misAlumnos = ArrayList<Alumnos>()
        var misAsignaturas = mutableSetOf<Asignaturas>()
        var misAsignaturasEnAlumnos = ArrayList<AsignaturasEnAlumnos>()

        //Tratamiento del Fichero.json
        val objetoJson = JSONObject(jsonEnString)
        //var jsonArrayAsignaturas: JSONArray = objetoJson.getJSONArray("asignaturas")
        var jsonArrayProfesores: JSONArray = objetoJson.getJSONArray("profesores")
        var jsonArrayAlumnos: JSONArray = objetoJson.getJSONArray("alumnos")

        for (i in 0 until jsonArrayProfesores.length()) {
            var objeto: JSONObject? = jsonArrayProfesores.getJSONObject(i)
            var nombre: String = objeto!!.getString("nombre")
            var appe: String = objeto!!.getString("apellido")
            var code: Int = objeto!!.getString("codigo").toInt()
            var asign: String = objeto!!.getString("asignatura")

            var asignaturas = Asignaturas(asign)
            misAsignaturas.add(asignaturas)

            var prof = Profesores(code, nombre, appe, asign)
            misFrofesores.add(prof)
        }

        for (i in 0 until jsonArrayAlumnos.length()) {
            var objeto: JSONObject? = jsonArrayAlumnos.getJSONObject(i)
            var nombre: String = objeto!!.getString("nombre")
            var appe: String = objeto!!.getString("apellido")
            var code: Int = objeto!!.getString("codigo").toInt()
            var asignaturas: JSONArray = objeto.getJSONArray("Asignaturas")

            var arrayListAsignaturasPorAlumno = arrayListOf<Asignaturas>()

            for (i in 0 until asignaturas.length()) {
                var estaAsig = Asignaturas(asignaturas.get(i).toString())
                arrayListAsignaturasPorAlumno.add(estaAsig)
            }

            var alum = Alumnos(code, nombre, appe)
            var asignaturasEnAlumnos =
                AsignaturasEnAlumnos(alum, arrayListAsignaturasPorAlumno.toList())

            misAsignaturasEnAlumnos.add(asignaturasEnAlumnos)
            misAlumnos.add(alum)

            poblarBBDD(misFrofesores, misAlumnos, misAsignaturas, misAsignaturasEnAlumnos)
        }
    }

    private fun poblarBBDD(
        misFrofesores: ArrayList<Profesores>,
        misAlumnos: ArrayList<Alumnos>,
        misAsignaturas: MutableSet<Asignaturas>,
        misAsignaturasEnAlumnos: ArrayList<AsignaturasEnAlumnos>
    ) {
        for (prof in misFrofesores) {
            dataRepository.insertAllProfesores(prof)
        }
        for (alu in misAlumnos) {
            dataRepository.insertAllAlumnos(alu)
        }
        for (asig in misAsignaturas) {
            dataRepository.insertAllAsignaturas(asig)
        }
        for (rela in misAsignaturasEnAlumnos) {
            dataRepository.insertRelation(rela)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("eleccion", eleccionAsignatura)
        outState.putStringArrayList("datosAlum", alumSeleccionado)
    }

    fun rellenarAlumnoSeleccionad(alumno: Alumnos, asigDeEsteAlum: ArrayList<String>) {
        alumSeleccionado.clear()
        alumSeleccionado.add(alumno.codigo.toString())
        alumSeleccionado.add(alumno.nombre.toString())
        alumSeleccionado.add(alumno.apellido.toString())
        for (a in asigDeEsteAlum) {
            alumSeleccionado.add(a)
        }
    }

    fun activarFragmentInfoYmatarAlResto(alumno: Alumnos) {

        var misRelaciones = dataRepository.getAllRelations()
        var asigDeEsteAlum = ArrayList<String>()

        for (relacion in misRelaciones) {
            if (relacion.alumnos.codigo == alumno.codigo) {
                for (asig in relacion.asignaturas) {
                    asigDeEsteAlum.add(asig.nombreAsignatura)
                }
            }
        }

        rellenarAlumnoSeleccionad(alumno, asigDeEsteAlum)

        infoaluFrag =
            newInstance3(alumno.codigo, alumno.nombre!!, alumno.apellido!!, asigDeEsteAlum)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (fragmentInfoAlumno == null) {
            fragmentTransaction.replace(R.id.frameLayoutProf, infoaluFrag!!)
            fragmentTransaction.remove(profFrag!!)
            fragmentTransaction.remove(aluFrag!!)

            if (spinner.visibility == View.VISIBLE) {
                spinner.visibility = View.GONE;
            }

            estaDestruido = true
        } else {
            fragmentTransaction.replace(R.id.frameLayoutAlumINFO, infoaluFrag!!)
        }
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (estaDestruido) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (spinner.visibility == View.GONE) {
                spinner.visibility = View.VISIBLE;
            }
            fragmentTransaction.remove(infoaluFrag!!)
            fragmentTransaction.replace(R.id.frameLayoutProf, profFrag!!)
            fragmentTransaction.replace(R.id.frameLayoutAlum, aluFrag!!)
            fragmentTransaction.commit()
            estaDestruido = false

        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Mensaje!!")
            builder.setMessage("¿Está seguro que quiere salir?")

            builder.setPositiveButton("Si") { dialog, _ -> finish() }
            builder.setNegativeButton("No") { dialog, which -> }

            builder.show()
        }
    }

}