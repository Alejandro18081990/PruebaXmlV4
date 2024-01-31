package com.example.pruebaxmlv4.modelo.modelView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pruebaxmlv2.modelo.daos.DaoSimpleXml
import com.example.pruebaxmlv4.modelo.entidades.Alumno

class ViewModelAlumno(application: Application) : AndroidViewModel(application) {

    var daoSimpleXml: DaoSimpleXml = DaoSimpleXml(application)

    fun addAlumno(alumno: Alumno) {
        daoSimpleXml.addAlumno(alumno)
    }

    fun procesarFicheroXml(): MutableList<Alumno> {
        return daoSimpleXml.procesarFicheroXml()
    }

    fun procesarArchivoXMLSAX(): MutableList<Alumno> {
        return daoSimpleXml.procesarArchivoXMLSAX()
    }

    fun procesarFicheroXmlInterno(): MutableList<Alumno> {
        return daoSimpleXml.procesarFicheroXmlInterno()
    }

    fun copiarArchivo() {
        daoSimpleXml.copiarArchivoDesdeAsset()
    }
}