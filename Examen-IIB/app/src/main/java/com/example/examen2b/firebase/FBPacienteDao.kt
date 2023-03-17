package com.example.examen2b.firebase

import android.os.Build
import com.example.examen2b.dao.ConsultorioDao
import com.example.examen2b.dao.PacientesDao
import com.example.examen2b.entidades.Paciente
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class FBPacienteDao: PacientesDao {

    private val db = Firebase.firestore
    private val medicosCollectionReference = db.collection("medico")

    override fun getPacientesPorMedico(medicoId: Int, onSuccess: (ArrayList<Paciente>) -> Unit) {
        medicosCollectionReference
            .document(medicoId.toString())
            .collection("paciente")
            .get()
            .addOnSuccessListener { documents ->
                val pacientes = ArrayList<Paciente>()

                for (document in documents) {
                        pacientes.add(
                            Paciente(
                                id = document.id.split("/").last().toInt(),
                                idMedico = medicoId,
                                nombre = document.getString("nombre")!!,
                                peso = document.getDouble("peso")!!,
                                tieneSeguro = document.getBoolean("tieneSeguro")!!,
                                diasDieta = document.getDouble("diasDieta")!!.toInt(),
                                fechaNacimiento = LocalDate.parse(document.getString("fechaNacimiento")!!)
                            )
                        )
                }
                onSuccess(pacientes)
            }
    }

    override fun create(entity: Paciente) {
        val paciente = hashMapOf(
            "nombre" to entity.nombre,
            "peso" to entity.peso,
            "tieneSeguro" to entity.tieneSeguro,
            "diasDieta" to entity.diasDieta,
            "fechaNacimiento" to  entity.fechaNacimiento.toString()
        )

        medicosCollectionReference
            .document(entity.idMedico.toString())
            .collection("paciente")
            .document(entity.id.toString()).set(paciente)
    }

    override fun read(code: Int, onSuccess: (Paciente) -> Unit) {
        ConsultorioDao.consultorio.getMedicoDao().getAllMedicos { documents ->
            for (document in documents) {
                val db = Firebase.firestore
                val pacienteCollectionReference = db.collection(
                    "medico/${document.id}/paciente"
                )

                pacienteCollectionReference
                    .get()
                    .addOnSuccessListener { documentsPacientes ->
                        for (documentPaciente in documentsPacientes) {
                            if (documentPaciente.id.toInt() == code) {
                                    onSuccess(
                                        Paciente(
                                            documentPaciente.id.toInt(),
                                            document.id,
                                            documentPaciente.getString("nombre")!!,
                                            documentPaciente.getDouble("peso")!!,
                                            documentPaciente.getBoolean("tieneSeguro")!!,
                                            documentPaciente.getDouble("diasDieta")!!.toInt(),
                                            LocalDate.parse(documentPaciente.data!!["fechaNacimiento"].toString())
                                        )
                                    )
                            }
                        }
                    }
            }
        }
    }

    override fun update(entity: Paciente) {
        val paciente = hashMapOf(
            "nombre" to entity.nombre,
            "fechaNacimiento" to  entity.fechaNacimiento.toString(),
            "peso" to entity.peso,
            "tieneSeguro" to entity.tieneSeguro,
            "diasDieta" to entity.diasDieta,
        )

        medicosCollectionReference
            .document(entity.idMedico.toString())
            .collection("paciente")
            .document(entity.idMedico.toString()).set(paciente)
    }

    override fun delete(code: Int, onSuccess: (Unit) -> Unit) {
        ConsultorioDao.consultorio.getMedicoDao().getAllMedicos { documents ->
            for (document in documents) {
                val db = Firebase.firestore
                val pacienteCollectionReference = db.collection(
                    "medico/${document.id}/paciente"
                )

                pacienteCollectionReference
                    .get()
                    .addOnSuccessListener { documentsPacientes ->
                        for (documentPaciente in documentsPacientes) {
                            if (documentPaciente.id.toInt() == code) {
                                val pacienteReference = pacienteCollectionReference
                                    .document(code.toString())

                                pacienteReference.delete().addOnSuccessListener {
                                    onSuccess(Unit)
                                }
                            }
                        }
                    }
            }
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