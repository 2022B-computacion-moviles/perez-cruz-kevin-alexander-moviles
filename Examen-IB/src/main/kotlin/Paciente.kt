class Paciente (
    var id: Int,
    var nombre: String,
    var peso: Double,
    var tieneSeguro: Boolean,
    var genero: Char
){
    override fun toString(): String {
        return ("ID: ${id}\t| Nombre: ${nombre}\t| Peso: ${peso}\t| Seguro: ${tieneSeguro}\t| Género: ${genero}")
        //return ("${id}\t\t| ${nombre}\t\t| ${peso}\t\t| ${tieneSeguro}\t\t| ${genero}")
    }
}