package com.example.deber01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView

class CRUDMedico : AppCompatActivity() {
    var idMedicoSeleccionado = 0
    //var medicos: ArrayList<Medico> = arrayListOf<Medico>()
    val medicos = ArrayList<Medico>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val medico1 = Medico(1,"Juan Pérez", 200.20, true, R.drawable.m1)
        val medico2 = Medico(2,"Norma Cruz", 100.0, false, R.drawable.m2)

        medicos.add(medico1)
        medicos.add(medico2)
        //var medicos: ArrayList<Medico> = arrayListOf<Medico>()

        //val recyclerView = findViewById<RecyclerView>(R.id.rv_medicos)
        //inicializarRecyclerViewMedico(medicos,recyclerView)

        val listViewMedico = findViewById<ListView>(R.id.lv_medicos)
        val adaptadorMedico = MedicoAdapter(
            this,
            medicos
        )
        listViewMedico.adapter = adaptadorMedico
        adaptadorMedico.notifyDataSetChanged()

        // Base de datos SQLite
        //BaseDeDatos.tablaMedico = ESqliteHelper(this)
        //medicos = BaseDeDatos.tablaMedico!!.consultarMedicos()

        val botonCrearMedico = findViewById<Button>(R.id.btn_crearMedico)
        botonCrearMedico
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombreMedico)
                val salario = findViewById<EditText>(R.id.input_salarioMedico)
                BaseDeDatos.tablaMedico!!.crearMedico(
                    nombre.text.toString(),
                    salario.text.toString().toDouble(),
                    true
                )
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
            R.id.mm_editar -> {
                val intent = Intent(this, MedicoActivity::class.java)
                intent.putExtra("medico", medicos[idMedicoSeleccionado])
                startActivity(intent)
                return true
            }
            R.id.mm_eliminar -> {
                BaseDeDatos.tablaMedico!!.eliminarMedicoFormulario(idMedicoSeleccionado)
                return true
            }
            R.id.mm_verpacientes -> {
                "${idMedicoSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun inicializarRecyclerViewMedico(
        lista:ArrayList<Medico>,
        recyclerView: RecyclerView
    ){
        val adaptador = MedicoRAdapter(
            lista,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
}