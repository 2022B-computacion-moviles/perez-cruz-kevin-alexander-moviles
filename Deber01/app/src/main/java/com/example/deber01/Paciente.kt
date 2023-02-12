package com.example.deber01

import java.io.Serializable

class Paciente(
    var id: Int,
    var nombre: String?,
    var peso: Double,
    var tieneSeguro: Boolean,
): Serializable