class DoctorService {
    private val jsonHelper = JSONHelper()

    fun createDoctor(id: Int, name: String, salary: Double, genero: Char, esEspecialista: Boolean, pacientes: ArrayList<Paciente>) {
        val doctor = Doctor(id, name, salary, genero, esEspecialista, pacientes)
        jsonHelper.saveDoctor(doctor)
    }

    fun getDoctor(id: Int): Doctor? {
        return jsonHelper.getDoctor(id)
    }

    fun updateDoctor(id: Int, name: String, salary: Double, genero: Char, esEspecialista: Boolean, pacientes: ArrayList<Paciente>) {
        val doctor = Doctor(id, name, salary, genero, esEspecialista, pacientes)
        jsonHelper.updateDoctor(doctor)
    }

    fun deleteDoctor(id: Int) {
        jsonHelper.deleteDoctor(id)
    }
}