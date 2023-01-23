import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

val reader = BufferedReader(InputStreamReader(System.`in`))
val helper = JSONHelper()

var headerMedicos : String = "ID\t\t| Nombre\t\t| Salario\t\t| Especialista\t\t| Género\t\t| Paciente(s)"
var headerPacientes : String = "ID\t\t| Nombre\t\t| Peso\t\t| Seguro\t\t| Género"

fun main(args: Array<String>) {
    var opcionCRUD : Int
    var endFlag : Boolean = false

    do{
        opcionCRUD = mostrarMenuGeneral()

        when (opcionCRUD){
            1 -> {
                var flag1 : Boolean = true
                while (flag1){
                    create()
                    flag1 = cambiarOpcionCRUD()
                }
            }
            2 -> {
                var flag2 : Boolean = true
                while (flag2){
                    read()
                    flag2 = cambiarOpcionCRUD()
                }
            }
            3 -> {
                var flag3 : Boolean = true
                while (flag3){
                    update()
                    flag3 = cambiarOpcionCRUD()
                }
            }
            4 -> {
                var flag4 : Boolean = true
                while (flag4){
                    delete()
                    flag4 = cambiarOpcionCRUD()
                }
            }
            5 -> {
                println("Saliendo del sistema CRUD...")
                System.exit(0)
            }
            else -> {1
                println("Ingrese una opción válida")
            }
        }
    } while (!endFlag)

}
fun mostrarMenuGeneral() : Int {
    println("\n\tOpciones CRUD")
    println("1. Create  (Crear)")
    println("2. Read    (Leer)")
    println("3. Update  (Modificar)")
    println("4. Delete  (Eliminar)")
    println("5. Salir   (Exit)")
    print("Seleccione una opción: ")
    return reader.readLine().toInt()
}

fun cambiarOpcionCRUD() : Boolean {
    print("\n\n¿Desea regresar al menú CRUD? (S/N): ")
    return reader.readLine().toLowerCase() == "n"
}

fun create(){
    // Médico
    var pacientes = arrayListOf <Paciente> ()

    var option: Boolean

    println("<--- Información del Médico --->")
    print("Ingrese el identificador (entero) del médico: ")
    var idMedico: Int = reader.readLine().toInt()
    print("Ingrese el nombre del médico: ")
    var nombreMedico: String = reader.readLine()
    print("Ingrese el salario del médico: ")
    var salario: Double = reader.readLine().toDouble()
    print("¿El médico es especialista? (S/N): ")
    var esEspecialista: Boolean = reader.readLine().toLowerCase() == "s"
    print("Ingrese el género del médico (M: masculino, F: Femenino, I: No definido): ")
    var generoMedico: Char = reader.readLine().get(0)

    print("¿Desea ingresar pacientes para el médico creado? (S/N): ")
    option = reader.readLine().toLowerCase() == "s"
    while(option){

        // Pacientes
        print("\tIngrese el identificador del paciente: ")
        var idPaciente: Int = reader.readLine().toInt()
        print("\tIngrese el nombre del paciente: ")
        var nombrePaciente: String = reader.readLine().toString()
        print("\tIngrese el peso del paciente (kg): ")
        var peso: Double = reader.readLine().toDouble()
        print("\t¿El paciente tiene seguro? (S/N): ")
        var tieneSeguro: Boolean = reader.readLine().toLowerCase() == "s"
        print("\tIngrese el género del paciente: ")
        var generoPaciente: Char = reader.readLine().get(0)

        pacientes.add(Paciente(idPaciente, nombrePaciente, peso, tieneSeguro, generoPaciente))

        print("\n\t¿Desea ingresar un nuevo paciente para este médico? (S/N): ")
        option= reader.readLine().toLowerCase() == "s"
    }

    helper.addMedico(Medico(idMedico, nombreMedico, salario, generoMedico, esEspecialista, pacientes))
}

fun read(){
    var JSONMedicos = helper.readMedicos()

    println("1. Litar todos los Médicos")
    println("2. Listar un solo Médico")
    print("Opción: ")
    var option: Int = reader.readLine().toInt()

    when (option) {
        1 -> {
            println(headerMedicos)
            for(medico in JSONMedicos){
                print(medico.toString())
                println()
            }
        }
        2 -> {
            var optionSelectMedico : Boolean = true
            while(optionSelectMedico) {
                for(medico in JSONMedicos){
                    println("${medico.id} - ${medico.nombre}")
                }
                print("Ingrese el identificador del Médico para listar su información: ")
                var idSearch : Int = reader.readLine().toInt()
                println(headerMedicos)
                if ( JSONMedicos.any { it.id == idSearch }) {
                    println(JSONMedicos.filter { it.id == idSearch }.toString())
                }
                println()
                print("¿Desea realiza otra consulta? (S/N): ")
                optionSelectMedico = reader.readLine().toLowerCase() == "s"
            }
        }
        else -> {
            println("Ingrese una opción válida")
        }
    }
}

