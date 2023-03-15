package com.example.examen2b

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "medico")
class Medico(
    var nombre: String?,
    var salario: Double,
    var esEspecialista: Boolean,
    var image: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
): Serializable {
    override fun toString(): String {
        return "Medico(id=$id, nombre='$nombre', salario='$salario', esEspecialista='$esEspecialista')"
    }
}