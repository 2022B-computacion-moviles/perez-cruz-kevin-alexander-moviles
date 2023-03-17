package com.example.examen2b.inicialData

import android.os.Build
import com.example.examen2b.entidades.Paciente
import java.time.LocalDate

class PacienteInicialData {

    companion object {
        var pacientesInitData = ArrayList<Paciente>()

        init {
                pacientesInitData.add(
                    Paciente(
                        1, 1,"Luis Almeida",56.0, false, 5, LocalDate.parse("2002-08-18")
                    )
                )

                pacientesInitData.add(
                    Paciente(
                        2, 1,"Kelly Arauz",48.0, true, 25, LocalDate.parse("2001-08-21")
                    )
                )
        }
    }
}