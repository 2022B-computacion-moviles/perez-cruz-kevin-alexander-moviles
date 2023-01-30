package com.example.movcompkapc

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador (
    /*: Context?,
): SQLiteOpenHelper(
    contexto,
    "moviles", // Nombre de nuestra BDD Sqlite (moviles.sqlite)
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scripSQLCrearTablaEntrenador = """
            CREATE TABLE ENTRENADOR(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            descripcion VARCHAR(50)
            )
        """.trimIndent()
        db?.execSQL(scripSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean {
        // this.readableDatabse Lectura
        // this.writableDatabse Escritura
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", // Tabla
                null, //
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarEntrenadorFormulario(id: Int): Boolean{
        // val conexionEscritura = this.writableDatabse
        val conexionEscritura = writableDatabase
        // "SELECT * FROM ENTRENADOR WHERE ID = ?"
        // arrayOf(
        //      id.toString()
        // )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR", // Tabla
                "id=?", // id=? and nombre=? Where (podemos mandar parametros en orden)
                arrayOf( // Arreglo de parametros en orden [1, "Adrian"]
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", // Nombre de la tabla
                valoresAActualizar, // Valore a actualizar
                "id=?", // Clausula where
                arrayOf(
                    idActualizar.toString()
                )
            )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }

    fun consultarEntrenadorPorId(id: Int): BEntrenador{
        // val baseDatosLectura = this.readableDatabase
        val baseDatosLectura = readableDatabase
        val scriptConsultaUsuario = "SELECT * FROM ENTRENADOR WHERE ID = ?"
        var resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaUsuario,
            arrayOf(
                id.toString()
            )
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador(0, "", "")

        // Lógica obtener usuario
        do {
            val id = resultadoConsultaLectura.getInt(0) // columna indice 0 -> ID
            val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
            val descripcion = resultadoConsultaLectura.getString(2) // Columna indice 2 -> DESCRIPCION
            if (id != null){
                usuarioEncontrado.id = id
                usuarioEncontrado.nombre = nombre
                usuarioEncontrado.descripción = descripcion
            }
        } while (resultadoConsultaLectura.moveToNext())

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }*/
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "moviles", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
               CREATE TABLE ENTRENADOR(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               descripcion VARCHAR(50)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }
    fun eliminarEntrenadorFormulario(id: Int): Boolean {
        //        val conexionEscritura = this.writableDatabase
        val conexionEscritura = writableDatabase
        // "SELECT * FROM ENTRENADOR WHERE ID = ?"
        // arrayOf(
        //    id.toString()
        // )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR", // Nombre tabla
                "id=?", // Consulta Where
                arrayOf(
                    id.toString()
                ) // Parametros
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id=?", // Clausula Where
                arrayOf(
                    idActualizar.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun consultarEntrenadorPorId(id: Int): BEntrenador {
        // val baseDatosLectura = this.readableDatabase
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM ENTRENADOR WHERE ID = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario, // Consulta
            arrayOf(
                id.toString()
            ) // Parametros consulta
        )
        // Logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        var usuarioEncontrado = BEntrenador(0, "", "")
        val arrreglo = arrayListOf<BEntrenador>()
        if (existeUsuario) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val descripcion =
                    resultadoConsultaLectura.getString(2) // Columna indice 2 -> DESCRIPCION
                if (id != null) {
                    usuarioEncontrado = BEntrenador(0, "", "")
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                    arrreglo.add(usuarioEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
}
