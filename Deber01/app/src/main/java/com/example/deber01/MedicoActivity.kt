package com.example.deber01

import android.app.Activity
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
    private val EDIT_ACTIVITY = 49
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico)

        database = AppDatabase.getDatabase(this)

        val idMedicoActualizar = intent.getIntExtra("id", 0)

        val imageUri = ImageController.getImageUri(this, idMedicoActualizar.toLong())
        findViewById<ImageView>(R.id.iv_medicoImagen).setImageURI(imageUri)

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
                startActivityForResult(intent, EDIT_ACTIVITY)
            }

            R.id.mm_eliminarMedico -> {
                medicoLiveData.removeObservers(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.medicos().delete(medico)
                    ImageController.deleteImage(this@MedicoActivity, medico.id.toLong())
                    this@MedicoActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                findViewById<ImageView>(R.id.iv_medicoImagen).setImageURI(data!!.data)
            }
        }
    }
}