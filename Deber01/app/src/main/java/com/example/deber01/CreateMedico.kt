package com.example.deber01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateMedico : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_medico)

        var idMedico: Int? = null

        if (intent.hasExtra("medico")){
            val medico = intent.extras?.getParcelable<Medico>("medico")

            var nombreInput = findViewById<EditText>(R.id.input_nombreMedico)
            nombreInput.setText(medico?.nombre)
            var salarioInput = findViewById<EditText>(R.id.input_salarioMedico)
            salarioInput.setText(medico!!.salario.toString())
            var especialistaSw = findViewById<Switch>(R.id.sw_esEspecialistaMedico)
            especialistaSw.setChecked(medico!!.esEspecialista)

            idMedico = medico.id
        }

        val database = AppDatabase.getDatabase(this)

        var saveBtn = findViewById<Button>(R.id.btn_guardarMedico)
        saveBtn.setOnClickListener{
            val nombre = findViewById<EditText>(R.id.input_nombreMedico).text.toString()
            val salario = findViewById<EditText>(R.id.input_salarioMedico).text.toString().toDouble()
            val esEspecialista = findViewById<Switch>(R.id.sw_esEspecialistaMedico).isChecked

            val medico = Medico(nombre, salario, esEspecialista, R.drawable.ic_launcher_background)

            if (idMedico != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    medico.id = idMedico

                    database.medicos().update(medico)

                    this@CreateMedico.finish()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    database.medicos().insertAll(medico)

                    this@CreateMedico.finish()
                }
            }
        }
    }
}