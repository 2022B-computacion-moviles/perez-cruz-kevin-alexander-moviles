package com.example.deber01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicoActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var medico: Medico
    private lateinit var medicoLiveData: LiveData<Medico>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico)

        database = AppDatabase.getDatabase(this)

        val idMedicoActualizar = intent.getIntExtra("id", 0)

        medicoLiveData = database.medicos().get(idMedicoActualizar)

        medicoLiveData.observe(this, Observer {
            medico = it
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
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menumedico, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mm_editarMedico -> {
                val intent = Intent(this, CreateMedico::class.java)
                intent.putExtra("medico", medico)
                startActivity(intent)
            }

            R.id.mm_eliminarMedico -> {
                medicoLiveData.removeObservers(this)
                CoroutineScope(Dispatchers.IO).launch {
                    database.medicos().delete(medico)
                    this@MedicoActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}