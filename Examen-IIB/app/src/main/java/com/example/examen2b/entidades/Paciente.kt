package com.example.examen2b.entidades

import java.time.LocalDate
class Paciente(
    var id: Int,
    var idMedico: Int,

    var nombre: String?,
    var peso: Double,
    var tieneSeguro: Boolean,
    var diasDieta: Int,
    var fechaNacimiento: LocalDate
)