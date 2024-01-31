package com.example.pruebaxmlv2.modelo.daos

import android.content.Context
import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebaxmlv4.modelo.entidades.Alumno
import com.example.pruebaxmlv4.modelo.entidades.Escuela
import com.example.pruebaxmlv4.modelo.handlers.AlumnoHandlerXml
import com.example.pruebaxmlv2.modelo.interfaces.InterfaceDaoAlumno
import org.simpleframework.xml.core.Persister
import java.io.*
import javax.xml.parsers.SAXParserFactory

class DaoSimpleXml constructor(var context: Context) : InterfaceDaoAlumno {

    //Serializar y deserializar
    val serializer = Persister()
    val nombreFichero = "escuela.xml"
    var listaAlumnosAssets = mutableListOf<Alumno>()
    var listaAlumnosHandler: MutableList<Alumno> = mutableListOf()
    var listaAlumnosFicheroInterno: MutableList<Alumno> = mutableListOf()

    override fun addAlumno(alumno: Alumno) {
        try {
            listaAlumnosFicheroInterno.add(alumno)
            val listEscuela = Escuela(listaAlumnosFicheroInterno)
            val outputStream = context.openFileOutput(nombreFichero, MODE_APPEND)
            //Pasamos por parámetros lo que escribimos y el dónde lo escribimos
            serializer.write(listEscuela, outputStream)
        } catch (e: IOException) {
            Log.d("ErrorAnadir",e.message.toString())
        }
    }


    override fun procesarArchivoXMLSAX(): MutableList<Alumno> {
        try {
            val factory = SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()
            val handler = AlumnoHandlerXml()
            val inputStream = context.assets.open(nombreFichero)
            parser.parse(inputStream, handler)
            listaAlumnosHandler = handler.alumnos
        } catch (e: Exception) {
            Log.d("ErrorSAX", e.message.toString())
        }
        return listaAlumnosHandler
    }

    override fun procesarFicheroXml(): MutableList<Alumno> {
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
        try {
            val file = File(context.filesDir, nombreFichero)
            val inputStream = FileInputStream(file)
            //Convertimos los datos del fichero interno en objetos de Kotlin
            //desserializandolos
            val escuelaList = serializer.read(Escuela::class.java, inputStream)
            Log.d("EscuelaList",escuelaList.toString())
            listaAlumnosFicheroInterno.addAll(escuelaList.alumnos)
            inputStream.close()
        } catch (e: Exception) {
            Log.d("ErrorInterno", e.message.toString())
        }
        return listaAlumnosFicheroInterno
    }

    override fun copiarArchivoDesdeAsset() {
        val archivoEnAssets = context.assets.open(nombreFichero)
        val archivoInterno = context.openFileOutput(nombreFichero, MODE_PRIVATE)
        archivoEnAssets.copyTo(archivoInterno)
        archivoEnAssets.close()
        archivoInterno.close()
    }

}
