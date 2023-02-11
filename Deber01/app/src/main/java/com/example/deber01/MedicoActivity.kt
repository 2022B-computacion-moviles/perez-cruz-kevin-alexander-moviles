package com.example.deber01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MedicoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico)

        val medico = intent.getParcelableExtra<Medico>("medico")

        val idMedico = findViewById<TextView>(R.id.tv_medicoID)
        idMedico.text = "${medico?.id}"
        val nombreMedico = findViewById<TextView>(R.id.tv_medicoNombre)
        nombreMedico.text = medico?.nombre
        val salarioMedico = findViewById<TextView>(R.id.tv_medicoSalario)
        salarioMedico.text = "$${medico?.salario}"
        val esEspecialistaMedico = findViewById<TextView>(R.id.tv_medicoEspecialista)
        esEspecialistaMedico.text = "${medico?.esEspecialista}"
        val fotoMedico = findViewById<ImageView>(R.id.iv_medicoImagen)
        fotoMedico.setImageResource(medico!!.image)
    }
}