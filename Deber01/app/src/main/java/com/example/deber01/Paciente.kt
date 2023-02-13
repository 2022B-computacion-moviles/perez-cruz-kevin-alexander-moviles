package com.example.deber01

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable
// ,  foreignKeys = [ForeignKey(entity = Medico::class, parentColumns = ["id"], childColumns = ["idMedico"], onDelete = CASCADE, onUpdate = CASCADE, deferred = false)]
@Entity(tableName = "paciente")
class Paciente(
    @PrimaryKey(autoGenerate = true)
    var idPaciente: Int = 0,
    var nombre: String?,
    var peso: Double,
    var tieneSeguro: Boolean,
    var imagen: Int,
    var idMedico: Int
): Serializable {
    override fun toString(): String {
        return "Paciente(idMedico=$idMedico, nombre='$nombre', peso='$peso', tieneSeguro='$tieneSeguro', id='$idPaciente')"
    }
}