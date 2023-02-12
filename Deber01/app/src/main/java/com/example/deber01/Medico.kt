package com.example.deber01

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
): Serializable