class Doctor(
    var id: Int,
    var nombre: String,
    var salario: Double,
    var genero: Char,
    var esEspecialista: Boolean,
    var pacientes: ArrayList<Paciente>
) {
    override fun toString(): String {
        return ("ID: ${id}\t| Nombre: ${nombre}\t| Salario: ${salario}\t| Especialista: ${esEspecialista}\t| Género: ${genero} | Paciente(s): ${pacientes}" )
    }
}