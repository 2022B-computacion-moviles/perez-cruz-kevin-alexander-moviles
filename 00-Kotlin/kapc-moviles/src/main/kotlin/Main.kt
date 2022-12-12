import java.util.Date

fun main(args: Array<String>) {
    println("Hola Mundo!")

    // Tipos de Variables

    // Inmutables: no se pueden reasignar, el = no altera su contenido
    val inmutable: String = "Kevin";
    // inmutable = "Alexander";

    // Mutables (reasignables)
    var mutable: String = "Kevin";
    mutable = "Alexander";

    // val > var (Se usará mayormente val)

    // Duck Typing -> Permite crear variables sin declarar su tipo
    val ejemploVariable = "Ejemplo"
    ejemploVariable.trim()
    val edad = 21

    // Variables Primitivas
    val nombreEstudiante: String = "Kevin"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true

    // Clases
    val fechaNacimiento: Date = Date() // No se usa "new" en clases

    // If-Else
    if(true){

    }else{

    }

    // Switch no existe
    val estadoCivilWhen = "S"
    when (estadoCivilWhen){
        ("S")->{
            println("Soltero")
        }
        "C" -> println("Casado")
        else -> println("Desconocido") // Para los demás casos
    }

    // Semejante al ? en Java
    val coqueto = if(estadoCivilWhen == "S") "Si" else "No"

    val sumaUno = Suma(1, 2)
    var sumaDos = Suma(1, null)
    var sumaTres = Suma(null, 2)
    var sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.historialSumas)
}

// Funciones
fun imprimirNombre(nombre: String): Unit{ // Es la equivalencia a void en Java
    println("Nombre: ${nombre}")
}

// Nullable
fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional por Defecto
    bonoEspecial: Double? = null // Opcional Nulo
): Double{
    // String -> String?
    // Int -> Int
    // Date - Date?
    if (bonoEspecial != null){
        return sueldo * tasa * bonoEspecial
    }
    return sueldo * tasa
}

// Clase con sintáxis de JAVA
abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int, // Parámetro
        dos: Int // Parámetro
    ){
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Iniciando")
    }
}

// Clase Kotlin
abstract class Numeros( // Constructor Primario
    // uno: Int, // Parámetro
    // public var uno: Int, // Propiedad de la clase pública
    protected val numeroUno: Int,
    protected val numeroDos: Int
){
    init{ // Bloque de código constructor primario
        // this.numeroDos // "this" opcional
        // this.numeroUno
        numeroUno
        numeroDos
        println("Iniciando")
    }
}

// Clase Heredada
class Suma( // Constructor Primario Suma
    uno: Int, // Parámetro
    dos: Int, // Parámetro
) : Numeros ( // Heredamos de la Clase Numeros
    // Super Constructor Numeros
    uno,
    dos
){
    init{ // Bloque Constructor Primario
        this.numeroUno
        this.numeroDos
    }
    constructor( // Segundo Constructor
        uno: Int?, // Parametros
        dos: Int // Parametros
    ): this(
        if(uno == null) 0 else uno,
        dos
    ){} // Opcionales
    constructor( // Tercer Constructor
        uno: Int, // Parametros
        dos: Int? // Parametros
    ): this(
        if(dos == null) 0 else dos,
        uno
    ){} // Opcionales
    constructor( // Cuarto Constructor
        uno: Int?, // Parametros
        dos: Int? // Parametros
    ): this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    ){} // Opcionales

    fun sumar(): Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{
        val pi = 3.14 // Suma.pi -> 3.14
        val historialSumas = arrayListOf<Int>() // Suma.historialSumas
        fun agregarHistorial(valorNuevaSuma: Int){ // Suma.agregarHistorial()
            historialSumas.add(valorNuevaSuma)
        }
    }
}
