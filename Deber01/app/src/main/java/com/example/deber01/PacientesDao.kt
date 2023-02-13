package com.example.deber01

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PacientesDao {
    @Query("SELECT paciente.idMedico, paciente.nombre, paciente.peso, paciente.tieneSeguro, paciente.imagen, paciente.idPaciente FROM paciente WHERE paciente.idMedico = :idMedico")
    fun getPacientesPorIdMedico(idMedico: Int): LiveData<List<Paciente>>

    @Query("SELECT paciente.idMedico, paciente.nombre, paciente.peso, paciente.tieneSeguro, paciente.imagen, paciente.idPaciente FROM paciente, medico WHERE paciente.idPaciente = :idPaciente AND medico.id = :idMedico")
    fun getPacientePorId(idPaciente: Int, idMedico: Int): LiveData<Paciente>

    @Insert
    fun insert(vararg paciente: Paciente) : List<Long>

    @Update
    fun update(paciente: Paciente)

    @Delete
    fun delete(paciente: Paciente)
}