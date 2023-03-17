package com.example.examen2b

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
@IgnoreExtraProperties
data class Medico (
    var id: String? = null,
    var nombre: String? = null,
    var salario: Double? = null,
    var esEspecialista: Boolean? = null,
    var aniosExp: Int? = null,
    var image: Int? = null,
    var create_date: Timestamp? = null,
    var update_date: Timestamp? = null
) {
    override fun toString(): String {
        return "Medico(id=$id, nombre='$nombre', salario='$salario', esEspecialista='$esEspecialista', aniosExp='$aniosExp')"
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "nombre" to nombre,
            "salario" to salario,
            "esEspecialista" to esEspecialista,
            "aniosExp" to aniosExp,
            "image" to image,
            "create_date" to create_date,
            "update_date" to update_date,
        )
    }
}