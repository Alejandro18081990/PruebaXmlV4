package com.example.pruebaxmlv2.modelo.daos

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.pruebaxmlv4.modelo.entidades.Alumno
import com.example.pruebaxmlv4.modelo.entidades.Escuela
import com.example.pruebaxmlv4.modelo.handlers.AlumnoHandlerXml
import com.example.pruebaxmlv2.modelo.interfaces.InterfaceDaoAlumno
import org.simpleframework.xml.core.Persister
import java.io.*
import javax.xml.parsers.SAXParserFactory

class DaoSimpleXml constructor(var context: Context) : InterfaceDaoAlumno {

    //Serializar y deserializar
    private val nombreFichero = "escuela.xml"
    var listaAlumnosAssets = mutableListOf<Alumno>()
    var listaAlumnosHandler: MutableList<Alumno> = mutableListOf()
    var listaAlumnosFicheroInterno: MutableList<Alumno> = mutableListOf()

    override fun addAlumno(alumno: Alumno) {
        val serializer = Persister()
        try {
            listaAlumnosFicheroInterno.add(alumno)
            val listEscuela = Escuela(listaAlumnosFicheroInterno)
            val outputStream = context.openFileOutput(nombreFichero, MODE_PRIVATE)
            //Pasamos por parámetros lo que escribimos y el dónde lo escribimos
            serializer.write(listEscuela, outputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //1.Abrimos el directorio y leemos el fichero xml
    //2.Mediante EstudiantesHanlderXml procesa poco al documento y se guarda
    //en una lista
    override fun procesarArchivoXMLSAX(): MutableList<Alumno> {
        try {
            val factory = SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()
            val handler = AlumnoHandlerXml()
            val inputStream = context.assets.open(nombreFichero)
            parser.parse(inputStream, handler)
            listaAlumnosHandler = handler.alumnos
            Log.d("ListaHandler", listaAlumnosHandler.toString())
        } catch (e: Exception) {
            Log.d("ErrorSAX", e.message.toString())
        }
        return listaAlumnosHandler
    }

    override fun procesarFicheroXml(): MutableList<Alumno> {
        val serializer = Persister()
        //var listaAlumnos = mutableListOf<Alumno>()
        var inputStream: InputStream? = null
        var reader: InputStreamReader? = null
        try {
            inputStream = context.assets.open(nombreFichero)
            reader = InputStreamReader(inputStream)
            val escuelaListType = serializer.read(Escuela::class.java, reader, false)
            listaAlumnosAssets.addAll(escuelaListType.alumnos)
        } catch (e: Exception) {
            // Manejo de errores
            Log.d("ErrorException0", e.message.toString())
        } finally {
            // Cerrar inputStream y reader
            try {
                reader?.close()
                inputStream?.close()
            } catch (e: IOException) {
                Log.d("ErrorException1", e.message.toString())
            }
        }
        return listaAlumnosAssets
    }

    override fun procesarFicheroXmlInterno(): MutableList<Alumno> {
        var listaAlumnosFicheroInterno = mutableListOf<Alumno>()
        val serializer = Persister()
        try {
            val file = File(context.filesDir, nombreFichero)
            val inputStream = FileInputStream(file)
            //Convertimos los datos del fichero interno en objetos de Kotlin
            //desserializandolos
            val escuelaList = serializer.read(Escuela::class.java, inputStream)
            listaAlumnosFicheroInterno.addAll(escuelaList.alumnos)
            inputStream.close()
        } catch (e: Exception) {
            Log.d("ErrorInterno", e.message.toString())
        }
        return listaAlumnosFicheroInterno
    }

    override fun copiarArchivo() {
        val archivoEnAssets = context.assets.open(nombreFichero)
        val archivoInterno = context.openFileOutput(nombreFichero, MODE_PRIVATE)
        archivoEnAssets.copyTo(archivoInterno)
        archivoEnAssets.close()
        archivoInterno.close()
    }
}
