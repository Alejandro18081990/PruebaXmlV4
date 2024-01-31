package com.example.pruebaxmlv4.modelo.entidades

import org.simpleframework.xml.*

@Root(name = "escuela")
data class Escuela constructor(
    @field: ElementList(inline = true, entry = "alumno")
    var alumnos: List<Alumno> = mutableListOf()
)


@Root(name = "alumno")
data class Alumno constructor(
    @field:Element(name = "nombre")
    var nombre: String = "",

    @field:Element(name = "edad")
    var edad: Int = 0,

    @field:Element(name = "matriculado")
    var matriculado: Boolean = false,

    @field:Element(name = "materias", type = Materias::class)
    var materias: Materias = Materias()
)
@Element(name = "materias")
data class Materias constructor(
    @field:ElementList(inline = true, name = "materias", entry = "materia")
    var materias: List<Materia> = mutableListOf()
)

@Element(name = "materia")
data class Materia constructor(
    @field:Attribute(name = "nombre")
    var nombre: String = "",
    @field:Attribute(name = "calificacion")
    var calificacion: Int = 0,
)