fun atributeUpdater(atributo: String, esMedico: Boolean): Boolean{
    if (esMedico) {
        print("¿Actualizar $atributo del médico? (S/N): ")
    }
    else{
        print("¿Actualizar $atributo del paciente? (S/N): ")
    }
    return reader.readLine().toLowerCase() == "s"
}

fun update(){
    var JSONMedicos = helper.readMedicos()

    println("1. Actualizar Médico")
    println("2. Actualizar Paciente de un Médico")
    print("Opción: ")
    var option: Int = reader.readLine().toInt()

    when (option) {
        1 -> {
            var optionUpdateMedico: Boolean=true
            while(optionUpdateMedico) {

                for (medico in JSONMedicos) {
                    println("${medico.id}: ${medico.nombre}")
                }
                print("Ingrese el identificador del Médico a actualizar: ")
                var idSearch : Int = reader.readLine().toInt()
                if (JSONMedicos.any { it.id == idSearch }) {
                    var medicoTemp = JSONMedicos.filter { it.id == idSearch }.firstOrNull()

                    if (atributeUpdater("identificador", true)) {
                        print("Ingrese el nuevo identificador: ")
                        medicoTemp!!.id = reader.readLine().toInt()
                    }
                    if (atributeUpdater("nombre", true)) {
                        print("Ingrese el nuevo nombre: ")
                        medicoTemp!!.nombre = reader.readLine()
                    }
                    if (atributeUpdater("salario", true)) {
                        print("Ingrese el nuevo salario: ")
                        medicoTemp!!.salario = reader.readLine().toDouble()
                    }
                    if (atributeUpdater("estado de Especialista", true)) {
                        print("Ingrese el nuevo estado de espacialidad (S/N): ")
                        medicoTemp!!.esEspecialista = reader.readLine().toLowerCase() == "s"
                    }
                    if (atributeUpdater("género", true)) {
                        print("Ingrese el nuevo género (M: masculino, F: Femenino, I: No definido): ")
                        medicoTemp!!.genero = reader.readLine().get(0)
                    }

                    var oldMedico = JSONMedicos.filter { it.id == idSearch }.firstOrNull()
                    val indexReplace = JSONMedicos.indexOf(oldMedico)
                    if (indexReplace > 0){
                        JSONMedicos.set(indexReplace, medicoTemp!!)
                    }
                    helper.modifyFile(JSONMedicos)
                }
                else {
                    println("Ingrese un indentificador existente")
                }
                println()
                print("¿Desea Continuar? (S/N): ")
                optionUpdateMedico = reader.readLine().toLowerCase() == "s"
            }
        }
        2 -> {
            for (medico in JSONMedicos) {
                println("${medico.id}: ${medico.nombre}")
            }
            print("Ingrese el identificador del Médico con los pacientes a actualizar: ")
            var idSearch: Int = reader.readLine().toInt()

            if (JSONMedicos.any { it.id == idSearch }) {

                var medicoTemp = JSONMedicos.filter { it.id == idSearch }.firstOrNull()

                var optionUpdatePaciente : Boolean=true
                while(optionUpdatePaciente) {
                    for (paciente in medicoTemp!!.pacientes) {
                        println(paciente.toString())
                        println()
                    }

                    print("Ingrese el identificador del paciente a modificar: ")
                    var oldIDPactient: Int = reader.readLine().toInt()
                    if (oldIDPactient > 0) {
                        var pacienteTemp = medicoTemp.pacientes.filter { it.id == idSearch }.firstOrNull()

                        if (atributeUpdater("identificador", false)){
                            print("Ingrese el nuevo identificador: ")
                            pacienteTemp!!.id = reader.readLine().toInt()
                        }
                        if (atributeUpdater("nombre", false)){
                            print("Ingrese el nuevo nombre: ")
                            pacienteTemp!!.nombre = reader.readLine()
                        }
                        if (atributeUpdater("peso", false)){
                            print("Ingrese el nuevo peso: ")
                            pacienteTemp!!.peso = reader.readLine().toDouble()
                        }
                        if (atributeUpdater("estado del Seguro", false)){
                            print("Ingrese el nuevo estado del Seguro (S/N): ")
                            pacienteTemp!!.tieneSeguro = reader.readLine().toLowerCase() == "s"
                        }
                        if (atributeUpdater("género", false)){
                            print("Ingrese el nuevo género (M: masculino, F: Femenino, I: No definido): ")
                            pacienteTemp!!.genero = reader.readLine().get(0)
                        }
                        var oldPaciente = medicoTemp.pacientes.filter { it.id == oldIDPactient }.firstOrNull()
                        val indexReplace = medicoTemp.pacientes.indexOf(oldPaciente)
                        if (medicoTemp.pacientes.any { it.id == oldIDPactient }){
                            medicoTemp.pacientes.set(indexReplace, pacienteTemp!!)
                        }
                        var oldMedico = JSONMedicos.filter { it.id == idSearch }.firstOrNull()
                        val indexReplaceMedico = JSONMedicos.indexOf(oldMedico)
                        if (indexReplace > 0){
                            JSONMedicos.set(indexReplaceMedico, medicoTemp!!)
                        }
                        helper.modifyFile(JSONMedicos)
                    }
                    print("\n¿Desea Continuar? (S/N): ")
                    optionUpdatePaciente = reader.readLine().toLowerCase() == "s"
                }
            }
            else {
                println(println("Sleccione un paciente existente"))
            }
        }
        else -> {
            println("Ingrese una opción válida")
        }
    }
}

