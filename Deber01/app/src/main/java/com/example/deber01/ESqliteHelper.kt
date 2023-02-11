package com.example.deber01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelper(
    contexto: Context
): SQLiteOpenHelper(
    contexto,
    "consultorio",
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {

        val scripSQLCrearTablaMedico =
            """
                CREATE TABLE MEDICO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                salario DOUBLE,
                esEspecialista BOOLEAN 
                )
            """.trimIndent()
        val scripSQLCrearTablaPaciente =
            """
                CREATE TABLE PACIENTE(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                peso DOUBLE,
                tieneSeguro BOOLEAN 
                )
            """.trimIndent()
        val crearPrimerMedico =
            """
                INSERT INTO MEDICO (nombre, salario, esEspecialista) VALUES('Adrian', 2000, "TRUE")
            """.trimIndent()

        db?.execSQL(scripSQLCrearTablaMedico)
        db?.execSQL(scripSQLCrearTablaPaciente)
        db?.execSQL(crearPrimerMedico)

        val baseDatosLectura = readableDatabase
        var resultadoConsultaLectura = baseDatosLectura.rawQuery(
            "SELECT * FROM MEDICO",
            null
        )
        while (resultadoConsultaLectura.moveToNext()) {
            val column1= resultadoConsultaLectura.getInt(0)
            val column2 = resultadoConsultaLectura.getString(1)
            val column3 = resultadoConsultaLectura.getDouble(2) // Columna indice 2 -> SALARIO
            val column4 = resultadoConsultaLectura.getInt(3) // Columna indice 3 -> EsEspecialista
            println("Result ${column1} ${column2} ${column3} ${column4}")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun crearMedico(
        nombre: String,
        salario: Double,
        esEspecialista: Boolean
    ): Boolean{
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("salario", salario)
        valoresAGuardar.put("esEspecialista", esEspecialista)

        val resultadoGuardar = baseDatosEscritura
            .insert(
                "MEDICO",
                null,
                valoresAGuardar
            )
        baseDatosEscritura.close()
        println("Agregado")
        return if(resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarMedicoFormulario(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "MEDICO", // Tabla
                "id=?", // id=? and nombre=? Where (podemos mandar parametros en orden)
                arrayOf( // Arreglo de parametros en orden [1, "Adrian"]
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarMedicoFormulario(
        idActualizar: Int,
        nombre: String,
        salario: Double,
        esEspecialista: Boolean
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("salario", salario)
        valoresAActualizar.put("esEspecialista", esEspecialista)
        val resultadoActualizacion = conexionEscritura
            .update(
                "MEDICO", // Nombre de la tabla
                valoresAActualizar, // Valores a actualizar
                "id=?", // Clausula where
                arrayOf(
                    idActualizar.toString()
                )
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarEntrenadorPorId(id: Int): Medico{
        val baseDatosLectura = readableDatabase
        val scriptConsultaUsuario = "SELECT * FROM MEDICO WHERE ID = ?"
        var resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaUsuario,
            arrayOf(
                id.toString()
            )
        )
        // Lógica obtener el usuario
        val existeMedico = resultadoConsultaLectura.moveToFirst()
        var medicoEncontrado = Medico(0, "", 0.0, false, 0)
        val arreglo = arrayListOf<Medico>()
        if (existeMedico) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val salario = resultadoConsultaLectura.getDouble(2) // Columna indice 2 -> SALARIO
                val esEspecialista = resultadoConsultaLectura.getInt(3) // Columna indice 3 -> EsEspecialista
                if (id != null) {
                    medicoEncontrado = Medico(0, "", 0.0, false,0)
                    medicoEncontrado.id = id
                    medicoEncontrado.nombre = nombre
                    medicoEncontrado.salario = salario
                    medicoEncontrado.esEspecialista = esEspecialista != 0

                    arreglo.add(medicoEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return medicoEncontrado
    }
    fun consultarMedicos(): ArrayList<Medico>{
        val baseDatosLectura = readableDatabase
        val scriptConsultaUsuario = "SELECT * FROM MEDICO"
        var resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaUsuario,
            null
        )
        // Lógica obtener el usuario
        val existeMedico = resultadoConsultaLectura.moveToFirst()
        var medico = Medico(0, "", 0.0, false,0)
        val medicos = arrayListOf<Medico>()
        do {
            val id = resultadoConsultaLectura.getInt(0) // columna indice 0 -> ID
            val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
            val salario = resultadoConsultaLectura.getDouble(2) // Columna indice 2 -> SALARIO
            val esEspecialista = resultadoConsultaLectura.getInt(3) // Columna indice 3 -> EsEspecialista
            val foto = resultadoConsultaLectura.getInt(4) // Columna indice 3 -> EsEspecialista
            if (id != null) {
                medico.id = id
                medico.nombre = nombre
                medico.salario = salario
                medico.esEspecialista = esEspecialista != 0
                medico.image = foto
                medicos.add(medico)
            }
        } while (resultadoConsultaLectura.moveToNext())
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return medicos
    }
}