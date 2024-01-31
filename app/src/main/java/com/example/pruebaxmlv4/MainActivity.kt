package com.example.pruebaxmlv4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.pruebaxmlv4.modelo.entidades.Alumno
import com.example.pruebaxmlv4.modelo.entidades.Materia
import com.example.pruebaxmlv4.modelo.entidades.Materias
import com.example.pruebaxmlv4.modelo.modelView.ViewModelAlumno

class MainActivity : AppCompatActivity() {

    lateinit var modelViewAlumno: ViewModelAlumno
    var listado: MutableList<Alumno> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        modelViewAlumno = ViewModelProvider(this)[ViewModelAlumno::class.java]


        var materiasInformatica = mutableListOf(
            Materia("Programación", 8),
            Materia("Base de datos", 6),
            Materia("Sistemas", 5)
        )

        var materiasPrimaria = mutableListOf(
            Materia("Lengua", 8),
            Materia("Matematicas", 6),
            Materia("Naturales", 5)
        )

        var materiasCiclo = Materias(materiasInformatica)
        var materiaColegio = Materias(materiasPrimaria)


        var alumnos = arrayOf(
            Alumno(12,"Alejandro", 33, true, materiasCiclo),
            Alumno(13,"Leo", 15, true, materiasCiclo),
            Alumno(14,"Noah", 11, false, materiaColegio),
        )

        //Poblamos el fichero interno con información del fichero de Assets
        modelViewAlumno.copiarArchivo()


        //Lectura fichero
        listado = modelViewAlumno.procesarFicheroXml()
        for (alumno in listado)
            Log.d("VerAlumnosFicheroSimple", alumno.toString())

        Log.d(
            "VerAlumnosSeparador",
            "---------------------------------------------------------------"
        )

        //Lectura fichero mediante SAX
        listado = modelViewAlumno.procesarArchivoXMLSAX()
        for (alumno in listado)
            Log.d("VerAlumnosFicheroSAX", alumno.toString())

        Log.d(
            "VerAlumnosSeparador",
            "---------------------------------------------------------------"
        )

        //Escritura fichero
        for (alumno in alumnos)
            modelViewAlumno.addAlumno(alumno)

        //Lectura fichero interno
        listado = modelViewAlumno.procesarFicheroXmlInterno()
        for (alumno in listado)
            Log.d("VerAlumnosFicheroInterno", alumno.toString())
    }
}