fun delete() {
    var JSONMedicos = helper.readMedicos()

    println("1. Eliminar Médico")
    println("2. Eliminar Paciente de un Médico")
    println("3. Eliminar todos los registros")
    print("Selecione una opción: ")
    var option : Int = reader.readLine().toInt()

    when (option) {
        1 -> {
            var opcionEraseMedico : Boolean = true
            for (medico in JSONMedicos) {
                println("${medico.id}: ${medico.nombre}")
            }
            print("Ingrese el identificador del Médico a eliminar: ")
            var idMedErase : Int = reader.readLine().toInt()
            if (idMedErase > 0) {
                var oldMedico = JSONMedicos.filter { it.id == idMedErase }.firstOrNull()
                val indexMedErase = JSONMedicos.indexOf(oldMedico)
                if (indexMedErase > 0){
                    JSONMedicos.removeAt(indexMedErase)
                    helper.modifyFile(JSONMedicos)
                }
            }
            print("\n¿Desea Continuar? (S/N): ")
            opcionEraseMedico  = reader.readLine().toLowerCase() == "s"
        }
        2 -> {
            for (medico in JSONMedicos) {
                println("${medico.id}: ${medico.nombre}")
            }
            print("Ingrese el identificador del Médico con los pacientes a eliminar: ")
            var idMedErase = reader.readLine().toInt()
            if (JSONMedicos.any { it.id == idMedErase}){
                var medicoTemp = JSONMedicos.filter { it.id == idMedErase }.firstOrNull()
                val indexMedEdited = JSONMedicos.indexOf(medicoTemp)

                var opcionErasePaciente : Boolean = true
                while (opcionErasePaciente) {
                    for (paciente in medicoTemp!!.pacientes) {
                        println(paciente.toString())
                    }
                    println("1. Eliminar un paciente en específico por su identificador")
                    println("2. Eliminar todos los pacientes de este médico")
                    print("Selecione una opción: ")
                    var optionErasePacByID : Int = reader.readLine().toInt()
                    when (optionErasePacByID) {
                        1 -> {
                            print("Ingrese el identificador del paciente a eliminar: ")
                            var idPacErase : Int = reader.readLine().toInt()
                            if (medicoTemp.pacientes.any { it.id == idPacErase }) {
                                var erasedPaciente = medicoTemp.pacientes.filter { it.id == idPacErase }.firstOrNull()
                                val indexErasePaciente = medicoTemp.pacientes.indexOf(erasedPaciente)
                                medicoTemp.pacientes.removeAt(indexErasePaciente)
                                JSONMedicos.set(indexMedEdited, medicoTemp!!)
                                helper.modifyFile(JSONMedicos)
                            }
                        }
                        2 -> {
                            print("¿Seguro desea eliminar todos los pacientes de este médico? (S/N): ")
                            if (reader.readLine().toLowerCase() == "s"){
                                medicoTemp.pacientes.clear()
                                JSONMedicos.set(indexMedEdited, medicoTemp)
                                println("Se han eliminado todos los pacientes de este médico")
                                helper.modifyFile(JSONMedicos)
                            }
                        }
                        3 -> {
                            println("Ingrese una opción válida")
                        }
                    }
                    print("\n¿Desea Continuar? (S/N): ")
                    opcionErasePaciente  = reader.readLine().toLowerCase() == "s"
                }
            }
            else {
                println("Ingrese un indentificador existente")
            }

        }
        3 -> {
            print("¿Seguro desea eliminar todos los registros en el Consultorio? (S/N): ")
            if (reader.readLine().toLowerCase() == "s"){
                JSONMedicos.clear()
                println("Se han eliminado todos los registros del Consultorio")
                helper.modifyFile(JSONMedicos)
            }
        }
        else -> {
            println("Ingrese una opción válida")
        }
    }
}
