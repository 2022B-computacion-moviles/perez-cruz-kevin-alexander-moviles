package com.example.examen2b

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.examen2b.dao.ConsultorioDao
import com.example.examen2b.entidades.Paciente
import java.time.LocalDate

class PacienteActivity : AppCompatActivity() {

    private var selectedPacienteId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        val idPaciente = findViewById<TextView>(R.id.tv_editarPaciente)
        val idMedico = findViewById<TextView>(R.id.tv_medicoPacienteEdit)

        val nombrePlainText = findViewById<EditText>(R.id.input_nombrePacienteEdit)
        val pesoPlainText = findViewById<EditText>(R.id.input_pesoPacienteEdit)
        val tieneSeguroPlainText = findViewById<Switch>(R.id.sw_seguroPacienteEdit)
        val diasDietaPlainText = findViewById<EditText>(R.id.input_diasDietaEdit)
        val fechaNacimientoPlainText = findViewById<EditText>(R.id.input_fechaNacimientoEdit)

        val btnEditarPaciente = findViewById<Button>(R.id.btn_editarPaciente)

        var selectedComponent: Paciente? = null
        selectedPacienteId = intent.getIntExtra("selectedPacienteId", 0)

        // Setting component data when it is ready
        ConsultorioDao.consultorio.getPacienteDao().read(
            selectedPacienteId!!,
            onSuccess = { component ->
                selectedComponent = component

                idPaciente.setText(selectedComponent!!.id.toString())
                idMedico.setText(selectedComponent!!.idMedico.toString())

                nombrePlainText.setText(selectedComponent!!.nombre)
                pesoPlainText.setText(selectedComponent!!.peso.toString())
                tieneSeguroPlainText.setChecked(selectedComponent!!.tieneSeguro)
                diasDietaPlainText.setText(selectedComponent!!.diasDieta.toString())
                fechaNacimientoPlainText.setText(selectedComponent!!.fechaNacimiento.toString())
            }
        )

        btnEditarPaciente.setOnClickListener {
                openEditionDialog(
                    Paciente(
                        selectedPacienteId!!,
                        selectedComponent!!.idMedico,
                        nombrePlainText.text.toString(),
                        pesoPlainText.text.toString().toDouble(),
                        tieneSeguroPlainText.isChecked,
                        diasDietaPlainText.text.toString().toInt(),
                        LocalDate.parse(fechaNacimientoPlainText.text.toString())
                    ),
                    selectedComponent!!.idMedico
                )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openEditionDialog(editedComponent: Paciente, selectedMedicoId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar")
        builder.setMessage("Estas seguro de editar este Paciente?")

        builder.setPositiveButton("Sí") { _, _ ->
            ConsultorioDao.consultorio.getPacienteDao().update(editedComponent)
            Toast.makeText(this, "Paciente Editado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CRUDPaciente::class.java)
            intent.putExtra("selectedMedicoId", selectedMedicoId)
            startActivity(intent)
        }
        builder.setNegativeButton("No") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mm_eliminarPaciente -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar")
        builder.setMessage("Estas seguro de querer eliminar este registro de Paciente?")

        builder.setPositiveButton("Sí") { _, _ ->
            ConsultorioDao.consultorio.getPacienteDao().delete(
                selectedPacienteId!!,
                onSuccess = {
                    val intent = Intent(this, CRUDPaciente::class.java)
                    startActivity(intent)
                }
            )
            Toast.makeText(this, "Paciente Eliminado", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }
}