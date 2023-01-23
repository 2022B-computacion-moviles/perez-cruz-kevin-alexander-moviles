fun main() {
    /*
    // Crea una instancia de la clase Paciente
    val paciente1 = Paciente(1, 47.5, "Kevin Perez", 'M', true)
    val paciente2 = Paciente(2, 58.0, "Melany Sandoval", 'F', false)
    val pacientes = arrayListOf<Paciente>(paciente1, paciente2)

    // Crea una instancia de la clase Doctor
    val doctor = Doctor(1, "Juan Perez", 120000.0, 'M', true, pacientes)

    // Crea un objeto JSON a partir de la instancia de la clase Doctor
    val json = Klaxon().toJsonString(doctor)

    // Crea un archivo JSON en la ubicación actual con el nombre "doctor.json"
    //File("files/doctor.json").writeText(json)
    print(doctor.toString())
    */
    val doctorService = DoctorService()
    val paciente1 = Paciente(1, 47.5, "Kevin Perez", 'M', true)
    val paciente2 = Paciente(2, 58.0, "Melany Sandoval", 'F', false)
    val pacientes = arrayListOf<Paciente>(paciente1, paciente2)
    // create a new doctor
    doctorService.createDoctor(1, "Juan Perez", 120000.0, 'M', true, pacientes)
}