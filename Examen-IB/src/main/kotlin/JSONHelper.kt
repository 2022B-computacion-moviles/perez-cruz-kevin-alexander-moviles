import java.io.File
import java.io.FileWriter
import java.io.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JSONHelper {
    var gson: Gson = Gson()
    val archivo: File = File("bdd/consultorio.json")

    fun createfile(){
        FileWriter(archivo).use { it.write("") }
    }

    fun modifyFile(concesionario: ArrayList<Medico>) {
        // Se convierte el objeto de la clase Medico a su representación en JSON
        val json = gson.toJson(concesionario)
        // Escribimos el JSON en el archivo
        FileWriter(archivo).use {
            it.write(json, 0, json.length)
        }
        // Sirve para confirmar la acción
        println("Archivo Modificado")
    }

    fun addMedico(medico: Medico){
        if (archivo.exists()) {
            val jsonInput = FileReader(archivo).use {
                it.readText()
            }

            if (jsonInput == "") {
                println("Archivo Vacío")
                var medicos= arrayListOf<Medico>(medico)
                val newJSON = gson.toJson(medicos)

                FileWriter(archivo).use {
                    it.write(newJSON, 0, newJSON.length)
                }
            } else {
                val tipoClase = object : TypeToken<ArrayList<Medico>>() {}.type
                val medicos: ArrayList<Medico> = gson.fromJson(jsonInput, tipoClase)
                medicos.add(medico)
                val newJSON = gson.toJson(medicos)

                FileWriter(archivo).use {
                    it.write(newJSON, 0, newJSON.length)
                }
            }
        }
            else{
                println("Archivo no existente. Creándolo...")
                createfile()
                var concesionarios= arrayListOf<Medico>(medico)
                val newJSON = gson.toJson(concesionarios)

                // sobrescribe el json
                FileWriter(archivo).use {
                    it.write(newJSON, 0, newJSON.length)
                }
            }
        // Sirve para confirmar la acción
        println("Archivo Modificado o Creado")
    }

    fun readMedicos(): ArrayList<Medico>{
        var medicos = arrayListOf<Medico>()
            if (archivo.exists()) {
                val input = FileReader(archivo).use {
                    it.readText()
                }
                if(input == ""){
                    medicos = arrayListOf<Medico>()
                }else{
                    val tipoClase = object : TypeToken<ArrayList<Medico>>() {}.type
                    var JSONMedicos: ArrayList<Medico> = gson.fromJson(input, tipoClase)
                    medicos = JSONMedicos
                }
            }
            else {
                medicos= arrayListOf<Medico>()
            }
        return medicos
    }
}