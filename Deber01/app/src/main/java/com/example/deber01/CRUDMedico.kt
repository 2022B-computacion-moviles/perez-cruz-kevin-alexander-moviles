package com.example.deber01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

class CRUDMedico : AppCompatActivity() {
    var idMedicoSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var medicos = emptyList<Medico>()

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

                return true
            }
            R.id.mm_eliminarMedico -> {
                return true
            }
            R.id.mm_verpacientes -> {

                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}