package com.example.examen2b.dao

import com.example.examen2b.entidades.Medico

interface MedicosDao: GeneralDao<Medico, Int> {

    fun getAllMedicos(onSuccess: (ArrayList<Medico>) -> Unit)
}