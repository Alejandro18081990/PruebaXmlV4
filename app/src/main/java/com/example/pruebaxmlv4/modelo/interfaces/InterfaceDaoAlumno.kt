package com.example.pruebaxmlv2.modelo.interfaces

import com.example.pruebaxmlv4.modelo.entidades.Alumno

interface InterfaceDaoAlumno {

    fun addAlumno(alumno: Alumno)

    fun procesarFicheroXml(): MutableList<Alumno>

    fun procesarArchivoXMLSAX(): MutableList<Alumno>

    fun procesarFicheroXmlInterno(): MutableList<Alumno>

    fun copiarArchivoDesdeAsset()
}