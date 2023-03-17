package com.example.examen2b.inicialData

import android.os.Build
import com.example.examen2b.entidades.Medico
import java.time.LocalDate

class MedicoInicialData {

    companion object {
        var medicosInitData = ArrayList<Medico>()
        init {
                medicosInitData.add(
                    Medico(
                        1, "Juan Martinez",  2500.0, true, 10, LocalDate.parse("1990-03-15")
                    )
                )

                medicosInitData.add(
                    Medico(
                        2, "Melany Cruz",  1500.0, false, 5, LocalDate.parse("1992-11-17")
                    )
                )
        }
    }
}