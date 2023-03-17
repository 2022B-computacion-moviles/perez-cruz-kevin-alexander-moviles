package com.example.examen2b.dao;

import com.example.examen2b.firebase.FBConsultorioDao;

abstract class ConsultorioDao {

    companion object {
        var consultorio: ConsultorioDao = FBConsultorioDao()
    }

    abstract fun getMedicoDao(): MedicosDao
    abstract fun getPacienteDao(): PacientesDao
}