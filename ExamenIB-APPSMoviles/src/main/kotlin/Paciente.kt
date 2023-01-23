import kotlinx.serialization.Serializable
@Serializable
class Paciente (
    var id: Int,
    var peso: Double,
    var nombre: String,
    var genero: Char,
    var tieneSeguro: Boolean
){
    override fun toString(): String {
        return ("ID: ${id}\t| Nombre: ${nombre}\t| Peso: ${peso}\t| Seguro: ${tieneSeguro}\t| Género: ${genero}")
    }
}