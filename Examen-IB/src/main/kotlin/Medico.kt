class Medico (
    var id: Int,
    var nombre: String,
    var salario: Double,
    var genero: Char,
    var esEspecialista: Boolean,
    var pacientes: ArrayList<Paciente>
    ) {
    override fun toString(): String {
        return ("${id}\t\t| ${nombre}\t| ${salario}\t\t\t| ${esEspecialista}\t\t\t\t| ${genero}\t\t\t\t| ${pacientes}")
    }
}
