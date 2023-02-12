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

class CreateMedico : AppCompatActivity() {

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_medico)

        var idMedico: Int? = null

        if (intent.hasExtra("medico")){
            val medico = intent.extras?.getSerializable("medico") as Medico

            var nombreInput = findViewById<EditText>(R.id.input_nombreMedico)
            nombreInput.setText(medico?.nombre)
            var salarioInput = findViewById<EditText>(R.id.input_salarioMedico)
            salarioInput.setText(medico!!.salario.toString())
            var especialistaSw = findViewById<Switch>(R.id.sw_esEspecialistaMedico)
            especialistaSw.setChecked(medico!!.esEspecialista)

            idMedico = medico.id

            val imageUri = ImageController.getImageUri(this, idMedico.toLong())
            findViewById<ImageView>(R.id.iv_imageSelectMedico).setImageURI(imageUri)
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

                    imageUri?.let {
                        val intent = Intent()
                        intent.data = it
                        setResult(Activity.RESULT_OK, intent)
                        ImageController.saveImage(this@CreateMedico, idMedico.toLong(), it)
                    }

                    this@CreateMedico.finish()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val id = database.medicos().insertAll(medico)[0]

                    imageUri?.let {
                        ImageController.saveImage(this@CreateMedico, id, it)
                    }

                    this@CreateMedico.finish()
                }
            }
        }

        val imageSelect = findViewById<ImageView>(R.id.iv_imageSelectMedico)
        imageSelect.setOnClickListener{
            ImageController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data

                val imageSelect = findViewById<ImageView>(R.id.iv_imageSelectMedico)
                imageSelect.setImageURI(imageUri)
            }
        }
    }
}