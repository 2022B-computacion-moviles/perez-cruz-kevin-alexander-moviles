package com.example.deber01

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CRUDPaciente : AppCompatActivity() {

    var idPacienteSeleccionado = 0
    var pacientes = emptyList<Paciente>()
    private val EDIT_ACTIVITY = 48

    var idMedico: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudpaciente)
        //
        idMedico = intent.getIntExtra("idMedico", 0)
        val nombreMedico = intent.getStringExtra("nombreMedico")
        //
        val database = AppDatabase.getDatabase(this)
        val listViewPaciente = findViewById<ListView>(R.id.lv_pacientes)

        var tvNombreDoctor = findViewById<TextView>(R.id.tv_MedicoPaciente)
        tvNombreDoctor.setText("${nombreMedico} | ${idMedico}")

        database.pacientes().getPacientesPorIdMedico(idMedico!!).observe(this, Observer {
            pacientes = it
            println(pacientes.toString())
            val adaptadorPaciente = PacienteAdapter(
                this,
                pacientes
            )
            listViewPaciente.adapter = adaptadorPaciente
            adaptadorPaciente.notifyDataSetChanged()
        })

        listViewPaciente.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, PacienteActivity::class.java)
            intent.putExtra("id", pacientes[i].idPaciente)
            intent.putExtra("idMedico", idMedico)
            startActivity(intent)
        }

        val botonCrearPaciente = findViewById<Button>(R.id.btn_crearPaciente)
        botonCrearPaciente
            .setOnClickListener {
                val intent = Intent(this, CreatePaciente::class.java)
                intent.putExtra("idMedico", idMedico)
                startActivity(intent)
            }
        registerForContextMenu(listViewPaciente)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menupaciente, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idPacienteSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mm_editarPaciente -> {
                val intent = Intent(this, CreatePaciente::class.java)
                intent.putExtra("paciente", pacientes[idPacienteSeleccionado])
                intent.putExtra("idMedico", idMedico)
                startActivityForResult(intent, EDIT_ACTIVITY)
                return true
            }
            R.id.mm_eliminarPaciente -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Eliminar Paciente")
                    setMessage("¿Estás seguro de querer eliminar este registro? Esta acción es irreversible")
                    setPositiveButton("Si") { _: DialogInterface, _: Int ->
                        val database = AppDatabase.getDatabase(this@CRUDPaciente)
                        CoroutineScope(Dispatchers.IO).launch {
                            database.pacientes().delete(pacientes[idPacienteSeleccionado])
                            ImageController.deleteImage(this@CRUDPaciente, pacientes[idPacienteSeleccionado].idPaciente.toLong())
                        }
                    }
                    setNegativeButton("No", null)
                }.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }
}