package com.example.examen2b

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.examen2b.dao.ConsultorioDao
import com.example.examen2b.entidades.Paciente

import java.time.LocalDate

class CreatePaciente : AppCompatActivity() {

    private var selectedMedicoId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_paciente)

        selectedMedicoId = intent.getIntExtra("selectedMedicoId", 0)

        val idMedicoPaciente = findViewById<TextView>(R.id.tv_medicoPaciente)

        val nombre = findViewById<EditText>(R.id.input_nombrePaciente)
        val peso = findViewById<EditText>(R.id.input_pesoPaciente)
        val tieneSeguro = findViewById<Switch>(R.id.sw_seguroPaciente)
        val fechaNacimiento = findViewById<EditText>(R.id.input_fechaNacimiento)
        val diasDieta = findViewById<EditText>(R.id.input_diasDieta)

        val crearPaciente = findViewById<Button>(R.id.btn_guardarPaciente)

        idMedicoPaciente.setText(selectedMedicoId.toString())

        crearPaciente.setOnClickListener {
            ConsultorioDao.consultorio.getPacienteDao().getRandomCode(
                onSuccess = { code ->
                        openCreationDialog(
                            Paciente(
                                code,
                                selectedMedicoId!!,
                                nombre.text.toString(),
                                peso.text.toString().toDouble(),
                                tieneSeguro.isChecked,
                                diasDieta.text.toString().toInt(),
                                LocalDate.parse(fechaNacimiento.text.toString()),
                            ),
                            selectedMedicoId!!
                        )
                }
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openCreationDialog(newPaciente: Paciente, selectedMedicoId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Guardar")
        builder.setMessage("Seguro que quieres guardar esta instancia de Paciente?")

        builder.setPositiveButton("Sí") { _, _ ->
            ConsultorioDao.consultorio.getPacienteDao().create(newPaciente)
            Toast.makeText(this, "Paciente Guardado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CRUDPaciente::class.java)
            intent.putExtra("selectedMedicoId", selectedMedicoId)
            startActivity(intent)
        }

        builder.setNegativeButton("No") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }
}