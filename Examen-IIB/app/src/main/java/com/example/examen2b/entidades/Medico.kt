package com.example.examen2b.entidades

import java.time.LocalDate

data class Medico (
    var id: Int,

    var nombre: String,
    var salario: Double,
    var esEspecialista: Boolean,
    var aniosExp: Int,
    var fechaNacimiento: LocalDate
)