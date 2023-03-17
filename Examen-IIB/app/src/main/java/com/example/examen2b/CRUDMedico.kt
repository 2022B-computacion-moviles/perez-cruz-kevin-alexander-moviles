package com.example.examen2b

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2b.dao.ConsultorioDao
import com.example.examen2b.entidades.Medico
import com.example.examen2b.inicialData.MedicoInicialData
import com.example.examen2b.inicialData.PacienteInicialData

class CRUDMedico : AppCompatActivity() {
    private var selectedMedicoId: Int? = null
    var medicosList = ArrayList<Medico>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val medicosRv = findViewById<RecyclerView>(R.id.rv_medicos)
        val btnCrear = findViewById<Button>(R.id.btn_crearMedico)

        ConsultorioDao.consultorio.getMedicoDao().getAllMedicos(
            onSuccess = { medicos ->
                medicosList = medicos
                if(medicosList.isEmpty()){
                    initData()
                    openActivity(CRUDMedico::class.java)

                }
                initializeRecyclerView(medicosList, medicosRv)
                registerForContextMenu(medicosRv)
            }
        )
        btnCrear.setOnClickListener {
            openActivity(CreateMedico::class.java)
        }
    }

    private fun initData() {
        for (medico in MedicoInicialData.medicosInitData) {
            ConsultorioDao.consultorio.getMedicoDao().create(medico)
        }

        for (paciente in PacienteInicialData.pacientesInitData) {
            ConsultorioDao.consultorio.getPacienteDao().create(paciente)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mm_editarMedico -> {
                val intent = Intent(this, MedicoActivity::class.java)
                intent.putExtra("selectedMedicoId", selectedMedicoId)
                startActivity(intent)
                return true
            }
            R.id.mm_eliminarMedico -> {
                openDeleteDialog()
                return true
            }
            R.id.mm_verpacientes -> {
                val intent = Intent(this, CRUDPaciente::class.java)
                intent.putExtra("selectedMedicoId", selectedMedicoId)
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun setSelectedMedicoId(medicoId: Int) {
        selectedMedicoId = medicoId
    }

    private fun openDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar")
        builder.setMessage("Esta seguro de querer eliminar este registro de Médico?")

        builder.setPositiveButton("Si") { _, _ ->
            ConsultorioDao.consultorio.getMedicoDao().delete(
                selectedMedicoId!!,
                onSuccess = {
                    ConsultorioDao.consultorio.getMedicoDao().getAllMedicos(
                        onSuccess = { medicos ->
                            initializeRecyclerView(medicos, findViewById(R.id.rv_medicos))
                        }
                    )
                }
            )
            Toast.makeText(this, "Médico Eliminado", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeRecyclerView(
        list: ArrayList<Medico>,
        recyclerView: RecyclerView
    ) {
        val adapter = MedicoAdapter(this, list)

        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }

    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}