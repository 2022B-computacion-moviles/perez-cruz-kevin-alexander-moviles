package com.example.examen2b

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.examen2b.dao.ConsultorioDao
import com.example.examen2b.entidades.Medico
import java.time.LocalDate

class CreateMedico : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_medico)

        val nombre = findViewById<EditText>(R.id.input_nombreMedico)
        val salario = findViewById<EditText>(R.id.input_salarioMedico)
        val esEspecialista = findViewById<Switch>(R.id.sw_esEspecialistaMedico)
        val aniosExp = findViewById<EditText>(R.id.input_aniosExp)
        val fechaNacimiento = findViewById<EditText>(R.id.input_fechaNacimientoDr)

        val guardarMedico = findViewById<Button>(R.id.btn_guardarMedico)

        guardarMedico.setOnClickListener {
            ConsultorioDao.consultorio.getMedicoDao().getRandomCode(
                onSuccess = { code ->
                        openCreationDialog(
                            Medico(
                                code,
                                nombre.text.toString(),
                                salario.text.toString().toDouble(),
                                esEspecialista.isChecked,
                                aniosExp.text.toString().toInt(),
                                LocalDate.parse(fechaNacimiento.text.toString())
                            )
                        )
                }
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openCreationDialog(newMedico: Medico) {
        ConsultorioDao.consultorio.getMedicoDao().create(newMedico)
        Toast.makeText(this, "Médico Guardado", Toast.LENGTH_SHORT).show()
        openActivity(CRUDMedico::class.java)

    }

    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
