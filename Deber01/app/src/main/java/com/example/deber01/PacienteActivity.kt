package com.example.deber01

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
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

class PacienteActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var paciente: Paciente
    private lateinit var pacienteLiveData: LiveData<Paciente>
    private val EDIT_ACTIVITY = 48

    var idMedico: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        database = AppDatabase.getDatabase(this)
        //
        val idPacienteActualizar = intent.getIntExtra("id", 0)
        idMedico = intent.getIntExtra("idMedico", 0)
        //
        val imageUri = ImageController.getImageUri(this, idPacienteActualizar.toLong())
        findViewById<ImageView>(R.id.iv_pacienteImagen).setImageURI(imageUri)

        pacienteLiveData = database.pacientes().getPacientePorId(idPacienteActualizar, idMedico)

        pacienteLiveData.observe(this, Observer {
            paciente = it
            val idPaciente = findViewById<TextView>(R.id.tv_pacienteID)
            idPaciente.text = "${paciente?.idPaciente}"
            val nombrePaciente = findViewById<TextView>(R.id.tv_pacienteNombre)
            nombrePaciente.text = paciente?.nombre
            val pesoPaciente = findViewById<TextView>(R.id.tv_pacientePeso)
            pesoPaciente.text = "${paciente?.peso} kg"
            val seguroPaciente = findViewById<TextView>(R.id.tv_seguroPaciente)
            seguroPaciente.text = "${paciente?.tieneSeguro}"
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menupaciente, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mm_editarPaciente -> {
                val intent = Intent(this, CreatePaciente::class.java)
                println(paciente.toString())
                println(idMedico)
                intent.putExtra("paciente", paciente)
                intent.putExtra("idMedico", idMedico)
                startActivityForResult(intent, EDIT_ACTIVITY)
            }

            R.id.mm_eliminarPaciente -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Eliminar Paciente")
                    setMessage("¿Estás seguro de querer eliminar este registro? Esta acción es irreversible")
                    setPositiveButton("Si") { _: DialogInterface, _: Int ->
                        pacienteLiveData.removeObservers(this@PacienteActivity)

                        CoroutineScope(Dispatchers.IO).launch {
                            database.pacientes().delete(paciente)
                            ImageController.deleteImage(this@PacienteActivity, paciente.idPaciente.toLong())
                            this@PacienteActivity.finish()
                        }
                    }
                    setNegativeButton("No", null)
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                findViewById<ImageView>(R.id.iv_pacienteImagen).setImageURI(data!!.data)
            }
        }
    }
}