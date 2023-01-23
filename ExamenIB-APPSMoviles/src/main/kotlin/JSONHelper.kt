import kotlinx.serialization.json.Json
import kotlinx.serialization.
import org.json.simple.JSONObject
import java.io.File
class JSONHelper {
    fun saveDoctor(doctor: Doctor) {
        val jsonObject = JSONObject()
        val pacientesSerializer = ArrayListSerializer(Paciente.serializer())
        jsonObject.put("id", doctor.id)
        jsonObject.put("nombre", doctor.nombre)
        jsonObject.put("salario", doctor.salario)
        jsonObject.put("genero", doctor.genero)
        jsonObject.put("esEspecialista", doctor.esEspecialista)
        jsonObject.put("pacientes", Json.encodeToString(doctor.pacientes))
        File("files/doctor.json").writeText(jsonObject.toJSONString())
    }

    fun getDoctor(id: Int): Doctor? {
        // code to read the doctor object from the JSON file
        val paciente1 = Paciente(1, 47.5, "Kevin Perez", 'M', true)
        val paciente2 = Paciente(2, 58.0, "Melany Sandoval", 'F', false)
        val pacientes = arrayListOf<Paciente>(paciente1, paciente2)
        val doctor = Doctor(1, "Juan Perez", 120000.0, 'M', true, pacientes)
        return doctor
    }

    fun updateDoctor(doctor: Doctor) {
        // code to update the doctor object in the JSON file
    }

    fun deleteDoctor(id: Int) {
        // code to delete the doctor object from the JSON file
    }
}