import java.util.*
import kotlin.collections.ArrayList

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

    // ARREGLOS

    // Tipos de Arreglos

    // Arreglo Estático
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    println(Arrays.toString(arregloEstatico))

    // Arreglos Dinamicos
    var arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // OPERADORES -> Sirve para los arreglos estaticos y dinamicos
    // FOR EACH -> Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach {
        valorActual: Int ->
        println("Valor actual: ${valorActual}")
        }
    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    // MAP -> Muta el arreglo (Cambia el arreglo)
    // 1) Enviamos el nuevo valor de la interaction
    // 2) Nos devuelve es un NUEVO ARREGLO con los valores modificados
    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    //        .map { valorActual: Int ->
    //            return @map valorActual + 15
    //        }
    println(respuestaMapDos)

    // Filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5 // Expression Condicion
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //Or AND And
    //OR -> ANY (Alguno cumple?)
    //AND -> ALL (Todos cumplen?)
    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny)

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll)

    // REDUCE -> Valor acumulado
    // Valor acumulado = 0 (Siempre 0 en lenguaje Kotlin)
    // [// [1, 2, 3, 4, 5] -> Summer todos los valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion 1
    // valorIteracion2 = valorIteracion1 + 2 = 1 + 2 = 3 -> Iteracion 2
    // valorIteracion3 = valorIteracion2 + 3 = 3 + 3 = 6 -> Iteracion 3
    // valorIteracion4 = valorIteracion3 + 4 = 6 + 4 = 10 -> Iteracion 4
    // valorIteracion5 = valorIteracion4 + 5 = 10 + 5 = 15 -> Iteracion 5
    val respuestaReduce: Int = arregloDinamico
        .reduce { // acumulado = 0 -> SIEMPRE EMPIEZA EN 0
                acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual)// -> Logica negocio
        }
    println(respuestaReduce) // 78
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
