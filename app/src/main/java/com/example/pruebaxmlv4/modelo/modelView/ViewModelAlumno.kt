package com.example.pruebaxmlv4.modelo.modelView

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.pruebaxmlv2.modelo.daos.DaoSimpleXml
import com.example.pruebaxmlv4.modelo.entidades.Alumno
import com.example.pruebaxmlv2.modelo.interfaces.InterfaceDaoAlumno

class ViewModelAlumno(application: Application) : AndroidViewModel(application),
    InterfaceDaoAlumno {

    var daoSimpleXml: DaoSimpleXml = DaoSimpleXml(application)

    override fun addAlumno(alumno: Alumno) {
        daoSimpleXml.addAlumno(alumno)
    }

    override fun procesarFicheroXml(): MutableList<Alumno> {
        return daoSimpleXml.procesarFicheroXml()
    }

    override fun procesarArchivoXMLSAX(): MutableList<Alumno> {
        return daoSimpleXml.procesarArchivoXMLSAX()
    }

    override fun procesarFicheroXmlInterno(): MutableList<Alumno> {
        return daoSimpleXml.procesarFicheroXmlInterno()
    }

    override fun copiarArchivo() {
        daoSimpleXml.copiarArchivo()
    }
}