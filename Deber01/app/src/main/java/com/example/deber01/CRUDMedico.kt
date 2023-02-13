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

class CRUDMedico : AppCompatActivity() {
    var idMedicoSeleccionado = 0
    var medicos = emptyList<Medico>()
    private val EDIT_ACTIVITY = 49
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var medicos = emptyList<Medico>()

        val database = AppDatabase.getDatabase(this)
        val listViewMedico = findViewById<ListView>(R.id.lv_medicos)

        database.medicos().getAll().observe(this, Observer {
            medicos = it

            val adaptadorMedico = MedicoAdapter(
                this,
                medicos
            )
            listViewMedico.adapter = adaptadorMedico
            adaptadorMedico.notifyDataSetChanged()
        })

        listViewMedico.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, MedicoActivity::class.java)
            intent.putExtra("id", medicos[i].id)
            startActivity(intent)
        }

        val botonCrearMedico = findViewById<Button>(R.id.btn_crearMedico)
        botonCrearMedico
            .setOnClickListener {
                val intent = Intent(this, CreateMedico::class.java)
                startActivity(intent)
            }

        registerForContextMenu(listViewMedico)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menumedico, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idMedicoSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mm_editarMedico -> {
                val intent = Intent(this, CreateMedico::class.java)
                intent.putExtra("medico", medicos[idMedicoSeleccionado])
                startActivityForResult(intent, EDIT_ACTIVITY)
                return true
            }
            R.id.mm_eliminarMedico -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Eliminar Médico")
                    setMessage("¿Estás seguro de querer eliminar este registro? Esta acción es irreversible")
                    setPositiveButton("Si") { _: DialogInterface, _: Int ->
                        val database = AppDatabase.getDatabase(this@CRUDMedico)
                        CoroutineScope(Dispatchers.IO).launch {
                            database.medicos().delete(medicos[idMedicoSeleccionado])
                            ImageController.deleteImage(this@CRUDMedico, medicos[idMedicoSeleccionado].id.toLong())
                        }
                    }
                    setNegativeButton("No", null)
                }.show()
                return true
            }
            R.id.mm_verpacientes -> {
                val intent = Intent(this, CRUDPaciente::class.java)
                intent.putExtra("idMedico", medicos[idMedicoSeleccionado].id)
                intent.putExtra("nombreMedico", medicos[idMedicoSeleccionado].nombre)
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}