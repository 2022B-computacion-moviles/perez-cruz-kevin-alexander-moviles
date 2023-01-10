package com.example.examen1b
import android.content.Context
import com.google.gson.Gson

class FileManager {
    private val fileName = "people.json"

    private fun writeToFile(context: Context, data: List<Person>) {
        val gson = Gson()
        val json = gson.toJson(data)
        val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        fileOutputStream.write(json.toByteArray())
        fileOutputStream.close()
    }

    fun readFromFile(context: Context): List<Person> {
        val fileInputStream = context.openFileInput(fileName)
        val inputString = fileInputStream.bufferedReader().use { it.readText() }
        fileInputStream.close()
        val gson = Gson()
        return gson.fromJson(inputString, Array<Person>::class.java).toList()
    }

    fun insert(context: Context, person: Person) {
        val people = readFromFile(context) + person
        writeToFile(context, people)
    }

    fun delete(context: Context, person: Person) {
        val people = readFromFile(context).filter { it != person }
        writeToFile(context, people)
    }

    fun update(context: Context, person: Person) {
        val people = readFromFile(context).map { if (it.name == person.name) person else it }
        writeToFile(context, people)
    }
}