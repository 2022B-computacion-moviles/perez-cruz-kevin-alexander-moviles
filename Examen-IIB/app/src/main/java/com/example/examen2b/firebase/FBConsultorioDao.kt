package com.example.examen2b.firebase

import com.example.examen2b.dao.ConsultorioDao
import com.example.examen2b.dao.PacientesDao
import com.example.examen2b.dao.MedicosDao

class FBConsultorioDao: ConsultorioDao(){
    override fun getMedicoDao(): MedicosDao {
        return FBMedicoDao()
    }

    override fun getPacienteDao(): PacientesDao {
        return FBPacienteDao()
    }
}