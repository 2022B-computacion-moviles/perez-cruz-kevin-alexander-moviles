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
    //var medicos: ArrayList<Medico> = arrayListOf<Medico>()
    var medicos = emptyList<Medico>()
    private lateinit var database: AppDatabase
    private lateinit var medico: Medico
    private lateinit var medicoLiveData: LiveData<Medico>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getDatabase(this)
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
        /*if(!medicos.isEmpty()){
            medicoLiveData = database.medicos().get(idMedicoSeleccionado)

            medicoLiveData.observe(this, Observer {
                medico = it
                val idMedico = findViewById<TextView>(R.id.tv_medicoID)
                idMedico.text = "${idMedicoSeleccionado} ${medico?.id}"
                val nombreMedico = findViewById<TextView>(R.id.tv_medicoNombre)
                nombreMedico.text = medico?.nombre
                val salarioMedico = findViewById<TextView>(R.id.tv_medicoSalario)
                salarioMedico.text = "$${medico?.salario}"
                val esEspecialistaMedico = findViewById<TextView>(R.id.tv_medicoEspecialista)
                esEspecialistaMedico.text = "${medico?.esEspecialista}"
                val fotoMedico = findViewById<ImageView>(R.id.iv_medicoImagen)
                fotoMedico.setImageResource(medico!!.image)
            })
        }*/
        /*val medico1 = Medico("Juan Pérez", 200.20, true, R.drawable.m1)
        val medico2 = Medico("Norma Cruz", 100.0, false, R.drawable.m2)

        medicos.add(medico1)
        medicos.add(medico2)*/
        //var medicos: ArrayList<Medico> = arrayListOf<Medico>()

        //val recyclerView = findViewById<RecyclerView>(R.id.rv_medicos)
        //inicializarRecyclerViewMedico(medicos,recyclerView)


        // Base de datos SQLite
        //BaseDeDatos.tablaMedico = ESqliteHelper(this)
        //medicos = BaseDeDatos.tablaMedico!!.consultarMedicos()

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

                medicoLiveData = database.medicos().get(idMedicoSeleccionado)
                println(idMedicoSeleccionado)

                /*medicoLiveData.observe(this, Observer {
                    medico = it
                    val idMedico = findViewById<TextView>(R.id.tv_medicoID)
                    idMedico.text = "${idMedicoSeleccionado} ${medico?.id}"
                    val nombreMedico = findViewById<TextView>(R.id.tv_medicoNombre)
                    nombreMedico.text = medico?.nombre
                    val salarioMedico = findViewById<TextView>(R.id.tv_medicoSalario)
                    salarioMedico.text = "$${medico?.salario}"
                    val esEspecialistaMedico = findViewById<TextView>(R.id.tv_medicoEspecialista)
                    esEspecialistaMedico.text = "${medico?.esEspecialista}"
                    val fotoMedico = findViewById<ImageView>(R.id.iv_medicoImagen)
                    fotoMedico.setImageResource(medico!!.image)
                })

                val intent = Intent(this, CreateMedico::class.java)
                intent.putExtra("medico", medico)
                startActivity(intent)*/
                return true
            }
            R.id.mm_eliminarMedico -> {
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