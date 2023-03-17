package com.example.examen2b.dao

import com.example.examen2b.entidades.Paciente
interface PacientesDao: GeneralDao<Paciente, Int>{
    fun getPacientesPorMedico(medicoId: Int,onSuccess: (ArrayList<Paciente>) -> Unit)
}