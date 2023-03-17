package com.example.examen2b

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.examen2b.dao.ConsultorioDao
import com.example.examen2b.entidades.Medico
import java.time.LocalDate

class MedicoActivity : AppCompatActivity() {

    var selectedMedicoID : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico)

        val idMedico = findViewById<TextView>(R.id.tv_editarMedico)
        val nombre = findViewById<EditText>(R.id.input_nombreMedicoEdit)
        val salario = findViewById<EditText>(R.id.input_salarioMedicoEdit)
        val esEspecialista= findViewById<Switch>(R.id.sw_esEspecialistaMedicoEdit)
        val aniosExp = findViewById<EditText>(R.id.input_aniosExpEdit)
        val fechaNacimiento = findViewById<EditText>(R.id.input_fechaNacimientoDrEdit)

        val BtnEditarMedico = findViewById<Button>(R.id.btn_editarMedico)

        selectedMedicoID = intent.getIntExtra("selectedMedicoId", 0)

        ConsultorioDao.consultorio.getMedicoDao().read(
            selectedMedicoID!!,
            onSuccess = { device ->
                idMedico.setText(device.id.toString())
                nombre.setText(device.nombre)
                salario.setText(device.salario.toString())
                esEspecialista.setChecked(device.esEspecialista)
                aniosExp.setText(device.aniosExp.toString())
                fechaNacimiento.setText(device.fechaNacimiento.toString())
            }
        )

        BtnEditarMedico.setOnClickListener {
                openEditionDialog(
                    Medico(
                        selectedMedicoID!!,
                        nombre.text.toString(),
                        salario.text.toString().toDouble(),
                        esEspecialista.isChecked,
                        aniosExp.text.toString().toInt(),
                        LocalDate.parse(fechaNacimiento.text.toString())
                    )
                )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openEditionDialog(editedMedico: Medico) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar")
        builder.setMessage("Estás seguro de editar este Médico?")

        builder.setPositiveButton("Sí") { _, _ ->
            ConsultorioDao.consultorio.getMedicoDao().update(editedMedico)
            Toast.makeText(this, "Médico Editado", Toast.LENGTH_SHORT).show()
            openActivity(CRUDMedico::class.java)
        }
        builder.setNegativeButton("No") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }

    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menumedico, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mm_eliminarMedico -> {
                openDeleteDialog()
                return true
            }
            R.id.mm_verpacientes -> {
                val intent = Intent(this, CRUDPaciente::class.java)
                intent.putExtra("selectedMedicoId", selectedMedicoID)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar")
        builder.setMessage("Esta seguro de querer eliminar este registro de Médico?")

        builder.setPositiveButton("Si") { _, _ ->
            ConsultorioDao.consultorio.getMedicoDao().delete(
                selectedMedicoID!!,
                onSuccess = {
                    val intent = Intent(this, CRUDMedico::class.java)
                    startActivity(intent)
                }
            )
            Toast.makeText(this, "Médico Eliminado", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }


}