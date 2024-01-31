package com.example.pruebaxmlv4.modelo.handlers

import com.example.pruebaxmlv4.modelo.entidades.Alumno
import com.example.pruebaxmlv4.modelo.entidades.Materia
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

class AlumnoHandlerXml : DefaultHandler() {

    private val cadena = StringBuilder()
    private var alumno: Alumno? = null
    private var materia: Materia? = null
    var listaMateria: MutableList<Materia> = mutableListOf()
    var alumnos: MutableList<Alumno> = mutableListOf()

    @Throws(SAXException::class)
    override fun startDocument() {
        cadena.clear()
        alumnos = mutableListOf()
        println("startDocument")
    }

    @Throws(SAXException::class)
    override fun startElement(
        uri: String,
        nombreLocal: String,
        nombre: String,
        attributes: Attributes
    ) {
        cadena.setLength(0)
        //IMPORTANTE INICIALIZAR LOS OBJETO ETIQUETA
        if (nombre == "materia") {
            materia = Materia()
            materia!!.nombre = attributes.getValue("nombre")
            materia!!.calificacion = attributes.getValue("calificacion").toInt()
        } else if (nombre == "alumno") {
            alumno = Alumno()
            alumno!!.id = attributes.getValue("id").toInt()
            alumno!!.materias.materias = mutableListOf()
        }
        println("startElement: $nombre")
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        cadena.append(ch, start, length)
        println("dato final: $cadena")
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, nombreLocal: String, nombre: String) {
        when (nombre) {
            "materias" -> {
                listaMateria.add(materia!!)
                alumno!!.materias.materias = listaMateria.toList()
            }
            "matriculado" -> {
                if (cadena.toString() == "true")
                    alumno?.matriculado = true
            }
            "nombre" -> alumno?.nombre = cadena.toString()
            "edad" -> alumno?.edad = cadena.toString().toInt()
            "alumno" -> alumnos.add(alumno!!)
        }
        println("endElement: $nombreLocal $nombre")
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        println("endDocument")
    }
}