package com.example.deber01

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePaciente : AppCompatActivity() {

    private val SELECT_ACTIVITY = 51
    private var imageUri: Uri? = null
    //private var idMedico: Int = 0
    var idMedico: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_paciente)

        var idPaciente: Int? = null
        idMedico = intent.getIntExtra("idMedico", 0)
        println("IdMedico: ${idMedico}")
        if (intent.hasExtra("paciente")){
            val paciente = intent.extras?.getSerializable("paciente") as Paciente

            var nombreInput = findViewById<EditText>(R.id.input_nombrePaciente)
            nombreInput.setText(paciente?.nombre)
            var pesoInput = findViewById<EditText>(R.id.input_pesoPaciente)
            pesoInput.setText(paciente!!.peso.toString())
            var seguroSw = findViewById<Switch>(R.id.sw_seguroPaciente)
            seguroSw.setChecked(paciente!!.tieneSeguro)

            idPaciente = paciente.idPaciente

            val imageUri = ImageController.getImageUri(this, idPaciente.toLong())
            findViewById<ImageView>(R.id.iv_imageSelectPaciente).setImageURI(imageUri)
        }

        val database = AppDatabase.getDatabase(this)
        var saveBtn = findViewById<Button>(R.id.btn_guardarPaciente)
        saveBtn.setOnClickListener{
            val nombre = findViewById<EditText>(R.id.input_nombrePaciente).text.toString()
            val peso = findViewById<EditText>(R.id.input_pesoPaciente).text.toString().toDouble()
            val seguro = findViewById<Switch>(R.id.sw_seguroPaciente).isChecked
            val paciente = Paciente(idMedico = idMedico, nombre = nombre, peso = peso, tieneSeguro = seguro, imagen = R.drawable.ic_launcher_background)
            println(paciente.toString())

            if (idPaciente != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    paciente.idPaciente = idPaciente

                    database.pacientes().update(paciente)

                    imageUri?.let {
                        val intent = Intent()
                        intent.data = it
                        setResult(Activity.RESULT_OK, intent)
                        ImageController.saveImage(this@CreatePaciente, idPaciente.toLong(), it)
                    }

                    this@CreatePaciente.finish()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val id = database.pacientes().insert(paciente)[0]

                    imageUri?.let {
                        ImageController.saveImage(this@CreatePaciente, id, it)
                    }

                    this@CreatePaciente.finish()
                }
            }
        }

        val imageSelect = findViewById<ImageView>(R.id.iv_imageSelectPaciente)
        imageSelect.setOnClickListener{
            ImageController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data

                val imageSelect = findViewById<ImageView>(R.id.iv_imageSelectPaciente)
                imageSelect.setImageURI(imageUri)
            }
        }
    }

}