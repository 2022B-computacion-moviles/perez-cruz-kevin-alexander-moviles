package com.example.examen2b

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicosDao {
    @Query("SELECT * FROM medico")
    fun getAll(): LiveData<List<Medico>>

    @Query("SELECT * FROM medico WHERE id = :id")
    fun get(id: Int): LiveData<Medico>

    @Insert
    fun insertAll(vararg medicos: Medico) : List<Long>

    @Update
    fun update(medico: Medico)

    @Delete
    fun delete(medico: Medico)
}