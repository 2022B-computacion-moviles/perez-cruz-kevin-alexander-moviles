package com.example.examen2b.firebase

import android.os.Build
import com.example.examen2b.dao.MedicosDao
import com.example.examen2b.entidades.Medico
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class FBMedicoDao: MedicosDao {

    private val db = Firebase.firestore
    private val medicosCollectionReference = db.collection("medico")

    override fun getAllMedicos(onSuccess: (ArrayList<Medico>) -> Unit) {
        medicosCollectionReference
            .get()
            .addOnSuccessListener { documents ->
                val medicos = ArrayList<Medico>()


                for (document in documents) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        medicos.add(
                            Medico(
                                id = document.id.split("/").last().toInt(),
                                nombre = document.getString("nombre")!!,
                                salario = document.getDouble("salario")!!,
                                esEspecialista =  document.getBoolean("esEspecialista")!!,
                                aniosExp =  document.getDouble("aniosExp")!!.toInt(),
                                fechaNacimiento = LocalDate.parse(document.getString("fechaNacimiento")!!)
                            )
                        )
                    }
                }
                onSuccess(medicos)
            }
    }

    override fun create(entity: Medico) {
        val medico = hashMapOf(
            "nombre" to entity.nombre,
            "fechaNacimiento" to entity.fechaNacimiento.toString(),
            "salario" to entity.salario,
            "aniosExp" to entity.aniosExp,
            "esEspecialista" to entity.esEspecialista
        )
        medicosCollectionReference.document(entity.id.toString()).set(medico)
    }

    override fun read(code: Int, onSuccess: (Medico) -> Unit) {
        val medicoReference = medicosCollectionReference.document(code.toString())

        medicoReference
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val medico = Medico(
                            code,
                            document.data!!["nombre"].toString(),
                            document.data!!["salario"].toString().toDouble(),
                            document.data!!["esEspecialista"].toString().toBoolean(),
                            document.data!!["aniosExp"].toString().toDouble().toInt(),
                            LocalDate.parse(document.data!!["fechaNacimiento"].toString())
                        )
                    onSuccess(medico)
                }
            }
    }

    override fun update(entity: Medico) {
        val medico = hashMapOf(
            "nombre" to entity.nombre,
            "fechaNacimiento" to entity.fechaNacimiento.toString(),
            "salario" to entity.salario,
            "aniosExp" to entity.aniosExp,
            "esEspecialista" to entity.esEspecialista
        )
        medicosCollectionReference.document(entity.id.toString()).set(medico)
    }

    override fun delete(code: Int, onSuccess: (Unit) -> Unit) {
        val MedicoReference = medicosCollectionReference.document(code.toString())

        MedicoReference.delete().addOnSuccessListener {
            onSuccess(Unit)
        }
    }

    override fun getRandomCode(onSuccess: (Int) -> Unit) {
        var id = Date().time.toInt()
        if(id<0){
            id*=-1
        }
        onSuccess(id)
    }
}