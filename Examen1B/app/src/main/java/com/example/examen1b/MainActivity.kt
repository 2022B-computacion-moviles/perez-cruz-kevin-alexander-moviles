package com.example.examen1b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    var fileManager = FileManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener los botones de la interfaz de usuario
        val insertButton = findViewById<Button>(R.id.insert_button)
        val readButton = findViewById<Button>(R.id.read_button)
        val updateButton = findViewById<Button>(R.id.update_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)

        // Establecer los listeners para cada botón
        insertButton.setOnClickListener { insertPerson() }
        readButton.setOnClickListener { readPeople() }
        updateButton.setOnClickListener { updatePerson() }
        deleteButton.setOnClickListener { deletePerson() }
    }

    private fun insertPerson() {
        // Obtener los valores del nombre y la edad desde la interfaz de usuario
        val nameEditText = findViewById<EditText>(R.id.name_edit_text)
        val ageEditText = findViewById<EditText>(R.id.age_edit_text)
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString().toInt()
        // Crear una nueva persona y añadirla al archivo
        val person = Person(name, age)
        fileManager.insert(person)
    }

    private fun readPeople() {
        // Leer todas las personas del archivo
        val people = fileManager.readFromFile(this)

        // Mostrar una lista de nombres de personas en un TextView
        val names = people.map { it.name }.joinToString(", ")
        val namesTextView = findViewById<TextView>(R.id.names_text_view)
        namesTextView.text = names
    }

    private fun updatePerson() {
        // Obtener el nombre y la edad de la persona a actualizar desde la interfaz de usuario
        val nameEditText = findViewById<EditText>(R.id.name_edit_text)
        val ageEditText = findViewById<EditText>(R.id.age_edit_text)
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString().toInt()

        // Crear una nueva persona con el nuevo nombre y edad y actualizarla en el archivo
        val person = Person(name, age)
        fileManager.update(person)
    }

    private fun deletePerson() {
        // Obtener el nombre de la persona a eliminar desde la interfaz de usuario
        val nameEditText = findViewById<EditText>(R.id.name_edit_text)
        val name = nameEditText.text.toString()
        // Obtener la persona con el nombre especificado y eliminarla del archivo
        val people = fileManager.readFromFile(this)
        val person = people.find { it.name == name }
        if (person != null) {
            fileManager.delete(person)
        }
    }
}